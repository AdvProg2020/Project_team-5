package ApProject_OnlineShop.bank;

import ApProject_OnlineShop.bank.DateBaseForBank.DataBaseForBank;
import com.google.gson.Gson;
import ApProject_OnlineShop.bank.model.*;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BankServer {
    private static BankServer bankServer = new BankServer();
    private static final int port = 8090;
    private ServerSocket serverSocket;
    private ArrayList<BankAccount> accounts;
    private ArrayList<Token> tokens;
    private ArrayList<Receipt> receipts;

    //    public BankServer(int port) throws IOException {
//        this.port = port;
//        this.serverSocket = new ServerSocket(this.port);
//        this.accounts = new ArrayList<>();
//        this.tokens = new ArrayList<>();
//        this.receipts = new ArrayList<>();
//    }

    private BankServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accounts = new ArrayList<>();
        this.tokens = new ArrayList<>();
        this.receipts = new ArrayList<>();
    }

    public static BankServer getInstance() {
        return bankServer;
    }

    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                new ClientHandler(dataOutputStream, dataInputStream, clientSocket, bankServer).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addBankAccount(BankAccount account) {
        accounts.add(account);
        try {
            DataBaseForBank.getInstance().saveItem(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username) {
        for (BankAccount account : accounts) {
            if (account.getUserName().equals(username))
                return false;
        }
        return true;
    }

    public boolean isAccountNumberAvailable(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return false;
        }
        return true;
    }

    public boolean isPasswordCorrect(String username, String password) {
        for (BankAccount account : accounts) {
            if (account.getUserName().equals(username) && account.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public Token findTokenByString(String tokenString) {
        for (Token token : tokens) {
            if (token.getTokenString().equals(tokenString))
                return token;
        }
        return null;
    }

    public BankAccount findAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return account;
        }
        return null;
    }

    public Receipt getReceiptById(String receiptId) {
        for (Receipt receipt : receipts) {
            String id = "" + receipt.getId();
            if (id.equals(receiptId))
                return receipt;
        }
        return null;
    }

    public BankAccount findAccountByUsername(String username) {
        for (BankAccount account : accounts) {
            if (account.getUserName().equals(username))
                return account;
        }
        return null;
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
        try {
            DataBaseForBank.getInstance().saveItem(receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTransactions(String username, String type) {
        long accountId = Long.parseLong(findAccountByUsername(username).getAccountNumber());
        String transactions = "";
        if (type.equals("*")) {
            for (Receipt receipt : receipts) {
                if ((receipt.getSourceAccountID() == accountId || receipt.getDestAccountID() == accountId) && receipt.getPaid() == 1)
                    transactions += new Gson().toJson(receipt) + "*";
            }
        } else if (type.equals("+")) {
            for (Receipt receipt : receipts) {
                if (receipt.getDestAccountID() == accountId && receipt.getPaid() == 1)
                    transactions += new Gson().toJson(receipt) + "*";
            }
        } else if (type.equals("-")) {
            for (Receipt receipt : receipts) {
                if (receipt.getSourceAccountID() == accountId && receipt.getPaid() == 1)
                    transactions += new Gson().toJson(receipt) + "*";
            }
        } else if (Pattern.matches("[\\d]+", type)) {
            for (Receipt receipt : receipts) {
                if (receipt.getId() == Long.parseLong(type)) {
                    transactions += new Gson().toJson(receipt) + "*";
                }
            }
        }
        if (transactions.length() > 0)
            transactions = transactions.substring(0, transactions.length() - 1);
        return transactions;
    }
}
