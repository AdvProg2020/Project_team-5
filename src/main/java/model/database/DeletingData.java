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
import model.requests.Request;

import java.io.File;

public class DeletingData {
    public void deleteManager(Manager manager) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Managers\\" + manager.getUsername() + ".json";
        deleteFile(filePath);
    }

    public void deleteCustomer(Customer customer) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
        deleteFile(filePath);
        //remove rates and comments of this customer???
    }

    public void deleteSeller(Seller seller) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
        deleteFile(filePath);
        for (Good good : seller.getActiveGoods()) {
            for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
                if (infoAboutGood.getSeller().equals(seller)) {
                    deleteProductInfo(infoAboutGood, good.getGoodId());
                    break;
                }
            }
        }
    }

    public void deleteProduct(Good good) throws FileCantBeDeletedException {
        String filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
        deleteFile(filePath);
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
        deleteFile(filePath);
    }

    public void deleteProductInfo(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws FileCantBeDeletedException {
        String filePath = "Resources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
        deleteFile(filePath);
    }

    public void deleteComment(Comment comment) throws FileCantBeDeletedException {
        String filePath = "Resources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
        deleteFile(filePath);
    }

    public void deleteOff(Off off) throws FileCantBeDeletedException {
        String filePath = "Resources\\Offs\\off_" + off.getOffId() + ".json";
        deleteFile(filePath);
    }

    public void deleteRate(Rate rate) throws FileCantBeDeletedException {
        String filePath = "Resources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
        deleteFile(filePath);
    }

    public void deleteCategory(Category category) throws FileCantBeDeletedException {
        String filePath = "Resources\\Categories\\" + category.getName() + ".json";
        deleteFile(filePath);
        for (SubCategory subCategory : category.getSubCategories()) {
            deleteSubCategory(subCategory);
        }
    }

    public void deleteSubCategory(SubCategory subCategory) throws FileCantBeDeletedException {
        String filePath = "Resources\\SubCategories\\" + subCategory.getParentCategory().getName() + "_" + subCategory.getName() + ".json";
        deleteFile(filePath);
        for (Good good : subCategory.getGoods()) {
            deleteProduct(good);
        }
    }

    public void deleteOrderForSeller(OrderForSeller orderForSeller) throws FileCantBeDeletedException {
        String filePath = "Resources\\Orders\\OrderForSellers\\order_" + orderForSeller.getOrderId() + ".json";
        deleteFile(filePath);
    }

    public void deleteOrderForCustomer(OrderForCustomer orderForCustomer) throws FileCantBeDeletedException {
        String filePath = "Resources\\Orders\\OrderForCustomers\\order_" + orderForCustomer.getOrderId() + ".json";
        deleteFile(filePath);
    }

    public void deleteRequest(Request request) throws FileCantBeDeletedException {
        String filePath = "Resources\\Requests\\request_" + request.getClass().getSimpleName() + "_" + request.getRequestId() + ".json";
        deleteFile(filePath);
    }

    private void deleteFile(String filePath) throws FileCantBeDeletedException {
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }
}
