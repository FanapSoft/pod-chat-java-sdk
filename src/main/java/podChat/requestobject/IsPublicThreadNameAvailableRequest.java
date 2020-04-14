package podChat.requestobject;


public class IsPublicThreadNameAvailableRequest extends GeneralRequestObject {
    private String  uniqueName;

    IsPublicThreadNameAvailableRequest(Builder builder) {
        super(builder);
        this.uniqueName = builder.uniqueName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String uniqueName;

        public Builder(String uniqueName) {
            this.uniqueName = uniqueName;
        }

        public IsPublicThreadNameAvailableRequest build() {
            return new IsPublicThreadNameAvailableRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
