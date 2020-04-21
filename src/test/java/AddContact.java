import Constant.Constant;
import com.google.gson.Gson;
import exception.ConnectionException;
import exmaple.ChatContract;
import org.junit.jupiter.api.*;
import org.mockito.*;
import podChat.model.ChatResponse;
import podChat.model.ErrorOutPut;
import podChat.requestobject.AddContactRequest;
import podChat.requestobject.ConnectRequest;

import java.util.ArrayList;

/**
 * Created By Khojasteh on 8/6/2019
 */

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AddContact implements ChatContract.view {
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

        } catch (ConnectionException e) {
            e.printStackTrace();
        }

    }

    // Add contact with phone and last name
    @Test
    @Order(2)
    void addContactPhoneLName() throws InterruptedException {
        AddContactRequest addContactRequest = new AddContactRequest
                .Builder()
                .cellphoneNumber("09156452709")
                .lastName("مظلوم")
                .build();
        chatController.addContact(addContactRequest);

        Thread.sleep(3000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract, Mockito.times(1)).onAddContact(argument.capture());
        ArgumentCaptor<ChatResponse> argument1 = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract).onThreadInfoUpdated(argument1.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());

    }

    // Add contact with email and name
    @Test
    @Order(2)
    void addContactEmailFName() throws InterruptedException {
        AddContactRequest addContactRequest = new AddContactRequest
                .Builder()
                .email("m@gmail.com")
                .firstName("mohammad")
                .build();
        chatController.addContact(addContactRequest);

        Thread.sleep(3000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract, Mockito.times(1)).onAddContact(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());

    }

    //Check the platform server error
    @Test
    @Order(2)
    void addContactError() throws InterruptedException {
        AddContactRequest addContactRequest = new AddContactRequest
                .Builder()
                .cellphoneNumber("09156452709")
                .build();
        chatController.addContact(addContactRequest);

        Thread.sleep(3000);

        ArgumentCaptor<ErrorOutPut> argument = ArgumentCaptor.forClass(ErrorOutPut.class);

        Mockito.verify(chatContract).onError(argument.capture());

        Assertions.assertTrue(argument.getValue().isHasError());

    }

    @Test
    @Order(2)
    void addContactUserName() throws InterruptedException {
        AddContactRequest addContactRequest = new AddContactRequest
                .Builder()
                .userName("f.khojasteh")
                .firstName("فاطمه")
                .cellphoneNumber("09151242904")
                .build();
        chatController.addContact(addContactRequest);

        Thread.sleep(3000);

        ArgumentCaptor<ChatResponse> argument = ArgumentCaptor.forClass(ChatResponse.class);

        Mockito.verify(chatContract, Mockito.times(1)).onAddContact(argument.capture());

        ChatResponse chatResponse = argument.getValue();

        Assertions.assertTrue(!chatResponse.hasError());

    }

}
