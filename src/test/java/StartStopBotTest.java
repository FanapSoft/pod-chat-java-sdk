import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.StartStopBotRequest;

import java.util.ArrayList;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StartStopBotTest implements ChatContract.view {
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

            ConnectRequest requestConnect = new ConnectRequest
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
    void stopBot() throws InterruptedException {
        StartStopBotRequest requestStartAndStopBot = new StartStopBotRequest
                .Builder(7459L, "SDK4BOT")
                .build();
        chatController.stopBot(requestStartAndStopBot);
        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onStopBot(argument.capture());

        ChatResponse chatResponse = argument.getValue();
        Assertions.assertTrue(!chatResponse.hasError());
    }


    @Test
    @Order(3)
    void startBot() throws InterruptedException {
        StartStopBotRequest requestStartAndStopBot = new StartStopBotRequest
                .Builder(7459L, "SDK4BOT")
                .build();
        chatController.startBot(requestStartAndStopBot);
        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onStartBot(argument.capture());

        ChatResponse chatResponse = argument.getValue();
        Assertions.assertTrue(!chatResponse.hasError());
    }

}