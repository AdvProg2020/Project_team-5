package model.requests;

public abstract class Request {
    private static long requestCount = 1;
    private long requestId;

    public Request() {
        this.requestId = requestCount++;
    }

    public long getRequestId() {
        return requestId;
    }

    public abstract void acceptRequest();
}
