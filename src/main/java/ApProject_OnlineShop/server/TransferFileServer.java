package ApProject_OnlineShop.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TransferFileServer {
    private static ServerSocket serverSocketForFile;

    public static void main(String[] args) {
        fileTransferServerOnline();
    }

    private static void fileTransferServerOnline() {
        try {
            serverSocketForFile = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server for transfer files is online...");
        while (true) {
            try {
                Socket clientSocket = serverSocketForFile.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new FileTransferClientHandler(clientSocket, dataOutputStream, dataInputStream, serverSocketForFile).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
