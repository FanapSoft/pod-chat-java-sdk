package podChat.requestobject;

import podChat.mainmodel.Invitee;
import podChat.mainmodel.RequestThreadInnerMessage;

import java.util.List;

public class CreateThreadWithMessageRequest extends BaseRequestObject {

    private int type;
    private String ownerSsoId;
    private List<Invitee> invitees;
    private String title;
    private RequestThreadInnerMessage message;
    private String description;
    private String image;
    private String uniqueName;

    CreateThreadWithMessageRequest(Builder<?> builder) {
        super(builder);
        this.type = builder.type;
        this.message = builder.message;
        this.title = builder.title;
        this.invitees = builder.invitees;
        this.description = builder.description;
        this.image = builder.image;
        this.uniqueName=builder.uniqueName;

    }

    public RequestThreadInnerMessage getMessage() {
        return message;
    }

    public void setMessage(RequestThreadInnerMessage message) {
        this.message = message;
    }

    public String getOwnerSsoId() {
        return ownerSsoId;
    }

    public void setOwnerSsoId(String ownerSsoId) {
        this.ownerSsoId = ownerSsoId;
    }

    public List<Invitee> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<Invitee> invitees) {
        this.invitees = invitees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public static class Builder<S extends UploadFileRequest> extends BaseRequestObject.Builder<Builder> {
        private final int type;
        private final List<Invitee> invitees;
        private String title;
        private RequestThreadInnerMessage message;
        private String description;
        private String image;
        private String uniqueName;

        public Builder(int type, List<Invitee> invitees,RequestThreadInnerMessage message) {
            this.invitees = invitees;
            this.type = type;
            this.message=message;
        }


        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public void uniqueName(String uniqueName) {
            this.uniqueName = uniqueName;
        }

        @Override
        protected CreateThreadWithMessageRequest.Builder self() {
            return this;
        }


        public CreateThreadWithMessageRequest build() {
            return new CreateThreadWithMessageRequest(this);
        }
    }
}