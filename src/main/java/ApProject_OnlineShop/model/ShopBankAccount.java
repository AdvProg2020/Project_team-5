package ApProject_OnlineShop.model;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;

import java.io.IOException;

public class ShopBankAccount {
    private String userName;
    private String password;
    private String accountID;
    private int minimumAmount;
    private int bankingFeePercent;

    public ShopBankAccount(String userName, String password, String accountID) {
        this.userName = userName;
        this.password = password;
        this.accountID = accountID;
        this.minimumAmount =10000;
        this.bankingFeePercent = 5;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public int getBankingFeePercent() {
        return bankingFeePercent;
    }

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

    public void setBankingFeePercent(int bankingFeePercent) {
        this.bankingFeePercent = bankingFeePercent;
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }
}
