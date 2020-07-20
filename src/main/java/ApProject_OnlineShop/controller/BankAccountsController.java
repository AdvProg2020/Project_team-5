package ApProject_OnlineShop.controller;

import java.io.*;
import java.net.Socket;

public class BankAccountsController {
    int bankPort;
    private static final String host="127.0.0.1";

    public BankAccountsController(int bankPort) {
        this.bankPort = bankPort;
    }

    public String createBankAccount(String firstName, String lastName, String userName, String password, String repeatPassword) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("create_account " + firstName + " " + lastName + " " + userName + " " + password + " " + repeatPassword);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
        return response;
    }

    public String getToken(String username, String password) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("get_token " + username + " " + password);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
        return response;
    }

    public String createReceipt(String token, String type, String money, String sourceId, String destinationID, String description) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("create_receipt " + token + " " + type + " " + money + " " + sourceId + " " + destinationID + " " + description);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
        return response;
    }

    public String getTransactions(String token, String type) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("get_transactions " + token + " " + type);
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
        return response;
    }

    public String pay(String receiptID) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("pay " + receiptID );
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
        return response;
    }

    public String getBalance(String token) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("get_balance " + token );
        dataOutputStream.flush();
        String response = dataInputStream.readUTF();
        dataOutputStream.writeUTF("exit");
        dataOutputStream.flush();
        return response;
    }
}
