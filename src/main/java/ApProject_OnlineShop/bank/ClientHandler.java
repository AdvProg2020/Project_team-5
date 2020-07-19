import model.BankAccount;
import model.Receipt;
import model.Token;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket clientSocket;
    private BankServer bank;

    public ClientHandler(DataOutputStream dataOutputStream, DataInputStream dataInputStream, Socket clientSocket, BankServer bank) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.clientSocket = clientSocket;
        this.bank = bank;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String input = dataInputStream.readUTF();
                if (Pattern.matches("create_account [\\S]+ [\\S]+ [\\S]+ [\\S]+ [\\S]+", input))
                    createACCount(input);
                else if (Pattern.matches("get_token [\\S]+ [\\S]+", input))
                    getToken(input);
                else if (Pattern.matches("create_receipt [\\S]+ [\\S]+ [\\S]+ [\\S]+ [\\S]+ .*", input))
                    createReceipt(input);
                else if (Pattern.matches("get_balance [\\S]+", input))
                    getBalance(input);
                else if (Pattern.matches("pay [\\d]+", input))
                    pay(input);
                else if (Pattern.matches("get_transactions [\\S]+ .", input))
                    getTransactions(input);
                else if (input.equals("exit")) {
                    dataOutputStream.writeUTF("disconnected successfully");
                    dataOutputStream.flush();
                    clientSocket.close();
                    System.out.println("client disconnected");
                    break;
                } else {
                    dataOutputStream.writeUTF("invalid input");
                    dataOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createACCount(String input) throws IOException {
        String[] splitInput = input.substring("create_account ".length()).split(" ");
        if (!splitInput[3].equals(splitInput[4]))
            dataOutputStream.writeUTF("passwords do not match");
        if (!bank.isUsernameAvailable(splitInput[2]))
            dataOutputStream.writeUTF("username is not available");
        else {
            String accountNumber;
            while (true) {
                accountNumber = BankAccount.createAccountNumber();
                if (bank.isAccountNumberAvailable(accountNumber))
                    break;
            }
            bank.addBankAccount(new BankAccount(splitInput[0], splitInput[1], splitInput[2], splitInput[3], accountNumber));
            dataOutputStream.writeUTF(accountNumber);
        }
        dataOutputStream.flush();
    }

    public void getToken(String input) throws IOException {
        String[] splitInput = input.substring("get_token ".length()).split(" ");
        if (bank.isUsernameAvailable(splitInput[0]))
            dataOutputStream.writeUTF("this username doesn't have account");
        else if (!bank.isPasswordCorrect(splitInput[0], splitInput[1]))
            dataOutputStream.writeUTF("invalid username or password");
        else {
            String tokenString = Token.getRandomToken();
            bank.addToken(new Token(tokenString, splitInput[0]));
            dataOutputStream.writeUTF(tokenString);
        }
        dataOutputStream.flush();
    }

    public void createReceipt(String input) throws IOException {
        String[] splitInput = input.substring("create_receipt ".length()).split(" ");
        if (!Pattern.matches("(deposit|move|withdraw)", splitInput[1]))
            dataOutputStream.writeUTF("invalid receipt type");
        else if (!Pattern.matches("[\\d]+", splitInput[2]))
            dataOutputStream.writeUTF("invalid money");
            // invalid parameters passed
        else if (bank.findTokenByString(splitInput[0]).isExpired())
            dataOutputStream.writeUTF("token expired");
        else if ((splitInput[1].equals("move") || splitInput[1].equals("withdraw")) && (bank.findTokenByString(splitInput[0]) == null || !bank.findTokenByString(splitInput[0]).getUserName().equals(bank.findAccountByNumber(splitInput[3]).getUserName())))
            dataOutputStream.writeUTF("token is invalid");
        else if ((splitInput[1].equals("move") || splitInput[1].equals("withdraw")) && bank.findAccountByNumber(splitInput[3]) == null)
            dataOutputStream.writeUTF("source account id is invalid");
        else if (splitInput[1].equals("move") && bank.findAccountByNumber(splitInput[4]) == null)
            dataOutputStream.writeUTF("dest account id is invalid");
        else if (splitInput[1].equals("move") && splitInput[3].equals(splitInput[4]))
            dataOutputStream.writeUTF("equal source and dest account");
        else if (splitInput[1].equals("deposit") && bank.findTokenByString(splitInput[0]) == null)
            dataOutputStream.writeUTF("token is invalid");
        else if (splitInput[1].equals("deposit") && bank.findAccountByNumber(splitInput[4]) == null)
            dataOutputStream.writeUTF("dest account id is invalid");
        else if ((splitInput[1].equals("deposit") && !splitInput[3].equals("-1")) || (splitInput[1].equals("withdraw") && !splitInput[4].equals("-1")))
            dataOutputStream.writeUTF("invalid account id");
        else {
            System.out.println("hi");
            String inputsWithoutDescription = "create-receipt " + splitInput[0] + " " + splitInput[1] + " " + splitInput[2] + " " + splitInput[3] + " " + splitInput[4] + " ";
            String description = input.substring(inputsWithoutDescription.length());
            if (!Pattern.matches("[\\w|\\s]*", description))
                dataOutputStream.writeUTF("your input contains invalid characters");
            else {
                String id = Receipt.getRandomReceiptId();
//                while (bank.getReceiptById(id) != null){
//                    id = Receipt.getRandomReceiptId();
//                }
                do {
                    id = Receipt.getRandomReceiptId();
                } while (bank.getReceiptById(id) != null);
                bank.addReceipt(new Receipt(id, splitInput[1], splitInput[2], splitInput[3], splitInput[4], description));
                dataOutputStream.writeUTF(id);
            }
        }
        dataOutputStream.flush();
    }

    public void getBalance(String input) throws IOException {
        String tokenString = input.substring("get_balance ".length());
        if (bank.findTokenByString(tokenString) == null)
            dataOutputStream.writeUTF("token is invalid");
        else if (bank.findTokenByString(tokenString).isExpired())
            dataOutputStream.writeUTF("token expired");
        else {
            dataOutputStream.writeUTF("" + bank.findAccountByUsername(bank.findTokenByString(tokenString).getUserName()).getMoney());
        }
        dataOutputStream.flush();
    }

    public void pay(String input) throws IOException {
        String receiptId = input.substring("pay ".length());
        if (bank.getReceiptById(receiptId) == null)
            dataOutputStream.writeUTF("invalid receipt id");
        else {
            Receipt receipt = bank.getReceiptById(receiptId);
            if (receipt.getPaid() == 1)
                dataOutputStream.writeUTF("receipt is paid before");
            else if (receipt.getSourceAccountID() != -1 && bank.findAccountByNumber("" + receipt.getSourceAccountID()).getMoney() < receipt.getMoney())
                dataOutputStream.writeUTF("source account does not have enough money");
            else if ((receipt.getSourceAccountID() != -1 && bank.findAccountByNumber("" + receipt.getSourceAccountID()) == null) ||
                    (receipt.getDestAccountID() != -1 && bank.findAccountByNumber("" + receipt.getDestAccountID()) == null))
                dataOutputStream.writeUTF("invalid account id");
            else {
                if (receipt.getSourceAccountID() != -1) {
                    BankAccount sourceAccount = bank.findAccountByNumber("" + receipt.getSourceAccountID());
                    sourceAccount.setMoney(sourceAccount.getMoney() - receipt.getMoney());
                }
                if (receipt.getDestAccountID() != -1) {
                    BankAccount destAccount = bank.findAccountByNumber("" + receipt.getDestAccountID());
                    destAccount.setMoney(destAccount.getMoney() + receipt.getMoney());
                }
                dataOutputStream.writeUTF("done successfully");
                receipt.setPaid();
            }
            dataOutputStream.flush();
        }
    }

    public void getTransactions(String input) throws IOException {
        String[] splitInput = input.substring("get_transactions ".length()).split(" ");
        if (bank.findTokenByString(splitInput[0]) == null)
            dataOutputStream.writeUTF("token is invalid");
        else if (bank.findTokenByString(splitInput[0]).isExpired())
            dataOutputStream.writeUTF("token expired");
        else if (!Pattern.matches("[*|+|-]", splitInput[1])) {
            if (bank.getReceiptById(splitInput[1]) == null || (!bank.findTokenByString(splitInput[0]).getUserName().equals(bank.findAccountByNumber("" + bank.getReceiptById(splitInput[1]).getSourceAccountID()).getUserName())
            && !bank.findTokenByString(splitInput[0]).getUserName().equals(bank.findAccountByNumber("" + bank.getReceiptById(splitInput[1]).getDestAccountID()).getUserName())))
                dataOutputStream.writeUTF("invalid receipt Id");
        } else {
            dataOutputStream.writeUTF(bank.getTransactions(bank.findTokenByString(splitInput[0]).getUserName(), splitInput[1]));
        }
        dataOutputStream.flush();
        //invalid receipt Id
    }
}
