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
        String filePath = "Resources\\Users\\Managers\\" + manager.getUsername() + ".json";
    }

    public void saveCustomer(Customer customer) {
        String filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
    }

    public void saveSeller(Seller seller) {
        String filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
    }

    public void saveProduct(Good good) {
        String filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
    }

    public void saveDiscount(DiscountCode discountCode) {
        String filePath = "Resources\\Discounts\\dis_" + discountCode.getCode() + ".json";
    }

    public void saveInfoAboutGood(SellerRelatedInfoAboutGood infoAboutGood, long goodId) {
        String filePath = "Resources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
    }

    public void saveComment(Comment comment) {
        String filePath = "Resources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
    }

    public void saveOff(Off off) {
        String filePath = "Resources\\Offs\\off_" + off.getOffId() + ".json";
    }

    public void saveRate(Rate rate) {
        String filePath = "Resources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
    }

    public void saveCategory(Category category) {
        String filePath = "Resources\\Categories\\" + category.getName() + ".json";
    }

    public void saveSubCategory(SubCategory subCategory) {
        String filePath = "Resources\\SubCategories\\" + subCategory.getParentCategory().getName() + "_" + subCategory.getName() + ".json";
    }

    public void saveOrderForSeller(OrderForSeller orderForSeller) {
        String filePath = "Resources\\Orders\\OrderForSellers\\order_" + orderForSeller.getOrderId() + ".json";
    }

    public void saveOrderForCustomer(OrderForCustomer orderForCustomer) {
        String filePath = "Resources\\Orders\\OrderForCustomers\\order_" + orderForCustomer.getOrderId() + ".json";
    }

    public void saveRequest(Request request) {
        String filePath = "Resources\\Requests\\request_" + request.getRequestId() + ".json";
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
