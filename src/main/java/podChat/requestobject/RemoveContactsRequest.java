package podChat.requestobject;


public class RemoveContactsRequest extends GeneralRequestObject {
    private long id;

    RemoveContactsRequest(Builder builder) {
        super(builder);
        this.id = builder.id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long id;

        public Builder(long id) {
            this.id = id;
        }


        public RemoveContactsRequest build() {
            return new RemoveContactsRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }
}
