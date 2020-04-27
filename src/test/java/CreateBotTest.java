import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.CreateBotRequest;

import java.util.ArrayList;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateBotTest implements ChatContract.view {
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
    void createBot() throws InterruptedException {
        CreateBotRequest createBotRequest = new CreateBotRequest
                .Builder("SDK6BOT")
                .build();
        chatController.createBot(createBotRequest);
        Thread.sleep(10000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onCreateBot(argument.capture());

        ChatResponse chatResponse = argument.getValue();
        Assertions.assertTrue(!chatResponse.hasError());
    }
}