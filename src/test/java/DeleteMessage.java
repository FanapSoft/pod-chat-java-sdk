import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.model.ErrorOutPut;
import podChat.requestobject.DeleteMessageRequest;
import podChat.requestobject.ConnectRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class DeleteMessage implements ChatContract.view {
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

    //The message may have already been deleted

    @Test
    @Order(2)
    void deleteMessage() throws InterruptedException {

        DeleteMessageRequest deleteMultipleMessagesRequest = new DeleteMessageRequest
                .Builder(47581l)
                .deleteForAll(true)
                .build();

        chatController.deleteMessage(deleteMultipleMessagesRequest);

        Thread.sleep(3000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract, Mockito.atLeast(0)).onDeleteMessage(argument.capture());
        ArgumentCaptor<ChatResponse> argument1 = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract, Mockito.atLeast(0)).onThreadInfoUpdated(argument1.capture());

        ArgumentCaptor<ErrorOutPut> argument2 = ArgumentCaptor.forClass(ErrorOutPut.class);

        Mockito.verify(chatContract, Mockito.atLeast(0)).onError(argument2.capture());

        List<ChatResponse> chatResponse = argument.getAllValues();
        List<ErrorOutPut> errorOutPut = argument2.getAllValues();

        Assertions.assertTrue(!chatResponse.isEmpty() || !errorOutPut.isEmpty());

    }

    //The messageId does not exist
    @Test
    @Order(2)
    void deleteMessageError() throws InterruptedException {

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest
                .Builder(469811L)
                .deleteForAll(true)
                .build();

        chatController.deleteMessage(deleteMessageRequest);

        Thread.sleep(3000);

        ArgumentCaptor<ErrorOutPut> argument = ArgumentCaptor.forClass(ErrorOutPut.class);

        Mockito.verify(chatContract, Mockito.times(1)).onError(argument.capture());

        Assertions.assertTrue(argument.getValue().isHasError());

    }

}
