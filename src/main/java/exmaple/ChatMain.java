package exmaple;

import com.google.gson.Gson;
import exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import podAsync.Async;
import podChat.mainmodel.Invitee;
import podChat.mainmodel.MessageVO;
import podChat.mainmodel.RequestSearchContact;
import podChat.mainmodel.RequestThreadInnerMessage;
import podChat.model.*;
import podChat.requestobject.*;
import podChat.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created By Khojasteh on 7/27/2019
 */
public class ChatMain implements ChatContract.view {
    public static String platformHost;
    public static String token;
    public static String ssoHost;
    public static String fileServer;
    public static String serverName;
    public static String uri;
    public static String queueInput;
    public static String queueOutput;
    public static String queueUserName;
    public static String queuePassword;
    public static Long chatId = 4101L;
    public static String podSpaceServer;
    public static Integer readThreadCount;
    public static Integer readMinThreadCount;
    public static Integer AUTO_ACKNOWLEDGE = 1;
    public static Integer CLIENT_ACKNOWLEDGE = 2;
    InputStream inputAll = this.getClass().getClassLoader().getResourceAsStream("config.properties");
    Properties propAll = new Properties();
    static ChatController chatController;

    private static Logger logger = LogManager.getLogger(ChatMain.class);
    Gson gson = new Gson();

    void init() throws IOException {
        propAll.load(inputAll);
        platformHost = propAll.getProperty("platformHost");
        token = propAll.getProperty("token");
        ssoHost = propAll.getProperty("ssoHost");
        fileServer = propAll.getProperty("fileServer");
        serverName = propAll.getProperty("serverName");
        uri = propAll.getProperty("uri");
        queueInput = propAll.getProperty("queueInput");
        queueOutput = propAll.getProperty("queueOutput");
        queueUserName = propAll.getProperty("queueUserName");
        queuePassword = propAll.getProperty("queuePassword");
        podSpaceServer = propAll.getProperty("podSpaceServer");
        readThreadCount = Integer.valueOf(propAll.getProperty("consumerMaxCount.config"));
        readMinThreadCount = Integer.valueOf(propAll.getProperty("consumerMinCount.config"));
        chatController = new ChatController(this);
        try {

            RequestConnect requestConnect = new RequestConnect
                    .Builder(new ArrayList<String>() {{
                add(uri);
            }},
                    queueInput,
                    queueOutput,
                    queueUserName,
                    queuePassword,
                    5,
                    CLIENT_ACKNOWLEDGE,
                    serverName,
                    token,
                    ssoHost,
                    platformHost,
                    fileServer,
                    4101L,
                    podSpaceServer)
                    .build();

            chatController.connect(requestConnect);

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
                getThreads();

//            sendMessage();
//            deleteMultipleMessage();
//            deleteMessage();
//            editMessage();
//            forwardMessage();
//            addParticipant();
//            removeParticipant();
//            Thread.sleep(2000);
//            getParticipant();
//            chatController.getUserInfo();
//            createThreadWithMessage();
//            createThreadWithFileMessage();
//            createPublicGroupOrChannelThread();
//            isNameAvailable();
//            joinThread();
//            leaveThread();
//            replyMessage();
//            replyFileMessage();
//            Thread.sleep(2000);
//            getDeliveryList();
//            getSeenList();
//            createBot();
//            startBot();
//            stopBot();
//            defineBotCommand();
//            mute();
//            Thread.sleep(2000);
//            unmute();
//            getHistory();
//            clearHistory();
//            block();
//            unblock();
//            Thread.sleep(2000);
//            getBlockList();
//            addAdmin();
//            Thread.sleep(2000);
//            getAdmin();
//            Thread.sleep(2000);
//            removeAdmin();
//            Thread.sleep(2000);
//            getAdmin();
//            pinThread();
//            Thread.sleep(2000);
//            getThreads();
//            unPinThread();
//            Thread.sleep(2000);
//            chatController.setToke("e1559e7981b94917a053b32ef36c334a");
//            getcontact();
//            chatController.setToke("e92a45d5abb54194bfe8f6e6d915189a");
//            getcontact();
//            addAuditor();
//            Thread.sleep(2000);
//            getParticipant();
//            Thread.sleep(2000);
//            removeAuditor();
//            Thread.sleep(2000);
//            getParticipant();
//            interactiveMessage();
//            uploadImage();
//            uploadFile();
//            getFile();
//            getImage();
//            spam();
//            Thread.sleep(2000);
//            getParticipant();
//            updateThreadInfo();
//            pinMessage();
//            Thread.sleep(2000);
//            getThreads();
//            sendFileMessage();
            Thread.sleep(2000);
//            unPinMessage();
//            chatController.getUserInfo();
//            getCurrentUserRoles();
//            getMentionedList();
//            updateProfile();
//            countUnreadMessage();
        } catch (ConnectionException | InterruptedException e) {
            System.out.println(e);
        }

    }

    /*********************************************************************
     *                             PROFILE                                 *
     *********************************************************************/

    void updateProfile() {
        RequestUpdateProfile requestUpdateProfile = new RequestUpdateProfile
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
        RequestRole requestRole = new RequestRole();
        requestRole.setId(4781);
        requestRole.setRoleTypes(new ArrayList<String>() {{
            add(RoleType.THREAD_ADMIN);
        }});


        ArrayList<RequestRole> requestRoleArrayList = new ArrayList<>();
        requestRoleArrayList.add(requestRole);

        RequestSetAdmin requestSetAdmin = new RequestSetAdmin
                .Builder(5941, requestRoleArrayList)
                .build();

        chatController.addAdmin(requestSetAdmin);

    }

    void addAuditor() {
        RequestRole requestRole = new RequestRole();
        requestRole.setId(1181);
        requestRole.setRoleTypes(new ArrayList<String>() {{
            add(RoleType.POST_CHANNEL_MESSAGE);
            add(RoleType.READ_THREAD);
        }});

        ArrayList<RequestRole> requestRoleArrayList = new ArrayList<>();
        requestRoleArrayList.add(requestRole);

        RequestSetAuditor requestSetAuditor = new RequestSetAuditor
                .Builder(5461, requestRoleArrayList)
                .build();

        chatController.addAuditor(requestSetAuditor);

    }


    void getCurrentUserRoles() {
        RequestCurrentUserRoles requestCurrentUserRoles = new RequestCurrentUserRoles
                .Builder(7149)
                .build();


        chatController.getCurrentUserRoles(requestCurrentUserRoles);
    }

    /**
     * delete role
     */
    void removeAdmin() {
        RequestRole requestRole = new RequestRole();
        requestRole.setId(4781);
        requestRole.setRoleTypes(new ArrayList<String>() {{
            add(RoleType.THREAD_ADMIN);
        }});

        ArrayList<RequestRole> requestRoleArrayList = new ArrayList<>();
        requestRoleArrayList.add(requestRole);

        RequestSetAdmin requestSetAdmin = new RequestSetAdmin
                .Builder(5941, requestRoleArrayList)
                .build();

        chatController.removeAdmin(requestSetAdmin);

    }


    void removeAuditor() {
        RequestRole requestRole = new RequestRole();
        requestRole.setId(1181);
        requestRole.setRoleTypes(new ArrayList<String>() {{
            add(RoleType.POST_CHANNEL_MESSAGE);
            add(RoleType.READ_THREAD);
        }});

        ArrayList<RequestRole> requestRoleArrayList = new ArrayList<>();
        requestRoleArrayList.add(requestRole);

        RequestSetAuditor requestSetAuditor = new RequestSetAuditor
                .Builder(5461, requestRoleArrayList)
                .build();


        chatController.removeAuditor(requestSetAuditor);

    }


    void getAdmin() {
        RequestGetAdmin requestGetAdmin = new RequestGetAdmin
                .Builder(5941)
                .build();

        chatController.getAdminList(requestGetAdmin);
    }

    /******************************************************************
     *                           CONTACT                              *
     * ****************************************************************/

    /**
     * add contact
     */
    void addContact() {
//        RequestAddContact requestAddContact = new RequestAddContact
//                .Builder()
//                .cellphoneNumber("09151242904")
//                .lastName("فاطمه")
//                .firstName("خجسته")
//                .build();

        RequestAddContact requestAddContact = RequestAddContact.newBuilder()
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
        RequestRemoveContact requestRemoveContact = new RequestRemoveContact
                .Builder(8559)
                .build();

        chatController.removeContact(requestRemoveContact);
    }

    /**
     * update contact
     */
    private void updateContact() {
        RequestUpdateContact requestUpdateContact = new RequestUpdateContact
                .Builder(8559, "زهرا", "مظلوم", "0911111111111", "zahra@gmail.com")
                .build();

        chatController.updateContact(requestUpdateContact);
    }

    /**
     * search contact
     */
    private void searchContact() {
        RequestSearchContact searchContact = new RequestSearchContact
                .Builder()
                .firstName("JiJi")

                .build();

        chatController.searchContact(searchContact);
    }

    /**
     * get contact
     */
    private void getcontact() {
        RequestGetContact requestGetContact = new RequestGetContact
                .Builder()
                .id(578L)
                .cellphoneNumber("03253456436643")
                .uniqueId("jiji")
//                .email("nnnv")
                .query("ji")
                .build();
        chatController.getContact(requestGetContact);
    }

    /**
     * block
     */
    private void block() {
        RequestBlock requestBlock = new RequestBlock
                .Builder()
                .contactId(578)
                .build();

        chatController.block(requestBlock);
    }

    /**
     * unblock
     */
    private void unblock() {
        RequestUnBlock requestUnBlock = new RequestUnBlock
                .Builder()
//                .contactId(578)
//                (6061)
                .blockId(2182)
                .build();

        chatController.unBlock(requestUnBlock);
    }

    /**
     * block list
     */
    private void getBlockList() {
        RequestBlockList requestBlockList = new RequestBlockList
                .Builder()
                .build();

        chatController.getBlockList(requestBlockList);
    }
    /******************************************************************
     *                           HISTORY                              *
     * ****************************************************************/

    /**
     * clear history
     */
    private void clearHistory() {
        RequestClearHistory requestClearHistory = new RequestClearHistory
                .Builder(5461)
                .build();

        chatController.clearHistory(requestClearHistory);
    }

    /**
     * get history
     */
    private void getHistory() {
    /*    RequestGetHistory requestGetHistory = new RequestGetHistory
                .Builder(5461)
                .build();

        chatController.getHistory(requestGetHistory);*/
        RequestGetHistory requestGetHistory2 = new RequestGetHistory
                .Builder(7129)
//                .messageType(ChatMessageType.INVITATION)
//                .uniqueIds(new String[]{"a98d00af-6cb7-4174-a82a-a8ec68af0bb1"})
                .build();

        chatController.getHistory(requestGetHistory2);

     /*   RequestGetHistory requestGetHistory1 = new RequestGetHistory
                .Builder(5461)
                .build();

        chatController.getHistory(requestGetHistory1, null);*/
    }

    /******************************************************************
     *                           BOT                               *
     * ****************************************************************/

    /**
     * create bot
     */
    private void createBot() {
        RequestCreateBot requestCreateBot = new RequestCreateBot
                .Builder("SDK4BOT")
                .build();
        chatController.createBot(requestCreateBot);
    }

    /**
     * start bot
     */
    private void startBot() {
        RequestStartAndStopBot requestStartAndStopBot = new RequestStartAndStopBot
                .Builder(7459L, "SDK4BOT")
                .build();
        chatController.startBot(requestStartAndStopBot);
    }

    /**
     * stop bot
     */
    private void stopBot() {
        RequestStartAndStopBot requestStartAndStopBot = new RequestStartAndStopBot
                .Builder(7459L, "SDK4BOT")
                .build();
        chatController.stopBot(requestStartAndStopBot);
    }

    /**
     * define bot command
     */
    private void defineBotCommand() {
        List<String> commands = new ArrayList<>();
        commands.add("get5");
        commands.add("/get6");
        RequestDefineCommandBot requestDefineCommandBot = new RequestDefineCommandBot
                .Builder("SDK4BOT", commands)
                .build();
        chatController.defineBotCommand(requestDefineCommandBot);
    }


    /******************************************************************
     *                           THREAD                               *
     * ****************************************************************/

    /**
     * leave thread
     */
    private void leaveThread() {
        RequestLeaveThread leaveThread = new RequestLeaveThread
                .Builder(5941)
                .build();

        chatController.leaveThread(leaveThread);
    }

    /**
     * delete message
     */
    private void deleteMessage() {
        RequestDeleteMessage deleteMessage = new RequestDeleteMessage
                .Builder(new ArrayList<Long>() {{
            add(91292L);
        }})
                .deleteForAll(true)
                .build();

        chatController.deleteMessage(deleteMessage);
    }

    /**
     * create thread with message
     */
    private void createThreadWithMessage() {
        RequestThreadInnerMessage requestThreadInnerMessage = new RequestThreadInnerMessage
                .Builder("hello world")
                .build();

        Invitee invitee = new Invitee();
        invitee.setId("15596");
        invitee.setIdType(InviteType.TO_BE_USER_ID);

//        Invitee invitee1 = new Invitee();
//        invitee1.setId("09156967335");
//        invitee1.setIdType(InviteType.TO_BE_USER_CELLPHONE_NUMBER);
//        Invitee invitee1 = new Invitee();
//        invitee1.setId(1181);
//        invitee1.setIdType(InviteType.TO_BE_USER_ID);

        RequestCreateThreadWithMessage requestCreateThreadWithMessage = new RequestCreateThreadWithMessage
                .Builder(ThreadType.NORMAL, new ArrayList<Invitee>() {{
            add(invitee);
        }},
                TextMessageType.TEXT)
                .message(requestThreadInnerMessage)
                .build();
        chatController.createThreadWithMessage(requestCreateThreadWithMessage);

    }

    private void getMentionedList() {
        RequestGetMentionedList requestGetMentionedList = new RequestGetMentionedList
                .Builder(7308)
                .allMentioned(true)
//                .unreadMentioned(true)
                .build();


        chatController.getMentionedList(requestGetMentionedList);
    }


    /**
     * create thread with file message
     */

    private void createThreadWithFileMessage() {
        RequestUploadImage requestUploadFile = new RequestUploadImage
                .Builder("54.jpg")
//                .hC(200)
                .build();

        Invitee invitee = new Invitee();
        invitee.setId("15596");
        invitee.setIdType(InviteType.TO_BE_USER_ID);

        RequestThreadInnerMessage requestThreadInnerMessage = new RequestThreadInnerMessage
                .Builder("hellllllllllllllo")
                .build();


        RequestCreateThreadWithFile requestCreateThreadWithFile = new RequestCreateThreadWithFile
                .Builder(ThreadType.NORMAL, new ArrayList<Invitee>() {{
            add(invitee);
        }}, requestUploadFile,
                TextMessageType.POD_SPACE_PICTURE)
                .message(requestThreadInnerMessage)
                .description("tesssssssssssst")
                .build();


        chatController.createThreadWithFileMessage(requestCreateThreadWithFile);

    }

    /**
     * edit message
     */
    private void editMessage() {
        RequestEditMessage requestEditMessage = new RequestEditMessage
                .Builder("hiii", 91288)
                .build();
        chatController.editMessage(requestEditMessage);
    }

    /**
     * send message
     */
    private void sendMessage() {
        RequestMessage requestThread = new RequestMessage
                .Builder("/getId@SDK4BOT", 7129L, TextMessageType.TEXT)
                .build();

        chatController.sendTextMessage(requestThread);
    }

    /**
     * get thread
     */
    private void getThreads() {
        RequestThread requestThread = new RequestThread
                .Builder()
                .threadIds(new ArrayList<Integer>() {{
                    add(7330);
                    add(7129);
                    add(8108);
                    add(8096);
                    add(8095);
                    add(8094);
                    add(8092);
                    add(8090);
                    add(8089);
                    add(8088);
                    add(8087);
                }})
//                .newMessages()
                .build();

        chatController.getThreads(requestThread);
    }

    /**
     * delete multiple message
     */
    private void deleteMultipleMessage() {
        RequestDeleteMessage requestDeleteMessage = new RequestDeleteMessage
                .Builder(new ArrayList<Long>() {{
            add(86370L);
            add(86369L);
        }})
                .deleteForAll(true)
                .build();

        chatController.deleteMultipleMessage(requestDeleteMessage);
    }

    /**
     * forward message
     */
    private void forwardMessage() {
        RequestForwardMessage forwardMessage = new RequestForwardMessage
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
        RequestReplyMessage requestReplyMessage = new RequestReplyMessage
                .Builder("7459", 7129, 108290, TextMessageType.TEXT)
                .systemMetaData("qaz1")
                .build();

        chatController.replyMessage(requestReplyMessage);
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
        invitee.setIdType(InviteType.TO_BE_USER_ID);
        invitee.setId("15596");

//        Invitee invitee2 = new Invitee();
//        invitee2.setIdType(InviteType.TO_BE_USER_CONTACT_ID);
//        invitee2.setId(13812);

        invitees[0] = invitee;
//        invitees[1] = invitee2;

        RequestCreateThread requestCreateThread = new RequestCreateThread
                .Builder<>(ThreadType.NORMAL, new ArrayList<Invitee>() {{
            add(invitee);
        }}).image("54.jpg")
                .build();

        chatController.createThread(requestCreateThread);
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

        RequestCreatePublicGroupOrChannelThread requestCreateThread = new RequestCreatePublicGroupOrChannelThread
                .Builder(ThreadType.PUBLIC_GROUP, new ArrayList<Invitee>() {{
            add(invitee);
        }}, "ssss")
                .build();

        chatController.createThread(requestCreateThread);
    }

    /**
     * seen message list
     */
    private void getSeenList() {
        RequestSeenMessageList requestSeenMessageList = new RequestSeenMessageList
                .Builder(91290)
                .build();

        chatController.seenMessageList(requestSeenMessageList);
    }

    /**
     * delivery message list
     */
    private void getDeliveryList() {
        RequestDeliveredMessageList requestDeliveredMessageList = new RequestDeliveredMessageList
                .Builder(55216)
                .build();

        chatController.deliveredMessageList(requestDeliveredMessageList);
    }

    /**
     * mute thread
     */
    private void mute() {
        RequestMuteThread requestMuteThread = new RequestMuteThread
                .Builder(4982)
                .build();

        chatController.muteThread(requestMuteThread);
    }

    /**
     * unmute thread
     */
    private void unmute() {
        RequestMuteThread requestMuteThread = new RequestMuteThread
                .Builder(4982)
                .build();

        chatController.unMuteThread(requestMuteThread);
    }


    /**
     * count unread messages
     */
    private void countUnreadMessage() {
        RequestUnreadMessageCount requestUnreadMessageCount = new RequestUnreadMessageCount
                .Builder()
                .mute(true)
                .build();

        chatController.countUnreadMessage(requestUnreadMessageCount);
    }

    private void joinThread() {
        RequestJoinThread requestJoinThread = new RequestJoinThread
                .Builder("jijiThread")
                .build();

        chatController.joinThead(requestJoinThread);
    }

    /**
     * spam thread
     */

    private void spam() {
        RequestSpam requestSpam = new RequestSpam
                .Builder(7330)
                .build();

        chatController.spam(requestSpam);
    }


    /**
     * bot message
     */

    private void interactiveMessage() {
        RequestInteract requestInteract = new RequestInteract
                .Builder(56249, "OK")
                .build();

        chatController.interactiveMessage(requestInteract);
    }


    private void pinThread() {
        RequestPinThread requestPinThread = new RequestPinThread
                .Builder(7129)
                .build();

        chatController.pinThread(requestPinThread);
    }

    private void unPinThread() {
        RequestPinThread requestPinThread = new RequestPinThread
                .Builder(5461)
                .build();

        chatController.unPinThread(requestPinThread);
    }


    private void pinMessage() {
        RequestPinMessage requestPinMessage = new RequestPinMessage
                .Builder(89288L)
                .build();

        chatController.pinMessage(requestPinMessage);
    }

    private void unPinMessage() {
        RequestPinMessage requestPinMessage = new RequestPinMessage
                .Builder(89288L)
                .build();

        chatController.unPinMessage(requestPinMessage);
    }


    private void isNameAvailable() {
        RequestIsNameAvailable requestIsNameAvailable = new RequestIsNameAvailable
                .Builder("sdf")
                .build();

        chatController.isNameAvailable(requestIsNameAvailable);
    }


    /**
     * get history
     */
    private void updateThreadInfo() {
        RequestThreadInfo requestThreadInfo = new RequestThreadInfo
                .Builder()
                .description("توضیح")
                .threadId(7149)
                .metadat("test")
                .image("img")
                .build();

        chatController.updateThreadInfo(requestThreadInfo);
    }

    /******************************************************************
     *                           PARTICIPANT                          *
     * ****************************************************************/

    /**
     * remove participant
     */
    private void removeParticipant() {
        RequestRemoveParticipants requestRemoveParticipants = new RequestRemoveParticipants
                .Builder(7308, new ArrayList<Long>() {{
            add(15503L);
        }})
                .build();

        chatController.removeParticipants(requestRemoveParticipants);
    }

    /**
     * get participant
     */
    private void getParticipant() {
        RequestThreadParticipant threadParticipant = new RequestThreadParticipant
                .Builder(7129L)
                .build();

        chatController.getThreadParticipant(threadParticipant);
    }

    /**
     * add participant
     */
    private void addParticipant() {
        RequestAddParticipants addParticipants = RequestAddParticipants
                .newBuilder()
                .threadId(7459L)
                .withUsername("SDK4BOT")
//                .threadId(7308L)
//                .withUsername("pooria")
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
                .Builder(8016, "54.jpg", TextMessageType.POD_SPACE_PICTURE, "ZNW6493RL1C8CW")
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


        RequestReplyFileMessage requestReplyFileMessage = new RequestReplyFileMessage
                .Builder("this is test",
                8016,
                118295,
                "1.txt"
                , TextMessageType.POD_SPACE_PICTURE, "ZNW6493RL1C8CW")
                .build();
        chatController.replyFileMessage(requestReplyFileMessage, null);
    }

    /**
     * 1upload image
     */

    private void uploadImage() {
        RequestUploadImage requestUploadImage = new RequestUploadImage
                .Builder("54.jpg")
                .build();
        chatController.uploadImage(requestUploadImage);
    }

    /**
     * upload file
     */
    private void uploadFile() {
        RequestUploadFile requestUploadFile = new RequestUploadFile
                .Builder("1.txt")
                .build();

        chatController.uploadFile(requestUploadFile);


    }

    /**
     * get file
     */
    private void getFile() {
        RequestGetFile requestGetFile = new RequestGetFile
                .Builder("RDFICOF8PJVNUE4M", "2.txt")
                .build();
        chatController.getFile(requestGetFile);


    }

    private void getImage() {
        RequestGetImage requestGetImage = new RequestGetImage
                .Builder("17159944353-0.8847278640747307", "C:\\Users\\Arash\\Documents\\pod-chat-java-sdk\\resultFinal.jpg")
                .build();
        chatController.getImage(requestGetImage);


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

    void registerDevice(ChatResponse<ResultThread> asyncResponse) {

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
