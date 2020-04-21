package podChat.requestobject;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddParticipantsRequestModel extends GeneralRequestObject {
    private long threadId;
    private List<Long> contactIds;
    private List<String> usernames;
    private List<Long> coreUserIds;

    AddParticipantsRequestModel(Builder builder) {
        super(builder);
        this.contactIds = builder.contactIds;
        this.threadId = builder.threadId;
        this.usernames = builder.usernames;
        this.coreUserIds = builder.coreUserIds;
    }

    public AddParticipantsRequestModel() {
    }

    public static ThreadIdStep newBuilder() {

        return new Steps();
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public List<Long> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<Long> contactIds) {
        this.contactIds = contactIds;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public List<Long> getCoreUserIds() {
        return coreUserIds;
    }

    public void setCoreUserIds(List<Long> coreUserIds) {
        this.coreUserIds = coreUserIds;
    }

    public static interface ThreadIdStep {

        ActionStep threadId(Long threadId);

    }

    public static interface ActionStep {

        BuildStep withUsername(String username);

        BuildStep withUserNames(String... userNames);

        BuildStep withUserNames(List<String> userNames);

        BuildStep withContactId(Long contactId);

        BuildStep withContactIds(Long... contactIds);

        BuildStep withContactIds(List<Long> contactIds);

        BuildStep withCoreUserId(Long coreUserId);

        BuildStep withCoreUserIds(Long... coreUserIds);

        BuildStep withCoreUserIds(List<Long> coreUserIds);

    }

    public static interface BuildStep {

        AddParticipantsRequestModel build();

    }

    @Deprecated
    public static class Builder extends GeneralRequestObject.Builder<Builder> {

        private long threadId;
        private List<Long> contactIds;
        private List<String> usernames;
        private List<Long> coreUserIds;

        @Deprecated
        public Builder(long threadId, List<Long> contactIds) {
            this.threadId = threadId;
            this.contactIds = contactIds;
        }

        @Deprecated
        public Builder(long threadId, List<Long> contactIds, List<String> usernames) {
            this.contactIds = contactIds;
            this.threadId = threadId;
            this.usernames = usernames;
        }


        @Override
        protected Builder self() {
            return this;
        }

        public AddParticipantsRequestModel build() {
            return new AddParticipantsRequestModel(this);
        }
    }

    private static class Steps implements ThreadIdStep, ActionStep, BuildStep {

        private long threadId;
        private List<Long> contactIds;
        private List<String> userNames;
        private List<Long> coreUserIds;


        @Override
        public BuildStep withUsername(String username) {
            userNames = new ArrayList<>();
            this.userNames.add(username);
            return this;
        }

        @Override
        public BuildStep withUserNames(String... userNames) {
            this.userNames = Arrays.asList(userNames);
            return this;
        }

        @Override
        public BuildStep withUserNames(List<String> userNames) {
            this.userNames = userNames;
            return this;
        }

        @Override
        public BuildStep withContactId(Long contactId) {
            contactIds = new ArrayList<>();
            this.contactIds.add(contactId);
            return this;
        }

        @Override
        public BuildStep withContactIds(Long... contactIds) {
            this.contactIds = Arrays.asList(contactIds);
            return this;
        }

        @Override
        public BuildStep withContactIds(List<Long> contactIds) {
            this.contactIds = contactIds;
            return this;
        }

        @Override
        public BuildStep withCoreUserId(Long coreUserId) {
            coreUserIds = new ArrayList<>();
            this.coreUserIds.add(coreUserId);
            return this;
        }

        @Override
        public BuildStep withCoreUserIds(Long... coreUserIds) {
            this.coreUserIds = Arrays.asList(coreUserIds);
            return this;
        }

        @Override
        public BuildStep withCoreUserIds(List<Long> coreUserIds) {
            this.coreUserIds = coreUserIds;
            return this;
        }

        @Override
        public AddParticipantsRequestModel build() {

            AddParticipantsRequestModel request = new AddParticipantsRequestModel();

            request.setThreadId(threadId);

            if (contactIds != null) {
                request.setContactIds(contactIds);
            } else if (userNames != null) {
                request.setUsernames(userNames);
            } else if (coreUserIds != null) {
                request.setCoreUserIds(coreUserIds);
            }

            return request;
        }

        @Override
        public ActionStep threadId(Long threadId) {
            this.threadId = threadId;
            return this;
        }
    }
}
