package ApProject_OnlineShop.GUI;

import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.requests.*;
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class FxmlController {
    private static boolean isMainLayoutPlay;
    private static boolean isAccountAreaPlay;
    private static boolean isAllProductPlay;
    private static String token;
    private static long id;
    private static final int port = 17689;
    private static final String ip = "2.tcp.ngrok.io";

    public void setScene(String address, String title) {
        playButtonMusic();
        Stage stage = StageController.getStage();
        Parent rootParent = null;
        try {
            rootParent = FXMLLoader.load(getClass().getClassLoader().getResource(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(rootParent, 1000, 800));
        stage.show();
    }

    public Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String header, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    public static void playButtonMusic() {
        String resource2 = Paths.get("src/main/resources/musics/buttonsEffects/Keyboard_Button_1-fesliyanstudios.com.mp3").toUri().toString();
        realPlay(resource2);
    }

    public static void playErrorMusic() {
        String resource2 = Paths.get("src/main/resources/musics/buttonsEffects/Error-sound.mp3").toUri().toString();
        realPlay(resource2);
    }

    public static void playSuccessMusic() {
        String resource2 = Paths.get("src/main/resources/musics/buttonsEffects/Success-sound-effect (mp3cut.net).mp3").toUri().toString();
        realPlay(resource2);
    }

    private static void realPlay(String url) {
        AudioClip audioClip = new AudioClip(url);
        audioClip.setVolume(0.2);
        audioClip.play();
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void playMusicForMainLayout(boolean show) {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/musics/mainLayout.mp3").toUri().toString());
        audioClip.setVolume(0.05);
        audioClip.setCycleCount(INDEFINITE);
        if (show) {
            audioClip.play();
        } else {
            audioClip.stop();
        }
    }

    private static void playMusicForAllProducts(boolean show) {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/musics/allProducts.mp3").toUri().toString());
        audioClip.setVolume(0.05);
        audioClip.setCycleCount(INDEFINITE);
        if (show) {
            audioClip.play();
        } else {
            audioClip.stop();
        }
    }

    private static void playMusicForAccountArea(boolean show) {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/musics/accountArea.mp3").toUri().toString());
        audioClip.setVolume(0.05);
        audioClip.setCycleCount(INDEFINITE);
        if (show) {
            audioClip.play();
        } else {
            audioClip.stop();
        }
    }

    public static void playMusicBackGround(boolean mainLayout, boolean allProducts, boolean accountArea) {
        if (mainLayout == isMainLayoutPlay && allProducts == isAllProductPlay && accountArea == isAccountAreaPlay)
            return;
        playMusicForMainLayout(mainLayout);
        playMusicForAccountArea(accountArea);
        playMusicForAllProducts(allProducts);
        isMainLayoutPlay = mainLayout;
        isAccountAreaPlay = accountArea;
        isAllProductPlay = allProducts;
    }

    public static String connectToServer(RequestForServer requestForServer) {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Successfully connected to server!");
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Gson gson = new Gson();
            dataOutputStream.writeUTF(gson.toJson(requestForServer, RequestForServer.class));
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] connectToServerBytes(RequestForServer requestForServer) {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Successfully connected to server!");
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Gson gson = new Gson();
            dataOutputStream.writeUTF(gson.toJson(requestForServer, RequestForServer.class));
            dataOutputStream.flush();
            return dataInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String connectToFileTransferServer(RequestForServer requestForServer, File file) {
        try {
            Socket socket = new Socket("2.tcp.ngrok.io", 11880);
            System.out.println(firstRequestToFileTransferServer(socket, requestForServer));
            String response = secondRequestToFileTransferServer(socket, file);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String firstRequestToFileTransferServer(Socket socket, RequestForServer requestForServer) {
        try {

            System.out.println("Successfully connected to file server!");
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Gson gson = new Gson();
            dataOutputStream.writeUTF(gson.toJson(requestForServer, RequestForServer.class));
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String secondRequestToFileTransferServer(Socket socket, File file) {
        try {
            System.out.println("Successfully connected to file server!");
            byte[] bytes = new byte[16 * 2048 * 4];
            InputStream inputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            int count;
            while ((count = inputStream.read(bytes)) > 0) {
                dataOutputStream.write(bytes, 0, count);
            }
            inputStream.close();
            return "file successfully uploaded.";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String connectToServerDownload(RequestForServer requestForServer) {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            System.out.println("Successfully connected to server!");
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Gson gson = new Gson();
            dataOutputStream.writeUTF(gson.toJson(requestForServer, RequestForServer.class));
            dataOutputStream.flush();
            File file = new File("Downloads\\" + requestForServer.getInputs().get(1));
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream("Downloads\\" + requestForServer.getInputs().get(1));
            byte[] bytes = new byte[16 * 2048 * 4];
            int count;
            while ((count = dataInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, count);
            }
            outputStream.close();
            return "file successfully downloaded.";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        FxmlController.token = token;
    }

    public static Person getCurrentPerson() {
        String input = connectToServer(new RequestForServer("getCurrentPerson", null, token, null));
        Person person = null;
        if (input.startsWith("customer")) {
            person = new Gson().fromJson(input.split("###")[1], Customer.class);
        } else if (input.startsWith("seller")) {
            person = new Gson().fromJson(input.split("###")[1], Seller.class);
        } else if (input.startsWith("manager")) {
            person = new Gson().fromJson(input.split("###")[1], Manager.class);
        } else if (input.startsWith("supporter")) {
            person = new Gson().fromJson(input.split("###")[1], Supporter.class);
        }
        return person;
    }

    public ArrayList<String> convertStringToArraylist(String data) {
        if (data.equals(""))
            return new ArrayList<>();
        String[] split = data.split("#");
        ArrayList<String> output = new ArrayList<>();
        for (String s : split) {
            output.add(s);
        }
        return output;
    }

    public ArrayList<Long> convertArrayListStringToArrayListLong(List<String> inputs) {
        ArrayList<Long> productIds = new ArrayList<>();
        for (String s : inputs) {
            productIds.add(Long.parseLong(s));
        }
        return productIds;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        FxmlController.id = id;
    }

    public ArrayList<String> convertJsonToArrayOfString(String string) {
        ArrayList<String> output = new ArrayList<>();
        int first = 0;
        int end = 0;
        while (true) {
            first = string.indexOf("{");
            end = string.indexOf("}");
            if (first == -1 || end == -1)
                break;
            output.add(string.substring(first, end + 1));
            string = string.substring(0, first) + string.substring(end + 1);
        }
        return output;
    }

    public ArrayList<Person> convertArrayListOfJsonToArrayListPersons(ArrayList<String> inputs) {
        ArrayList<Person> persons = new ArrayList<>();
        for (String input : inputs) {
            Person person;
            if (input.contains("discountCodesIds")) {
                person = new Gson().fromJson(input, Customer.class);
            } else if (input.contains("activeGoodsIds")) {
                person = new Gson().fromJson(input, Seller.class);
            } else {
                person = new Gson().fromJson(input, Manager.class);
            }
            persons.add(person);
        }
        return persons;
    }

    public ArrayList<String> convertJsonToArrayOfStringForRequest(String string) {
        ArrayList<String> output = new ArrayList<>();
        int first = 0;
        int end = 0;
        while (true) {
            first = string.indexOf("{");
            end = string.indexOf("}");
            if (first == -1 || end == -1)
                break;
            char[] array = string.toCharArray();
            int i = first + 1;
            int numOpen = 1;
            int numClose = 0;
            while (true) {
                if (array[i] == '{') numOpen++;
                if (array[i] == '}') numClose++;
                if (numClose == numOpen)
                    break;
                i++;
            }
            end = i;
            output.add(string.substring(first, end + 1));
            string = string.substring(0, first) + string.substring(end + 1);
        }
        return output;
    }

    public ArrayList<Request> convertArrayListOfJsonToArrayListRequests(ArrayList<String> inputs) {
        ArrayList<Request> requests = new ArrayList<>();
        for (String input : inputs) {
            Request request = null;
            if (input.contains("comment")) {
                request = new Gson().fromJson(input, AddingCommentRequest.class);
            } else if (input.contains("brandOfGood")) {
                request = new Gson().fromJson(input, AddingGoodRequest.class);
            } else if (input.contains("offGoods")) {
                request = new Gson().fromJson(input, AddingOffRequest.class);
            } else if (input.contains("goodId") && input.contains("editedFields")) {
                request = new Gson().fromJson(input, EditingGoodRequest.class);
            } else if (input.contains("offId") && input.contains("editedFields")) {
                request = new Gson().fromJson(input, EditingOffRequest.class);
            } else if (input.contains("companyName")) {
                request = new Gson().fromJson(input, RegisteringSellerRequest.class);
            }
            requests.add(request);
        }
        return requests;
    }

    public void sendImageToServer(File file, String path) {
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println("Successfully connected to server!");
            Gson gson = new Gson();
            ArrayList<String> input2 = new ArrayList<>();
            input2.add(path);
            RequestForServer requestForServer = new RequestForServer("Others", "photo", null, input2);
            dataOutputStream.writeUTF(gson.toJson(requestForServer, RequestForServer.class));
            dataOutputStream.flush();
            dataInputStream.readUTF();
            secondRequestToFileTransferServer(socket, file);
//            System.out.println(file.getAbsolutePath());
//            System.out.println(Files.readAllBytes(Paths.get(file.getAbsolutePath())).length);
//            dataOutputStream.write(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
//            dataOutputStream.flush();
//            System.out.println("hi");
//            System.out.println(dataInputStream.readUTF());
//            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return null;
    }

    public ArrayList<String> getInputsForServer() {
        ArrayList<String> input = new ArrayList<>();
        input.add(getId() + "");
        return input;
    }
}
