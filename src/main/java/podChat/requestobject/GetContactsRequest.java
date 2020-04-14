package podChat.requestobject;


public class GetContactsRequest extends BaseRequestObject {
    private String query;

    GetContactsRequest(Builder builder) {
        super(builder);
        this.query=builder.query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public static class Builder extends BaseRequestObject.Builder<Builder> {
        private String query;

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public GetContactsRequest build() {
            return new GetContactsRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
