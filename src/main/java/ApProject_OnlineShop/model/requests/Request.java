package ApProject_OnlineShop.model.requests;

import ApProject_OnlineShop.exception.FileCantBeSavedException;

import java.io.IOException;

public abstract class Request {
    private static long requestCount = 1;
    private long requestId;
    private  String name = this.getClass().getSimpleName();

    public Request() {
        this.requestId = requestCount++;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = this.getClass().getSimpleName();
    }

    public String getBriefInfo() {
        return "request id: " + this.requestId + " " + this.getClass().getSimpleName();
    }

    public long getRequestId() {
        return requestId;
    }

    public abstract void acceptRequest() throws FileCantBeSavedException, IOException;

    public static void setRequestCount(long requestCount) {
        Request.requestCount = requestCount;
    }
}
