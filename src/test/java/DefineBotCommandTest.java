import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.mainmodel.Invitee;
import podChat.model.ChatResponse;
import podChat.requestobject.BotInfoVO;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.CreateThreadRequest;
import podChat.util.InviteType;
import podChat.util.ThreadType;

import java.util.ArrayList;
import java.util.List;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class DefineBotCommandTest  implements ChatContract.view {
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
    void defineBotCommand() throws InterruptedException {

        List<String> commands = new ArrayList<>();
        commands.add("/ss");
        BotInfoVO botInfoVO = new BotInfoVO
                .Builder("SDKBOT", commands)
                .build();
        chatController.defineBotCommand(botInfoVO);

        java.lang.Thread.sleep(3000);


        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract, Mockito.times(1)).onDefineBotCommand(argument.capture());

        Assertions.assertTrue(!argument.getValue().hasError());


    }
}