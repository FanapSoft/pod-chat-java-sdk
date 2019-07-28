package podChat.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.sentry.Sentry;
import podAsync.Async;
import podAsync.AsyncAdapter;
import podChat.mainmodel.*;
import podChat.model.Error;
import podChat.model.*;
import podChat.networking.api.ContactApi;
import podChat.networking.retrofithelper.RetrofitUtil;
import podChat.util.*;
import util.log.ChatLogger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Chat extends AsyncAdapter {
    private static Async async;
    private static Gson gson;
    private String token;
    private static Chat instance;
    private String platformHost;
    private static ChatListenerManager listenerManager;
    private long userId;
    private ContactApi contactApi;
    private static HashMap<String, Callback> messageCallbacks;
    private static HashMap<Long, ArrayList<Callback>> threadCallbacks;
    private static HashMap<String, ChatHandler> handlerSend;
    private boolean syncContact = false;
    private boolean state = false;
    private long lastSentMessageTime;
    private boolean chatState = false;
    private boolean chatReady = false;
    private boolean asyncReady = false;
    private static final int TOKEN_ISSUER = 1;
    private boolean currentDeviceExist;
    private String fileServer;
    private boolean syncContacts = false;
    private int lastOffset;
    private List<Contact> contactList;
    private int getUserInfoRetryCount;
    private int getUserInfoRetry = 5;

    /**
     * Initialize the Chat
     **/
    public static Chat init(boolean useSentry, boolean isLoggable) {

        if (instance == null) {
            //TODO
            if (useSentry) Sentry.init("");

            ChatLogger.isLoggable = isLoggable;
            gson = new Gson();

            async = Async.getInstance();
            instance = new Chat();

            listenerManager = new ChatListenerManager();
        }
        return instance;
    }

    /**
     * Connect to the Async .
     *
     * @param socketAddress {**REQUIRED**}
     * @param platformHost  {**REQUIRED**}
     * @param severName     {**REQUIRED**}
     * @param appId         {**REQUIRED**}
     * @param token         {**REQUIRED**}
     * @param fileServer    {**REQUIRED**}
     * @param ssoHost       {**REQUIRED**}
     */
    public void connect(String socketAddress, String appId, String severName, String token,
                        String ssoHost, String platformHost, String fileServer) {
//        Looper.prepare();
        if (platformHost.endsWith("/")) {
            PingExecutor.getInstance();

            messageCallbacks = new HashMap<>();
            threadCallbacks = new HashMap<>();
            handlerSend = new HashMap<>();

            async.addListener(this);

            contactApi = RetrofitUtil.getInstance().create(ContactApi.class);

            setPlatformHost(platformHost);
            setToken(token);
            setFileServer(fileServer);

            gson = new GsonBuilder().create();
            async.connect(socketAddress, appId, severName, token, ssoHost, "");
//            deviceIdRequest(ssoHost, socketAddress, appId, severName);
            state = true;
        } else {
            String jsonError = getErrorOutPut("PlatformHost " + ChatConstant.ERROR_CHECK_URL, ChatConstant.ERROR_CODE_CHECK_URL);
            ChatLogger.error(jsonError);
        }
    }

    /**
     * When state of the Async changed then the chat ping is stopped buy (chatState)flag
     */
    @Override
    public void onStateChanged(String state) throws IOException {
        super.onStateChanged(state);

        listenerManager.callOnChatState(state);

        switch (state) {
            case ChatStateType.OPEN:
                chatState = true;
                ping();
                break;

            case ChatStateType.ASYNC_READY:
                asyncReady = true;
                getUserInfo(null);
                break;

            case ChatStateType.CONNECTING:
            case ChatStateType.CLOSING:
            case ChatStateType.CLOSED:
                chatState = false;
                chatReady = false;
                break;
        }
    }

    /**
     * First we check the message type and then we set the
     * the  specific callback for that
     */
    @Override
    public void onReceivedMessage(String textMessage) throws IOException {
        super.onReceivedMessage(textMessage);
        int messageType = 0;
        ChatMessage chatMessage = gson.fromJson(textMessage, ChatMessage.class);

        String messageUniqueId = chatMessage.getUniqueId();
        long threadId = chatMessage.getSubjectId();
        Callback callback = messageCallbacks.get(messageUniqueId);

        if (chatMessage != null) {
            messageType = chatMessage.getType();

            switch (messageType) {
                case ChatMessageType.ADD_PARTICIPANT:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.UNBLOCK:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.BLOCK:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.CHANGE_TYPE:
                    break;
                case ChatMessageType.SENT:
                    handleSent(chatMessage, messageUniqueId, threadId);
                    break;
                case ChatMessageType.DELIVERY:
                    handleDelivery(chatMessage, messageUniqueId, threadId);
                    break;
                case ChatMessageType.SEEN:
                    handleSeen(chatMessage, messageUniqueId, threadId);
                    break;
                case ChatMessageType.ERROR:
                    handleError(chatMessage);
                    break;
                case ChatMessageType.FORWARD_MESSAGE:
                    handleForwardMessage(chatMessage);
                    break;
                case ChatMessageType.GET_CONTACTS:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.GET_HISTORY:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.GET_STATUS:
                    break;
                case ChatMessageType.GET_THREADS:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.INVITATION:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.REMOVED_FROM_THREAD:
                    //TODO removed threadVo
                    break;
                case ChatMessageType.LEAVE_THREAD:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.MESSAGE:
                    handleNewMessage(chatMessage);
                    break;
                case ChatMessageType.MUTE_THREAD:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.PING:

                    break;
                case ChatMessageType.RELATION_INFO:
                    break;
                case ChatMessageType.REMOVE_PARTICIPANT:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.RENAME:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.THREAD_PARTICIPANTS:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.UN_MUTE_THREAD:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.USER_INFO:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.USER_STATUS:
                    break;
                case ChatMessageType.GET_BLOCKED:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.DELETE_MESSAGE:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.EDIT_MESSAGE:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
                case ChatMessageType.THREAD_INFO_UPDATED:
                    OutPutInfoThread outPutInfoThread = new OutPutInfoThread();

                    ResultThread resultThread = new ResultThread();

                    ThreadVo threadVo = gson.fromJson(chatMessage.getContent(), ThreadVo.class);

                    resultThread.setThreadVo(threadVo);

                    outPutInfoThread.setResult(resultThread);
                    listenerManager.callOnThreadInfoUpdated(chatMessage.getContent());
                    break;
                case ChatMessageType.LAST_SEEN_UPDATED:
                    listenerManager.callOnLastSeenUpdated(chatMessage.getContent());
                    break;
                case ChatMessageType.UPDATE_THREAD_INFO:
                    handleResponseMessage(callback, chatMessage, messageUniqueId);
                    break;
            }
        }
    }


    /**
     * Send text message to the thread
     *
     * @param textMessage  String that we want to sent to the thread
     * @param threadId     Id of the destination thread
     * @param JsonMetaData It should be Json,if you don't have metaData you can set it to "null"
     */
    public void sendTextMessage(String textMessage, long threadId, String JsonMetaData, ChatHandler handler) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(textMessage);
        chatMessage.setType(ChatMessageType.MESSAGE);
        chatMessage.setTokenIssuer("1");
        chatMessage.setToken(getToken());

        if (JsonMetaData != null) {
            chatMessage.setSystemMetadata(JsonMetaData);
        }

        String uniqueId = getUniqueId();
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setTime(1000);
        chatMessage.setSubjectId(threadId);

        String asyncContent = gson.toJson(chatMessage);
        setThreadCallbacks(threadId, uniqueId);

//        handler.onSent(uniqueId, threadId, null);
        handler.onSent(uniqueId, threadId);
        handler.onSentResult(null);
        handlerSend.put(uniqueId, handler);

        sendAsyncMessage(asyncContent, 4, "SEND_TEXT_MESSAGE");
    }

    /*  /**
     * First we get the contact from server then at the respond of that
     * {@link #handleSyncContact(ChatMessage)} we add all of the PhoneContact that get from
     * {@link #getPhoneContact(Context)} that's not in the list of serverContact.
     */
//    public void syncContact(Context context, Activity activity) {
//        if (Permission.Check_READ_CONTACTS(activity)) {
//            syncContact = true;
//
//            getContacts(50, 0L, null);
//            setContext(context);
//            lastOffset = 0;
//            contactList = new ArrayList<>();
//        } else {
//            String jsonError = getErrorOutPut(ChatConstant.ERROR_READ_CONTACT_PERMISSION, ChatConstant.ERROR_CODE_READ_CONTACT_PERMISSION);
//            if (BuildConfig.DEBUG) Logger.e(jsonError);
//        }
//    }

    /* /**
     * This method first check the type of the file and then choose the right
     * server and send that
     *
     * @param description Its the description that you want to send with file in the thread
     * @param fileUri     Uri of the file that you want to send to thread
     * @param threadId    Id of the thread that you want to send file
     * @param metaData    [optional]
     */
//    public void sendFileMessage(Context context, Activity activity, String description, long threadId, Uri fileUri, String metaData) {
//
//        File file = new File(fileUri.getPath());
//        String mimeType = handleMimType(fileUri, file);
//
//        if (mimeType.equals("image/png") || mimeType.equals("image/jpeg")) {
//            sendImageFileMessage(context, activity, description, threadId, fileUri, mimeType, metaData);
//        } else {
//            String path = FilePick.getSmartFilePath(context, fileUri);
//            uploadFileMessage(activity, description, threadId, mimeType, path, metaData);
//        }
//    }

//    private String handleMimType(Uri uri, File file) {
//        String mimType;
//
//        if (context.getContentResolver().getType(uri) != null) {
//            mimType = context.getContentResolver().getType(uri);
//        } else {
//            MimeTypeMap mime = MimeTypeMap.getSingleton();
//            int index = file.getName().lastIndexOf('.') + 1;
//            String ext = file.getName().substring(index).toLowerCase();
//            mimType = mime.getMimeTypeFromExtension(ext);
//        }
//        return mimType;
//    }


    //    public void uploadImage(Context context, Activity activity, Uri fileUri) {
//        if (fileServer != null) {
//            if (Permission.Check_READ_STORAGE(activity)) {
//                String mimeType = context.getContentResolver().getType(fileUri);
//                RetrofitHelperFileServer retrofitHelperFileServer = new RetrofitHelperFileServer(getFileServer());
//                FileApi fileApi = retrofitHelperFileServer.getService(FileApi.class);
//                File file = new File(getRealPathFromURI(context, fileUri));
//                if (mimeType.equals("image/png") || mimeType.equals("image/jpeg")) {
//
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//
//                    Observable<Response<FileImageUpload>> uploadObservable = fileApi.sendImageFile(body, getToken(), TOKEN_ISSUER, name);
//                    uploadObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<FileImageUpload>>() {
//                        @Override
//                        public void call(Response<FileImageUpload> fileUploadResponse) {
//                            if (fileUploadResponse.isSuccessful()) {
//                                boolean hasError = fileUploadResponse.body().isHasError();
//                                if (hasError) {
//                                    String errorMessage = fileUploadResponse.body().getErrorMessage();
//                                    int errorCode = fileUploadResponse.body().getErrorCode();
//                                    String jsonError = getErrorOutPut(errorMessage, errorCode);
//                                    if (BuildConfig.DEBUG) Logger.e(jsonError);
//                                } else {
//                                    FileImageUpload fileImageUpload = fileUploadResponse.body();
//                                    String imageJson = JsonUtil.getJson(fileImageUpload);
//                                    listenerManager.callOnUploadImageFile(imageJson, fileImageUpload);
//                                    if (BuildConfig.DEBUG) Logger.json(imageJson);
//                                }
//                            }
//                        }
//                    }, throwable -> Logger.e(throwable.getMessage()));
//                } else {
//                    String jsonError = getErrorOutPut(ChatConstant.ERROR_NOT_IMAGE, ChatConstant.ERROR_CODE_NOT_IMAGE);
//                    if (BuildConfig.DEBUG) Logger.e(jsonError);
//                }
//            } else {
//                String jsonError = getErrorOutPut(ChatConstant.ERROR_READ_EXTERNAL_STORAGE_PERMISSION, ChatConstant.ERROR_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
//                if (BuildConfig.DEBUG) Logger.e(jsonError);
//            }
//        } else {
//            if (BuildConfig.DEBUG) Logger.e("FileServer url Is null");
//        }
//
//    }
//
//    public void uploadFile(Context context, Activity activity, String fileUri, Uri uri) {
//        if (Permission.Check_READ_STORAGE(activity)) {
//            if (getFileServer() != null) {
//                String mimeType = context.getContentResolver().getType(uri);
//                File file = new File(fileUri);
//                RetrofitHelperFileServer retrofitHelperFileServer = new RetrofitHelperFileServer(getFileServer());
//                FileApi fileApi = retrofitHelperFileServer.getService(FileApi.class);
//                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//                RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//                Observable<Response<FileUpload>> uploadObservable = fileApi.sendFile(body, getToken(), TOKEN_ISSUER, name);
//                uploadObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<FileUpload>>() {
//                    @Override
//                    public void call(Response<FileUpload> fileUploadResponse) {
//                        if (fileUploadResponse.isSuccessful()) {
//                            boolean hasError = fileUploadResponse.body().isHasError();
//                            if (hasError) {
//                                String errorMessage = fileUploadResponse.body().getMessage();
//                                int errorCode = fileUploadResponse.body().getErrorCode();
//                                String jsonError = getErrorOutPut(errorMessage, errorCode);
//                                if (BuildConfig.DEBUG) Logger.e(jsonError);
//                            } else {
//                                FileUpload result = fileUploadResponse.body();
//                                String json = JsonUtil.getJson(result);
//                                listenerManager.callOnUploadFile(json);
//                                if (BuildConfig.DEBUG) Logger.json(json);
//                            }
//                        }
//                    }
//                }, throwable -> {
//                    if (BuildConfig.DEBUG) Logger.e(throwable.getMessage());
//                });
//            } else {
//                if (BuildConfig.DEBUG) Logger.e("FileServer url Is null");
//            }
//
//        } else {
//            String jsonError = getErrorOutPut(ChatConstant.ERROR_READ_EXTERNAL_STORAGE_PERMISSION, ChatConstant.ERROR_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
//            if (BuildConfig.DEBUG) Logger.e(jsonError);
//        }
//    }
//
//    public String getFile(long fileId, String hashCode, boolean downloadable) {
//        final String[] url = new String[1];
//        if (cache) {
//            FileMetaDataContent cachedFile = messageDatabaseHelper.getFile(fileId);
//            if (cachedFile != null) {
//                if (cachedFile.getLocalPath() != null || !cachedFile.getLocalPath().equals("")) {
//                    File file = new File(cachedFile.getLocalPath());
//                    if (file.exists()) {
//                        return cachedFile.getLocalPath();
//                    } else {
//                        RetrofitHelperFileServer retrofitHelperFileServer = new RetrofitHelperFileServer(getFileServer());
//                        FileApi fileApi = retrofitHelperFileServer.getService(FileApi.class);
//
//                        Call<ResponseBody> downloadFile = fileApi.downloadFile(cachedFile.getLink());
//                        downloadFile.enqueue(new retrofit2.Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                if (response.isSuccessful()) {
//                                    WriteFileToDisk writeFileToDisk = new WriteFileToDisk(response.body(), cachedFile.getName(), getContext());
//                                    writeFileToDisk.execute();
//                                    url[0] = writeFileToDisk.getLocalPath();
////                                    messageDatabaseHelper.saveFile();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                            }
//                        });
//
//                    }
//                } else {
//                    url[0] = getFileServer() + "nzh/file/" + "?fileId=" + cachedFile.getId() + "&downloadable=" + downloadable + "&hashCode=" + cachedFile.getHashCode();
//                }
//            }
//
//        } else {
//            url[0] = getFileServer() + "nzh/file/" + "?fileId=" + fileId + "&downloadable=" + downloadable + "&hashCode=" + hashCode;
//        }
//
//        return url[0];
//    }
//
//    public String getImage(long imageId, String hashCode, boolean downloadable) {
//        final String[] url = new String[1];
//        try {
//            if (cache) {
//                FileImageMetaData cachedFile = messageDatabaseHelper.getImageFile(imageId);
//                if (cachedFile != null) {
//                    if (cachedFile.getLocalPath() != null || !cachedFile.getLocalPath().equals("")) {
//                        File file = new File(cachedFile.getLocalPath());
//                        if (file.exists()) {
//                            return cachedFile.getLocalPath();
//                        } else {
//                            RetrofitHelperFileServer retrofitHelperFileServer = new RetrofitHelperFileServer(getFileServer());
//                            FileApi fileApi = retrofitHelperFileServer.getService(FileApi.class);
//
//                            Call<ResponseBody> downloadFile = fileApi.downloadFile(cachedFile.getLink());
//                            downloadFile.enqueue(new retrofit2.Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    if (response.isSuccessful()) {
//                                        WriteFileToDisk writeFileToDisk = new WriteFileToDisk(response.body(), cachedFile.getName(), getContext());
//                                        writeFileToDisk.execute();
//                                        url[0] = writeFileToDisk.getLocalPath();
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                }
//                            });
//                        }
//                    } else {
//                        url[0] = getFileServer() + "nzh/file/" + "?fileId=" + cachedFile.getId() + "&downloadable=" + downloadable + "&hashCode=" + cachedFile.getHashCode();
//                    }
//                }
//
//            } else {
//                url[0] = getFileServer() + "nzh/file/" + "?fileId=" + imageId + "&downloadable=" + downloadable + "&hashCode=" + hashCode;
//            }
//        } catch (Exception e) {
//
//        }
//
//
//        return url[0];
//    }
    private String getErrorOutPut(String errorMessage, long errorCode) {
        ErrorOutPut error = new ErrorOutPut(true, errorMessage, errorCode);

        String jsonError = gson.toJson(error);

        listenerManager.callOnError(jsonError, error);

        return jsonError;
    }

    /**
     * Remove the peerId and send ping again but this time
     * peerId that was set in the server was removed
     */
    public void logOutSocket() {
        async.logOut();
    }

    /**
     * Notice : You should consider that this method is for rename group and you have to be the admin
     * to change the thread name if not you don't have the Permission
     */
//    @Deprecated
//    public void renameThread(long threadId, String title, ChatHandler handler) {
//        String uniqueId = getUniqueId();
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setType(ChatMessageType.RENAME);
//        chatMessage.setSubjectId(threadId);
//        chatMessage.setContent(title);
//        chatMessage.setToken(getToken());
//        chatMessage.setTokenIssuer("1");
//        chatMessage.setUniqueId(uniqueId);
//
//        setCallBacks(null, null, null, true, ChatMessageType.RENAME, null, uniqueId);
//
//        String asyncContent = gson.toJson(chatMessage);
//
//        sendAsyncMessage(asyncContent, 4);
//
//        handler.onRenameThread(uniqueId);
//    }

    /**
     * @param contactIds List of CONTACT IDs
     * @param threadId   Id of the thread that you are {*NOTICE*}admin of that and you want to
     *                   add someone as a participant.
     */
    public void addParticipants(long threadId, List<Long> contactIds, ChatHandler handler) {

        String uniqueId = getUniqueId();

        AddParticipant addParticipant = new AddParticipant();
        addParticipant.setSubjectId(threadId);
        addParticipant.setUniqueId(uniqueId);

        JsonArray contacts = new JsonArray();

        for (Long p : contactIds) {
            contacts.add(p);
        }
        addParticipant.setContent(contacts.toString());
        addParticipant.setSubjectId(threadId);
        addParticipant.setToken(getToken());
        addParticipant.setTokenIssuer("1");
        addParticipant.setUniqueId(uniqueId);

        addParticipant.setType(ChatMessageType.ADD_PARTICIPANT);
        String asyncContent = gson.toJson(addParticipant);

        setCallBacks(null, null, null, true, ChatMessageType.ADD_PARTICIPANT, null, uniqueId);

        sendAsyncMessage(asyncContent, 4, "SEND_ADD_PARTICIPANTS");

        handler.onAddParticipants(uniqueId);
    }

    /**
     * @param participantIds List of PARTICIPANT IDs from ThreadVo's Participants object
     * @param threadId       Id of the thread that we wants to remove their participant
     */
//    public void removeParticipants(long threadId, List<Long> participantIds, ChatHandler handler) {
//
//        String uniqueId = getUniqueId();
//        RemoveParticipant removeParticipant = new RemoveParticipant();
//        removeParticipant.setTokenIssuer("1");
//        removeParticipant.setType(Constants.REMOVE_PARTICIPANT);
//        removeParticipant.setSubjectId(threadId);
//        removeParticipant.setToken(getToken());
//        removeParticipant.setUniqueId(uniqueId);
//
//        JsonArray contacts = new JsonArray();
//        for (Long p : participantIds) {
//            contacts.add(p);
//        }
//        removeParticipant.setContent(contacts.toString());
//
//        String asyncContent = JsonUtil.getJson(removeParticipant);
//        sendAsyncMessage(asyncContent, 4, "SEND_REMOVE_PARTICIPANT");
//        setCallBacks(null, null, null, true, Constants.REMOVE_PARTICIPANT, null, uniqueId);
//        handler.onRemoveParticipants(uniqueId);
//    }

//    public void leaveThread(long threadId, ChatHandler handler) {
//        String uniqueId = getUniqueId();
//        RemoveParticipant removeParticipant = new RemoveParticipant();
//
//        removeParticipant.setSubjectId(threadId);
//        removeParticipant.setToken(getToken());
//        removeParticipant.setTokenIssuer("1");
//        removeParticipant.setUniqueId(uniqueId);
//        removeParticipant.setType(Constants.LEAVE_THREAD);
//
//        setCallBacks(null, null, null, true, Constants.LEAVE_THREAD, null, uniqueId);
//        String json = JsonUtil.getJson(removeParticipant);
//        sendAsyncMessage(json, 4, "SEND_LEAVE_THREAD");
//        handler.onLeaveThread(uniqueId);
//    }

    /**
     * forward message
     *
     * @param threadId   destination thread id
     * @param messageIds Array of message ids that we want to forward them
     */
//    public void forwardMessage(long threadId, ArrayList<Long> messageIds) {
//        ChatMessageForward chatMessageForward = new ChatMessageForward();
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayList<String> uniqueIds = new ArrayList<>();
//        chatMessageForward.setSubjectId(threadId);
//        ArrayList<Callback> callbacks = new ArrayList<>();
//
//        for (int i = 0; i < messageIds.size(); i++) {
//            String uniqueId = getUniqueId();
//            uniqueIds.add(uniqueId);
//            Callback callback = new Callback();
//            callback.setDelivery(true);
//            callback.setSeen(true);
//            callback.setSent(true);
//            callback.setUniqueId(uniqueId);
//            callbacks.add(callback);
//        }
//        threadCallbacks.put(threadId, callbacks);
//        try {
//            chatMessageForward.setUniqueId(mapper.writeValueAsString(uniqueIds));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        chatMessageForward.setContent(messageIds.toString());
//        chatMessageForward.setToken(getToken());
//        chatMessageForward.setTokenIssuer("1");
//        chatMessageForward.setType(Constants.FORWARD_MESSAGE);
//
//        String asyncContent = JsonUtil.getJson(chatMessageForward);
//        sendAsyncMessage(asyncContent, 4, "SEND_FORWARD_MESSAGE");
//    }
//
//    /**
//     * Reply the message in the current thread and send az message and receive at the
//     *
//     * @param messageContent content of the reply message
//     * @param threadId       id of the thread
//     * @param messageId      of that message we want to reply
//     */
//    public void replyMessage(String messageContent, long threadId, long messageId, ChatHandler handler) {
//        String uniqueId = getUniqueId();
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setUniqueId(uniqueId);
//        chatMessage.setRepliedTo(messageId);
//        chatMessage.setSubjectId(threadId);
//        chatMessage.setTokenIssuer("1");
//        chatMessage.setToken(getToken());
//        chatMessage.setContent(messageContent);
//        chatMessage.setTime(1000);
//        chatMessage.setType(Constants.MESSAGE);
//        JsonAdapter<ChatMessage> chatMessageJsonAdapter = moshi.adapter(ChatMessage.class);
//        String asyncContent = chatMessageJsonAdapter.toJson(chatMessage);
//
//        setThreadCallbacks(threadId, uniqueId);
//        sendAsyncMessage(asyncContent, 4, "SEND_REPLY_MESSAGE");
//        handler.onReplyMessage(uniqueId);
//    }
//
//    /**
//     * DELETE MESSAGE IN THREAD
//     *
//     * @param messageId    Id of the message that you want to be removed.
//     * @param deleteForAll If you want to delete message for everyone you can set it true if u dont want
//     *                     you can set it false or even null.
//     */
//    public void deleteMessage(long messageId, Boolean deleteForAll, ChatHandler handler) {
//        deleteForAll = deleteForAll != null ? deleteForAll : false;
//        String uniqueId = getUniqueId();
//        BaseMessage baseMessage = new BaseMessage();
//        DeleteMessage deleteMessage = new DeleteMessage();
//        deleteMessage.setDeleteForAll(deleteForAll);
//        String content = JsonUtil.getJson(deleteMessage);
//        baseMessage.setContent(content);
//        baseMessage.setSubjectId(messageId);
//        baseMessage.setToken(getToken());
//        baseMessage.setTokenIssuer("1");
//        baseMessage.setType(Constants.DELETE_MESSAGE);
//        baseMessage.setUniqueId(uniqueId);
//
//        String asyncContent = JsonUtil.getJson(baseMessage);
//        sendAsyncMessage(asyncContent, 4, "SEND_DELETE_MESSAGE");
//        setCallBacks(null, null, null, true, Constants.DELETE_MESSAGE, null, uniqueId);
//        handler.onDeleteMessage(uniqueId);
//    }


    /**
     * Get the list of threads or you can just pass the thread id that you want
     *
     * @param count  number of thread
     * @param offset specified offset you want
     */
    public void getThreads(Integer count, Long offset, ArrayList<Integer> threadIds, String threadName, ChatHandler handler) {

        String uniqueId = null;
        String asyncContent = null;

        if (chatReady) {
            ChatMessageContent chatMessageContent = new ChatMessageContent();

            Long offsets = offset;
            if (count == null) {
                chatMessageContent.setCount(50);
            } else {
                chatMessageContent.setCount(count);
            }

            if (offset == null) {
                chatMessageContent.setOffset(0);
                offsets = 0L;
            } else {
                chatMessageContent.setOffset(offset);
            }

            if (threadName != null) {
                chatMessageContent.setName(threadName);
            }
            if (threadIds != null) {
                chatMessageContent.setThreadIds(threadIds);
            }
            String content = gson.toJson(chatMessageContent);

            uniqueId = getUniqueId();

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent(content);
            chatMessage.setType(ChatMessageType.GET_THREADS);
            chatMessage.setTokenIssuer("1");
            chatMessage.setToken(getToken());
            chatMessage.setUniqueId(uniqueId);


            asyncContent = gson.toJson(chatMessage);

            setCallBacks(null, null, null, true, ChatMessageType.GET_THREADS, offsets, uniqueId);
        }

        sendAsyncMessage(asyncContent, 3, "Get thread send");
        if (handler != null) {
            handler.onGetThread(uniqueId);
        }
    }

    /**
     * Get history of the thread
     * <p>
     * count    count of the messages
     * order    If order is empty [default = desc] and also you have two option [ asc | desc ]
     * lastMessageId
     * FirstMessageId
     *
     * @param threadId Id of the thread that we want to get the history
     */
    public void getHistory(History history, long threadId, ChatHandler handler) {


        long offsets = history.getOffset();

        if (history.getCount() != 0) {
            history.setCount(history.getCount());
        } else {
            history.setCount(50L);
        }

        if (history.getOffset() != 0) {
            history.setOffset(history.getOffset());
        } else {
            history.setOffset(0L);
            offsets = 0;
        }

        Gson gson = new GsonBuilder().create();
        JsonObject jObj = (JsonObject) gson.toJsonTree(history);

        if (history.getLastMessageId() == 0) {
            jObj.remove("lastMessageId");
        }

        if (history.getFirstMessageId() == 0) {
            jObj.remove("firstMessageId");
        }

        String uniqueId = getUniqueId();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(jObj.toString());
        chatMessage.setType(ChatMessageType.GET_HISTORY);
        chatMessage.setToken(getToken());
        chatMessage.setTokenIssuer("1");
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setSubjectId(threadId);

        String asyncContent = gson.toJson(chatMessage);
        setCallBacks(null, null, null, true, ChatMessageType.GET_HISTORY, offsets, uniqueId);
        sendAsyncMessage(asyncContent, 3, "SEND GET THREAD HISTORY");
        if (handler != null) {
            handler.onGetHistory(uniqueId);
        }
    }

    public void searchHistory(NosqlListMessageCriteriaVO messageCriteriaVO, ChatHandler handler) {

        String content = gson.toJson(messageCriteriaVO);

        String uniqueId = getUniqueId();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setType(ChatMessageType.GET_HISTORY);
        chatMessage.setToken(getToken());
        chatMessage.setTokenIssuer("1");
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setSubjectId(messageCriteriaVO.getMessageThreadId());

        String asyncContent = gson.toJson(chatMessage);

        setCallBacks(null, null, null, true, ChatMessageType.GET_HISTORY, messageCriteriaVO.getOffset(), uniqueId);

        sendAsyncMessage(asyncContent, 3, "SEND SEARCH0. HISTORY");

        if (handler != null) {
            handler.onSearchHistory(uniqueId);
        }
    }

    /**
     * Get all of the contacts of the user
     */
    public void getContacts(Integer count, Long offset, ChatHandler handler) {

        Long offsets = offset;

        if (chatReady) {
            ChatMessageContent chatMessageContent = new ChatMessageContent();

            if (offset != null) {
                chatMessageContent.setOffset(offset);
            } else {
                chatMessageContent.setOffset(0);
                offsets = 0L;
            }

//            JsonAdapter<ChatMessageContent> messageContentJsonAdapter = moshi.adapter(ChatMessageContent.class);
//            String content = messageContentJsonAdapter.toJson(chatMessageContent);

            Gson gson = new GsonBuilder().create();
            JsonObject jObj = (JsonObject) gson.toJsonTree(chatMessageContent);
            jObj.remove("lastMessageId");
            jObj.remove("firstMessageId");
            if (count != null) {
                jObj.remove("count");
                jObj.addProperty("size", count);
            } else {
                jObj.remove("count");
                jObj.addProperty("size", 50);
            }

            String uniqueId = getUniqueId();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent(jObj.toString());
            chatMessage.setType(ChatMessageType.GET_CONTACTS);
            chatMessage.setToken(getToken());
            chatMessage.setUniqueId(uniqueId);

            String asyncContent = gson.toJson(chatMessage);

            setCallBacks(null, null, null, true, ChatMessageType.GET_CONTACTS, offsets, uniqueId);
            sendAsyncMessage(asyncContent, 3, "GET_CONTACT_SEND");

            if (handler != null) {
                handler.onGetContact(uniqueId);
            }
        }
    }

//    public void searchContact(SearchContact searchContact) {
//
//        if (chatReady) {
//            Observable<Response<SearchContactVO>> observable = contactApi.searchContact(getToken(), TOKEN_ISSUER,
//                    searchContact.getId()
//                    , searchContact.getFirstName()
//                    , searchContact.getLastName()
//                    , searchContact.getEmail()
//                    , getUniqueId()
//                    , searchContact.getOffset()
//                    , searchContact.getSize()
//                    , searchContact.getTypeCode()
//                    , searchContact.getQuery()
//                    , searchContact.getCellphoneNumber());
//            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<SearchContactVO>>() {
//                @Override
//                public void call(Response<SearchContactVO> contactResponse) {
//
//                    if (contactResponse.isSuccessful()) {
//                        SearchContactVO contact = contactResponse.body();
//                        ChatResponse<ResultContact> chatResponse = new ChatResponse<>();
//
//                        ResultContact resultContact = new ResultContact();
//                        resultContact.setContentCount(contact.getCount());
//                        resultContact.setContacts(contactResponse.body().getResult());
//
//                        chatResponse.setHasError(contactResponse.body().getHasError());
//                        chatResponse.setErrorCode(contactResponse.body().getErrorCode());
//                        chatResponse.setErrorMessage(contactResponse.body().getMessage());
//                        chatResponse.setResult(resultContact);
//
//                        String jsonContact = JsonUtil.getJson(chatResponse);
//
//                        listenerManager.callOnSearchContact(chatResponse, jsonContact);
//                        if (log) Logger.json(jsonContact);
//                        if (log) Logger.i("RECEIVE_SEARCH_CONTACT");
//                    } else {
//
//                        if (contactResponse.body() != null) {
//                            String errorMessage = contactResponse.body().getMessage() != null ? contactResponse.body().getMessage() : "";
//                            int errorCode = contactResponse.body().getErrorCode() != null ? contactResponse.body().getErrorCode() : 0;
////                            String error = getErrorOutPut(errorMessage, errorCode, uniqueId);
////                            if (log) Logger.json(error);
//                        }
//                    }
//
//                }
//            }, (Throwable throwable) -> Logger.e(throwable.getMessage()));
//        } else {
//            if (BuildConfig.DEBUG) Logger.e("Chat is not ready");
//        }
//    }

    /**
     * Add one contact to the contact list
     *
     * @param firstName       Notice: if just put fistName without lastName its ok.
     * @param lastName        last name of the contact
     * @param cellphoneNumber Notice: If you just  put the cellPhoneNumber doesn't necessary to add email
     * @param email           email of the contact
     */
   /* public void addContact(String firstName, String lastName, String cellphoneNumber, String email) {
        String uniqueId = getUniqueId();
        Observable<Response<Contacts>> addContactObservable;

        if (getPlatformHost() != null) {
            addContactObservable = contactApi.addContact(getToken(), 1, firstName, lastName, email, uniqueId, cellphoneNumber);
            addContactObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(addContactResponse -> {
                if (addContactResponse.isSuccessful()) {
                    Contacts contacts = addContactResponse.body();
                    if (!contacts.getHasError()) {
                        OutPutAddContact outPutAddContact = Util.getReformatOutPutAddContact(contacts);

                        String contactsJson = JsonUtil.getJson(outPutAddContact);
                        listenerManager.callOnAddContact(contactsJson);
                        if (BuildConfig.DEBUG) Logger.json(contactsJson);
                        if (BuildConfig.DEBUG) Logger.i("RECEIVED_ADD_CONTACT");
                    } else {
                        String jsonError = getErrorOutPut(contacts.getMessage(), contacts.getErrorCode());
                        if (BuildConfig.DEBUG) Logger.e(jsonError);
                    }

                }
            }, (Throwable throwable) ->
            {
                Logger.e("Error on add contact", throwable.getMessage());
                Logger.e(throwable.getMessage());
            });
        } else {
            ChatLogger.error("PlatformHost Address Is Empty!");
        }
    }*/

    /**
     * Remove contact with the user id
     *
     * @param userId id of the user that we want to remove from contact list
     */
 /*   public void removeContact(long userId) {
        if (getPlatformHost() != null) {
            Observable<Response<ContactRemove>> removeContactObservable = contactApi.removeContact(getToken(), 1, userId);
            removeContactObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
                if (response.isSuccessful()) {
                    ContactRemove contactRemove = response.body();
                    if (!contactRemove.getHasError()) {
                        String contactRemoveJson = JsonUtil.getJson(contactRemove);
                        listenerManager.callOnRemoveContact(contactRemoveJson);
                        if (BuildConfig.DEBUG) Logger.json(contactRemoveJson);
                    } else {
                        String jsonError = getErrorOutPut(contactRemove.getErrorMessage(), contactRemove.getErrorCode());
                        if (BuildConfig.DEBUG) Logger.e(jsonError);
                    }
                }
            }, (Throwable throwable) -> {
                if (BuildConfig.DEBUG) Logger.e("Error on remove contact", throwable.getMessage());
                if (BuildConfig.DEBUG) Logger.e(throwable.getMessage());
            });
        } else {
            if (BuildConfig.DEBUG) Logger.e("PlatformHost address is :Empty");
        }
    }*/

    /**
     * Update contacts
     * all of the params all required to update
     */
 /*   public void updateContact(long userId, String firstName, String lastName, String cellphoneNumber, String email) {
        Observable<Response<UpdateContact>> updateContactObservable = contactApi.updateContact(getToken(), 1
                , userId, firstName, lastName, email, getUniqueId(), cellphoneNumber);
        updateContactObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            if (response.isSuccessful()) {
                UpdateContact updateContact = response.body();
                if (!response.body().getHasError()) {
                    OutPutUpdateContact outPut = new OutPutUpdateContact();
                    outPut.setMessage(updateContact.getMessage());
                    outPut.setErrorCode(updateContact.getErrorCode());
                    outPut.setHasError(updateContact.getHasError());
                    outPut.setOtt(updateContact.getOtt());
                    outPut.setReferenceNumber(updateContact.getReferenceNumber());
                    outPut.setCount(updateContact.getCount());
                    ResultUpdateContact resultUpdateContact = new ResultUpdateContact();
                    resultUpdateContact.setContacts(updateContact.getResult());
                    outPut.setResult(resultUpdateContact);
                    String json = JsonUtil.getJson(outPut);
                    listenerManager.callOnUpdateContact(json);
                    Logger.json(json);
                } else {
                    String jsonError = getErrorOutPut(response.body().getMessage(), response.body().getErrorCode());
                    if (BuildConfig.DEBUG) Logger.e(jsonError);
                }
            }
        }, (Throwable throwable) ->
        {
            if (throwable != null) {
                Logger.e("cause" + "" + throwable.getCause());
            }
        });
    }*/

   /* public void mapSearch(String searchTerm, Double latitude, Double longitude) {
        RetrofitHelperMap retrofitHelperMap = new RetrofitHelperMap("https://api.neshan.org/");
        MapApi mapApi = retrofitHelperMap.getService(MapApi.class);
        Observable<Response<MapNeshan>> observable = mapApi.mapSearch("8b77db18704aa646ee5aaea13e7370f4f88b9e8c"
                , searchTerm, latitude, longitude);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<MapNeshan>>() {
            @Override
            public void call(Response<MapNeshan> mapNeshanResponse) {
                OutPutMapNeshan outPutMapNeshan = new OutPutMapNeshan();
                if (mapNeshanResponse.isSuccessful()) {
                    MapNeshan mapNeshan = mapNeshanResponse.body();

                    outPutMapNeshan = new OutPutMapNeshan();
                    outPutMapNeshan.setCount(mapNeshan.getCount());
                    ResultMap resultMap = new ResultMap();
                    resultMap.setMaps(mapNeshan.getItems());
                    outPutMapNeshan.setResult(resultMap);
                    String json = JsonUtil.getJson(outPutMapNeshan);
                    listenerManager.callOnMapSearch(json, outPutMapNeshan);
                    if (BuildConfig.DEBUG) Logger.i("RECEIVE_MAP_SEARCH");
                    if (BuildConfig.DEBUG) Logger.json(json);
                } else {
                    ErrorOutPut errorOutPut = new ErrorOutPut();
                    errorOutPut.setErrorCode(mapNeshanResponse.code());
                    errorOutPut.setErrorMessage(mapNeshanResponse.message());
                    errorOutPut.setHasError(true);
                    String json = JsonUtil.getJson(outPutMapNeshan);
                    listenerManager.callOnError(json, errorOutPut);
                }
            }
        }, (Throwable throwable) -> {
            ErrorOutPut errorOutPut = new ErrorOutPut();
            errorOutPut.setErrorMessage(throwable.getMessage());
            errorOutPut.setHasError(true);
            String json = JsonUtil.getJson(errorOutPut);

            listenerManager.callOnError(json, errorOutPut);
        });
    }

    public void mapRouting(String origin, String destination) {
        RetrofitHelperMap retrofitHelperMap = new RetrofitHelperMap("https://api.neshan.org/");
        MapApi mapApi = retrofitHelperMap.getService(MapApi.class);
        Observable<Response<MapRout>> responseObservable = mapApi.mapRouting("8b77db18704aa646ee5aaea13e7370f4f88b9e8c"
                , origin, destination, true);
        responseObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<MapRout>>() {
            @Override
            public void call(Response<MapRout> mapRoutResponse) {
                if (mapRoutResponse.isSuccessful()) {
                    MapRout mapRout = mapRoutResponse.body();
                    OutPutMapRout outPutMapRout = new OutPutMapRout();
                    outPutMapRout.setResult(mapRout);
                    String jsonMapRout = JsonUtil.getJson(outPutMapRout);
                    listenerManager.callOnMapRouting(jsonMapRout);
                    Logger.i("RECEIVE_MAP_ROUTING");
                    Logger.json(jsonMapRout);
                }
            }
        }, (Throwable throwable) -> {
            Logger.e(throwable, "Error on map routing");
        });
    }

    public void block(Long contactId, ChatHandler handler) {
        BlockContactId blockAcount = new BlockContactId();
        blockAcount.setContactId(contactId);
        String uniqueId = getUniqueId();
        String json = JsonUtil.getJson(blockAcount);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(json);
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setTokenIssuer("1");
        chatMessage.setType(Constants.BLOCK);
        setCallBacks(null, null, null, true, Constants.BLOCK, null, uniqueId);
        String asyncContent = JsonUtil.getJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_BLOCK");
        handler.onBlock(uniqueId);
    }

    public void unblock(long blockId, ChatHandler handler) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSubjectId(blockId);
        String uniqueId = getUniqueId();
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setTokenIssuer("1");
        chatMessage.setType(Constants.UNBLOCK);
        setCallBacks(null, null, null, true, Constants.UNBLOCK, null, uniqueId);
        String asyncContent = JsonUtil.getJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_UN_BLOCK");
        handler.onUnBlock(uniqueId);
    }

    public void spam(long threadId) {

        String uniqueId = getUniqueId();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(Constants.SPAM_PV_THREAD);
        chatMessage.setTokenIssuer("1");
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setSubjectId(threadId);
        setCallBacks(null, null, null, true, Constants.SPAM_PV_THREAD, null, uniqueId);
        String asyncContent = JsonUtil.getJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_REPORT_SPAM");
    }

    //TODO change params to long
    public void getBlockList(Integer count, Integer offset, ChatHandler handler) {

        ChatMessageContent chatMessageContent = new ChatMessageContent();
        if (offset != null) {
            chatMessageContent.setOffset(offset);
        }
        if (count != null) {
            chatMessageContent.setCount(count);
        }
        String json = JsonUtil.getJson(chatMessageContent);

        String uniqueId = getUniqueId();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(json);
        chatMessage.setType(Constants.GET_BLOCKED);
        chatMessage.setTokenIssuer("1");
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        setCallBacks(null, null, null, true, Constants.GET_BLOCKED, null, uniqueId);
        String asyncContent = JsonUtil.getJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_BLOCK_List");
        if (handler != null) {
            handler.onGetBlockList(uniqueId);
        }
    }

    private class BlockContactId {
        private long contactId;

        public long getContactId() {
            return contactId;
        }

        public void setContactId(long contactId) {
            this.contactId = contactId;
        }
    }*/

    /**
     * Create the thread to p to p/channel/group. The list below is showing all of the thread type
     * int NORMAL = 0;
     * int OWNER_GROUP = 1;
     * int PUBLIC_GROUP = 2;
     * int CHANNEL_GROUP = 4;
     * int CHANNEL = 8;
     */
    public void createThread(int threadType, Invitee[] invitee, String threadTitle, ChatHandler handler) {
        List<Invitee> invitees = new ArrayList<>(Arrays.asList(invitee));
        ChatThread chatThread = new ChatThread();
        chatThread.setType(threadType);
        chatThread.setInvitees(invitees);
        chatThread.setTitle(threadTitle);

        String contentThreadChat = gson.toJson(chatThread);
        String uniqueId = getUniqueId();
        ChatMessage chatMessage = getChatMessage(contentThreadChat, uniqueId);

        setCallBacks(null, null, null, true, ChatMessageType.INVITATION, null, uniqueId);

        String asyncContent = gson.toJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_CREATE_THREAD");

        if (handler != null) {
            handler.onCreateThread(uniqueId);
        }
    }

   /* public void updateThreadInfo(long threadId, ThreadInfoVO threadInfoVO, ChatHandler handler) {

        String content = JsonUtil.getJson(threadInfoVO);

        String uniqueId = getUniqueId();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setTokenIssuer("1");
        chatMessage.setToken(getToken());
        chatMessage.setSubjectId(threadId);
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setType(Constants.UPDATE_THREAD_INFO);
        chatMessage.setContent(content);

        String asyncContent = JsonUtil.getJson(chatMessage);
        setCallBacks(null, null, null, true, Constants.UPDATE_THREAD_INFO, null, uniqueId);
        sendAsyncMessage(asyncContent, 4, "UPDATE_THREAD_INFO");
        if (handler != null) {
            handler.onUpdateThreadInfo(uniqueId);
        }
    }*/

    /**
     * Get the participant list of specific thread
     *
     * @param threadId id of the thread we want to ge the participant list
     */
   /* public void getThreadParticipants(Integer count, Long offset, long threadId, ChatHandler handler) {

        offset = offset != null ? offset : 0;
        count = count != null ? count : 50;
        if (cache) {
            List<Participant> participants = messageDatabaseHelper.getThreadParticipant(offset, count, threadId);
            if (participants != null && participants.size() != 0) {
                long participantCount = messageDatabaseHelper.getParticipantCount(threadId);
                OutPutParticipant outPutParticipant = new OutPutParticipant();
                outPutParticipant.setErrorCode(0);
                outPutParticipant.setErrorMessage("");
                outPutParticipant.setHasError(false);

                ResultParticipant resultParticipant = new ResultParticipant();

                resultParticipant.setContentCount(participants.size());
                if (participants.size() + offset < participantCount) {
                    resultParticipant.setHasNext(true);
                } else {
                    resultParticipant.setHasNext(false);
                }
                resultParticipant.setParticipants(participants);
                outPutParticipant.setResult(resultParticipant);
                resultParticipant.setNextOffset(offset + participants.size());
                String jsonParticipant = JsonUtil.getJson(outPutParticipant);
                listenerManager.callOnGetThreadParticipant(jsonParticipant, outPutParticipant);
                Logger.json(jsonParticipant);
            }
        }

        ChatMessageContent chatMessageContent = new ChatMessageContent();
        if (count == null) {
            chatMessageContent.setCount(50);
        } else {
            chatMessageContent.setCount(count);
        }

        if (offset == null) {
            chatMessageContent.setOffset(0);
        } else {
            chatMessageContent.setOffset(offset);
        }

        JsonAdapter<ChatMessageContent> messageContentJsonAdapter = moshi.adapter(ChatMessageContent.class);
        String content = messageContentJsonAdapter.toJson(chatMessageContent);
        String uniqueId = getUniqueId();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setType(Constants.THREAD_PARTICIPANTS);
        chatMessage.setTokenIssuer("1");
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setSubjectId(threadId);

        JsonAdapter<ChatMessage> chatMessageJsonAdapter = moshi.adapter(ChatMessage.class);
        String asyncContent = chatMessageJsonAdapter.toJson(chatMessage);
        setCallBacks(null, null, null, true, Constants.THREAD_PARTICIPANTS, offset, uniqueId);
        sendAsyncMessage(asyncContent, 3, "SEND_THREAD_PARTICIPANT");
        if (handler != null) {
            handler.onGetThreadParticipant(uniqueId);
        }
    }

    public void seenMessage(long messageId, long ownerId, ChatHandler handler) {

        if (ownerId != getUserId()) {
            String uniqueId = getUniqueId();
            ChatMessage message = new ChatMessage();
            message.setType(Constants.SEEN);
            message.setContent(String.valueOf(messageId));
            message.setTokenIssuer("1");
            message.setToken(getToken());
            message.setUniqueId(uniqueId);
            message.setTime(1000);

            JsonAdapter<ChatMessage> chatMessageJsonAdapter = moshi.adapter(ChatMessage.class);
            String asyncContent = chatMessageJsonAdapter.toJson(message);
            sendAsyncMessage(asyncContent, 4, "SEND_SEEN_MESSAGE");
            if (handler != null) {
                handler.onSeen(uniqueId);
            }
        }
    }
*/

    /**
     * Get the information of the current user
     */
    public void getUserInfo(ChatHandler handler) {

        getUserInfoRetryCount++;

        if (getUserInfoRetryCount > getUserInfoRetry) {
            getUserInfoRetryCount = 0;

            //TODO

        } else {

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessageType.USER_INFO);

            String uniqueId = getUniqueId();

            chatMessage.setUniqueId(uniqueId);
            chatMessage.setToken(getToken());
            chatMessage.setTokenIssuer("1");

            setCallBacks(null, null, null, true, ChatMessageType.USER_INFO, null, uniqueId);

            String asyncContent = gson.toJson(chatMessage);

            if (asyncReady) {
                ChatLogger.info("SEND_USER_INFO");

                async.sendMessage(asyncContent, 3);

                if (handler != null) {
                    handler.onGetUserInfo(uniqueId);
                }

                long lastSentMessageTimeout = 9 * 1000;
                lastSentMessageTime = new Date().getTime();

                if (state) {
                    PingExecutor.getInstance().
                            scheduleAtFixedRate(() -> checkForPing(lastSentMessageTimeout),
                                    0, 20000,
                                    TimeUnit.MILLISECONDS);

                } else {
                    Error error = new Error();
//                error.setCode();
                    ChatLogger.error("Async is Close");
                }
            } else {
                String jsonError = getErrorOutPut(ChatConstant.ERROR_CHAT_READY, ChatConstant.ERROR_CODE_CHAT_READY);
                ChatLogger.error(jsonError);
            }
        }

    }

    public void checkForPing(long lastSentMessageTimeout) {
        long currentTime = new Date().getTime();

        if (currentTime - lastSentMessageTime > lastSentMessageTimeout) {
            ping();
        }
    }

    /**
     * Mute the thread so notification is off for that thread
     */
   /* public void muteThread(long threadId, ChatHandler handler) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(Constants.MUTE_THREAD);
        chatMessage.setToken(getToken());
        chatMessage.setTokenIssuer("1");
        chatMessage.setSubjectId(threadId);
        String uniqueId = getUniqueId();
        chatMessage.setUniqueId(uniqueId);
        setCallBacks(null, null, null, true, Constants.MUTE_THREAD, null, uniqueId);

        String asyncContent = JsonUtil.getJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_MUTE_THREAD");
        if (handler != null) {
            handler.onMuteThread(uniqueId);
        }
    }*/

    /**
     * Unmute the thread so notification is on for that thread
     */
   /* public void unMuteThread(long threadId, ChatHandler handler) {
        String uniqueId = getUniqueId();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(Constants.UN_MUTE_THREAD);
        chatMessage.setToken(getToken());
        chatMessage.setTokenIssuer("1");
        chatMessage.setSubjectId(threadId);
        chatMessage.setUniqueId(uniqueId);

        setCallBacks(null, null, null, true, Constants.UN_MUTE_THREAD, null, uniqueId);
        String asyncContent = JsonUtil.getJson(chatMessage);
        sendAsyncMessage(asyncContent, 4, "SEND_UN_MUTE_THREAD");
        if (handler != null) {
            handler.onUnMuteThread(uniqueId);
        }
    }
*/
    /**
     * Message can be edit when you pass the message id and the edited
     * content to editMessage function
     */
  /*  public void editMessage(int messageId, String messageContent, ChatHandler handler) {
        String uniqueId = getUniqueId();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(Constants.EDIT_MESSAGE);
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setSubjectId(messageId);
        chatMessage.setContent(messageContent);
        chatMessage.setTokenIssuer("1");

        String asyncContent = JsonUtil.getJson(chatMessage);
        setCallBacks(null, null, null, true, Constants.EDIT_MESSAGE, null, uniqueId);
        sendAsyncMessage(asyncContent, 4, "SEND_EDIT_MESSAGE");
        if (handler != null) {
            handler.onEditMessage(uniqueId);
        }
    }*/

    /**
     * Add a listener to receive events on this Chat.
     *
     * @param listener A listener to add.
     * @return {@code this} object.
     */
    public Chat addListener(ChatListener listener) {
        listenerManager.addListener(listener);
        return this;
    }

    public Chat addListeners(List<ChatListener> listeners) {
        listenerManager.addListeners(listeners);
        return this;
    }

    public Chat removeListener(ChatListener listener) {
        listenerManager.removeListener(listener);
        return this;
    }


    /**
     * Ping for staying chat alive
     */
    private void ping() {
        if (chatState && async.getPeerId() != null) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessageType.PING);
            chatMessage.setTokenIssuer("1");
            chatMessage.setToken(getToken());

            String asyncContent = gson.toJson(chatMessage);

            sendAsyncMessage(asyncContent, 4, "CHAT PING");
        }
    }

   /* private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }*/

    private void handleError(ChatMessage chatMessage) {
        podChat.model.Error error = gson.fromJson(chatMessage.getContent(), podChat.model.Error.class);

        if (error.getCode() == 401) {
            PingExecutor.stopThread();
        }
        String errorMessage = error.getMessage();
        long errorCode = error.getCode();

        String errorJson = getErrorOutPut(errorMessage, errorCode);

        ChatLogger.error(errorJson);
    }

    /**
     * When the new message arrived we send the deliver message to the sender user.
     */
    private void handleNewMessage(ChatMessage chatMessage) {
        ChatLogger.info("RECEIVED_NEW_MESSAGE");
        ChatLogger.info(chatMessage.getContent());

        MessageVO messageVO = gson.fromJson(chatMessage.getContent(), MessageVO.class);

        OutPutNewMessage outPutNewMessage = new OutPutNewMessage();
        outPutNewMessage.setResult(messageVO);

        String json = gson.toJson(outPutNewMessage);

        listenerManager.callOnNewMessage(json, outPutNewMessage);

        long ownerId = 0;

        if (messageVO != null) {
            ownerId = messageVO.getParticipant().getId();
        }
        if (ownerId != getUserId()) {
            ChatMessage message = getChatMessage(messageVO);

            String asyncContent = gson.toJson(message);

            async.sendMessage(asyncContent, 4);

            ChatLogger.info("SEND_DELIVERY_MESSAGE");
            ChatLogger.info(asyncContent);
        }
    }

    private void handleSent(ChatMessage chatMessage, String messageUniqueId, long threadId) {

        if (threadCallbacks.containsKey(threadId)) {
            ArrayList<Callback> callbacks = threadCallbacks.get(threadId);
            for (Callback callback : callbacks) {
                if (messageUniqueId.equals(callback.getUniqueId())) {
                    int indexUnique = callbacks.indexOf(callback);
                    if (callbacks.get(indexUnique).isSent()) {

//                        ChatHandler channel = new ChatHandler() {};
//                        channel.onSent(messageUniqueId,threadId,chatMessage.getContent());
                        listenerManager.callOnSentMessage(chatMessage.getContent());
//
//                        runOnUIThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                handlerSend.get(callback.getUniqueId()).onSentResult(chatMessage.getContent());
//                            }
//                        });


                        Callback callbackUpdateSent = new Callback();
                        callbackUpdateSent.setSent(false);
                        callbackUpdateSent.setDelivery(callback.isDelivery());
                        callbackUpdateSent.setSeen(callback.isSeen());
                        callbackUpdateSent.setUniqueId(callback.getUniqueId());

                        callbacks.set(indexUnique, callbackUpdateSent);
                        threadCallbacks.put(threadId, callbacks);
                        ChatLogger.info("Is Sent" + " " + callback.getUniqueId());
                    }
                    break;
                }
            }
        }
    }

    private void handleSeen(ChatMessage chatMessage, String messageUniqueId, long threadId) {

        if (threadCallbacks.containsKey(threadId)) {
            ArrayList<Callback> callbacks = threadCallbacks.get(threadId);
            for (Callback callback : callbacks) {
                if (messageUniqueId.equals(callback.getUniqueId())) {
                    int indexUnique = callbacks.indexOf(callback);

                    while (indexUnique > -1) {

                        if (callbacks.get(indexUnique).isSeen()) {

                            if (callbacks.get(indexUnique).isDelivery()) {
                                listenerManager.callOnDeliveryMessage(callback.getUniqueId());

                                Callback callbackUpdateSent = new Callback();
                                callbackUpdateSent.setSent(callback.isSent());
                                callbackUpdateSent.setDelivery(false);
                                callbackUpdateSent.setSeen(callback.isSeen());
                                callbackUpdateSent.setUniqueId(callback.getUniqueId());
                                callbacks.set(indexUnique, callbackUpdateSent);

                                threadCallbacks.put(threadId, callbacks);

                                ChatLogger.info("Is Delivered" + " " + "Unique Id" + callback.getUniqueId());
                            }
                            listenerManager.callOnSeenMessage(callback.getUniqueId());
                            callbacks.remove(indexUnique);
                            threadCallbacks.put(threadId, callbacks);

                            ChatLogger.info("Is Seen" + " " + "Unique Id" + callback.getUniqueId());
                        }
                        indexUnique--;
                    }
                    break;
                }
            }
        }
    }

    private void handleDelivery(ChatMessage chatMessage, String messageUniqueId, long threadId) {
        if (threadCallbacks.containsKey(threadId)) {
            ArrayList<Callback> callbacks = threadCallbacks.get(threadId);

            for (Callback callback : callbacks) {
                if (messageUniqueId.equals(callback.getUniqueId())) {
                    int indexUnique = callbacks.indexOf(callback);

                    while (indexUnique > -1) {

                        if (callbacks.get(indexUnique).isDelivery()) {
                            listenerManager.callOnDeliveryMessage(callback.getUniqueId());

                            Callback callbackUpdateSent = new Callback();
                            callbackUpdateSent.setSent(callback.isSent());
                            callbackUpdateSent.setDelivery(false);
                            callbackUpdateSent.setSeen(callback.isSeen());
                            callbackUpdateSent.setUniqueId(callback.getUniqueId());
                            callbacks.set(indexUnique, callbackUpdateSent);

                            threadCallbacks.put(threadId, callbacks);

                            ChatLogger.info("Is Delivered" + " " + callback.getUniqueId());
                        }
                    }
                    indexUnique--;
                }
                break;
            }
        }
    }


    private void handleForwardMessage(ChatMessage chatMessage) {
        ChatLogger.info("RECEIVED_FORWARD_MESSAGE");
        ChatLogger.info(chatMessage.getContent());

        Gson gson = new Gson();
        MessageVO jsonMessage = gson.fromJson(chatMessage.getContent(), MessageVO.class);
//        MessageVO jsonMessage = JsonUtil.fromJSON(chatMessage.getContent(), MessageVO.class);
        long ownerId = 0;
        if (jsonMessage != null) {
            ownerId = jsonMessage.getParticipant().getId();
        }
        if (ownerId != getUserId()) {
            ChatMessage message = getChatMessage(jsonMessage);

            String asyncContent = gson.toJson(message);

            ChatLogger.info("SEND_DELIVERY_MESSAGE");
            ChatLogger.info(asyncContent);

            async.sendMessage(asyncContent, 4);
        }
    }

//    private void handleSyncContact(ChatMessage chatMessage) {
//        Type type = Types.newParameterizedType(List.class, Contact.class);
//
//        ArrayList<String> firstNames = new ArrayList<>();
//        ArrayList<String> cellphoneNumbers = new ArrayList<>();
//        ArrayList<String> lastNames = new ArrayList<>();
//
//        try {
//            List<Contact> serverContacts = gson.fromJson(chatMessage.getContent(), Contact.class);
//
//            if (serverContacts != null) {
//                List<Contact> phoneContacts = getPhoneContact(getContext());
//
//                HashMap<String, String> mapServerContact = new HashMap<>();
//
//                for (int a = 0; a < serverContacts.size(); a++) {
//                    mapServerContact.put(serverContacts.get(a).getCellphoneNumber(), serverContacts.get(a).getFirstName());
//                }
//
//                for (int j = 0; j < phoneContacts.size(); j++) {
//                    if (!mapServerContact.containsKey(phoneContacts.get(j).getCellphoneNumber())) {
//                        firstNames.add(phoneContacts.get(j).getFirstName());
//                        cellphoneNumbers.add(phoneContacts.get(j).getCellphoneNumber());
//                        lastNames.add(phoneContacts.get(j).getLastName());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!firstNames.isEmpty() || !cellphoneNumbers.isEmpty()) {
//            addContacts(firstNames, cellphoneNumbers);
//            syncContacts = true;
//        }
//        syncContact = false;
//    }

    private void handleResponseMessage(Callback callback, ChatMessage chatMessage, String messageUniqueId) {
        switch (callback.getRequestType()) {
            case ChatMessageType.REMOVED_FROM_THREAD:

                listenerManager.callOnRemovedFromThread(chatMessage.getContent());

                break;
            case ChatMessageType.GET_HISTORY:

                handleOutPutGetHistory(callback, chatMessage, messageUniqueId);

                break;
            case ChatMessageType.GET_CONTACTS:

               /* if (syncContact) {
                    handleSyncContact(chatMessage);
                } else {
                    OutPutContact outPutContact = new OutPutContact();

                    if (callback.isResult()) {
                        String contactJson = reformatGetContactResponse(chatMessage, outPutContact, callback);

                        listenerManager.callOnGetContacts(contactJson, outPutContact);
                        messageCallbacks.remove(messageUniqueId);

                        ChatLogger.info("RECEIVE_GET_CONTACT");
                        ChatLogger.info(contactJson);
                    }
                }*/
                break;
            case ChatMessageType.UPDATE_THREAD_INFO:

                ChatLogger.info("RECEIVE_UPDATE_THREAD_INFO");
                ChatLogger.info(chatMessage.getContent());
                break;

            case ChatMessageType.GET_THREADS:
                OutPutThreads outPutThreads = new OutPutThreads();

                if (callback.isResult()) {
                    String threadJson = reformatGetThreadsResponse(chatMessage, outPutThreads, callback);

                    listenerManager.callOnGetThread(threadJson, outPutThreads);
                    messageCallbacks.remove(messageUniqueId);
                    ChatLogger.info("RECEIVE_GET_THREAD");
                    ChatLogger.info(threadJson);
                }
                break;
            case ChatMessageType.INVITATION:

                if (callback.isResult()) {
                    OutPutThread outPutThread = new OutPutThread();
                    String inviteJson = reformatCreateThread(chatMessage, outPutThread);

                    listenerManager.callOnCreateThread(inviteJson, outPutThread);
                    messageCallbacks.remove(messageUniqueId);

                    ChatLogger.info("RECEIVE_CREATE_THREAD");
                    ChatLogger.info(inviteJson);
                }
                break;

            case ChatMessageType.MUTE_THREAD:

                /*if (callback.isResult()) {
                    OutPutMute outPut = new OutPutMute();
                    String muteThreadJson = reformatMuteThread(chatMessage, outPut);
                    listenerManager.callOnMuteThread(muteThreadJson, outPut);
                    messageCallbacks.remove(messageUniqueId);
                    ChatLogger.info("RECEIVE_MUTE_THREAD");
                    ChatLogger.info(muteThreadJson);
                }*/
                break;
            case ChatMessageType.UN_MUTE_THREAD:

               /* if (callback.isResult()) {
                    OutPutMute outPut = new OutPutMute();
                    String unmuteThreadJson = reformatMuteThread(chatMessage, outPut);

                    listenerManager.callOnUnmuteThread(unmuteThreadJson, outPut);
                    messageCallbacks.remove(messageUniqueId);

                    ChatLogger.info("RECEIVE_UN_MUTE_THREAD");
                    ChatLogger.info(unmuteThreadJson);
                }*/
                break;
            case ChatMessageType.EDIT_MESSAGE:

                if (callback.isResult()) {
                    ChatLogger.info("RECEIVE_EDIT_MESSAGE");
                    ChatLogger.info(chatMessage.getContent());

                    listenerManager.callOnEditedMessage(chatMessage.getContent());
                    messageCallbacks.remove(messageUniqueId);
                }

                break;
            case ChatMessageType.USER_INFO:

                if (callback.isResult()) {
                    handleOutPutUserInfo(chatMessage, messageUniqueId);
                }

                break;
            case ChatMessageType.THREAD_PARTICIPANTS:

              /*  if (callback.isResult()) {
                    OutPutParticipant outPutParticipant = reformatThreadParticipants(callback, chatMessage);

                    String jsonParticipant = gson.toJson(outPutParticipant);

                    listenerManager.callOnGetThreadParticipant(jsonParticipant, outPutParticipant);
                    messageCallbacks.remove(messageUniqueId);

                    ChatLogger.info("RECEIVE_PARTICIPANT");
                    ChatLogger.info(jsonParticipant);
                }
*/
                break;
            case ChatMessageType.ADD_PARTICIPANT:
                if (callback.isResult()) {
                    handleAddParticipant(chatMessage, messageUniqueId);
                }

                break;
            case ChatMessageType.REMOVE_PARTICIPANT:
               /* if (callback.isResult()) {
                    handleOutPutRemoveParticipant(callback, chatMessage, messageUniqueId);
                }*/

                break;
            case ChatMessageType.LEAVE_THREAD:
                if (callback.isResult()) {
                    handleOutPutLeaveThread(chatMessage, messageUniqueId);
                }

                break;
            case ChatMessageType.RENAME:

              /*  if (callback.isResult()) {

                    OutPutThread outPutThread = reformatRenameThread(chatMessage);
                    String jsonRename = gson.toJson(outPutThread);

                    listenerManager.callOnRenameThread(jsonRename, outPutThread);
                    messageCallbacks.remove(messageUniqueId);

                    ChatLogger.info("RECEIVE_RENAME_THREAD");
                    ChatLogger.info(jsonRename);
                }*/

                break;
            case ChatMessageType.DELETE_MESSAGE:
                //handleOutPutDeleteMsg(chatMessage);

                break;
            case ChatMessageType.BLOCK:
               /* if (callback.isResult()) {
                    handleOutPutBlock(chatMessage, messageUniqueId);
                }*/

                break;
            case ChatMessageType.UNBLOCK:
               /* if (callback.isResult()) {
                    handleUnBlock(chatMessage, messageUniqueId);
                }*/

                break;
            case ChatMessageType.GET_BLOCKED:
               /* if (callback.isResult()) {
                    handleOutPutGetBlockList(chatMessage);
                }*/

                break;
        }
    }

    private void handleOutPutUserInfo(ChatMessage chatMessage, String messageUniqueId) {
        OutPutUserInfo outPutUserInfo = new OutPutUserInfo();
        String userInfoJson = reformatUserInfo(chatMessage, outPutUserInfo);

        listenerManager.callOnUserInfo(userInfoJson, outPutUserInfo);
        messageCallbacks.remove(messageUniqueId);

        ChatLogger.info("RECEIVE_USER_INFO");
        ChatLogger.info(userInfoJson);

        listenerManager.callOnChatState("CHAT_READY");

        chatReady = true;

        ChatLogger.info("CHAT_READY");
    }

    private void handleOutPutLeaveThread(ChatMessage chatMessage, String messageUniqueId) {
        ResultLeaveThread leaveThread = gson.fromJson(chatMessage.getContent(), ResultLeaveThread.class);

        leaveThread.setThreadId(chatMessage.getSubjectId());

        OutPutLeaveThread outPutLeaveThread = new OutPutLeaveThread();
        outPutLeaveThread.setErrorCode(0);
        outPutLeaveThread.setHasError(false);
        outPutLeaveThread.setErrorMessage("");

        outPutLeaveThread.setResult(leaveThread);
        String jsonThread = gson.toJson(outPutLeaveThread);

        listenerManager.callOnThreadLeaveParticipant(jsonThread, outPutLeaveThread);
        messageCallbacks.remove(messageUniqueId);

        ChatLogger.info("RECEIVE_LEAVE_THREAD");
        ChatLogger.info(jsonThread);
    }

    private void handleAddParticipant(ChatMessage chatMessage, String messageUniqueId) {
        ThreadVo threadVo = gson.fromJson(chatMessage.getContent(), ThreadVo.class);

        ResultAddParticipant resultAddParticipant = new ResultAddParticipant();
        resultAddParticipant.setThreadVo(threadVo);
        OutPutAddParticipant outPutAddParticipant = new OutPutAddParticipant();
        outPutAddParticipant.setErrorCode(0);
        outPutAddParticipant.setErrorMessage("");
        outPutAddParticipant.setHasError(false);
        outPutAddParticipant.setResult(resultAddParticipant);

        String jsonAddParticipant = gson.toJson(outPutAddParticipant);

        listenerManager.callOnThreadAddParticipant(jsonAddParticipant, outPutAddParticipant);
        messageCallbacks.remove(messageUniqueId);

        ChatLogger.info("RECEIVE_PARTICIPANT");
        ChatLogger.info(jsonAddParticipant);
    }

   /* private void handleOutPutDeleteMsg(ChatMessage chatMessage) {
        OutPutDeleteMessage outPutDeleteMessage = new OutPutDeleteMessage();
        outPutDeleteMessage.setErrorCode(0);
        outPutDeleteMessage.setErrorMessage("");
        outPutDeleteMessage.setHasError(false);
        ResultDeleteMessage resultDeleteMessage = new ResultDeleteMessage();
        DeleteMessageContent deleteMessage = new DeleteMessageContent();
        deleteMessage.setId(Long.valueOf(chatMessage.getContent()));
        resultDeleteMessage.setDeletedMessage(deleteMessage);
        outPutDeleteMessage.setResult(resultDeleteMessage);
        String jsonDeleteMsg = JsonUtil.getJson(outPutDeleteMessage);

        listenerManager.callOnDeleteMessage(jsonDeleteMsg, outPutDeleteMessage);
        if (BuildConfig.DEBUG) Logger.i("RECEIVE_DELETE_MESSAGE");
        if (BuildConfig.DEBUG) Logger.json(jsonDeleteMsg);
    }

    private void handleOutPutBlock(ChatMessage chatMessage, String messageUniqueId) {
        Contact contact = JsonUtil.fromJSON(chatMessage.getContent(), Contact.class);
        OutPutBlock outPutBlock = new OutPutBlock();
        ResultBlock resultBlock = new ResultBlock();
        resultBlock.setContact(contact);
        outPutBlock.setResult(resultBlock);
        outPutBlock.setErrorCode(0);
        outPutBlock.setHasError(false);
        String jsonBlock = JsonUtil.getJson(outPutBlock);
        listenerManager.callOnBlock(jsonBlock, outPutBlock);
        if (BuildConfig.DEBUG) Logger.i("RECEIVE_BLOCK");
        if (BuildConfig.DEBUG) Logger.json(jsonBlock);
        messageCallbacks.remove(messageUniqueId);
    }

    private void handleUnBlock(ChatMessage chatMessage, String messageUniqueId) {
        Contact contact = JsonUtil.fromJSON(chatMessage.getContent(), Contact.class);
        OutPutBlock outPutBlock = new OutPutBlock();
        ResultBlock resultBlock = new ResultBlock();
        resultBlock.setContact(contact);
        outPutBlock.setResult(resultBlock);
        outPutBlock.setErrorCode(0);
        outPutBlock.setHasError(false);
        String jsonUnBlock = JsonUtil.getJson(outPutBlock);
        listenerManager.callOnUnBlock(jsonUnBlock, outPutBlock);
        if (BuildConfig.DEBUG) Logger.i("RECEIVE_UN_BLOCK");
        if (BuildConfig.DEBUG) Logger.json(jsonUnBlock);
        messageCallbacks.remove(messageUniqueId);
    }

    private void handleOutPutGetBlockList(ChatMessage chatMessage) {
        OutPutBlockList outPutBlockList = new OutPutBlockList();
        outPutBlockList.setErrorCode(0);
        outPutBlockList.setHasError(false);
        ResultBlockList resultBlockList = new ResultBlockList();

        List<Contact> contacts = JsonUtil.fromJSON(chatMessage.getContent(), new TypeReference<List<Contact>>() {
        });
        resultBlockList.setContacts(contacts);
        outPutBlockList.setResult(resultBlockList);
        String jsonGetBlock = JsonUtil.getJson(outPutBlockList);
        listenerManager.callOnGetBlockList(jsonGetBlock, outPutBlockList);
        if (BuildConfig.DEBUG) Logger.i("RECEIVE_GET_BLOCK_LIST");
        if (BuildConfig.DEBUG) Logger.json(jsonGetBlock);
    }

    private void handleOutPutRemoveParticipant(Callback callback, ChatMessage chatMessage, String messageUniqueId) {
        reformatThreadParticipants(callback, chatMessage);

        OutPutParticipant outPutParticipant = new OutPutParticipant();
        outPutParticipant.setErrorCode(0);
        outPutParticipant.setErrorMessage("");
        outPutParticipant.setHasError(false);
        ResultParticipant resultParticipant = new ResultParticipant();

        List<Participant> participants = new ArrayList<>();
        Type typeParticipant = Types.newParameterizedType(List.class, Participant.class);
        JsonAdapter<List<Participant>> adapter = moshi.adapter(typeParticipant);
        try {
            participants = adapter.fromJson(chatMessage.getContent());
        } catch (IOException e) {
            if (BuildConfig.DEBUG) Logger.e(e.getMessage() + e.getCause());
        }

        resultParticipant.setParticipants(participants);
        outPutParticipant.setResult(resultParticipant);
        String jsonRmParticipant = JsonUtil.getJson(outPutParticipant);

        listenerManager.callOnThreadRemoveParticipant(jsonRmParticipant, outPutParticipant);
        messageCallbacks.remove(messageUniqueId);
        if (BuildConfig.DEBUG) Logger.i("RECEIVE_REMOVE_PARTICIPANT");
        if (BuildConfig.DEBUG) Logger.json(jsonRmParticipant);
    }*/

    private void handleOutPutGetHistory(Callback callback, ChatMessage chatMessage, String messageUniqueId) {


        OutPutHistory outPut = new OutPutHistory();
        ResultsHistory resultsHistory = new ResultsHistory();

        Type listType = new TypeToken<ArrayList<MessageVO>>() {
        }.getType();

        List<MessageVO> messageVOS = gson.fromJson(chatMessage.getContent(), listType);

        resultsHistory.setNextOffset(callback.getOffset() + messageVOS.size());
        resultsHistory.setContentCount(chatMessage.getContentCount());
        if (messageVOS.size() + callback.getOffset() < chatMessage.getContentCount()) {
            resultsHistory.setHasNext(true);
        } else {
            resultsHistory.setHasNext(false);
        }

        resultsHistory.setHistory(messageVOS);
        outPut.setErrorCode(0);
        outPut.setHasError(false);
        outPut.setErrorMessage("");
        outPut.setResult(resultsHistory);

        String json = gson.toJson(outPut);
        listenerManager.callOnGetThreadHistory(json, outPut);

        messageCallbacks.remove(messageUniqueId);
        ChatLogger.info("RECEIVE_GET_HISTORY");
        ChatLogger.info(json);
    }

 /*   private OutPutParticipant reformatThreadParticipants(Callback callback, ChatMessage chatMessage) {

        List<Participant> participants = new ArrayList<>();
        Type type = Types.newParameterizedType(List.class, Participant.class);
        JsonAdapter<List<Participant>> adapter = moshi.adapter(type);
        try {
            participants = adapter.fromJson(chatMessage.getContent());
        } catch (IOException e) {
            if (BuildConfig.DEBUG) Logger.e(e.getMessage() + e.getCause());
        }

        if (cache) {
            messageDatabaseHelper.saveParticipants(participants, chatMessage.getSubjectId());
        }

        OutPutParticipant outPutParticipant = new OutPutParticipant();
        outPutParticipant.setErrorCode(0);
        outPutParticipant.setErrorMessage("");
        outPutParticipant.setHasError(false);

        ResultParticipant resultParticipant = new ResultParticipant();

        resultParticipant.setContentCount(chatMessage.getContentCount());
        if (participants.size() + callback.getOffset() < chatMessage.getContentCount()) {
            resultParticipant.setHasNext(true);
        } else {
            resultParticipant.setHasNext(false);
        }

        resultParticipant.setParticipants(participants);
        outPutParticipant.setResult(resultParticipant);
        resultParticipant.setNextOffset(callback.getOffset() + participants.size());
        return outPutParticipant;
    }

    @NonNull
    private OutPutThread reformatRenameThread(ChatMessage chatMessage) {
        OutPutThread outPutThread = new OutPutThread();
        outPutThread.setErrorMessage("");
        outPutThread.setErrorCode(0);
        outPutThread.setHasError(false);

        ResultThread resultThread = new ResultThread();
        ThreadVo threadVo = JsonUtil.fromJSON(chatMessage.getContent(), ThreadVo.class);
        resultThread.setThreadVo(threadVo);
        outPutThread.setResult(resultThread);
        return outPutThread;
    }*/

//    private void sendTextMessageWithFile(String description, long threadId, String metaData, String systemMetadata) {
//        ChatMessage chatMessage = new ChatMessage();
//        if (systemMetadata != null) {
//            chatMessage.setSystemMetadata(systemMetadata);
//        }
//        chatMessage.setContent(description);
//        chatMessage.setType(ChatMessageType.MESSAGE);
//        chatMessage.setTokenIssuer("1");
//        chatMessage.setToken(getToken());
//        chatMessage.setMetadata(metaData);
//
//        String uniqueId = getUniqueId();
//        chatMessage.setUniqueId(uniqueId);
//        chatMessage.setTime(1000);
//        chatMessage.setSubjectId(threadId);
//
//        String asyncContent = getUserInfoRetry.toJson(chatMessage);
//
//        setThreadCallbacks(threadId, uniqueId);
//        sendAsyncMessage(asyncContent, 4);
//    }

    private void setThreadCallbacks(long threadId, String uniqueId) {
        if (chatReady) {
            Callback callback = new Callback();
            callback.setDelivery(true);
            callback.setSeen(true);
            callback.setSent(true);
            callback.setUniqueId(uniqueId);
            ArrayList<Callback> callbacks = new ArrayList<>();
            callbacks.add(callback);
            threadCallbacks.put(threadId, callbacks);
        }
    }

    private void sendAsyncMessage(String asyncContent, int asyncMsgType, String logMessage) {
        if (chatReady) {

            ChatLogger.info(logMessage);
            ChatLogger.info(asyncContent);

            try {
                async.sendMessage(asyncContent, asyncMsgType);

            } catch (Exception e) {
                e.printStackTrace();
            }

            long lastSentMessageTimeout = 9 * 1000;
            lastSentMessageTime = new Date().getTime();

            if (state) {

                PingExecutor.getInstance().
                        scheduleAtFixedRate(() -> checkForPing(lastSentMessageTimeout),
                                0, 20000,
                                TimeUnit.MILLISECONDS);
            } else {
                ChatLogger.error("Async is Close");
            }
        } else {
            String jsonError = getErrorOutPut(ChatConstant.ERROR_CHAT_READY, ChatConstant.ERROR_CODE_CHAT_READY);
            ChatLogger.error(jsonError);
        }
    }

    /**
     * Get the list of the Device Contact
     */
  /*  private List<Contact> getPhoneContact(Context context) {
        String name, phoneNumber, lastName;
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor == null) throw new AssertionError();
        ArrayList<Contact> storeContacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            lastName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact();
            char ch1 = phoneNumber.charAt(0);
            if (Character.toString(ch1) != "+") {
                contact.setCellphoneNumber(phoneNumber.replaceAll(Character.toString(ch1), "+98"));
            }
            contact.setCellphoneNumber(phoneNumber.replaceAll(" ", ""));
            contact.setFirstName(name.replaceAll(" ", ""));
            contact.setLastName(lastName.replaceAll(" ", ""));
            storeContacts.add(contact);
        }
        cursor.close();
        return storeContacts;
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void uploadFileMessage(Activity activity, String description, long threadId, String mimeType, String filePath, String metadata) {
        if (Permission.Check_READ_STORAGE(activity)) {
            if (getFileServer() != null) {
                File file = new File(filePath);
                RetrofitHelperFileServer retrofitHelperFileServer = new RetrofitHelperFileServer(getFileServer());
                FileApi fileApi = retrofitHelperFileServer.getService(FileApi.class);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                Observable<Response<FileUpload>> uploadObservable = fileApi.sendFile(body, getToken(), TOKEN_ISSUER, name);
                uploadObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<FileUpload>>() {
                    @Override
                    public void call(Response<FileUpload> fileUploadResponse) {
                        if (fileUploadResponse.isSuccessful()) {
                            boolean error = fileUploadResponse.body().isHasError();
                            if (error) {
                                String errorMessage = fileUploadResponse.body().getMessage();
                                if (BuildConfig.DEBUG) Logger.e(errorMessage);
                            } else {

                                ResultFile result = fileUploadResponse.body().getResult();
                                int fileId = result.getId();
                                String hashCode = result.getHashCode();

                                MetaDataFile metaDataFile = new MetaDataFile();
                                FileMetaDataContent metaDataContent = new FileMetaDataContent();
                                metaDataContent.setHashCode(hashCode);
                                metaDataContent.setId(fileId);
                                metaDataContent.setName(result.getName());
                                //TODO handle this field
                                metaDataContent.setLocalPath(filePath);

                                metaDataContent.setLink(getFileServer() + "nzh/file/" + "?fileId=" + result.getId() + "&downloadable=" + true + "&hashCode=" + result.getHashCode());

                                metaDataFile.setFile(metaDataContent);

                                if (cache) {
                                    messageDatabaseHelper.saveFile(metaDataContent);
                                }

                                Gson gson = new Gson();
                                String jsonMeta = gson.toJson(metaDataFile);
//                                        JsonUtil.getJson(metaDataFile);
                                if (BuildConfig.DEBUG) Logger.json(jsonMeta);
                                sendTextMessageWithFile(description, threadId, jsonMeta, metadata);
                            }
                        }
                    }
                }, throwable -> {
                    if (BuildConfig.DEBUG) Logger.e(throwable.getMessage());
                });
            } else {
                if (BuildConfig.DEBUG) Logger.e("FileServer url Is null");
            }
        } else {
            String jsonError = getErrorOutPut(ChatConstant.ERROR_READ_EXTERNAL_STORAGE_PERMISSION, ChatConstant.ERROR_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
            if (BuildConfig.DEBUG) Logger.e(jsonError);
        }
    }

    private void sendImageFileMessage(Context context, Activity activity, String description, long threadId, Uri fileUri, String mimeType, String metadata) {
        if (fileServer != null) {
            if (Permission.Check_READ_STORAGE(activity)) {
                RetrofitHelperFileServer retrofitHelperFileServer = new RetrofitHelperFileServer(getFileServer());
                FileApi fileApi = retrofitHelperFileServer.getService(FileApi.class);
                String path = FilePick.getSmartFilePath(context, fileUri);
                File file = new File(path);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                String filePath = getRealPathFromURI(context, fileUri);
                Observable<Response<FileImageUpload>> uploadObservable = fileApi.sendImageFile(body, getToken(), TOKEN_ISSUER, name);
                uploadObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<FileImageUpload>>() {
                    @Override
                    public void call(Response<FileImageUpload> fileUploadResponse) {
                        if (fileUploadResponse.isSuccessful()) {
                            boolean error = fileUploadResponse.body().isHasError();
                            if (error) {
                                String errorMessage = fileUploadResponse.body().getErrorMessage();
                                Logger.e(errorMessage);
                            } else {

                                ResultImageFile result = fileUploadResponse.body().getResult();
                                int imageId = result.getId();
                                String hashCode = result.getHashCode();

                                MetaDataImageFile metaData = new MetaDataImageFile();
                                FileImageMetaData fileImageMetaData = new FileImageMetaData();
                                fileImageMetaData.setHashCode(hashCode);
                                fileImageMetaData.setId(imageId);
                                fileImageMetaData.setActualHeight(result.getActualHeight());
                                fileImageMetaData.setActualWidth(result.getActualWidth());
                                fileImageMetaData.setMimeType(mimeType);

                                fileImageMetaData.setLocalPath(filePath);

                                fileImageMetaData.setLink(getFileServer() + "nzh/uploadImage" + "?imageId=" + imageId + "&downloadable=" + "true" + "&hashCode=" + hashCode);
                                metaData.setFile(fileImageMetaData);
                                //Insert to cache
                                if (cache) {
                                    messageDatabaseHelper.saveImageFile(fileImageMetaData);
                                }

                                String metaJson = JsonUtil.getJson(metaData);
                                if (BuildConfig.DEBUG) Logger.json(metaJson);
                                sendTextMessageWithFile(description, threadId, metaJson, metadata);

                                FileImageUpload fileImageUpload = fileUploadResponse.body();
                                String imageJson = JsonUtil.getJson(fileImageUpload);
                                listenerManager.callOnUploadImageFile(imageJson, fileImageUpload);
                                if (log) Logger.json(imageJson);
                            }
                        }
                    }
                }, throwable -> Logger.e(throwable.getMessage()));

            } else {
                String jsonError = getErrorOutPut(ChatConstant.ERROR_READ_EXTERNAL_STORAGE_PERMISSION, ChatConstant.ERROR_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
                if (log) Logger.e(jsonError);
            }
        } else {
            if (BuildConfig.DEBUG) Logger.e("FileServer url Is null");
        }
    }*/

    //model
  /*  private class DeleteMessage {
        private boolean deleteForAll;

        public boolean isDeleteForAll() {
            return deleteForAll;
        }

        public void setDeleteForAll(boolean deleteForAll) {
            this.deleteForAll = deleteForAll;
        }
    }*/

    //TODO make it public
    // Add list of contacts with their mobile numbers and their cellphoneNumbers
   /* private void addContacts(ArrayList<String> firstNames, ArrayList<String> cellphoneNumbers) {
        ArrayList<String> lastNames = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < cellphoneNumbers.size(); i++) {
            emails.add("");
            lastNames.add("");
        }
        Observable<Response<AddContacts>> addContactsObservable;
        if (getPlatformHost() != null) {
            addContactsObservable = contactApi.addContacts(getToken(), TOKEN_ISSUER, firstNames, lastNames, emails, cellphoneNumbers, cellphoneNumbers);
            addContactsObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<AddContacts>>() {
                @Override
                public void call(Response<AddContacts> contactsResponse) {
                    boolean error = contactsResponse.body().getHasError();
                    if (contactsResponse.isSuccessful()) {
                        if (error) {
                            String jsonError = getErrorOutPut(contactsResponse.body().getMessage(), contactsResponse.body().getErrorCode());
                            if (BuildConfig.DEBUG) Logger.e(jsonError);
                        } else {
                            AddContacts contacts = contactsResponse.body();
                            String contactsJson = JsonUtil.getJson(contacts);
                            if (syncContacts) {
                                listenerManager.callOnSyncContact(contactsJson);
                                if (BuildConfig.DEBUG) Logger.i("SYNC_CONTACT");
                                syncContacts = false;
                            } else {
                                listenerManager.callOnAddContact(contactsJson);
                                if (BuildConfig.DEBUG) Logger.i("ADD_CONTACTS");
                            }
                            if (BuildConfig.DEBUG) Logger.json(contactsJson);
                        }
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if (BuildConfig.DEBUG) Logger.e("Error on add contacts", throwable.toString());
                    if (BuildConfig.DEBUG) Logger.e(throwable.getCause().getMessage());
                }
            });
        }
    }*/
    private ChatMessage getChatMessage(String contentThreadChat, String uniqueId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(contentThreadChat);
        chatMessage.setType(ChatMessageType.INVITATION);
        chatMessage.setToken(getToken());
        chatMessage.setUniqueId(uniqueId);
        chatMessage.setTokenIssuer("1");

        return chatMessage;
    }

    /**
     * Get the manager that manages registered listeners.
     */
    ChatListenerManager getListenerManager() {
        return listenerManager;
    }

    private ChatMessage getChatMessage(MessageVO jsonMessage) {
        ChatMessage message = new ChatMessage();
        message.setType(ChatMessageType.DELIVERY);
        message.setContent(String.valueOf(jsonMessage.getId()));
        message.setTokenIssuer("1");
        message.setToken(getToken());
        message.setUniqueId(getUniqueId());
        message.setTime(1000);
        return message;
    }

    private String reformatUserInfo(ChatMessage chatMessage, OutPutUserInfo outPutUserInfo) {

        ResultUserInfo result = new ResultUserInfo();

        UserInfo userInfo = gson.fromJson(chatMessage.getContent(), UserInfo.class);

        setUserId(userInfo.getId());
        result.setUser(userInfo);
        outPutUserInfo.setErrorCode(0);
        outPutUserInfo.setErrorMessage("");
        outPutUserInfo.setHasError(false);
        outPutUserInfo.setResult(result);
        return gson.toJson(outPutUserInfo);
    }

   /* private String reformatMuteThread(ChatMessage chatMessage, OutPutMute outPut) {
        outPut.setResult(chatMessage.getContent());
        outPut.setHasError(false);
        outPut.setErrorMessage("");
        return JsonUtil.getJson(outPut);
    }*/

    private String reformatCreateThread(ChatMessage chatMessage, OutPutThread outPutThread) {
        ChatLogger.info("RECEIVE_INVITATION ", chatMessage.getContent());

        ResultThread resultThread = new ResultThread();

        Gson gson = new Gson();
        ThreadVo threadVo = gson.fromJson(chatMessage.getContent(), ThreadVo.class);

        resultThread.setThreadVo(threadVo);
        outPutThread.setHasError(false);
        outPutThread.setErrorCode(0);
        outPutThread.setErrorMessage("");
        outPutThread.setResult(resultThread);
        return gson.toJson(outPutThread);
    }

   /* private void deviceIdRequest(String ssoHost, String serverAddress, String appId, String severName) {
        if (BuildConfig.DEBUG) Logger.i("GET_DEVICE_ID");
        currentDeviceExist = false;

        RetrofitHelperSsoHost retrofitHelperSsoHost = new RetrofitHelperSsoHost(ssoHost);
        TokenApi tokenApi = retrofitHelperSsoHost.getService(TokenApi.class);
        rx.Observable<Response<DeviceResult>> listObservable = tokenApi.getDeviceId("Bearer" + " " + getToken());
        listObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(deviceResults -> {
            if (deviceResults.isSuccessful()) {
                ArrayList<Device> devices = deviceResults.body().getDevices();
                for (Device device : devices) {
                    if (device.isCurrent()) {
                        currentDeviceExist = true;
                        if (BuildConfig.DEBUG) Logger.i("DEVICE_ID :" + device.getUid());
                        async.connect(serverAddress, appId, severName, token, ssoHost, device.getUid());
                        break;
                    }
                }
                if (!currentDeviceExist) {
                    String jsonError = getErrorOutPut(ChatConstant.ERROR_CURRENT_DEVICE, ChatConstant.ERROR_CODE_CURRENT_DEVICE);
                    if (BuildConfig.DEBUG) Logger.e(jsonError);
                }
            } else {
                if (deviceResults.code() == 401) {
                    String jsonError = getErrorOutPut("unauthorized", deviceResults.code());
                    if (BuildConfig.DEBUG) Logger.e(jsonError);
                }
                String jsonError = getErrorOutPut(deviceResults.message(), deviceResults.code());
                if (BuildConfig.DEBUG) Logger.e(jsonError);
            }
        }, (Throwable throwable) -> {
            Logger.e("Error on get devices");
        });
    }*/

    /**
     * Reformat the get thread response
     */
    private String reformatGetThreadsResponse(ChatMessage chatMessage, OutPutThreads outPutThreads, Callback callback) {
        ChatLogger.info(chatMessage.getContent());
//        List<ThreadVo> threadVos = new ArrayList<>();

        Type listType = new TypeToken<List<ThreadVo>>() {
        }.getType();
        List<ThreadVo> threadVos = gson.fromJson(chatMessage.getContent(), listType);

//        Type type = Types.newParameterizedType(List.class, ThreadVo.class);
//        JsonAdapter<List<ThreadVo>> adapter = moshi.adapter(type);
//        try {
//            threadVos = adapter.fromJson(chatMessage.getContent());
//        } catch (IOException e) {
//            if (BuildConfig.DEBUG) Logger.e(e.getMessage() + e.getCause());
//        }

        ResultThreads resultThreads = new ResultThreads();
        resultThreads.setThreadVos(threadVos);
        resultThreads.setContentCount(chatMessage.getContentCount());
        outPutThreads.setErrorCode(0);
        outPutThreads.setErrorMessage("");
        outPutThreads.setHasError(false);

        if (threadVos.size() + callback.getOffset() < chatMessage.getContentCount()) {
            resultThreads.setHasNext(true);
        } else {
            resultThreads.setHasNext(false);
        }
        resultThreads.setNextOffset(callback.getOffset() + threadVos.size());
        outPutThreads.setResult(resultThreads);
        return gson.toJson(outPutThreads);
    }


    private String reformatError(boolean hasError, ChatMessage chatMessage, OutPutHistory outPut) {
        Error error = gson.fromJson(chatMessage.getContent(), Error.class);
        ChatLogger.error("RECEIVED_ERROR", chatMessage.getContent());
        ChatLogger.error("ErrorMessage", error.getMessage());
        ChatLogger.error("ErrorCode", String.valueOf(error.getCode()));
        outPut.setHasError(hasError);
        outPut.setErrorMessage(error.getMessage());
        outPut.setErrorCode(error.getCode());
        return gson.toJson(outPut);
    }

    private String reformatGetContactResponse(ChatMessage chatMessage, OutPutContact outPutContact, Callback callback) {
        ResultContact resultContact = new ResultContact();
        ArrayList<Contact> contacts = gson.fromJson(chatMessage.getContent(), new TypeToken<List<Contact>>() {
        }.getType());

        //TODO maybe is not correct
        ArrayList<Contact> contactsList = new ArrayList<>();
        resultContact.setContacts(contactsList);
        resultContact.setContentCount(chatMessage.getContentCount());


        if (contacts.size() + callback.getOffset() < chatMessage.getContentCount()) {
            resultContact.setHasNext(true);
        } else {
            resultContact.setHasNext(false);
        }
        resultContact.setNextOffset(callback.getOffset() + contacts.size());
        resultContact.setContentCount(chatMessage.getContentCount());

        outPutContact.setResult(resultContact);
        outPutContact.setErrorMessage("");

        return gson.toJson(outPutContact);
    }

    private static synchronized String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String getToken() {
        return token;
    }

    private long getUserId() {
        return userId;
    }

    private void setUserId(long userId) {
        this.userId = userId;
    }

    private void setCallBacks(Boolean delivery, Boolean sent, Boolean seen, Boolean result, int requestType, Long offset, String uniqueId) {
        if (chatReady || asyncReady) {
            delivery = delivery != null ? delivery : false;
            sent = sent != null ? sent : false;
            seen = seen != null ? seen : false;
            result = result != null ? result : false;
            offset = offset != null ? offset : 0;
            Callback callback = new Callback();
            callback.setDelivery(delivery);
            callback.setOffset(offset);
            callback.setSeen(seen);
            callback.setSent(sent);
            callback.setRequestType(requestType);
            callback.setResult(result);
            messageCallbacks.put(uniqueId, callback);
        }
    }

    private void setPlatformHost(String platformHost) {
        this.platformHost = platformHost;
    }

    private String getPlatformHost() {
        return platformHost;
    }


    private void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    private String getFileServer() {
        return fileServer;
    }

    public interface GetThreadHandler {
        void onGetThread();
    }

    public interface SendTextMessageHandler {
        void onSent(String uniqueId, long threadId);

        void onSentResult(String content);
    }
}