package model.requests;

public abstract class Request {
    private long requestId;

    public abstract void acceptRequest();
}
