package model.database;

import com.gilecode.yagson.YaGson;
import model.Shop;
import model.persons.Manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class LoadingData {
    private FileReader fileReader;
    private YaGson yaGson;

    public LoadingData() {
        yaGson = new YaGson();
    }

    public void loadManager() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Managers");
        for (File file : files) {
            Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Manager.class));
        }
    }

    public void loadCustomer() {

    }

    public void loadSeller() {

    }

    public void loadProduct() {

    }

    public void loadDiscount() {

    }

    public void loadComment() {

    }

    public void loadOff() {

    }

    public void loadRate() {

    }

    public void loadCategory() {

    }

    public void loadSubCategory() {

    }

    public void loadOrderForSeller() {

    }

    public void loadOrderForCustomer() {

    }

    public void loadRequests() {

    }

    private File[] loadFolder(String folderPath) throws IOException {
        File file = new File(folderPath);
        if (!file.exists())
            file.createNewFile();
        return file.listFiles();
    }

    private String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
