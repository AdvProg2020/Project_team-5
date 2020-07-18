package ApProject_OnlineShop.server;

import ApProject_OnlineShop.model.persons.Person;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferClientHandler extends Thread {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Person user;

    public FileTransferClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        this.user = null;
    }

    @Override
    public void run() {
        super.run();
    }
}
