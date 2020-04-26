package exmaple;

import exception.ConnectionException;
import podChat.ProgressHandler;
import podChat.mainmodel.*;
import podChat.model.*;
import podChat.model.BotInfoVO;
import podChat.requestobject.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Khojasteh on 7/30/2019
 */
public interface ChatContract {

    interface view {

//        default void onRecivedNotification(Notification notification) {

//        }

        default void onLogEvent(String log) {
        }

        default void onGetThreadList(ChatResponse<ResultThreads> thread) {
        }

        default void onUserInfo(ChatResponse<ResultUserInfo> thread) {
        }

        default void onGetThreadHistory(ChatResponse<ResultHistory> history) {
        }

        default void onGetCurrentUserRoles(ChatResponse<ResultCurrentUserRoles> response) {
        }

        default void onGetContacts(ChatResponse<ResultContact> response) {
        }

        default void onGetThreadParticipant(ChatResponse<ResultParticipant> response) {
        }

        default void onSentMessage(ChatResponse<ResultMessage> chatResponse) {
        }

        default void onGetDeliverMessage(ChatResponse<ResultMessage> chatResponse) {
        }

        default void onGetSeenMessage(ChatResponse<ResultMessage> response) {
        }

        default void onEditMessage(ChatResponse<ResultNewMessage> response) {
        }

        default void onDeleteMessage(ChatResponse<ResultDeleteMessage> outPutDeleteMessage) {
        }

        default void onCreateThread(ChatResponse<ResultThread> outPutThread) {
        }

        default void onMuteThread(ChatResponse<ResultMute> outPut) {
        }

        default void onJoinThread(ChatResponse<ResultThread> response) {
        }

        default void onCountUnreadMessage(ChatResponse<ResultUnreadMessageCount> response) {
        }

        default void onUnMuteThread(ChatResponse<ResultMute> response) {
        }

        default void onRenameGroupThread() {
        }

        default void onAddContact(ChatResponse<ResultAddContact> chatResponse) {
        }

        default void onUpdateContact(ChatResponse<ResultUpdateContact> chatResponse) {
        }

        default void onUpdateProfile(ChatResponse<ResultUpdateProfile> chatResponse) {
        }

        default void onUploadFile(ChatResponse<ResultFile> response) {
        }

        default void onGetFile(ChatResponse<ResultFile> response) {
        }

        default void onGetImage(ChatResponse<ResultImageFile> response) {
        }

        default void onUploadImageFile(ChatResponse<ResultImageFile> response) {
        }

        default void onRemoveContact(ChatResponse<ResultRemoveContact> response) {
        }

        default void onAddParticipant(ChatResponse<ResultAddParticipant> outPutAddParticipant) {
        }

        default void onRemoveParticipant(ChatResponse<ResultParticipant> response) {
        }

        default void onLeaveThread(ChatResponse<ResultLeaveThread> response) {
        }

        default void OnClearHistory(ChatResponse<ResultClearHistory> chatResponse) {
        }

        default void onBlock(ChatResponse<ResultBlock> outPutBlock) {
        }

        default void onUnblock(ChatResponse<ResultBlock> outPutBlock) {
        }

        default void onSearchContact(ChatResponse<ResultContact> chatResponse) {
        }

        default void onSetRole(ChatResponse<ResultSetRole> chatResponse) {
        }


        default void onRemoveRole(ChatResponse<ResultSetRole> chatResponse) {
        }

        default void onUserInfo() {

        }


        default void onSearchHisory() {
        }

        default void onState(String state) {
        }

        default void onGetBlockList(ChatResponse<ResultBlockList> outPutBlockList) {
        }

        default void onMapSearch() {
        }

//        default void onMapStaticImage(ChatResponse<ResultStaticMapImage> chatResponse) {
//        }

        default void onMapRouting() {
        }

        default void onMapReverse() {
        }

        default void onError(ErrorOutPut error) {
        }

        default void onSpam() {
        }

        default void onNewMessage(ChatResponse<ResultNewMessage> chatResponse) {
        }

        default void OnDeliveredMessageList(ChatResponse<ResultParticipant> response) {
        }

        default void OnSeenMessageList(ChatResponse<ResultParticipant> response) {
        }

        default void OnSetRole(ChatResponse<ResultSetRole> chatResponse) {
        }

        default void OnInteractMessage(ChatResponse<ResultInteractMessage> chatResponse) {
        }

        default void onPinThread(ChatResponse<ResultPinThread> chatResponse) {
        }

        default void onUnPinThread(ChatResponse<ResultPinThread> chatResponse) {
        }

        default void onIsNameAvailable(ChatResponse<ResultIsNameAvailable> chatResponse) {
        }

        default void onPinMessage(ChatResponse<ResultPinMessage> chatResponse) {
        }

        default void onUnPinMessage(ChatResponse<ResultPinMessage> chatResponse) {
        }

        default void onThreadInfoUpdated(ChatResponse<ResultThread> chatResponse) {
        }

        default void onCreateBot(ChatResponse<ResultCreateBot> chatResponse) {
        }
        default void onStartBot(ChatResponse<ResultStartBot> chatResponse) {
        }

        default void onDefineBotCommand (ChatResponse<BotInfoVO> chatResponse) {
        }

    }

    interface controller {

        //  void sendLocationMessage(RequestLocationMessage request);

        void isDatabaseOpen();

        void uploadImage(UploadImageRequest requestUploadImage);

        void uploadFileMessage(RequestFileMessage requestFileMessage, ProgressHandler.sendFileMessage handler);

        void replyFileMessage(SendReplyFileMessageRequest sendReplyFileMessageRequest, ProgressHandler.sendFileMessage handler);

        void uploadFile(UploadFileRequest uploadFileRequest);

        void getFile(GetFileRequest getFileRequest);

        //void retryUpload(RetryUpload retry, ProgressHandler.sendFileMessage handler);
        void getImage(GetImageRequest getImageRequest);

        void resendMessage(String uniqueId);

        void cancelMessage(String uniqueId);

        void retryUpload(String uniqueId);

        void cancelUpload(String uniqueId);

        void seenMessageList(GetMessageDeliveredSeenListRequest requestParam);

        void deliveredMessageList(GetMessageDeliveredSeenListRequest requestParams);

        void createThreadWithMessage(CreateThreadWithMessageRequest threadRequest);

//        String createThread(int threadType, Invitee[] invitee, String threadTitle, String description, String image
//                , String metadata);


        void getThreads(GetThreadsRequest getThreadsRequest);

        void getThreads(Integer count, Long offset, ArrayList<Integer> threadIds, String threadName,

                        long creatorCoreUserId, long partnerCoreUserId, long partnerCoreContactId, String typeCode);

        void getThreads(Integer count, Long offset, ArrayList<Integer> threadIds, String threadName);

        void setToke(String token);

        void connect(ConnectRequest connectRequest) throws ConnectionException;

        void getUserInfo();

        void getHistory(History history, long threadId);

        void getHistory(GetHistoryRequest request);

        void searchHistory(NosqlListMessageCriteriaVO messageCriteriaVO);

        void getContact(Integer count, Long offset, String typeCode, String query);

        void getContact(GetContactsRequest request);

        void createThread(CreateThreadRequest createThreadRequest);

        void createThreadWithFileMessage(CreateThreadWithFileRequest requestCreateThreadWithMessage);

        void sendTextMessage(String textMessage, long threadId, Integer messageType, String metaData, String typeCode, long repliedTo, String systemMetadata);

        void sendTextMessage(SendTextMessageRequest sendTextMessageRequest);

        void replyMessage(String messageContent, long threadId, long messageId, String systemMetaData
                , Integer messageType, String typeCode);

        // void replyFileMessage(RequestReplyFileMessage request, ProgressHandler.sendFileMessage handler);

        void replyMessage(ReplyTextMessageRequest request);

        void muteThread(int threadId);

        void muteThread(MuteUnmuteThreadRequest muteUnmuteThreadRequest);

        void renameThread(long threadId, String title);

        void unMuteThread(int threadId);

        void unMuteThread(MuteUnmuteThreadRequest muteUnmuteThreadRequest);

        void editMessage(int messageId, String messageContent, String metaData);

        void editMessage(EditMessageRequest request);

        void getThreadParticipant(int count, Long offset, long threadId);

        void getThreadParticipant(GetThreadParticipantsRequest request);

        void getMentionedList(GetMentionedRequest getMentionedRequest);

        void getCurrentUserRoles(GetCurrentUserRolesRequest getCurrentUserRolesRequest);

        void get(int count, Long offset, long threadId);

        void addContact(AddContactRequest request);

        void removeContact(RemoveContactsRequest removeContactsRequest);


        void searchContact(SearchContactsRequest searchContactsRequest);

        void block(Long contactId, Long userId, Long threadId);

        void block(BlockRequest request);

        void unBlock(Long blockId, Long userId, Long threadId, Long contactId);

        void unBlock(UnBlockRequest request);

        void spam(long threadId);

        void spam(SpamPrivateThreadRequest spamPrivateThreadRequest);

        void getBlockList(Long count, Long offset);

        // String sendFileMessage(Context context, Activity activity, String description, long threadId, Uri fileUri, String metaData, Integer messageType, ProgressHandler.sendFileMessage handler);

        //  void sendFileMessage(RequestFileMessage requestFileMessage, ProgressHandler.sendFileMessage handler);

        //  void syncContact(Activity activity);

        void forwardMessage(long threadId, ArrayList<Long> messageIds);

        void forwardMessage(ForwardMessageRequest request);

        void updateContact(UpdateContactsRequest updateContact);

        //  void uploadImage(Activity activity, Uri fileUri);

        //   void uploadFile(Activity activity, Uri uri);

        void seenMessage(long messageId, long ownerId);

        void logOut();

        void removeParticipants(long threadId, List<Long> participantIds);

        void removeParticipants(RemoveParticipantsRequestModel removeParticipantsRequestModel);

        void addParticipants(long threadId, List<Long> contactIds);

        void addParticipants(AddParticipantsRequestModel addParticipantsRequestModel);

        void leaveThread(long threadId);

        void leaveThread(LeaveThreadRequest threadId);

        void updateThreadInfo(long threadId, ThreadInfoVO threadInfoVO);

        void updateThreadInfo(UpdateThreadInfoRequest request);

        void deleteMessage(DeleteMessageRequest deleteMessage);

        void deleteMultipleMessage(DeleteMultipleMessagesRequest deleteMessage);

//        void uploadImageProgress(Context context, Activity activity, Uri fileUri, ProgressHandler.onProgress handler);
//
//        void uploadFileProgress(Context context, Activity activity, Uri fileUri, ProgressHandler.onProgressFile handler);

        void addAdmin(SetRemoveRoleRequest requestSetAdmin);

        void removeAdmin(SetRemoveRoleRequest requestSetAdmin);

        void addAuditor(SetRemoveRoleRequest setRemoveRoleRequest);

        void removeAuditor(SetRemoveRoleRequest setRemoveRoleRequest);

        void clearHistory(ClearHistoryRequest clearHistoryRequest);

        void getAdminList(GetAllThreadAdminsRequest getAllThreadAdminsRequest);

        String startSignalMessage(SignalMsgRequest signalMsgRequest);

        void stopSignalMessage(String uniqueId);

        void getBlockList(GetBlockedListRequest request);

        void interactiveMessage(InteractRequest request);

        void pinThread(PinUnpinThreadRequest request);

        void unPinThread(PinUnpinThreadRequest request);

        void pinMessage(PinUnpinMessageRequest request);

        void unPinMessage(PinUnpinMessageRequest request);

        void updateProfile(UpdateChatProfileRequest request);

        void isNameAvailable(IsPublicThreadNameAvailableRequest request);

        void joinThead(JoinPublicThreadRequest request);

        void countUnreadMessage(GetAllUnreadMessageCountRequest request);

        void createBot(CreateBotRequest request);

        void startBot(StartBotRequest request);

        void stopBot(StartBotRequest request);

        void defineBotCommand(podChat.requestobject.BotInfoVO request);
    }
}
