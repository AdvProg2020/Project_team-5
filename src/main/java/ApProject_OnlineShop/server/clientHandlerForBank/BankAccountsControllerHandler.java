package ApProject_OnlineShop.server.clientHandlerForBank;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.server.RequestForServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankAccountsControllerHandler {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Person user;

    public BankAccountsControllerHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket, Person user) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        this.user = user;
    }

    public void bankAccountsControllerHandler(RequestForServer requestForServer) throws IOException {
        if (requestForServer.getFunction().equals("createBankAccount")) {
           createAccountHandler(requestForServer);
        }
    }

    private void createAccountHandler(RequestForServer requestForServer) throws IOException {
        String response = MainController.getInstance().getBankAccountsController().createBankAccount(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1),
                requestForServer.getInputs().get(2),requestForServer.getInputs().get(3), requestForServer.getInputs().get(5));
        if(!response.startsWith("password") &&response.startsWith("username")){
            Person user = Shop.getInstance().findUser(requestForServer.getInputs().get(2));
            if (user instanceof Customer)
                ((Customer) user).setBankAccountId(response);
            if (user instanceof Seller)
                ((Seller)user).setBankAccountId(response);
        }
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

}
