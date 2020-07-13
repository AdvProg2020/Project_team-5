package ApProject_OnlineShop.database;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.ShopBankAccount;
import ApProject_OnlineShop.model.persons.*;
import com.gilecode.yagson.YaGson;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SavingData {
    private Gson yaGson;
    private static boolean testMode = false;

    public SavingData() {
        yaGson = new Gson();
    }

    public static void setTestMode(boolean testMode) {
        SavingData.testMode = testMode;
    }

    public void saveManager(Manager manager) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Users\\Managers\\" + manager.getUsername() + ".json";
        else
            filePath = "TestResources\\Users\\Managers\\" + manager.getUsername() + ".json";
        saveFile(yaGson.toJson(manager, Manager.class), filePath);
    }

    public void saveCustomer(Customer customer) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Users\\Customers\\" + customer.getUsername() + ".json";
        else
            filePath = "TestResources\\Users\\Customers\\" + customer.getUsername() + ".json";
        saveFile(yaGson.toJson(customer, Customer.class), filePath);
    }

    public void saveSupporter(Supporter supporter) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Users\\Supporters\\" + supporter.getUsername() + ".json";
        saveFile(yaGson.toJson(supporter, Supporter.class), filePath);
    }

    public void saveShopBankAccount(ShopBankAccount account) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\BankAccounts\\" + "Shop" + ".json";
        saveFile(yaGson.toJson(account, ShopBankAccount.class), filePath);
    }

    public void saveSeller(Seller seller) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Users\\Sellers\\" + seller.getUsername() + ".json";
        else
            filePath = "TestResources\\Users\\Sellers\\" + seller.getUsername() + ".json";
        saveFile(yaGson.toJson(seller, Seller.class), filePath);
    }

    public void saveCompany(Company company) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Companies\\company_" + company.getName() + ".json";
        else
            filePath = "TestResources\\Companies\\company_" + company.getName() + ".json";
        saveFile(yaGson.toJson(company, Company.class), filePath);
    }

    public void saveProduct(Good good) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Products\\product_" + good.getGoodId() + ".json";
        else
            filePath = "TestResources\\Products\\product_" + good.getGoodId() + ".json";
        saveFile(yaGson.toJson(good, Good.class), filePath);
    }

    public void saveDiscount(DiscountCode discountCode) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Discounts\\dis_" + discountCode.getCode() + ".json";
        else
            filePath = "TestResources\\Discounts\\dis_" + discountCode.getCode() + ".json";
        saveFile(yaGson.toJson(discountCode, DiscountCode.class), filePath);
    }

    public void saveInfoAboutGood(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
        else
            filePath = "TestResources\\ProductsInfo\\productInfo_" + goodId + "_" + infoAboutGood.getSeller().getUsername() + ".json";
        saveFile(yaGson.toJson(infoAboutGood, SellerRelatedInfoAboutGood.class), filePath);
    }

    public void saveComment(Comment comment) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
        else
            filePath = "TestResources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json";
        saveFile(yaGson.toJson(comment, Comment.class), filePath);
    }

    public void saveGoodsInCarts(GoodInCart goodInCart) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\GoodsInCarts\\goodCart_" + goodInCart.getGoodInCartId() + ".json";
        else
            filePath = "TestResources\\GoodsInCarts\\goodCart_" + goodInCart.getGoodInCartId() + ".json";
        saveFile(yaGson.toJson(goodInCart, GoodInCart.class), filePath);
    }

    public void saveOff(Off off) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Offs\\off_" + off.getOffId() + ".json";
        else
            filePath = "TestResources\\Offs\\off_" + off.getOffId() + ".json";
        saveFile(yaGson.toJson(off, Off.class), filePath);
    }

    public void saveRate(Rate rate) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
        else
            filePath = "TestResources\\Rates\\rate_" + rate.getGood().getGoodId() + "_" + rate.getCustomer().getUsername() + ".json";
        saveFile(yaGson.toJson(rate, Rate.class), filePath);
    }

    public void saveCategory(Category category) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Categories\\" + category.getName() + ".json";
        else
            filePath = "TestResources\\Categories\\" + category.getName() + ".json";
        saveFile(yaGson.toJson(category, Category.class), filePath);
    }

    public void saveSubCategory(SubCategory subCategory) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\SubCategories\\" + subCategory.getParentCategory().getName() + "_" + subCategory.getName() + ".json";
        else
            filePath = "TestResources\\SubCategories\\" + subCategory.getParentCategory().getName() + "_" + subCategory.getName() + ".json";
        saveFile(yaGson.toJson(subCategory, SubCategory.class), filePath);
    }

    public void saveOrderForSeller(OrderForSeller orderForSeller) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Orders\\OrderForSellers\\order_" + orderForSeller.getOrderId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        saveFile(yaGson.toJson(orderForSeller, OrderForSeller.class), filePath);
    }

    public void saveOrderForCustomer(OrderForCustomer orderForCustomer) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Orders\\OrderForCustomers\\order_" + orderForCustomer.getOrderId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
        saveFile(yaGson.toJson(orderForCustomer, OrderForCustomer.class), filePath);
    }

    public void saveRequest(Request request) throws IOException, FileCantBeSavedException {
        String filePath = "Resources\\Requests\\request_" + request.getClass().getSimpleName() + "_" + request.getRequestId() + ".json";
        if (testMode)
            filePath = "Test".concat(filePath);
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

    public void saveAuction(Auction auction) throws IOException, FileCantBeSavedException {
        String filePath;
        if (!testMode)
            filePath = "Resources\\Auctions\\auction_" + auction.getAuctionId() + ".json";
        else
            filePath = "TestResources\\Auctions\\auction_" + auction.getAuctionId() + ".json";
        saveFile(yaGson.toJson(auction, Auction.class), filePath);
    }

    private void saveFile(String serializedData, String filePath) throws IOException, FileCantBeSavedException {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new FileCantBeSavedException();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(serializedData);
        fileWriter.close();
    }
}
