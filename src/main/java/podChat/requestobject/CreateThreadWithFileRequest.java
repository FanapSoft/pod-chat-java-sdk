package podChat.requestobject;

import podChat.mainmodel.Invitee;
import podChat.mainmodel.RequestThreadInnerMessage;

import java.util.List;

public class CreateThreadWithFileRequest<S extends UploadFileRequest> extends CreateThreadWithMessageRequest {
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

    public static class Builder<S extends UploadFileRequest> extends CreateThreadWithMessageRequest.Builder<S> {
        private S file;

        public Builder(int type, List<Invitee> invitees, S file, RequestThreadInnerMessage messag) {
            super(type, invitees, messag);
            this.file = file;
        }

        public Builder(int type, List<Invitee> invitees,  RequestThreadInnerMessage messag) {
            super(type, invitees, messag);
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