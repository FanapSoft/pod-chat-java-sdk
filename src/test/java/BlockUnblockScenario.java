import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.requestobject.BlockRequest;
import podChat.requestobject.GetBlockedListRequest;
import podChat.requestobject.ConnectRequest;
import podChat.requestobject.UnBlockRequest;

import java.util.ArrayList;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BlockUnblockScenario implements ChatContract.view {
    long userId = 4781;
    long contactId = 13882;
    long threadId = 5461;

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
    void blockWithUserId() throws InterruptedException {

        BlockRequest blockRequest = new BlockRequest
                .Builder()
                .userId(userId)
                .build();

        chatController.block(blockRequest);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onBlock(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }

    @Test
    @Order(3)
    void unBlockWithUserId() throws InterruptedException {

        UnBlockRequest requestBlock = new UnBlockRequest
                .Builder()
                .userId(userId)
                .build();

        chatController.unBlock(requestBlock);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onUnblock(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }


    @Test
    @Order(4)
    void blockWithContactId() throws InterruptedException {

        BlockRequest blockRequest = new BlockRequest
                .Builder()
                .contactId(contactId)
                .build();

        chatController.block(blockRequest);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onBlock(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }

    @Test
    @Order(5)
    void unBlockWithContactId() throws InterruptedException {

        UnBlockRequest requestBlock = new UnBlockRequest
                .Builder()
                .contactId(contactId)
                .build();

        chatController.unBlock(requestBlock);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onUnblock(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }

    @Test
    @Order(6)
    void blockWithThreadId() throws InterruptedException {

        BlockRequest blockRequest = new BlockRequest
                .Builder()
                .threadId(threadId)
                .build();

        chatController.block(blockRequest);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onBlock(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }


    @Test
    @Order(7)
    void UnBlockWithThreadId() throws InterruptedException {

        UnBlockRequest requestBlock = new UnBlockRequest
                .Builder()
                .threadId(threadId)
                .build();

        chatController.unBlock(requestBlock);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onUnblock(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }

    @AfterEach
    void getBlock() throws InterruptedException {

        GetBlockedListRequest getBlockedListRequest = new GetBlockedListRequest
                .Builder()
                .build();

        chatController.getBlockList(getBlockedListRequest);

        Thread.sleep(2000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onGetBlockList(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());
    }

}
