package ApProject_OnlineShop.database;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.Request;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class DeletingData {
    private static boolean testMode = false;

    public static void setTestMode(boolean testMode) {
        DeletingData.testMode = testMode;
    }

    public void deleteManager(Manager manager) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Managers\\" + manager.getUsername() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteCustomer(Customer customer) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
        for (OrderForCustomer previousOrder : customer.getPreviousOrders()) {
            Database.getInstance().deleteItem(previousOrder);
            Shop.getInstance().getHasMapOfOrders().remove(previousOrder.getOrderId());
        }
        for (DiscountCode discountCode : customer.getDiscountCodes()) {
            discountCode.getOriginalIncludedCustomers().remove(customer.getUsername());
            Database.getInstance().saveItem(discountCode);
        }
    }

    public void deleteSeller(Seller seller) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
        for (Good good : seller.getActiveGoods()) {
            for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
                if (infoAboutGood.getSeller().equals(seller)) {
                    good.removeSeller(seller);
                    deleteProductInfo(infoAboutGood, good.getGoodId());
                    break;
                }
            }
            Database.getInstance().saveItem(good);
        }
        for (Off off : seller.getActiveOffs()) {
            Shop.getInstance().removeOff(off);
            Database.getInstance().deleteItem(off);
        }
        for (OrderForSeller previousSell : seller.getPreviousSells()) {
            Database.getInstance().deleteItem(previousSell);
            Shop.getInstance().getHasMapOfOrders().remove(previousSell.getOrderId());
        }
    }

    public void deleteCompany(Company company) throws FileCantBeDeletedException {
        String filePath = "Resources\\Companies\\company_" + company.getName() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteProduct(Good good) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
        if (!testMode)
            deleteFile("Resources\\productImages\\" + good.getGoodId() + ".jpg");
        for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
            deleteProductInfo(infoAboutGood, good.getGoodId());
            infoAboutGood.getSeller().removeFromActiveGoods(good.getGoodId());
            Database.getInstance().saveItem(infoAboutGood.getSeller());
        }
        for (Comment comment : good.getComments()) {
            comment.getId();
            deleteComment(comment);
        }
        Shop.getInstance().removeProduct(good);
    }

    public void deleteDiscount(DiscountCode discountCode) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Discounts\\dis_" + discountCode.getCode() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
            customer.removeDiscountCode(discountCode);
            Database.getInstance().saveItem(customer);
        }
        deleteFile(filePath);
    }

    public void deleteProductInfo(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws FileCantBeDeletedException {
        String filePath = "Resources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteComment(Comment comment) throws FileCantBeDeletedException {
        String filePath = "Resources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteGoodsInCarts(GoodInCart goodInCart) throws FileCantBeDeletedException {
        String filePath = "Resources\\GoodsInCarts\\goodCart_" + goodInCart.getGoodInCartId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteOff(Off off) throws FileCantBeDeletedException {
        String filePath = "Resources\\Offs\\off_" + off.getOffId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteRate(Rate rate) throws FileCantBeDeletedException {
        String filePath = "Resources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    public void deleteCategory(Category category) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Categories\\" + category.getName() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);

        for (SubCategory subCategory : category.getSubCategories()) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeSubCategory(category.getName(), subCategory.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        deleteFile(filePath);
    }

    public void deleteSubCategory(SubCategory subCategory) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\SubCategories\\" + subCategory.getParentCategory().getName() + "_" + subCategory.getName() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        for (Good good : subCategory.getGoods()) {
            deleteProduct(good);
        }
        deleteFile(filePath);
    }

    public void deleteOrderForSeller(OrderForSeller orderForSeller) throws FileCantBeDeletedException {
        String filePath = "Resources\\Orders\\OrderForSellers\\order_" + orderForSeller.getOrderId() + ".json";
        deleteFile(filePath);
    }

    public void deleteOrderForCustomer(OrderForCustomer orderForCustomer) throws FileCantBeDeletedException {
        String filePath = "Resources\\Orders\\OrderForCustomers\\order_" + orderForCustomer.getOrderId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        for (GoodInCart goodsDetail : orderForCustomer.getGoodsDetails()) {
            deleteGoodsInCarts(goodsDetail);
        }
        deleteFile(filePath);
    }

    public void deleteRequest(Request request) throws FileCantBeDeletedException {
        String filePath = "Resources\\Requests\\request_" + request.getClass().getSimpleName() + "_" + request.getRequestId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        deleteFile(filePath);
    }

    private void deleteFile(String filePath) throws FileCantBeDeletedException {
        File file = new File(filePath);
        if (!file.delete())
            throw new FileCantBeDeletedException();
    }
}
