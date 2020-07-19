package ApProject_OnlineShop.bank.DateBaseForBank;

import ApProject_OnlineShop.bank.BankServer;
import ApProject_OnlineShop.bank.model.BankAccount;
import ApProject_OnlineShop.bank.model.Receipt;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoadDateBaseForBank {
    private Gson yaGson;

    public LoadDateBaseForBank() {
        this.yaGson = new Gson();
        File file = new File("BankResources");
        if (!file.exists())
            file.mkdir();
        file = new File("BankResources\\Accounts");
        if (!file.exists())
            file.mkdir();
        file = new File("BankResources\\Receipts");
        if (!file.exists())
            file.mkdir();
    }

    public void loadAccounts() throws IOException {
        File[] files = loadFolder("BankResources\\Accounts");
        if (files != null) {
            for (File file : files) {
                BankServer.getInstance().addBankAccount(yaGson.fromJson(readFile(file), BankAccount.class));
            }
        }
    }

    public void loadReceipts() throws IOException {
        File[] files = loadFolder("BankResources\\Receipts");
        if (files != null) {
            for (File file : files) {
                BankServer.getInstance().addReceipt(yaGson.fromJson(readFile(file), Receipt.class));
            }
        }
    }

    private File[] loadFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists())
            file.mkdir();
        return file.listFiles();
    }

    private String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
