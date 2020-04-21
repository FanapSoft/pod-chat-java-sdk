import Constant.Constant;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.model.ErrorOutPut;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.SendTextMessageRequest;
import podChat.util.TextMessageType;

import java.util.ArrayList;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class SendMessage implements ChatContract.view {
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

//    @Test
//    @Order(2)
//    void sendMessage() throws InterruptedException {
//
//        RequestMessage requestThread = new RequestMessage
//                .Builder("this is final test", 5461L, TextMessageType.TEXT)
//                .build();
//
//        chatController.sendTextMessage(requestThread);
//
//        Thread.sleep(5000);
//
//        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);
//
//        Mockito.verify(chatContract, Mockito.times(1)).onSentMessage(argument.capture());
//        Mockito.verify(chatContract, Mockito.times(1)).onNewMessage(argument.capture());
//
//
//    }

    @Test
    @Order(2)
    void sendMessageError() throws InterruptedException {

        SendTextMessageRequest requestThread = new SendTextMessageRequest
                .Builder("this is final test", 5462, TextMessageType.TEXT)
                .build();

        chatController.sendTextMessage(requestThread);

        Thread.sleep(5000);

        ArgumentCaptor<ErrorOutPut> argument = ArgumentCaptor.forClass(ErrorOutPut.class);

        Mockito.verify(chatContract).onError(argument.capture());

        Assertions.assertTrue(argument.getValue().isHasError());

    }
    @Test
    @Order(2)
    void sendMessageWithMentionUser() throws InterruptedException {

        SendTextMessageRequest requestThread = new SendTextMessageRequest
                .Builder("this is mention test @f.khojasteh", 7129, TextMessageType.TEXT)
                .build();

        chatController.sendTextMessage(requestThread);

        Thread.sleep(10000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);
//        ArgumentCaptor<ChatResponse> argument1 = ArgumentCaptor.forClass(ChatResponse.class);
//        ArgumentCaptor<ChatResponse> argument2 = ArgumentCaptor.forClass(ChatResponse.class);
//        ArgumentCaptor<ChatResponse> argument3 = ArgumentCaptor.forClass(ChatResponse.class);
//        ArgumentCaptor<ChatResponse> argument4 = ArgumentCaptor.forClass(ChatResponse.class);
//        Mockito.verify(chatContract, Mockito.times(1)).onSentMessage(argument.capture());
//        Mockito.verify(chatContract, Mockito.times(1)).onNewMessage(argument1.capture());
//        Mockito.verify(chatContract, Mockito.times(1)).onThreadInfoUpdated(argument4.capture());
//        Mockito.verify(chatContract, Mockito.times(1)).onGetDeliverMessage(argument2.capture());
//        Mockito.verify(chatContract, Mockito.times(1)).OnSeenMessageList(argument3.capture());
        ChatResponse chatResponse = argument.getValue();
//        ChatResponse chatResponse1 = argument1.getValue();
//        ChatResponse chatResponse2 = argument2.getValue();
//        ChatResponse chatResponse3 = argument1.getValue();
//        ChatResponse chatResponse4 = argument4.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
//
//        Assertions.assertTrue(!chatResponse1.hasError());
//        Assertions.assertTrue(!chatResponse2.hasError());
//        Assertions.assertTrue(!chatResponse3.hasError());
//        Assertions.assertTrue(!chatResponse4.hasError());

}}
