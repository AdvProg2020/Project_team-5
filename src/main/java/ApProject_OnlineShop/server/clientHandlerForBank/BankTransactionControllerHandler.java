package ApProject_OnlineShop.server.clientHandlerForBank;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.server.RequestForServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankTransactionControllerHandler {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Person user;

    public BankTransactionControllerHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket, Person user) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        this.user = user;
    }

    public void bankTransactionControllerHandler(RequestForServer requestForServer) throws IOException {
        if (requestForServer.getFunction().equals("increaseCustomerCredit")){
            increaseCreditCustomerHandler(requestForServer);
        }else if (requestForServer.getFunction().equals("withdrawMoneySeller")){
            withdrawMoneySellerHandler(requestForServer);
        }else if(requestForServer.getFunction().equals("depositMoneySeller")){
            depositMoneySellerHandler(requestForServer);
        }
    }

    private void increaseCreditCustomerHandler(RequestForServer requestForServer) throws IOException {
        String response = MainController.getInstance().getBankTransactionsController().increaseCustomerCredit(requestForServer.getInputs().get(0),
                requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

    private void withdrawMoneySellerHandler(RequestForServer requestForServer) throws IOException {
        String response = MainController.getInstance().getBankTransactionsController().withdrawMoneySeller(requestForServer.getInputs().get(0),
                requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

    private void depositMoneySellerHandler(RequestForServer requestForServer) throws IOException {
        String response = MainController.getInstance().getBankTransactionsController().depositMoneySeller(requestForServer.getInputs().get(0),
                requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }
}
