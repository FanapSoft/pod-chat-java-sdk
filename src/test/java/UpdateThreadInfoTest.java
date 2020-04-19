import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.requestobject.RequestConnect;
import podChat.requestobject.RequestThreadInfo;

import java.util.ArrayList;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UpdateThreadInfoTest implements ChatContract.view {
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
    void updateThreadInfo() throws InterruptedException {
        RequestThreadInfo requestThreadInfo = new RequestThreadInfo
                .Builder()
                .description("توضیح")
                .threadId(7149)
                .title("گروه")
                .metadat("test")
                .image("img")
//                .uniqueIds(new String[]{"a98d00af-6cb7-4174-a82a-a8ec68af0bb1"})
                .build();

        chatController.updateThreadInfo(requestThreadInfo);


        Thread.sleep(3000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onThreadInfoUpdated(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }
}
