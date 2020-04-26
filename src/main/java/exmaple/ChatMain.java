package exmaple;

import com.google.gson.Gson;
import exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import podAsync.Async;
import podChat.mainmodel.Invitee;
import podChat.mainmodel.MessageVO;
import podChat.mainmodel.RequestThreadInnerMessage;
import podChat.mainmodel.SearchContactsRequest;
import podChat.model.*;
import podChat.requestobject.*;
import podChat.requestobject.BotInfoVO;
import podChat.util.InviteType;
import podChat.util.RoleType;
import podChat.util.TextMessageType;
import podChat.util.ThreadType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Khojasteh on 7/27/2019
 */
public class ChatMain implements ChatContract.view {
//    public static String platformHost = "https://sandbox.pod.ir:8043";
//    public static String token = "c5fdfbf2da7f48e88a3926550bc2f7c1";
//    public static String ssoHost = "https://accounts.pod.ir";
//    public static String fileServer = "https://core.pod.ir";
//    public static String serverName = "chat-server";
//    public static String queueServer = "10.56.16.25";
//    public static String queuePort = "61616";
//    public static String queueInput = "queue-in-amjadi-stomp";
//    public static String queueOutput = "queue-out-amjadi-stomp";
//    public static String queueUserName = "root";
//    public static String queuePassword = "zalzalak";
//    public static Long chatId = 4101L;
//    public static String uri = "10.56.16.25:61616";

        public static String platformHost = "http://172.16.110.235:8003/srv/bptest-core/";
    //        public static String token = "3c4d62b6068043aa898cf7426d5cae68"; //jiji
    public static String token = "bebc31c4ead6458c90b607496dae25c6"; //alexi
    //        public static String token = "3c4d62b6068043aa898cf7426d5cae68"; //fifi
//    public static String token = "f19933ae1b1e424db9965a243bf3bcd3"; //zizi
    public static String ssoHost = "http://172.16.110.76";
    public static String fileServer = "http://172.16.110.76:8080";
    public static String serverName = "chatlocal";
    public static String uri = "192.168.112.23:61616";
    public static String queueInput = "queue-in-integration";
    public static String queueOutput = "queue-out-integration";
    public static String queueUserName = "root";
    public static String queuePassword = "j]Bm0RU8gLhbPUG";
    public static Long chatId = 4101L;
    static ChatController chatController;


    private static Logger logger = LogManager.getLogger(Async.class);
    Gson gson = new Gson();

    void init() {
        chatController = new ChatController(this);
        try {

            ConnectRequest connectRequest = new ConnectRequest
                    .Builder(new ArrayList<String>() {{
                add(uri);
            }},
                    queueInput,
                    queueOutput,
                    queueUserName,
                    queuePassword,
                    serverName,
                    token,
                    ssoHost,
                    platformHost,
                    fileServer,
                    4101L)
                    .build();

            chatController.connect(connectRequest);

//            chatController.getUserInfo();

//            addContact();
//            Thread.sleep(2000);
//            getcontact();
//            Thread.sleep(2000);
//            removeContact();
//            Thread.sleep(2000);
//            getcontact();
//            updateContact();
//            Thread.sleep(2000);
//            getcontact();
//            searchContact();


//            createThread();
//            getThreads();
//            sendMessage();

//            deleteMultipleMessage();
//            deleteMessage();
//            editMessage();
//            forwardMessage();

//            addParticipant();
//            removeParticipant();
//            Thread.sleep(2000);
//            getParticipant();
//chatController.getUserInfo();
//            createThreadWithMessage();
//            createThreadWithFileMessage();
//            createPublicGroupOrChannelThread();
//            isNameAvailable();

//            joinThread();

//            leaveThread();
//            replyMessage();
//            replyFileMessage(); /// check it

//            Thread.sleep(2000);

//            getDeliveryList();
//            getSeenList();

//            mute();
//            Thread.sleep(2000);
//            unmute();

//            getHistory();
//            clearHistory();

//            block();
//            unblock();
//            Thread.sleep(2000);
//            getBlockList();


//
            addAdmin();
//            Thread.sleep(2000);
//            getAdmin();
//            Thread.sleep(2000);
//            removeAdmin();
//            Thread.sleep(2000);
//            getAdmin();


//            pinThread();
//            Thread.sleep(2000);
//            getThreads();
//

//            unPinThread();
//            Thread.sleep(2000);


//            chatController.setToke("e1559e7981b94917a053b32ef36c334a");
//            getcontact();
//
//
//            chatController.setToke("e92a45d5abb54194bfe8f6e6d915189a");
//            getcontact();
//            addAuditor();
//            removeAuditor();
        /*    addAuditor();
            Thread.sleep(2000);
            getParticipant();
            Thread.sleep(2000);
            removeAuditor();
            Thread.sleep(2000);
            getParticipant();*/

//            interactiveMessage();

//            uploadImage();  //checkit
//            uploadFile();    ///checkit
//            getFile();
//            getImage();
//            spam();

//            Thread.sleep(2000);

//            getParticipant();
//            updateThreadInfo();
//            pinMessage();

//            Thread.sleep(2000);
//            getThreads();
            Thread.sleep(2000);
//            unPinMessage();
//            chatController.getUserInfo();

//            getCurrentUserRoles();

//            getMentionedList();
//            createBot();
//            updateProfile();


//            sendFileMessage();


//            countUnreadMessage();
        } catch (ConnectionException | InterruptedException e) {
            System.out.println(e);
        }


    }

    /*********************************************************************
     *                             PROFILE                                 *
     *********************************************************************/

    void updateProfile() {
        UpdateChatProfileRequest requestUpdateProfile = new UpdateChatProfileRequest
                .Builder()
                .metadata("test2")
                .bio("hello")
                .build();

        chatController.updateProfile(requestUpdateProfile);

        chatController.getUserInfo();
    }

    /*********************************************************************
     *                             ADMIN                                 *
     *********************************************************************/

    /**
     * set role
     */


    void addAdmin() {
        RoleModelRequest roleModelRequest = new RoleModelRequest();
        roleModelRequest.setUserId(15503);
        roleModelRequest.setRoles(new ArrayList<String>() {{
            add(RoleType.THREAD_ADMIN);
        }});


        ArrayList<RoleModelRequest> roleModelRequestArrayList = new ArrayList<>();
        roleModelRequestArrayList.add(roleModelRequest);

        SetRemoveRoleRequest requestSetAdmin = new SetRemoveRoleRequest
                .Builder(7308, roleModelRequestArrayList)
                .build();

        chatController.addAdmin(requestSetAdmin);

    }

    void addAuditor() {
        RoleModelRequest roleModelRequest = new RoleModelRequest();
        roleModelRequest.setUserId(15503);
        roleModelRequest.setRoles(new ArrayList<String>() {{
            add(RoleType.POST_CHANNEL_MESSAGE);
            add(RoleType.READ_THREAD);
        }});

        ArrayList<RoleModelRequest> roleModelRequestArrayList = new ArrayList<>();
        roleModelRequestArrayList.add(roleModelRequest);

        SetRemoveRoleRequest setRemoveRoleRequest = new SetRemoveRoleRequest
                .Builder(7308, roleModelRequestArrayList)
                .build();

        chatController.addAuditor(setRemoveRoleRequest);

    }


    void getCurrentUserRoles() {
        GetCurrentUserRolesRequest getCurrentUserRolesRequest = new GetCurrentUserRolesRequest
                .Builder(7149)
                .build();


        chatController.getCurrentUserRoles(getCurrentUserRolesRequest);
    }

    /**
     * delete role
     */
    void removeAdmin() {
        RoleModelRequest roleModelRequest = new RoleModelRequest();
        roleModelRequest.setUserId(15503);
        roleModelRequest.setRoles(new ArrayList<String>() {{
            add(RoleType.THREAD_ADMIN);
        }});

        ArrayList<RoleModelRequest> roleModelRequestArrayList = new ArrayList<>();
        roleModelRequestArrayList.add(roleModelRequest);

        SetRemoveRoleRequest requestSetAdmin = new SetRemoveRoleRequest
                .Builder(7308, roleModelRequestArrayList)
                .build();

        chatController.removeAdmin(requestSetAdmin);

    }


    void removeAuditor() {
        RoleModelRequest roleModelRequest = new RoleModelRequest();
        roleModelRequest.setUserId(15503);
        roleModelRequest.setRoles(new ArrayList<String>() {{
            add(RoleType.POST_CHANNEL_MESSAGE);
            add(RoleType.READ_THREAD);
        }});

        ArrayList<RoleModelRequest> roleModelRequestArrayList = new ArrayList<>();
        roleModelRequestArrayList.add(roleModelRequest);

        SetRemoveRoleRequest setRemoveRoleRequest = new SetRemoveRoleRequest
                .Builder(7308, roleModelRequestArrayList)
                .build();


        chatController.removeAuditor(setRemoveRoleRequest);

    }


    void getAdmin() {
        GetAllThreadAdminsRequest getAllThreadAdminsRequest = new GetAllThreadAdminsRequest
                .Builder(5941)
                .build();

        chatController.getAdminList(getAllThreadAdminsRequest);
    }

    /******************************************************************
     *                           CONTACT                              *
     * ****************************************************************/

    /**
     * add contact
     */
    void addContact() {
        AddContactRequest requestAddContact = AddContactRequest
                .newBuilder()
                .firstName("pooria")
                .userName("pooria")
                .build();

        Gson gson = new Gson();
        System.out.println(gson.toJson(requestAddContact));
        chatController.addContact(requestAddContact);
    }

    /**
     * remove contact
     */
    private void removeContact() {
        RemoveContactsRequest removeContactsRequest = new RemoveContactsRequest
                .Builder(8559)
                .build();

        chatController.removeContact(removeContactsRequest);
    }

    /**
     * update contact
     */
    private void updateContact() {
        UpdateContactsRequest updateContactsRequest = new UpdateContactsRequest
                .Builder(8559, "زهرا", "مظلوم", "0911111111111", "zahra@gmail.com")
                .build();

        chatController.updateContact(updateContactsRequest);
    }

    /**
     * search contact
     */
    private void searchContact() {
        SearchContactsRequest searchContact = new SearchContactsRequest
                .Builder()
                .firstName("JiJi")

                .build();

        chatController.searchContact(searchContact);
    }

    /**
     * get contact
     */
    private void getcontact() {
        GetContactsRequest getContactsRequest = new GetContactsRequest
                .Builder()
                .build();
        chatController.getContact(getContactsRequest);
    }

    /**
     * block
     */
    private void block() {
        BlockRequest blockRequest = new BlockRequest
                .Builder()
                .contactId(578)
                .build();

        chatController.block(blockRequest);
    }

    /**
     * unblock
     */
    private void unblock() {
        UnBlockRequest unBlockRequest = new UnBlockRequest
                .Builder()
//                .contactId(578)
//                (6061)
                .blockId(2182)
                .build();

        chatController.unBlock(unBlockRequest);
    }

    /**
     * block list
     */
    private void getBlockList() {
        GetBlockedListRequest getBlockedListRequest = new GetBlockedListRequest
                .Builder()
                .build();

        chatController.getBlockList(getBlockedListRequest);
    }
    /******************************************************************
     *                           HISTORY                              *
     * ****************************************************************/

    /**
     * clear history
     */
    private void clearHistory() {
        ClearHistoryRequest clearHistoryRequest = new ClearHistoryRequest
                .Builder(5461)
                .build();

        chatController.clearHistory(clearHistoryRequest);
    }

    /**
     * get history
     */
    private void getHistory() {
    /*    RequestGetHistory requestGetHistory = new RequestGetHistory
                .Builder(5461)
                .build();

        chatController.getHistory(requestGetHistory);*/
        GetHistoryRequest getHistoryRequest2 = new GetHistoryRequest
                .Builder(7149)
//                .uniqueIds(new String[]{"a98d00af-6cb7-4174-a82a-a8ec68af0bb1"})
                .build();

        chatController.getHistory(getHistoryRequest2);

     /*   RequestGetHistory requestGetHistory1 = new RequestGetHistory
                .Builder(5461)
                .build();

        chatController.getHistory(requestGetHistory1, null);*/
    }

    /******************************************************************
     *                           THREAD                               *
     * ****************************************************************/

    /**
     * leave thread
     */
    private void leaveThread() {
        LeaveThreadRequest leaveThread = new LeaveThreadRequest
                .Builder(7433)
                .build();

        chatController.leaveThread(leaveThread);
    }

    /**
     * delete message
     */
    private void deleteMessage() {
        DeleteMessageRequest deleteMessage = new DeleteMessageRequest
                .Builder(91292l)
                .deleteForAll(true)
                .build();

        chatController.deleteMessage(deleteMessage);
    }

    /**
     * create thread with message
     */
    private void createThreadWithMessage() {
        RequestThreadInnerMessage requestThreadInnerMessage = new RequestThreadInnerMessage
                .Builder()
                .text("hello world")
                .messageType(TextMessageType.TEXT)
                .build();

        Invitee invitee = new Invitee();
        invitee.setId("09122964316");
        invitee.setIdType(InviteType.TO_BE_USER_CELLPHONE_NUMBER);

//        Invitee invitee1 = new Invitee();
//        invitee1.setId("09156967335");
//        invitee1.setIdType(InviteType.TO_BE_USER_CELLPHONE_NUMBER);
//        Invitee invitee1 = new Invitee();
//        invitee1.setId(1181);
//        invitee1.setIdType(InviteType.TO_BE_USER_ID);

        CreateThreadWithMessageRequest createThreadWithMessageRequest = new CreateThreadWithMessageRequest
                .Builder(ThreadType.NORMAL, new ArrayList<Invitee>() {{
            add(invitee);
        }},
                requestThreadInnerMessage)
                .build();
        chatController.createThreadWithMessage(createThreadWithMessageRequest);

    }

    private void getMentionedList() {
        GetMentionedRequest getMentionedRequest = new GetMentionedRequest
                .Builder(7308)
                .allMentioned(false)
                .unreadMentioned(false)
                .build();


        chatController.getMentionedList(getMentionedRequest);
    }


    /**
     * create thread with file message
     */

    private void createThreadWithFileMessage() {
        UploadImageRequest requestUploadFile = new UploadImageRequest
                .Builder("C:\\Users\\Arash\\Documents\\pod-chat-java-sdk\\resultFinal.jpg")
                .hC(200)
                .build();

        Invitee invitee = new Invitee();
        invitee.setId("09900449643");
        invitee.setIdType(InviteType.TO_BE_USER_CELLPHONE_NUMBER);

        RequestThreadInnerMessage requestThreadInnerMessage = new RequestThreadInnerMessage
                .Builder()
                .messageType(TextMessageType.PICTURE)
                .build();


        CreateThreadWithFileRequest requestCreateThreadWithFile = new CreateThreadWithFileRequest
                .Builder(ThreadType.NORMAL, new ArrayList<Invitee>() {{
            add(invitee);
        }}, requestUploadFile,
                requestThreadInnerMessage)
                .description("tesssssssssssst")
                .build();


        chatController.createThreadWithFileMessage(requestCreateThreadWithFile);

    }

    /**
     * edit message
     */
    private void editMessage() {
        EditMessageRequest editMessageRequest = new EditMessageRequest
                .Builder("hiii", 91288)
                .build();
        chatController.editMessage(editMessageRequest);
    }


    /**
     * create bot
     */
    private void createBot() {
        CreateBotRequest createBotRequest = new CreateBotRequest
                .Builder("SDKBOT")
                .build();
        chatController.createBot(createBotRequest);
    }

    /**
     * start bot
     */
    private void startBot() {
        StartBotRequest startBotRequest = new StartBotRequest
                .Builder(1234L, "SDKBOT")
                .build();
        chatController.startBot(startBotRequest);
    }

    /**
     * stop bot
     */
    private void stopBot() {
        StartBotRequest startBotRequest = new StartBotRequest
                .Builder(1234L, "SDKBOT")
                .build();
        chatController.stopBot(startBotRequest);
    }

    /**
     * define bot command
     */
    private void defineBotCommand() {
        List<String> commands=new ArrayList<>();
        commands.add("/ss");
        BotInfoVO botInfoVO = new BotInfoVO
                .Builder("SDKBOT",commands)
                .build();
        chatController.defineBotCommand(botInfoVO);
    }
    /**
     * send message
     */
    private void sendMessage() {
        SendTextMessageRequest requestThread = new SendTextMessageRequest
                .Builder("hi test @f.khojasteh", 7308, TextMessageType.TEXT)
                .build();

        chatController.sendTextMessage(requestThread);
    }

    /**
     * get thread
     */
    private void getThreads() {
        GetThreadsRequest getThreadsRequest = new GetThreadsRequest
                .Builder()
                .threadIds(new ArrayList<Integer>() {{
                    add(7330);
                    add(7129);
                }})
//                .newMessages()
                .build();

        chatController.getThreads(getThreadsRequest);
    }

    /**
     * delete multiple message
     */
    private void deleteMultipleMessage() {
        DeleteMultipleMessagesRequest deleteMultipleMessagesRequest = new DeleteMultipleMessagesRequest
                .Builder(new ArrayList<Long>() {{
            add(95314L);
            add(95315L);
        }})
                .deleteForAll(true)
                .build();

        chatController.deleteMultipleMessage(deleteMultipleMessagesRequest);
    }

    /**
     * forward message
     */
    private void forwardMessage() {
        ForwardMessageRequest forwardMessage = new ForwardMessageRequest
                .Builder(7129, new ArrayList<Long>() {{
            add(91290L);
        }})
                .build();

        chatController.forwardMessage(forwardMessage);
    }

    /**
     * reply message
     */
    private void replyMessage() {
        ReplyTextMessageRequest replyTextMessageRequest = new ReplyTextMessageRequest
                .Builder("hi", 7129, 91288, TextMessageType.TEXT)
                .build();

        chatController.replyMessage(replyTextMessageRequest);
    }

    /**
     * create thread
     */
    private void createThread() {
       /* Invitee[] invitees = new Invitee[2];
        Invitee invitee = new Invitee();
        invitee.setIdType(InviteType.TO_BE_USER_CONTACT_ID);
        invitee.setId(13812);

        Invitee invitee1 = new Invitee();
        invitee1.setIdType(InviteType.TO_BE_USER_CONTACT_ID);
        invitee1.setId(13882);

        invitees[0] = invitee;
        invitees[1] = invitee1;

        chatController.createThread(ThreadType.PUBLIC_GROUP, invitees, "sendMessage", "", "", "");*/

        Invitee[] invitees = new Invitee[1];
        Invitee invitee = new Invitee();
        invitee.setIdType(InviteType.TO_BE_USER_CONTACT_ID);
        invitee.setId("578");

//        Invitee invitee2 = new Invitee();
//        invitee2.setIdType(InviteType.TO_BE_USER_CONTACT_ID);
//        invitee2.setId(13812);

        invitees[0] = invitee;
//        invitees[1] = invitee2;

        CreateThreadRequest createThreadRequest = CreateThreadRequest
                .newBuilder()
                .NonPublicThread(ThreadType.PUBLIC_GROUP, new ArrayList<Invitee>() {{
                    add(invitee);
                }})
                .description("test step ")
                .build();

        chatController.createThread(createThreadRequest);
    }

    /**
     * create public group or channel thread
     */

    private void createPublicGroupOrChannelThread() {
        Invitee[] invitees = new Invitee[1];
        Invitee invitee = new Invitee();
        invitee.setIdType(InviteType.TO_BE_USER_CONTACT_ID);
        invitee.setId("578");
        invitees[0] = invitee;

        CreateThreadRequest createThreadRequest = CreateThreadRequest
                .newBuilder()
                .publicThreadOrChannel(ThreadType.PUBLIC_GROUP, new ArrayList<Invitee>() {{
                    add(invitee);
                }}, "test123join")
                .description("test step ")
                .build();

        chatController.createThread(createThreadRequest);
    }

    /**
     * seen message list
     */
    private void getSeenList() {
        GetMessageDeliveredSeenListRequest requestSeenMessageList = new GetMessageDeliveredSeenListRequest
                .Builder(91290)
                .build();

        chatController.seenMessageList(requestSeenMessageList);
    }

    /**
     * delivery message list
     */
    private void getDeliveryList() {
        GetMessageDeliveredSeenListRequest getMessageDeliveredSeenListRequest = new GetMessageDeliveredSeenListRequest
                .Builder(55216)
                .build();

        chatController.deliveredMessageList(getMessageDeliveredSeenListRequest);
    }

    /**
     * mute thread
     */
    private void mute() {
        MuteUnmuteThreadRequest muteUnmuteThreadRequest = new MuteUnmuteThreadRequest
                .Builder(4982)
                .build();

        chatController.muteThread(muteUnmuteThreadRequest);
    }

    /**
     * unmute thread
     */
    private void unmute() {
        MuteUnmuteThreadRequest muteUnmuteThreadRequest = new MuteUnmuteThreadRequest
                .Builder(4982)
                .build();

        chatController.unMuteThread(muteUnmuteThreadRequest);
    }


    /**
     * count unread messages
     */
    private void countUnreadMessage() {
        GetAllUnreadMessageCountRequest getAllUnreadMessageCountRequest = new GetAllUnreadMessageCountRequest
                .Builder()
                .mute(true)
                .build();

        chatController.countUnreadMessage(getAllUnreadMessageCountRequest);
    }

    private void joinThread() {
        JoinPublicThreadRequest joinPublicThreadRequest = new JoinPublicThreadRequest
                .Builder("jijiThread")
                .build();

        chatController.joinThead(joinPublicThreadRequest);
    }

    /**
     * spam thread
     */

    private void spam() {
        SpamPrivateThreadRequest spamPrivateThreadRequest = new SpamPrivateThreadRequest
                .Builder(7330)
                .build();

        chatController.spam(spamPrivateThreadRequest);
    }


    /**
     * bot message
     */

    private void interactiveMessage() {
        InteractRequest interactRequest = new InteractRequest
                .Builder(95293, "OK")
                .build();

        chatController.interactiveMessage(interactRequest);
    }


    private void pinThread() {
        PinUnpinThreadRequest pinUnpinThreadRequest = new PinUnpinThreadRequest
                .Builder(7129)
                .build();

        chatController.pinThread(pinUnpinThreadRequest);
    }

    private void unPinThread() {
        PinUnpinThreadRequest pinUnpinThreadRequest = new PinUnpinThreadRequest
                .Builder(5461)
                .build();

        chatController.unPinThread(pinUnpinThreadRequest);
    }


    private void pinMessage() {
        PinUnpinMessageRequest pinUnpinMessageRequest = new PinUnpinMessageRequest
                .Builder(89288L)
                .build();

        chatController.pinMessage(pinUnpinMessageRequest);
    }

    private void unPinMessage() {
        PinUnpinMessageRequest pinUnpinMessageRequest = new PinUnpinMessageRequest
                .Builder(89288L)
                .build();

        chatController.unPinMessage(pinUnpinMessageRequest);
    }


    private void isNameAvailable() {
        IsPublicThreadNameAvailableRequest isPublicThreadNameAvailableRequest = new IsPublicThreadNameAvailableRequest
                .Builder("sdf")
                .build();

        chatController.isNameAvailable(isPublicThreadNameAvailableRequest);
    }


    /**
     * get history
     */
    private void updateThreadInfo() {
        UpdateThreadInfoRequest updateThreadInfoRequest = new UpdateThreadInfoRequest
                .Builder()
                .description("توضیح")
                .threadId(7149)
                .metadat("test")
                .image("img")
                .title("hi")
                .build();

        chatController.updateThreadInfo(updateThreadInfoRequest);
    }

    /******************************************************************
     *                           PARTICIPANT                          *
     * ****************************************************************/

    /**
     * remove participant
     */
    private void removeParticipant() {
        RemoveParticipantsRequestModel removeParticipantsRequestModel = new RemoveParticipantsRequestModel
                .Builder(7308, new ArrayList<Long>() {{
            add(15503L);
        }})
                .build();

        chatController.removeParticipants(removeParticipantsRequestModel);
    }

    /**
     * get participant
     */
    private void getParticipant() {
        GetThreadParticipantsRequest threadParticipant = new GetThreadParticipantsRequest
                .Builder(7308)
                .name("Pooria Pahlevani")
                .build();

        chatController.getThreadParticipant(threadParticipant);
    }

    /**
     * add participant
     */
    private void addParticipant() {
        AddParticipantsRequestModel addParticipants = AddParticipantsRequestModel
                .newBuilder()
                .threadId(7308L)
                .withUsername("pooria")
                .build();

        chatController.addParticipants(addParticipants);
    }

    /******************************************************************
     *                           FIlE                                 *
     * ****************************************************************/

    /**
     * send file message
     */
    private void sendFileMessage() {
        RequestFileMessage requestFileMessage = new RequestFileMessage
                .Builder(7129, "D:\\chat.txt", TextMessageType.FILE)
                .description("this is test")
                .build();

        chatController.uploadFileMessage(requestFileMessage, null);
    }

    /**
     * reply file message
     */
    private void replyFileMessage() {
  /*      RequestReplyFileMessage requestReplyFileMessage = new RequestReplyFileMessage
                .Builder("this is test", 5461, 47921, "C:\\Users\\fanap-10\\Pictures\\Saved Pictures\\a.jpg")
                .xC(0)
                .yC(0)
                .hC(100)
                .wC(200)
                .build();*/


        SendReplyFileMessageRequest sendReplyFileMessageRequest = new SendReplyFileMessageRequest
                .Builder("this is test",
                7129,
                91290,
                "D:\\download.jpg"
                , TextMessageType.PICTURE)
                .build();
        chatController.replyFileMessage(sendReplyFileMessageRequest, null);
    }

    /**
     * upload image
     */

    private void uploadImage() {
        UploadImageRequest requestUploadImage = new UploadImageRequest
                .Builder("trees.jpg")
                .build();
        System.out.println(gson.toJson(requestUploadImage));
        chatController.uploadImage(requestUploadImage);
    }

    /**
     * upload file
     */
    private void uploadFile() {
        UploadFileRequest uploadFileRequest = new UploadFileRequest
                .Builder("D:\\Music.rar")
                .build();

        chatController.uploadFile(uploadFileRequest);


    }

    /**
     * get file
     */
    private void getFile() {
        GetFileRequest getFileRequest = new GetFileRequest
                .Builder(12512, "17158ccd289-0.3253966932798973", true, "C:\\Users\\Arash\\Documents\\pod-chat-java-sdk\\resultFinal.txt")
                .build();
        chatController.getFile(getFileRequest);


    }

    private void getImage() {
        GetImageRequest getImageRequest = new GetImageRequest
                .Builder(12516, "17159944353-0.8847278640747307", true, "C:\\Users\\Arash\\Documents\\pod-chat-java-sdk\\resultFinal.jpg")
                .build();
        chatController.getImage(getImageRequest);


    }

    @Override
    public void onNewMessage(ChatResponse<ResultNewMessage> chatResponse) {
        ResultNewMessage resultNewMessage = chatResponse.getResult();
//        if (!temp) {
//            long threadId = resultNewMessage.getThreadId();
//
//            MessageVO messageVO = resultNewMessage.getMessageVO();
//
//            long messageId = messageVO.getId();
//
//            RequestReplyMessage requestReplyMessage = new RequestReplyMessage
//                    .Builder("HELLOOOO", threadId, messageId)
//                    .build();
//
//            chatController.replyMessage(requestReplyMessage, null);
//            temp = true;
//        }
    }


    @Override
    public void onGetThreadHistory(ChatResponse<ResultHistory> history) {
        List<MessageVO> messageVOS = history.getResult().getHistory();

        for (MessageVO messageVO : messageVOS) {
            if (!messageVO.getSeen() && messageVO.getParticipant().getId() != 4101) {

                chatController.seenMessage(messageVO.getId(), messageVO.getParticipant().getId());
            }
        }

    }

    @Override
    public void onCreateThread(ChatResponse<ResultThread> outPutThread) {
        System.out.println("");
    }

    @Override
    public void onError(ErrorOutPut error) {
//        if (error.getErrorCode() == 21) {
//            Scanner myObj = new Scanner(System.in);
//            System.out.println("Enter token");
//
//            String token = myObj.nextLine();
//
//            chatController.setToke(token);
//            getThreads();
//
//        }
    }

    @Override
    public void onSearchContact(ChatResponse<ResultContact> chatResponse) {

        System.out.println(gson.toJson(chatResponse));
    }

    @Override
    public void OnInteractMessage(ChatResponse<ResultInteractMessage> chatResponse) {
        System.out.println("helllo");
    }

    @Override
    public void onGetContacts(ChatResponse<ResultContact> response) {
        System.out.println(response);
    }

    @Override
    public void onAddContact(ChatResponse<ResultAddContact> chatResponse) {
        System.out.println(gson.toJson(chatResponse));
    }

    @Override
    public void onSetRole(ChatResponse<ResultSetRole> chatResponse) {
        System.out.println("helllo");
    }


    @Override
    public void onRemoveRole(ChatResponse<ResultSetRole> chatResponse) {
        System.out.println("helllo");
    }

    @Override
    public void onGetCurrentUserRoles(ChatResponse<ResultCurrentUserRoles> response) {
        System.out.println("d");
    }

}
