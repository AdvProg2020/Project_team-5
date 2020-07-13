package ApProject_OnlineShop.model;

import java.time.LocalTime;

public class Massage {
    private String senderUserName;
    private String receiverUserName;
    private String massage;
    private LocalTime time;

    public Massage(String senderUserName, String receiverUserName, String massage) {
        this.senderUserName = senderUserName;
        this.receiverUserName = receiverUserName;
        this.massage = massage;
        this.time = LocalTime.now();
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public String getMassage() {
        return massage;
    }

    public LocalTime getTime() {
        return time;
    }
}
