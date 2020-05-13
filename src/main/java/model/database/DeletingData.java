package model.database;

import exception.FileCantBeDeletedException;
import model.category.Category;
import model.category.SubCategory;
import model.orders.OrderForCustomer;
import model.orders.OrderForSeller;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import model.productThings.*;

import java.io.File;

public class DeletingData {
    public void deleteManager(Manager manager) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Managers\\" + manager.getUsername() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteCustomer(Customer customer) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteSeller(Seller seller) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteProduct(Good good) throws FileCantBeDeletedException {
        String filePath = "Resources\\Products\\product" + good.getGoodId() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
        for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
            filePath = "Resources\\ProductsInfo\\productInfo" + good.getGoodId() + "_" + infoAboutGood.getSeller().getUsername() + ".json";
            file = new File(filePath);
            if (!file.delete())
                throw new FileCantBeDeletedException();
        }
        for (Comment comment : good.getComments()) {
            deleteComment(comment);
        }
        //remove rates
    }

    public void deleteDiscount(DiscountCode discountCode) {

    }

    public void deleteComment(Comment comment) {

    }

    public void deleteOff(Off off) {

    }

    public void deleteRate(Rate rate) {

    }

    public void deleteCategory(Category category) {

    }

    public void deleteSubCategory(SubCategory subCategory) {

    }

    public void deleteOrderForSeller(OrderForSeller orderForSeller) {

    }

    public void deleteOrderForCustomer(OrderForCustomer orderForCustomer) {

    }
}
