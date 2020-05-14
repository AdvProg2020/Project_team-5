package model.database;

import exception.FileCantBeSavedException;
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
import java.io.FileWriter;
import java.io.IOException;

public class SavingData {
    public void saveManager(Manager manager) {

    }

    public void saveCustomer(Customer customer) {

    }

    public void saveSeller(Seller seller) {

    }

    public void saveProduct(Good good) {

    }

    public void saveDiscount(DiscountCode discountCode) {

    }

    public void saveComment(Comment comment) {

    }

    public void saveOff(Off off) {

    }

    public void saveRate(Rate rate) {

    }

    public void saveCategory(Category category) {

    }

    public void saveSubCategory(SubCategory subCategory) {

    }

    public void saveOrderForSeller(OrderForSeller orderForSeller) {

    }

    public void saveOrderForCustomer(OrderForCustomer orderForCustomer) {

    }

    public void saveRequest(Request request) {

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
