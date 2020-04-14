package podChat.requestobject;


public class GetBlockedListRequest extends GeneralRequestObject  {
    private long count;
    private long offset;

    GetBlockedListRequest(Builder builder){
        super(builder);
        this.count = builder.count;
        this.offset = builder.offset;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder>{
        private long count;
        private long offset;

        public Builder count(long count){
            this.count = count;
            return this;
        }


        public Builder offset(long offset){
            this.offset = offset;
            return this;
        }


        public GetBlockedListRequest build(){
            return new GetBlockedListRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
