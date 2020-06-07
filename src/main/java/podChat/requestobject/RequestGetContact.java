package podChat.requestobject;


public class RequestGetContact extends BaseRequestObject {
    private String email;
    private String cellphoneNumber;
    private Long id;
    private String uniqueId;
    private String query;

    RequestGetContact(Builder builder) {
        super(builder);
        this.email = builder.email;
        this.cellphoneNumber = builder.cellphoneNumber;
        this.id = builder.id;
        this.uniqueId = builder.uniqueId;
        this.query = builder.query;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public static class Builder extends BaseRequestObject.Builder<Builder> {
        private String email;
        private String cellphoneNumber;
        private Long id;
        private String uniqueId;
        private String query;

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder cellphoneNumber(String cellphoneNumber) {
            this.cellphoneNumber = cellphoneNumber;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder uniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
            return this;
        }

        public RequestGetContact build() {
            return new RequestGetContact(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
