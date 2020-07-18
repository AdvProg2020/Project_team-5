package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.RequestForServer;
import ApProject_OnlineShop.model.persons.Person;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FileTransferClientHandler extends Thread {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Person user;
    private Gson gson;

    public FileTransferClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        this.user = null;
        gson = new Gson();
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
        RequestForServer requestForServer = null;
        try {
            requestForServer = gson.fromJson(dataInputStream.readUTF(), RequestForServer.class);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dataOutputStream.writeUTF("upload failed.");
                dataOutputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Person person = Server.getOnlineUsers().get(requestForServer.getToken());
        byte[] file = null;
        try {
            file = dataInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dataOutputStream.writeUTF("upload failed.");
                dataOutputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            MainController.getInstance().getAccountAreaForSellerController().uploadFileProductOnServer(file, requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dataOutputStream.writeUTF("upload failed.");
                dataOutputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
