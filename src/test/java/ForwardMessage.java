import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.model.ErrorOutPut;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.ForwardMessageRequest;

import java.util.ArrayList;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ForwardMessage implements ChatContract.view {
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
    void forwardMessage() throws InterruptedException {
        ForwardMessageRequest forwardMessage = new ForwardMessageRequest
                .Builder(7129, new ArrayList<Long>() {{
            add(94291L);
//            add(47402l);
        }})
                .build();

        chatController.forwardMessage(forwardMessage);

        Thread.sleep(5000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);
        ArgumentCaptor<ChatResponse> argument1 = ArgumentCaptor.forClass(ChatResponse.class);
        Mockito.verify(chatContract, Mockito.times(2)).onThreadInfoUpdated(argument1.capture());

        Mockito.verify(chatContract, Mockito.times(2)).onSentMessage(argument.capture());
        Mockito.verify(chatContract, Mockito.times(2)).onNewMessage(argument.capture());

    }


    @Test
    @Order(3)
    void ForwardMessageWithNotExistMessageId() throws InterruptedException {
        ForwardMessageRequest forwardMessage = new ForwardMessageRequest
                .Builder(5461, new ArrayList<Long>() {{
            add(474031l);
        }})
                .build();

        chatController.forwardMessage(forwardMessage);

        Thread.sleep(3000);

        ArgumentCaptor<ErrorOutPut> argument = ArgumentCaptor.forClass(ErrorOutPut.class);

        Mockito.verify(chatContract, Mockito.times(1)).onError(argument.capture());

        ErrorOutPut errorOutPut = argument.getValue();

        Assertions.assertTrue(errorOutPut.isHasError());

    }

}
