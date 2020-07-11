package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.PasswordIncorrectException;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Person user;
    private Gson gson;

    public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        gson = new Gson();
        user = null;
    }

    @Override
    public void run() {
        handelClient();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handelClient() {
        String input = null;
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestForServer requestForServer = new Gson().fromJson(input, RequestForServer.class);
        try {
            handleRequest(requestForServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(RequestForServer requestForServer) throws IOException {
        if (requestForServer.getToken() != null)
            user = Server.getOnlineUsers().get(requestForServer.getToken());
        if (requestForServer.getController().equals("getCurrentPerson")) {
            getCurrentPerson();
        } else if (requestForServer.getController().equals("LoginRegisterController")) {
            loginRegisterControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("AccountAreaController")) {
            accountAreaControllerHandler(requestForServer);
        }
    }

    private void accountAreaControllerHandler(RequestForServer requestForServer) {
        Person person = Server.getOnlineUsers().get(requestForServer.getToken());
        if (person == null)
            return;
        if (requestForServer.getFunction().equals("showCategories")) {
            ArrayList<String> data;
            if (person instanceof Customer) {
                data = MainController.getInstance().getAccountAreaForCustomerController().showCategories();
            } else if (person instanceof Seller) {
                data = MainController.getInstance().getAccountAreaForSellerController().showCategories();
            } else if (person instanceof Manager) {
                data = MainController.getInstance().getAccountAreaForManagerController().showCategories();
            }
//            dataOutputStream.writeUTF(data);
        } else if (requestForServer.getFunction().equals("getUserPersonalInfo")) {

        } else if (requestForServer.getFunction().equals("editField")) {

        } else if (requestForServer.getFunction().equals("getSortedOrders")) {

        }
    }

    private void getCurrentPerson() {
        if (user == null) {
            try {
                dataOutputStream.writeUTF("null");
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (user instanceof Customer) {
            try {
                dataOutputStream.writeUTF("customer###" + gson.toJson(user, Customer.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (user instanceof Seller) {
            try {
                dataOutputStream.writeUTF("seller###" + gson.toJson(user, Seller.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (user instanceof Manager) {
            try {
                dataOutputStream.writeUTF("manager###" + gson.toJson(user, Manager.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loginRegisterControllerHandler(RequestForServer requestForServer) throws IOException {
        if (requestForServer.getFunction().equals("createAccount")) {
            try {
                ArrayList<String> details = new ArrayList<>();
                for (int i = 2; i < requestForServer.getInputs().size(); i++) {
                    details.add(requestForServer.getInputs().get(i));
                }
                MainController.getInstance().getLoginRegisterController()
                        .createAccount(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), details);
                dataOutputStream.writeUTF("successfully account created.");
                dataOutputStream.flush();
            } catch (UsernameIsTakenAlreadyException e) {
                try {
                    dataOutputStream.writeUTF(e.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    dataOutputStream.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {
                try {
                    dataOutputStream.writeUTF(mainManagerAlreadyRegistered.getMessage());
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileCantBeSavedException e) {
                e.printStackTrace();
            }
        } else if (requestForServer.getFunction().equals("loginUser")) {
            try {
                Person person = MainController.getInstance().getLoginRegisterController().loginUser(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
                String token = generateToken();
                Server.addOnlineUser(person, token);
                dataOutputStream.writeUTF("successfully login #" + token);
                dataOutputStream.flush();
            } catch (UsernameNotFoundException e) {
                try {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (PasswordIncorrectException e) {
                try {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestForServer.getFunction().equals("logoutUser")) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Server.removeOnlineUser(requestForServer.getToken());
            dataOutputStream.writeUTF("logout successfully");
            dataOutputStream.flush();
        }
    }

    private String generateToken() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder randomCode = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            randomCode.append(AlphaNumericString.charAt(index));
        }
        return randomCode.toString();
    }

}
