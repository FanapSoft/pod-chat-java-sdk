import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.requestobject.RequestConnect;
import podChat.requestobject.GetAllUnreadMessageCountRequest;

import java.util.ArrayList;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountUnreadMessageTest implements ChatContract.view {
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

            RequestConnect requestConnect = new RequestConnect
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

            chatController.connect(requestConnect);

            Thread.sleep(2000);

        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void countUnreadMessage() throws InterruptedException {
        GetAllUnreadMessageCountRequest getAllUnreadMessageCountRequest = new GetAllUnreadMessageCountRequest
                .Builder()
                .mute(true)
                .build();

        chatController.countUnreadMessage(getAllUnreadMessageCountRequest);
        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onCountUnreadMessage(argument.capture());

        ChatResponse chatResponse = argument.getValue();
        Assertions.assertTrue(!chatResponse.hasError());
    }

}
