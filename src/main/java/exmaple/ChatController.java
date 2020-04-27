package exmaple;

import exception.ConnectionException;
import podChat.ProgressHandler;
import podChat.chat.Chat;
import podChat.chat.ChatAdapter;
import podChat.chat.ChatListener;
import podChat.mainmodel.*;
import podChat.model.*;
import podChat.requestobject.*;
import podChat.requestobject.DefineCommandBotRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Khojasteh on 7/30/2019
 */
public class ChatController extends ChatAdapter implements ChatContract.controller {

    private Chat chat;
    private ChatContract.view view;

    public ChatController(ChatContract.view view) {
        chat = Chat.init(true);

        chat.addListener(this);
        chat.addListener(new ChatListener() {
            @Override
            public void onSent(ChatResponse<ResultMessage> response) {
            }
        });

        this.view = view;
    }


    @Override
    public void isDatabaseOpen() {

    }

    @Override
    public void uploadImage(UploadImageRequest requestUploadImage) {
        chat.uploadImage(requestUploadImage);
    }

    @Override
    public void uploadFileMessage(RequestFileMessage requestFileMessage, ProgressHandler.sendFileMessage handler) {
        chat.sendFileMessage(requestFileMessage, handler);
    }

    @Override
    public void replyFileMessage(SendReplyFileMessageRequest sendReplyFileMessageRequest, ProgressHandler.sendFileMessage handler) {
        chat.replyFileMessage(sendReplyFileMessageRequest, handler);
    }


    @Override
    public void uploadFile(UploadFileRequest uploadFileRequest) {
        chat.uploadFile(uploadFileRequest);
    }

    @Override
    public void getFile(GetFileRequest getFileRequest) {
        chat.getFile(getFileRequest);
    }

    @Override
    public void getImage(GetImageRequest getImageRequest) {
        chat.getImage(getImageRequest);
    }

    @Override
    public void resendMessage(String uniqueId) {

    }

    @Override
    public void cancelMessage(String uniqueId) {

    }

    @Override
    public void retryUpload(String uniqueId) {

    }

    @Override
    public void cancelUpload(String uniqueId) {

    }

    @Override
    public void seenMessageList(GetMessageDeliveredSeenListRequest requestParam) {
        chat.getMessageSeenList(requestParam);
    }

    @Override
    public void deliveredMessageList(GetMessageDeliveredSeenListRequest requestParams) {
        chat.getMessageDeliveredList(requestParams);
    }

    @Override
    public void createThreadWithMessage(CreateThreadWithMessageRequest threadRequest) {
        chat.createThreadWithMessage(threadRequest);
    }

    @Override
    public void getThreads(GetThreadsRequest getThreadsRequest) {
        chat.getThreads(getThreadsRequest);
    }

    @Override
    public void getThreads(Integer count, Long offset, ArrayList<Integer> threadIds, String threadName,
                           long creatorCoreUserId, long partnerCoreUserId, long partnerCoreContactId, String typeCode) {
        chat.getThreads(count, offset, threadIds, threadName, creatorCoreUserId, partnerCoreUserId, partnerCoreContactId
                , typeCode);
    }

    @Override
    public void getThreads(Integer count, Long offset, ArrayList<Integer> threadIds, String threadName) {

    }

    @Override
    public void setToke(String token) {
        chat.setToken(token);
    }

    @Override
    public void connect(ConnectRequest connectRequest) throws ConnectionException {
        chat.connect(connectRequest);

    }


    @Override
    public void getUserInfo() {
        chat.getUserInfo();
    }

    @Override
    public void getHistory(History history, long threadId) {

    }

    @Override
    public void getHistory(GetHistoryRequest request) {
        chat.getHistory(request);
    }

    @Override
    public void searchHistory(NosqlListMessageCriteriaVO messageCriteriaVO) {

    }

    @Override
    public void getContact(Integer count, Long offset, String typeCode, String query) {
        chat.getContacts(count, offset, typeCode, query);

    }

    @Override
    public void getContact(GetContactsRequest request) {
        chat.getContacts(request);

    }


    @Override
    public void createThread(CreateThreadRequest createThreadRequest) {
        chat.createThread(createThreadRequest);
    }

    @Override
    public void createThreadWithFileMessage(CreateThreadWithFileRequest requestCreateThreadWithMessage) {
        chat.createThreadWithFileMessage(requestCreateThreadWithMessage);
    }

    @Override
    public void sendTextMessage(String textMessage, long threadId, Integer messageType, String metaData, String typeCode, long repliedTo, String systemMetadata) {
        chat.sendTextMessage(textMessage, threadId, messageType, metaData, typeCode, repliedTo, systemMetadata);
    }

    @Override
    public void sendTextMessage(SendTextMessageRequest sendTextMessageRequest) {
        chat.sendTextMessage(sendTextMessageRequest);
    }

    @Override
    public void replyMessage(String messageContent, long threadId, long messageId, String systemMetaData,
                             Integer messageType, String typeCode) {
        chat.replyTextMessage(messageContent, threadId, messageId, systemMetaData, messageType, typeCode);
    }


    @Override
    public void replyMessage(ReplyTextMessageRequest request) {
        chat.replyTextMessage(request);
    }

    @Override
    public void muteThread(int threadId) {

    }

    @Override
    public void muteThread(MuteUnmuteThreadRequest muteUnmuteThreadRequest) {
        chat.muteThread(muteUnmuteThreadRequest);
    }

    @Override
    public void renameThread(long threadId, String title) {

    }

    @Override
    public void unMuteThread(int threadId) {

    }

    @Override
    public void unMuteThread(MuteUnmuteThreadRequest muteUnmuteThreadRequest) {
        chat.unMuteThread(muteUnmuteThreadRequest);
    }

    @Override
    public void editMessage(int messageId, String messageContent, String metaData) {
        chat.editMessage(messageId, messageContent, metaData);
    }

    @Override
    public void editMessage(EditMessageRequest request) {
        chat.editMessage(request);
    }


    @Override
    public void getThreadParticipant(int count, Long offset, long threadId) {

    }

    @Override
    public void getThreadParticipant(GetThreadParticipantsRequest request) {
        chat.getThreadParticipants(request);
    }

    @Override
    public void getMentionedList(GetMentionedRequest getMentionedRequest) {
        chat.getMentionedMessages(getMentionedRequest);
    }

    @Override
    public void getCurrentUserRoles(GetCurrentUserRolesRequest getCurrentUserRolesRequest) {
        chat.getCurrentUserRoles(getCurrentUserRolesRequest);
    }

    @Override
    public void get(int count, Long offset, long threadId) {

    }

    @Override
    public void addContact(AddContactRequest request) {
        chat.addContact(request);
    }

    @Override
    public void removeContact(RemoveContactsRequest removeContactsRequest) {
        chat.removeContact(removeContactsRequest);
    }

    @Override
    public void searchContact(SearchContactsRequest searchContactsRequest) {
        chat.searchContacts(searchContactsRequest);
    }

    @Override
    public void block(Long contactId, Long userId, Long threadId) {

    }

    @Override
    public void block(BlockRequest request) {
        chat.block(request);
    }


    @Override
    public void unBlock(Long blockId, Long userId, Long threadId, Long contactId) {

    }

    @Override
    public void unBlock(UnBlockRequest request) {
        chat.unblock(request);
    }

    @Override
    public void spam(long threadId) {

    }

    @Override
    public void spam(SpamPrivateThreadRequest spamPrivateThreadRequest) {
        chat.spamPrivateThread(spamPrivateThreadRequest);
    }

    @Override
    public void getBlockList(Long count, Long offset) {

    }

    @Override
    public void forwardMessage(long threadId, ArrayList<Long> messageIds) {

    }

    @Override
    public void forwardMessage(ForwardMessageRequest request) {
        chat.forwardMessage(request);
    }

    @Override
    public void updateContact(UpdateContactsRequest updateContact) {
        chat.updateContact(updateContact);
    }

    @Override
    public void seenMessage(long messageId, long ownerId) {
        chat.seenMessage(messageId, ownerId);
    }

    @Override
    public void logOut() {

    }

    @Override
    public void removeParticipants(long threadId, List<Long> participantIds) {

    }

    @Override
    public void removeParticipants(RemoveParticipantsRequestModel removeParticipantsRequestModel) {
        chat.removeParticipants(removeParticipantsRequestModel);
    }

    @Override
    public void addParticipants(long threadId, List<Long> contactIds) {
        chat.addParticipants(threadId, contactIds);
    }

    @Override
    public void addParticipants(AddParticipantsRequestModel addParticipantsRequestModel) {
        chat.addParticipants(addParticipantsRequestModel);
    }

    @Override
    public void leaveThread(long threadId) {

    }

    @Override
    public void leaveThread(LeaveThreadRequest leaveThreadRequest) {
        chat.leaveThread(leaveThreadRequest);
    }

    @Override
    public void updateThreadInfo(long threadId, ThreadInfoVO threadInfoVO) {
        chat.updateThreadInfo(threadId, threadInfoVO);
    }

    @Override
    public void updateThreadInfo(UpdateThreadInfoRequest request) {
        chat.updateThreadInfo(request);
    }

    @Override
    public void deleteMessage(DeleteMessageRequest deleteMessage) {
        chat.deleteMessage(deleteMessage);
    }

    @Override
    public void deleteMultipleMessage(DeleteMultipleMessagesRequest deleteMessage) {
        chat.deleteMultipleMessage(deleteMessage);
    }

    @Override
    public void addAdmin(SetRemoveRoleRequest requestSetAdmin) {
        chat.setAdmin(requestSetAdmin);
    }

    @Override
    public void removeAdmin(SetRemoveRoleRequest requestSetAdmin) {
        chat.removeAdmin(requestSetAdmin);
    }

    @Override
    public void addAuditor(SetRemoveRoleRequest setRemoveRoleRequest) {
        chat.setAuditor(setRemoveRoleRequest);
    }

    @Override
    public void removeAuditor(SetRemoveRoleRequest setRemoveRoleRequest) {
        chat.removeAuditor(setRemoveRoleRequest);
    }


    @Override
    public void clearHistory(ClearHistoryRequest clearHistoryRequest) {
        chat.clearHistory(clearHistoryRequest);
    }

    @Override
    public void getAdminList(GetAllThreadAdminsRequest getAllThreadAdminsRequest) {
        chat.getThreadAdmins(getAllThreadAdminsRequest);
    }

    @Override
    public String startSignalMessage(SignalMsgRequest signalMsgRequest) {
        return null;
    }

    @Override
    public void stopSignalMessage(String uniqueId) {

    }

    @Override
    public void getBlockList(GetBlockedListRequest request) {
        chat.getBlockedList(request);
    }

    @Override
    public void interactiveMessage(InteractRequest request) {
        chat.interactMessage(request);
    }

    @Override
    public void pinThread(PinUnpinThreadRequest request) {
        chat.pinThread(request);
    }

    @Override
    public void unPinThread(PinUnpinThreadRequest request) {
        chat.unPinThread(request);
    }

    @Override
    public void pinMessage(PinUnpinMessageRequest request) {
        chat.pinMessage(request);
    }

    @Override
    public void unPinMessage(PinUnpinMessageRequest request) {
        chat.unPinMessage(request);
    }

    @Override
    public void updateProfile(UpdateChatProfileRequest request) {
        chat.updateChatProfile(request);
    }

    @Override
    public void isNameAvailable(IsPublicThreadNameAvailableRequest request) {
        chat.isPublicThreadNameAvailable(request);
    }

    @Override
    public void joinThead(JoinPublicThreadRequest request) {
        chat.joinPublicThread(request);
    }

    @Override
    public void countUnreadMessage(GetAllUnreadMessageCountRequest request) {
        chat.getAllUnreadMessagesCount(request);
    }

    @Override
    public void createBot(CreateBotRequest request) {
        chat.createBot(request);
    }

    @Override
    public void startBot(StartStopBotRequest request) {
        chat.startBot(request);
    }

    @Override
    public void stopBot(StartStopBotRequest request) {
        chat.stopBot(request);
    }

    @Override
    public void defineBotCommand(DefineCommandBotRequest request) {
        chat.defineBotCommand(request);
    }


    //View
    @Override
    public void onDeliver(ChatResponse<ResultMessage> chatResponse) {
        super.onDeliver(chatResponse);
        view.onGetDeliverMessage(chatResponse);
    }

    @Override
    public void onGetThread(ChatResponse<ResultThreads> thread) {
        super.onGetThread(thread);
        view.onGetThreadList(thread);
    }

    @Override
    public void onThreadInfoUpdated(ChatResponse<ResultThread> response) {
        super.onThreadInfoUpdated(response);
        view.onThreadInfoUpdated(response);
    }

    @Override
    public void onGetContacts(ChatResponse<ResultContact> outPutContact) {
        super.onGetContacts(outPutContact);
        view.onGetContacts(outPutContact);
    }

    @Override
    public void onSeen(ChatResponse<ResultMessage> chatResponse) {
        super.onSeen(chatResponse);
        view.onGetSeenMessage(chatResponse);
    }

    @Override
    public void onSent(ChatResponse<ResultMessage> chatResponse) {
        super.onSent(chatResponse);
        view.onSentMessage(chatResponse);
    }


    @Override
    public void onCreateThread(ChatResponse<ResultThread> outPutThread) {
        super.onCreateThread(outPutThread);
        view.onCreateThread(outPutThread);
    }

    @Override
    public void onGetThreadParticipant(ChatResponse<ResultParticipant> outPutParticipant) {
        super.onGetThreadParticipant(outPutParticipant);
        view.onGetThreadParticipant(outPutParticipant);
    }

    @Override
    public void onEditedMessage(ChatResponse<ResultNewMessage> chatResponse) {
        super.onEditedMessage(chatResponse);
        view.onEditMessage(chatResponse);
    }

    @Override
    public void onGetHistory(ChatResponse<ResultHistory> history) {
        super.onGetHistory(history);
        view.onGetThreadHistory(history);
    }

    @Override
    public void onMuteThread(ChatResponse<ResultMute> outPutMute) {
        super.onMuteThread(outPutMute);
        view.onMuteThread(outPutMute);
    }

    @Override
    public void onUnmuteThread(ChatResponse<ResultMute> outPutMute) {
        super.onUnmuteThread(outPutMute);
        view.onUnMuteThread(outPutMute);
    }

    @Override
    public void onRenameThread(OutPutThread outPutThread) {
        super.onRenameThread(outPutThread);
        view.onRenameGroupThread();
    }

    @Override
    public void onContactAdded(ChatResponse<ResultAddContact> chatResponse) {
        super.onContactAdded(chatResponse);
        view.onAddContact(chatResponse);
    }

    @Override
    public void onUpdateContact(ChatResponse<ResultUpdateContact> chatResponse) {
        super.onUpdateContact(chatResponse);
        view.onUpdateContact(chatResponse);
    }

    @Override
    public void onUploadFile(ChatResponse<ResultFile> response) {
        super.onUploadFile(response);
        view.onUploadFile(response);
    }

    @Override
    public void onGetFile(ChatResponse<ResultFile> response) {
        super.onGetFile(response);
        view.onGetFile(response);
    }

    @Override
    public void onGetImage(ChatResponse<ResultImageFile> response) {
        super.onGetImage(response);
        view.onGetImage(response);
    }

    @Override
    public void onUploadImageFile(ChatResponse<ResultImageFile> chatResponse) {
        super.onUploadImageFile(chatResponse);
        view.onUploadImageFile(chatResponse);
    }

    @Override
    public void onRemoveContact(ChatResponse<ResultRemoveContact> response) {
        super.onRemoveContact(response);
        view.onRemoveContact(response);
    }

    @Override
    public void onThreadAddParticipant(ChatResponse<ResultAddParticipant> outPutAddParticipant) {
        super.onThreadAddParticipant(outPutAddParticipant);
        view.onAddParticipant(outPutAddParticipant);
    }

    @Override
    public void onThreadRemoveParticipant(ChatResponse<ResultParticipant> chatResponse) {
        super.onThreadRemoveParticipant(chatResponse);
        view.onRemoveParticipant(chatResponse);
    }


    @Override
    public void onDeleteMessage(ChatResponse<ResultDeleteMessage> outPutDeleteMessage) {
        super.onDeleteMessage(outPutDeleteMessage);
        view.onDeleteMessage(outPutDeleteMessage);
    }

    @Override
    public void onThreadLeaveParticipant(ChatResponse<ResultLeaveThread> response) {
        super.onThreadLeaveParticipant(response);
        view.onLeaveThread(response);
    }

    @Override
    public void onChatState(String state) {
        view.onState(state);
    }

    @Override
    public void onNewMessage(ChatResponse<ResultNewMessage> chatResponse) {
        super.onNewMessage(chatResponse);

        ResultNewMessage result = chatResponse.getResult();
        MessageVO messageVO = result.getMessageVO();
        Participant participant = messageVO.getParticipant();
        long id = messageVO.getId();

        chat.seenMessage(id, participant.getId());

        view.onNewMessage(chatResponse);
    }

    @Override
    public void onBlock(ChatResponse<ResultBlock> outPutBlock) {
        super.onBlock(outPutBlock);
        view.onBlock(outPutBlock);
    }

    @Override
    public void onUnBlock(ChatResponse<ResultBlock> outPutBlock) {
        super.onUnBlock(outPutBlock);
        view.onUnblock(outPutBlock);
    }


    @Override
    public void onGetBlockList(ChatResponse<ResultBlockList> outPutBlockList) {
        super.onGetBlockList(outPutBlockList);
        view.onGetBlockList(outPutBlockList);
    }

    @Override
    public void OnSeenMessageList(ChatResponse<ResultParticipant> chatResponse) {
        view.OnSeenMessageList(chatResponse);
    }

    @Override
    public void onSearchContact(ChatResponse<ResultContact> chatResponse) {
        super.onSearchContact(chatResponse);
        view.onSearchContact(chatResponse);
    }

    @Override
    public void onError(ErrorOutPut error) {
        super.onError(error);
        view.onError(error);
    }

    @Override
    public void OnDeliveredMessageList(ChatResponse<ResultParticipant> chatResponse) {
        super.OnDeliveredMessageList(chatResponse);
        view.OnDeliveredMessageList(chatResponse);
    }

    @Override
    public void OnSetRole(ChatResponse<ResultSetRole> chatResponse) {
        super.OnSetRole(chatResponse);
        view.OnSetRole(chatResponse);
    }

    @Override
    public void OnInteractMessage(ChatResponse<ResultInteractMessage> chatResponse) {
        super.OnInteractMessage(chatResponse);
        view.OnInteractMessage(chatResponse);
    }

    @Override
    public void OnRemoveRole(ChatResponse<ResultSetRole> chatResponse) {
        super.OnRemoveRole(chatResponse);
        view.onRemoveRole(chatResponse);
    }

    @Override
    public void onGetCurrentUserRoles(ChatResponse<ResultCurrentUserRoles> response) {
        super.onGetCurrentUserRoles(response);
        view.onGetCurrentUserRoles(response);
    }


    @Override
    public void onCreateBot(ChatResponse<ResultCreateBot> response) {
        super.onCreateBot(response);
        view.onCreateBot(response);
    }

    @Override
    public void onStartBot(ChatResponse<ResultStartBot> response) {
        super.onStartBot(response);
        view.onStartBot(response);
    }

    @Override
    public void onStopBot(ChatResponse<ResultStartBot> response) {
        super.onStopBot(response);
        view.onStopBot(response);
    }

    @Override
    public void onDefineBotCommand(ChatResponse<ResultDefineCommandBot> response) {
        super.onDefineBotCommand(response);
        view.onDefineBotCommand(response);
    }

}

