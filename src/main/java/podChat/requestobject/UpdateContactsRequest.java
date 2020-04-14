package podChat.requestobject;


public class UpdateContactsRequest extends GeneralRequestObject {

    private String firstName;
    private String lastName;
    private String cellphoneNumber;
    private String email;
    private long id;
    private long ownerId;

    private UpdateContactsRequest(Builder builder) {
        super(builder);
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.cellphoneNumber = builder.cellphoneNumber;
        this.email = builder.email;
        this.ownerId=builder.ownerId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long id;
        private String firstName;
        private String lastName;
        private String cellphoneNumber;
        private String email;
        private long ownerId;

        public Builder(long id, String firstName, String lastName, String cellphoneNumber, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.cellphoneNumber = cellphoneNumber;
            this.email = email;
        }


        public Builder ownerId(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public UpdateContactsRequest build() {
            return new UpdateContactsRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
