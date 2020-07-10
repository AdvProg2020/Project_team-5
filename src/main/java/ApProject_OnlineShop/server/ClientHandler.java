package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.PasswordIncorrectException;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.model.persons.Person;
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

    public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        handelClient();
    }

    private void handelClient() {
        String input = null;
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestForServer requestForServer = new Gson().fromJson(input, RequestForServer.class);
        handleRequest(requestForServer);
    }

    private void handleRequest(RequestForServer requestForServer) {
        Person currentPerson = null;
        if (requestForServer.getToken() != null)
            currentPerson = Server.getOnlineUsers().get(requestForServer.getToken());
        if (requestForServer.getController().equals("LoginRegisterController")) {
            loginRegisterControllerHandler(requestForServer);
        }
    }

    private void loginRegisterControllerHandler(RequestForServer requestForServer) {
        if (requestForServer.getFunction().equals("createAccount")) {
            try {
                MainController.getInstance().getLoginRegisterController()
                        .createAccount(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1),
                                (ArrayList<String>) requestForServer.getInputs().subList(2, requestForServer.getInputs().size()));
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
                MainController.getInstance().getLoginRegisterController().loginUser(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
                dataOutputStream.writeUTF("successfully login.");
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

        }
    }
}
