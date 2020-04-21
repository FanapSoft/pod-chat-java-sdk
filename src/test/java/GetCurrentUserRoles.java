import Constant.Constant;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.GetCurrentUserRolesRequest;

import java.util.ArrayList;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class GetCurrentUserRoles implements ChatContract.view {
    @Mock
    static ChatContract.view chatContract;
    @InjectMocks
    static ChatController chatController = Mockito.mock(ChatController.class);

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
    void getCurrentUserRoles() throws InterruptedException {

        GetCurrentUserRolesRequest getCurrentUserRolesRequest = new GetCurrentUserRolesRequest
                .Builder(111)
                .build();

        chatController.getCurrentUserRoles(getCurrentUserRolesRequest);

        Thread.sleep(5000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onGetCurrentUserRoles(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }

}
