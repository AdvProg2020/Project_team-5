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
import model.requests.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoadingData {
    private YaGson yaGson;

    public LoadingData() {
        yaGson = new YaGson();
        File file = new File("Resources");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\Users");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\Orders");
        if (!file.exists())
            file.mkdir();
    }

    public void loadManager() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Managers");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Manager.class));
            }
    }

    public void loadCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Customers");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Customer.class));
            }
    }

    public void loadSeller() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Sellers");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Seller.class));
            }
    }

    //TODO
    /*
    public void loadInfoAboutGood(Good good) throws IOException {
        File[] files = loadFolder("Resources\\ProductsInfo");
        if (files != null)
            for (File file : files) {
                SellerRelatedInfoAboutGood infoAboutGood = yaGson.fromJson(readFile(file), SellerRelatedInfoAboutGood.class);
                good.addSeller(infoAboutGood);
            }
    }
    */


    public void loadProduct() throws IOException {
        File[] files = loadFolder("Resources\\Products");
        if (files != null)
            for (File file : files) {
                Good good = yaGson.fromJson(readFile(file), Good.class);
                Shop.getInstance().addProduct(good);
            }
        //Good.setGoodsCount(getMaximumOfNumbers(Shop.getInstance().getAllGoods().stream().map(Good::getGoodId).collect(Collectors.toList())) + 1);
    }

    public void loadDiscount() throws IOException {
        File[] files = loadFolder("Resources\\Discounts");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addDiscountCode(yaGson.fromJson(readFile(file), DiscountCode.class));
            }
    }

    public void loadComment() throws IOException {
        File[] files = loadFolder("Resources\\Comments");
        if (files != null)
            for (File file : files) {
                Comment comment = yaGson.fromJson(readFile(file), Comment.class);
                comment.getGood().addComment(comment);
            }
    }

    public void loadOff() throws IOException {
        File[] files = loadFolder("Resources\\Offs");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addOff(yaGson.fromJson(readFile(file), Off.class));
            }
        //Off.setOffsCount(getMaximumOfNumbers(Shop.getInstance().getOffs().stream().map(Off::getOffId).collect(Collectors.toList())) + 1);
    }

    public void loadRate() throws IOException {
        File[] files = loadFolder("Resources\\Rates");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addRate(yaGson.fromJson(readFile(file), Rate.class));
            }
    }

    public void loadCategory() throws IOException {
        File[] files = loadFolder("Resources\\Categories");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addCategory(yaGson.fromJson(readFile(file), Category.class));
            }
    }

    public void loadSubCategory() throws IOException {
        File[] files = loadFolder("Resources\\SubCategories");
        if (files != null)
            for (File file : files) {
                SubCategory subCategory = yaGson.fromJson(readFile(file), SubCategory.class);
                subCategory.getParentCategory().addSubCategory(subCategory);
            }
    }

    public void loadOrderForSeller() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderForSellers");
        if (files != null)
            for (File file : files) {
                OrderForSeller orderForSeller = yaGson.fromJson(readFile(file), OrderForSeller.class);
                orderForSeller.getSeller().addOrder(orderForSeller);
            }
    }

    public void loadOrderForCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderForCustomers");
        if (files != null)
            for (File file : files) {
                OrderForCustomer orderForCustomer = yaGson.fromJson(readFile(file), OrderForCustomer.class);
                //TODO
                //where we should add order for customer???
            }
        //ToDo
        //load orders count
    }

    public void loadRequests() throws IOException {
        File[] files = loadFolder("Resources\\Requests");
        if (files != null) {
            for (File file : files) {
                loadRequestByType(file);
            }
            //Request.setRequestCount(getMaximumOfNumbers(Shop.getInstance()
              //      .getAllRequest().stream().map(Request::getRequestId).collect(Collectors.toList())) + 1);
        }
    }

    private void loadRequestByType(File file) throws IOException {
        String name = file.getName();
        if (name.startsWith("request_AddingCommentRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), AddingCommentRequest.class));
        } else if (name.startsWith("request_AddingGoodRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), AddingGoodRequest.class));
        } else if (name.startsWith("request_AddingOffRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), AddingOffRequest.class));
        } else if (name.startsWith("request_EditingGoodRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), EditingGoodRequest.class));
        } else if (name.startsWith("request_EditingOffRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), EditingOffRequest.class));
        } else if (name.startsWith("request_RegisteringSellerRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), RegisteringSellerRequest.class));
        } else throw new IOException();
    }

    private File[] loadFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists())
            file.mkdir();
        return file.listFiles();
    }

    private String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    private long getMaximumOfNumbers(List<Long> numbers) {
        return  Collections.max(numbers);
    }

}
