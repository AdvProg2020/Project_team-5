package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForFiltering;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForSorting;
import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.GoodInCart;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private static ServerSocket serverSocket;
    private static HashMap<String, Person> onlineUsers = new HashMap<String, Person>();
    private static HashMap<Long, ArrayList<GoodInCart>> carts = new HashMap<>();
    private static HashMap<Long, ControllerForFiltering> controllerForFilteringHashMap = new HashMap<>();
    private static HashMap<Long, ControllerForSorting> controllerForSortingHashMap = new HashMap<>();
    private static long idForCarts = 1;

    public static void main(String[] args) {
        backServerOnline();
    }

    private static void backServerOnline() {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            Database.getInstance().initializeShop();
        } catch (IOException | FileCantBeDeletedException | FileCantBeSavedException e) {
            e.printStackTrace();
        }*/
        System.out.println("Server backs online!");
        while (true) {
            System.out.println("waiting for client to connect");
            try {
                Socket clientSocket = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new ClientHandler(clientSocket, dataOutputStream, dataInputStream, serverSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addOnlineUser(Person person, String token) {
        onlineUsers.put(token, person);
    }

    public static void removeOnlineUser(String token) {
        onlineUsers.remove(token);
    }

    public static HashMap<String, Person> getOnlineUsers() {
        return onlineUsers;
    }

    public static HashMap<Long, ArrayList<GoodInCart>> getCarts() {
        return carts;
    }

    public static long getIdForCarts() {
        return idForCarts;
    }

    public static void setIdForCarts(long idForCarts) {
        Server.idForCarts = idForCarts;
    }

    public static HashMap<Long, ControllerForFiltering> getControllerForFilteringHashMap() {
        return controllerForFilteringHashMap;
    }

    public static HashMap<Long, ControllerForSorting> getControllerForSortingHashMap() {
        return controllerForSortingHashMap;
    }
}
