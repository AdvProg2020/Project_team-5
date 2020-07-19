package ApProject_OnlineShop.bank.model;


import ApProject_OnlineShop.bank.DateBaseForBank.DataBaseForBank;
import ApProject_OnlineShop.exception.FileCantBeSavedException;

import java.io.IOException;

public class Receipt {
    Long id;
    String receiptType;
    long money;
    long sourceAccountID;
    long destAccountID;
    String description;
    int paid;

    public Receipt(String id, String receiptType, String money , String sourceAccountID, String destAccountID, String description) {
        this.id = Long.parseLong(id);
        this.money = Long.parseLong(money);
        this.sourceAccountID = Long.parseLong(sourceAccountID);
        this.destAccountID = Long.parseLong(destAccountID);
        this.description = description;
        this.paid = 0;
        this.receiptType = receiptType;
    }

    public long getMoney() {
        return money;
    }

    public int getPaid() {
        return paid;
    }

    public long getId() {
        return id;
    }

    public long getSourceAccountID() {
        return sourceAccountID;
    }

    public long getDestAccountID() {
        return destAccountID;
    }

    public void setPaid(){
        this.paid = 1;
        try {
            DataBaseForBank.getInstance().saveItem(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRandomReceiptId(){
        return "" + (long)(Math.random() * 10000000);
    }
}
