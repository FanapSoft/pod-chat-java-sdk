package podChat.requestobject;

import podChat.mainmodel.Invitee;
import podChat.util.ThreadType;

import java.util.ArrayList;
import java.util.List;

public class RequestCreateThread extends GeneralRequestObject {

    private int type;
    private List<Invitee> invitees;
    private String uniqueName;
    private String title;
    private String description;
    private String image;
    private String metadata;

    RequestCreateThread(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.title = builder.title;
        this.uniqueName = builder.uniqueName;
        this.invitees = builder.invitees;
        this.description = builder.description;
        this.image = builder.image;
        this.metadata = builder.metadata;
    }

    RequestCreateThread() {
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

    public static FromStep newBuilder() {
        return new Steps();
    }

    public static interface FromStep {

        ActionStep init(int type,List<Invitee> invitees);

    }

    public static interface ActionStep {

        BuildStep title(String title);

        BuildStep description(String description);

        BuildStep image(String image);

        BuildStep metadata(String metadata);

    }

    public static interface Build {

        ActionStep uniqueName(String uniqueName);


    }

    public static interface BuildStep {

        RequestCreateThread build();

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

        public RequestCreateThread build() {
            return new RequestCreateThread(this);
        }
    }


    public static class Steps implements BuildStep, Build, ActionStep, FromStep {

        private int type;
        private List<Invitee> invitees;
        private String title;
        private String description;
        private String image;
        private String metadata;
        private String uniqueName;

        @Override
        public ActionStep init(int type,List<Invitee> invitees){
            this.type = type;
            this.invitees=invitees;
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
        public BuildStep metadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        @Override
        public ActionStep uniqueName(String uniqueName) {
            this.uniqueName = uniqueName;
            return this;
        }

        @Override
        public RequestCreateThread build() {
            RequestCreateThread request = new RequestCreateThread();

            request.setType(type);
            request.setInvitees(invitees);
            request.setTitle(title);
            request.setMetadata(metadata);
            request.setImage(image);
            request.setDescription(description);
            if (type == 2 || type == 4) {
                request.setUniqueName(uniqueName);
            }
            return request;
        }
    }

}
