package podChat.requestobject;

import podChat.mainmodel.Invitee;
import podChat.util.ThreadType;

import java.util.List;

public class CreateThreadRequest extends GeneralRequestObject {

    private int type;
    private List<Invitee> invitees;
    private String uniqueName;
    private String title;
    private String description;
    private String image;
    private String metadata;

    public CreateThreadRequest(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.title = builder.title;
        this.uniqueName = builder.uniqueName;
        this.invitees = builder.invitees;
        this.description = builder.description;
        this.image = builder.image;
        this.metadata = builder.metadata;
    }

    public CreateThreadRequest() {
    }

    public static CreateThreadStep newBuilder() {
        return new Steps();
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public static interface CreateThreadStep {

        OptionalStep publicThreadOrChannel(int threadType, List<Invitee> invitees, String uniqueName);

        OptionalStep NonPublicThread(int threadType, List<Invitee> invitees);

    }


    public static interface OptionalStep {

        BuildStep metadata(String metadata);

        BuildStep title(String title);

        BuildStep description(String description);

        BuildStep image(String image);

        CreateThreadRequest build();
    }

    public static interface BuildStep {

        CreateThreadRequest build();
    }

    private static class Steps implements CreateThreadStep, OptionalStep, BuildStep {
        private int type;
        private List<Invitee> invitees;
        private String uniqueName;
        private String title;
        private String description;
        private String image;
        private String metadata;

        @Override
        public OptionalStep publicThreadOrChannel(int threadType, List<Invitee> invitees, String uniqueName) {
            this.type = threadType;
            this.invitees = invitees;
            this.uniqueName = uniqueName;
            return this;
        }

        @Override
        public OptionalStep NonPublicThread(int threadType, List<Invitee> invitees) {
            this.type = threadType;
            this.invitees = invitees;
            return this;
        }

        @Override
        public BuildStep metadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        @Override
        public BuildStep title(String title) {
            this.title = title;
            return this;
        }

        @Override
        public BuildStep description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public BuildStep image(String image) {
            this.image = image;
            return this;
        }

        @Override
        public CreateThreadRequest build() {

            CreateThreadRequest request = new CreateThreadRequest();

            request.setDescription(description);
            request.setImage(image);
            request.setInvitees(invitees);
            request.setTitle(title);
            request.setType(type);
            request.setMetadata(metadata);

            if (type == ThreadType.PUBLIC_GROUP || type == ThreadType.CHANNEL_GROUP) {
                request.setUniqueName(uniqueName);
            }

            return request;


        }
    }

    @Deprecated
    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private final int type;
        private final List<Invitee> invitees;
        private String uniqueName;
        private String title;
        private String description;
        private String image;
        private String metadata;

        @Deprecated
        public Builder(int type, List<Invitee> invitees) {
            this.invitees = invitees;
            this.type = type;
        }

        @Deprecated
        public Builder(int type, List<Invitee> invitees, String uniqueName) {
            this.invitees = invitees;
            this.type = type;
            this.uniqueName = uniqueName;
        }

        public Builder metadata(String metadata) {
            this.metadata = metadata;
            return this;
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

        @Override
        protected Builder self() {
            return this;
        }

        public CreateThreadRequest build() {
            return new CreateThreadRequest(this);
        }
    }


}