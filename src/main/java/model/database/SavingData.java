package model.database;

import com.gilecode.yagson.YaGson;
import exception.FileCantBeSavedException;
import model.category.Category;
import model.category.SubCategory;
import model.orders.OrderForCustomer;
import model.orders.OrderForSeller;
import model.persons.Company;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import model.productThings.*;
import model.requests.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SavingData {
    private YaGson yaGson;

    public SavingData() {
        yaGson = new YaGson();
    }

    public void saveManager(Manager manager) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Managers\\"+ manager.getUsername() + ".json";
        saveFile(yaGson.toJson(manager, Manager.class), filePath);
    }

    public void saveCustomer(Customer customer) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
        saveFile(yaGson.toJson(customer, Customer.class), filePath);
    }

    public void saveSeller(Seller seller) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
        saveFile(yaGson.toJson(seller, Seller.class), filePath);
    }

    public void saveCompany(Company company) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Companies\\company_" + company.getName() + ".json";
        saveFile(yaGson.toJson(company, Company.class), filePath);
    }

    public void saveProduct(Good good) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
        saveFile(yaGson.toJson(good, Good.class), filePath);
    }

    public void saveDiscount(DiscountCode discountCode) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Discounts\\dis_" + discountCode.getCode() + ".json";
        saveFile(yaGson.toJson(discountCode, DiscountCode.class), filePath);
    }

    public void saveInfoAboutGood(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
        saveFile(yaGson.toJson(infoAboutGood, SellerRelatedInfoAboutGood.class), filePath);
    }

    public void saveComment(Comment comment) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
        saveFile(yaGson.toJson(comment, Comment.class), filePath);
    }

    public void saveOff(Off off) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Offs\\off_" + off.getOffId() + ".json";
        saveFile(yaGson.toJson(off, Off.class), filePath);
    }

    public void saveRate(Rate rate) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
        saveFile(yaGson.toJson(rate, Rate.class), filePath);
    }

    public void saveCategory(Category category) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Categories\\" + category.getName() + ".json";
        saveFile(yaGson.toJson(category, Category.class), filePath);
    }

    public void saveSubCategory(SubCategory subCategory) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\SubCategories\\" + subCategory.getParentCategory().getName() + "_" + subCategory.getName() + ".json";
        saveFile(yaGson.toJson(subCategory, SubCategory.class), filePath);
    }

    public void saveOrderForSeller(OrderForSeller orderForSeller) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Orders\\OrderForSellers\\order_" + orderForSeller.getOrderId() + ".json";
        saveFile(yaGson.toJson(orderForSeller, OrderForSeller.class), filePath);
    }

    public void saveOrderForCustomer(OrderForCustomer orderForCustomer) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Orders\\OrderForCustomers\\order_" + orderForCustomer.getOrderId() + ".json";
        saveFile(yaGson.toJson(orderForCustomer, OrderForCustomer.class), filePath);
    }

    public void saveRequest(Request request) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Requests\\request_" + request.getClass().getSimpleName() + "_" + request.getRequestId() + ".json";
        if (request instanceof AddingCommentRequest)
            saveFile(yaGson.toJson(request, AddingCommentRequest.class), filePath);
        else if (request instanceof AddingGoodRequest)
            saveFile(yaGson.toJson(request, AddingGoodRequest.class), filePath);
        else if (request instanceof AddingOffRequest)
            saveFile(yaGson.toJson(request, AddingOffRequest.class), filePath);
        else if (request instanceof EditingGoodRequest)
            saveFile(yaGson.toJson(request, EditingGoodRequest.class), filePath);
        else if (request instanceof EditingOffRequest)
            saveFile(yaGson.toJson(request, EditingOffRequest.class), filePath);
        else if (request instanceof RegisteringSellerRequest)
            saveFile(yaGson.toJson(request, RegisteringSellerRequest.class), filePath);
    }

    private void saveFile(String serializedData, String filePath) throws IOException, FileCantBeSavedException {
        File file = new File(filePath);
        if (!file.exists()) {
            if(!file.createNewFile())
                throw new FileCantBeSavedException();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(serializedData);
        fileWriter.close();
    }
}
