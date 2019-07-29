package podChat.mainmodel;


import podChat.model.ReplyInfoVO;

public class MessageVO {
    private boolean edited;
    private boolean editable;
    private boolean delivered;
    private boolean seen;
    private boolean deletable;
    private long id;
    private String uniqueId;
    private int messageType;
    private long previousId;
    private String message;
    private Participant participant;

    private long time;
    private long timeNanos;

    private String metadata;
    private String systemMetadata;
    private Thread conversation;
    private ReplyInfoVO replyInfoVO;
    private ForwardInfo forwardInfo;

    public MessageVO() {
    }

    public MessageVO(
            long id,
            boolean edited,
            boolean editable,
            boolean delivered,
            boolean seen,
            boolean deletable,
            String uniqueId,
            int messageType,
            long previousId,
            String message,
            Participant participant,
            long time,
            long timeNanos,
            String metadata,
            String systemMetadata,
            Thread conversation,
            ReplyInfoVO replyInfoVO,
            ForwardInfo forwardInfo
    ) {
        this.id = id;
        this.edited = edited;
        this.editable = editable;
        this.delivered = delivered;
        this.deletable = deletable;
        this.seen = seen;
        this.uniqueId = uniqueId;
        this.messageType = messageType;
        this.previousId = previousId;
        this.message = message;
        this.participant = participant;
        this.time = time;
        this.timeNanos = timeNanos;
        this.metadata = metadata;
        this.systemMetadata = systemMetadata;
        this.conversation = conversation;
        this.replyInfoVO = replyInfoVO;
        this.forwardInfo = forwardInfo;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(long previousId) {
        this.previousId = previousId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Thread getConversation() {
        return conversation;
    }

    public void setConversation(Thread conversation) {
        this.conversation = conversation;
    }

    public ReplyInfoVO getReplyInfoVO() {
        return replyInfoVO;
    }

    public void setReplyInfoVO(ReplyInfoVO replyInfoVO) {
        this.replyInfoVO = replyInfoVO;
    }

    public ForwardInfo getForwardInfo() {
        return forwardInfo;
    }

    public void setForwardInfo(ForwardInfo forwardInfo) {
        this.forwardInfo = forwardInfo;
    }

    public String getSystemMetadata() {
        return systemMetadata;
    }

    public void setSystemMetadata(String systemMetadata) {
        this.systemMetadata = systemMetadata;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getId() {
        return id;
    }

    public void setId(long messageId) {
        this.id = messageId;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public long getTimeNanos() {
        return timeNanos;
    }

    public void setTimeNanos(long timeNanos) {
        this.timeNanos = timeNanos;
    }
}
