import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.model.ErrorOutPut;
import podChat.requestobject.AddParticipantsRequestModel;
import podChat.requestobject.ConnectRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AddParticipant implements ChatContract.view {
    @Mock
    static ChatContract.view chatContract;
    @InjectMocks
    static ChatController chatController = Mockito.mock(ChatController.class);

    Gson gson = new Gson();

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    public void connect() throws InterruptedException {
        try {
            chatController = new ChatController(chatContract);

            ConnectRequest connectRequest = new ConnectRequest
                    .Builder(new ArrayList<String>() {{
                add(Constant.uri);
            }},
                    Constant.queueInput,
                    Constant.queueOutput,
                    Constant.queueUserName,
                    Constant.queuePassword,
                    Constant.serverName,
                    Constant.token,
                    Constant.ssoHost,
                    Constant.platformHost,
                    Constant.fileServer,
                    Constant.chatId)
                    .typeCode("default")
                    .build();

            chatController.connect(connectRequest);

            Thread.sleep(2000);

        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void addParticipant() throws InterruptedException {

        AddParticipantsRequestModel addParticipants = new AddParticipantsRequestModel
                .Builder(5781, new ArrayList<Long>() {{
            add(15141l);
        }})
                .build();

        chatController.addParticipants(addParticipants);

        Thread.sleep(5000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);
        Mockito.verify(chatContract, Mockito.times(1)).onAddParticipant(argument.capture());

        List<ChatResponse> sentMessage = argument.getAllValues();

        Assertions.assertTrue(!sentMessage.isEmpty());


    }

    @Test
    @Order(2)
    void addParticipantWithCoreUserId() throws InterruptedException {

        AddParticipantsRequestModel addParticipants = AddParticipantsRequestModel
                .newBuilder()
                .threadId(7308L)
                .withCoreUserIds(1507L,1556L)
                .build();

        chatController.addParticipants(addParticipants);

        Thread.sleep(5000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);
        Mockito.verify(chatContract, Mockito.times(1)).onAddParticipant(argument.capture());

        List<ChatResponse> sentMessage = argument.getAllValues();

        Assertions.assertTrue(!sentMessage.isEmpty());


    }

    // The thread id does not exist
    @Test
    @Order(2)
    void addParticipantWithError() throws InterruptedException {
        AddParticipantsRequestModel addParticipants = new AddParticipantsRequestModel
                .Builder(57811, new ArrayList<Long>() {{
            add(15141l);
        }})
                .build();

        chatController.addParticipants(addParticipants);

        Thread.sleep(3000);

        ArgumentCaptor<ErrorOutPut> argument = ArgumentCaptor.forClass(ErrorOutPut.class);

        Mockito.verify(chatContract, Mockito.times(1)).onError(argument.capture());


        ErrorOutPut errorOutPut = argument.getValue();

        Assertions.assertTrue(errorOutPut.isHasError());

    }


}
