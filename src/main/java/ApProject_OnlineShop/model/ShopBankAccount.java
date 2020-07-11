package ApProject_OnlineShop.model;

public class ShopBankAccount {
    private String userName;
    private String password;
    private String accountID;

    public ShopBankAccount(String userName, String password, String accountID) {
        this.userName = userName;
        this.password = password;
        this.accountID = accountID;
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
}
