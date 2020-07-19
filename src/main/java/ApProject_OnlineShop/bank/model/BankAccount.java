package ApProject_OnlineShop.bank.model;

import ApProject_OnlineShop.bank.DateBaseForBank.DataBaseForBank;
import ApProject_OnlineShop.exception.FileCantBeSavedException;

import java.io.IOException;

public class BankAccount {
    String firstName;
    String lastName;
    String userName;
    String password;
    String accountNumber;
    long money;

    public BankAccount(String firstName, String lastName, String userName, String password, String accountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.money = 0;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public synchronized long getMoney() {
        return money;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public synchronized void setMoney(long money) {
        this.money = money;
        try {
            DataBaseForBank.getInstance().saveItem(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createAccountNumber(){
        return "" + (long)(Math.random() * 100000);
    }
}
