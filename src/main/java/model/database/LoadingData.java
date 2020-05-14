package model.database;

import com.gilecode.yagson.YaGson;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.orders.OrderForCustomer;
import model.orders.OrderForSeller;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import model.productThings.*;
import model.requests.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoadingData {
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

    public void loadCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Customers");
        for (File file : files) {
            Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Customer.class));
        }
    }

    public void loadSeller() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Sellers");
        for (File file : files) {
            Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Seller.class));
        }
    }

    //TODO
    /*
    public void loadInfoAboutGood(Good good) throws IOException {
        File[] files = loadFolder("Resources\\ProductsInfo");
        for (File file : files) {
            SellerRelatedInfoAboutGood infoAboutGood = yaGson.fromJson(readFile(file), SellerRelatedInfoAboutGood.class);
            good.addSeller(infoAboutGood);
        }
    }
    */


    public void loadProduct() throws IOException {
        File[] files = loadFolder("Resources\\Products");
        for (File file : files) {
            Good good = yaGson.fromJson(readFile(file), Good.class);
            Shop.getInstance().addProduct(good);
        }
    }

    public void loadDiscount() throws IOException {
        File[] files = loadFolder("Resources\\Discounts");
        for (File file : files) {
            Shop.getInstance().addDiscountCode(yaGson.fromJson(readFile(file), DiscountCode.class));
        }
    }

    public void loadComment() throws IOException {
        File[] files = loadFolder("Resources\\Comments");
        for (File file : files) {
            Comment comment = yaGson.fromJson(readFile(file), Comment.class);
            comment.getGood().addComment(comment);
        }
    }

    public void loadOff() throws IOException {
        File[] files = loadFolder("Resources\\Offs");
        for (File file : files) {
            Shop.getInstance().addOff(yaGson.fromJson(readFile(file), Off.class));
        }
    }

    public void loadRate() throws IOException {
        File[] files = loadFolder("Resources\\Rates");
        for (File file : files) {
            Shop.getInstance().addRate(yaGson.fromJson(readFile(file), Rate.class));
        }
    }

    public void loadCategory() throws IOException {
        File[] files = loadFolder("Resources\\Categories");
        for (File file : files) {
            Shop.getInstance().addCategory(yaGson.fromJson(readFile(file), Category.class));
        }
    }

    public void loadSubCategory() throws IOException {
        File[] files = loadFolder("Resources\\SubCategories");
        for (File file : files) {
            SubCategory subCategory = yaGson.fromJson(readFile(file), SubCategory.class);
            subCategory.getParentCategory().addSubCategory(subCategory);
        }
    }

    public void loadOrderForSeller() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderForSellers");
        for (File file : files) {
            OrderForSeller orderForSeller = yaGson.fromJson(readFile(file), OrderForSeller.class);
            orderForSeller.getSeller().addOrder(orderForSeller);
        }
    }

    public void loadOrderForCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderForCustomers");
        for (File file : files) {
            OrderForCustomer orderForCustomer = yaGson.fromJson(readFile(file), OrderForCustomer.class);
            //TODO
            //where we should add order for customer???
        }
    }

    public void loadRequests() throws IOException {
        File[] files = loadFolder("Resources\\Requests");
        for (File file : files) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), Request.class));
        }
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
