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
    public void deleteManager(Manager manager) throws FileCantBeDeletedException {
        String filePath = "Resources\\Users\\Managers\\" + manager.getUsername() + ".json";
        deleteFile(filePath);
    }

    public void deleteCustomer(Customer customer) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
        deleteFile(filePath);
        for (OrderForCustomer previousOrder : customer.getPreviousOrders()) {
            Database.getInstance().deleteItem(previousOrder);
            Shop.getInstance().getHasMapOfOrders().remove(previousOrder.getOrderId());
        }
        for (DiscountCode discountCode : customer.getDiscountCodes()) {
            discountCode.getIncludedCustomers().remove(customer);
            Database.getInstance().saveItem(discountCode);
        }
    }

    public void deleteSeller(Seller seller) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
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
        deleteFile(filePath);
    }

    public void deleteProduct(Good good) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
        deleteFile(filePath);

        Database.getInstance().saveItem(good.getSubCategory());
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
        for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
            customer.removeDiscountCode(discountCode);
            Database.getInstance().saveItem(customer);
        }
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

    public void deleteGoodsInCarts(GoodInCart goodInCart) throws FileCantBeDeletedException {
        String filePath = "Resources\\GoodsInCarts\\goodCart_" + goodInCart.getGoodInCartId() + ".json";
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

    public void deleteCategory(Category category) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        String filePath = "Resources\\Categories\\" + category.getName() + ".json";
        //List<String> subCategoryNames = category.getSubCategories().stream().map(SubCategory::getName).collect(Collectors.toList());
        /*
        category.getSubCategories().forEach((s) -> {
            try {
                deleteSubCategory(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

         */

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
        for (GoodInCart goodsDetail : orderForCustomer.getGoodsDetails()) {
            deleteGoodsInCarts(goodsDetail);
        }
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
