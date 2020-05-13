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
        String filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
        for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
            deleteProductInfo(infoAboutGood, good.getGoodId());
        }
        for (Comment comment : good.getComments()) {
            deleteComment(comment);
        }
        //remove rates
    }

    public void deleteDiscount(DiscountCode discountCode) throws FileCantBeDeletedException {
        String filePath = "Resources\\Discounts\\dis_" + discountCode.getCode() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteProductInfo(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws FileCantBeDeletedException {
        String filePath = "Resources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteComment(Comment comment) throws FileCantBeDeletedException {
        String filePath = "Resources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteOff(Off off) throws FileCantBeDeletedException {
        String filePath = "Resources\\Offs\\off_" + off.getOffId() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }

    public void deleteRate(Rate rate) throws FileCantBeDeletedException {
        String filePath = "Resources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
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
