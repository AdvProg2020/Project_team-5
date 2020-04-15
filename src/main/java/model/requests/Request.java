package model.requests;

public abstract class Request {
    private long requestId;

    public long getRequestId() {
        return requestId;
    }

    public abstract void acceptRequest();
}
