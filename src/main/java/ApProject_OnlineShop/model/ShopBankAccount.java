package ApProject_OnlineShop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ShopBankAccount")
public class ShopBankAccount implements Serializable {
    @Id
    @Column(name = "AccountId", nullable = false, unique = true)
    private String accountId;

    @Column(name = "Username", nullable = false, unique = true)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "minimumAmount", nullable = false)
    private int minimumAmount;

    @Column(name = "FeePercent", nullable = false)
    private int bankingFeePercent;

    public ShopBankAccount(String userName, String password, String accountId) {
        this.userName = userName;
        this.password = password;
        this.accountId = accountId;
        this.minimumAmount = 10000;
        this.bankingFeePercent = 5;
    }

    public ShopBankAccount() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getMinimumAmount() {
        return minimumAmount;
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
    }
}
