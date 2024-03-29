package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.RequestForServer;
import ApProject_OnlineShop.model.persons.Person;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

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
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            dataOutputStream.writeUTF("request received.");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("Resources\\fileProducts\\" + requestForServer.getInputs().get(0) + "." + requestForServer.getInputs().get(1));
        try {
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream("Resources\\fileProducts\\" + requestForServer.getInputs().get(0) + "." + requestForServer.getInputs().get(1));
            byte[] bytes = new byte[16 * 2048 * 4];
            int count;
            while ((count = dataInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, count);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*byte[] file = null;
        try {
            file = dataInputStream.readAllBytes();
            System.out.println(file.length);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dataOutputStream.writeUTF("upload failed.");
                dataOutputStream.flush();
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            File fileDest = File.createTempFile(requestForServer.getInputs().get(0), "." + requestForServer.getInputs().get(1), new File("Resources\\fileProducts"));
            Files.write(fileDest.toPath(), file);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dataOutputStream.writeUTF("upload failed.");
                dataOutputStream.flush();
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/
        /*try {
            dataOutputStream.writeUTF("file successfully uploaded.");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
