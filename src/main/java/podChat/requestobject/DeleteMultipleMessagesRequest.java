package podChat.requestobject;

import java.util.ArrayList;

public class DeleteMultipleMessagesRequest extends GeneralRequestObject {

    private ArrayList<Long> messageIds;
    private boolean deleteForAll;

    private DeleteMultipleMessagesRequest(Builder builder) {
        super(builder);
        this.deleteForAll = builder.deleteForAll;
        this.messageIds = builder.messageIds;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private boolean deleteForAll;
        private ArrayList<Long> messageIds;


        public Builder(ArrayList<Long> messageIds) {
            this.messageIds = messageIds;
        }

        public Builder messageIds(ArrayList<Long> messageIds) {
            this.messageIds = messageIds;
            return this;
        }


        public Builder deleteForAll(boolean deleteForAll) {
            this.deleteForAll = deleteForAll;
            return this;
        }


        public DeleteMultipleMessagesRequest build() {
            return new DeleteMultipleMessagesRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }

    }

    public ArrayList<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(ArrayList<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public boolean isDeleteForAll() {
        return deleteForAll;
    }

    public void setDeleteForAll(boolean deleteForAll) {
        this.deleteForAll = deleteForAll;
    }

}
