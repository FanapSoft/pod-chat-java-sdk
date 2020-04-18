package podChat.requestobject;

import podChat.mainmodel.Invitee;
import podChat.mainmodel.RequestThreadInnerMessage;

import java.util.List;

public class CreateThreadWithFileRequest<S extends RequestUploadFile> extends CreateThreadWithMessageRequest {
    private S file;

    CreateThreadWithFileRequest(Builder<S> builder) {
        super(builder);
        this.file = builder.file;

    }

    public S getFile() {
        return file;
    }

    public void setFile(S file) {
        this.file = file;
    }

    public static class Builder<S extends RequestUploadFile> extends CreateThreadWithMessageRequest.Builder<S> {
        private S file;

        public Builder(int type, List<Invitee> invitees, S file, int messageType) {
            super(type, invitees, messageType);
            this.file = file;
        }

        public Builder(int type, List<Invitee> invitees, int messageType) {
            super(type, invitees, messageType);
        }

        @Override
        public Builder message(RequestThreadInnerMessage message) {
            super.message(message);
            return this;
        }

        @Override
        public Builder title(String title) {
            super.title(title);
            return this;
        }

        @Override
        public Builder description(String description) {
            super.description(description);
            return this;
        }

        @Override
        public Builder image(String image) {
            super.image(image);
            return this;
        }

        @Override
        protected Builder self() {
            super.self();
            return this;
        }

        public CreateThreadWithFileRequest build() {
            return new CreateThreadWithFileRequest(this);
        }
    }
}