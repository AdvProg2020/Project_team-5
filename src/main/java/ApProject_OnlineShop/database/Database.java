package ApProject_OnlineShop.database;

import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.Request;

import java.io.IOException;

public class Database {
    private DeletingData deletingData;
    private SavingData savingData;
    private LoadingData loadingData;
    private static Database database;

    private Database() {
        this.deletingData = new DeletingData();
        this.savingData = new SavingData();
        this.loadingData = new LoadingData();
    }
    public static Database getInstance() {
        if (database == null)
            database = new Database();
        return database;
    }

    public void initializeShop() throws IOException, FileCantBeDeletedException, FileCantBeSavedException { //TODO : Kheili naghese bayad fekr konm
        Shop.getInstance().donatePeriodRandomDiscountCodes();
        Shop.getInstance().expireItemsThatTheirTimeIsFinished();
        loadingData.loadManager();
        loadingData.loadCustomer();
        loadingData.loadSeller();
        loadingData.loadCompany();
        loadingData.loadCategory();
        loadingData.loadSubCategory();
        loadingData.loadProduct();
        loadingData.loadInfoAboutGood();
        loadingData.loadComment();
        loadingData.loadRate();
        loadingData.loadDiscount();
        loadingData.loadOff();
        loadingData.loadGoodsInCarts();
        loadingData.loadOrderForSeller();
        loadingData.loadOrderForCustomer();
        loadingData.loadRequests();
    }

    public void deleteItem(Object item) throws FileCantBeDeletedException, IOException, FileCantBeSavedException {
        if (item instanceof Manager) {
            deletingData.deleteManager((Manager) item);
        } else if (item instanceof Customer) {
            deletingData.deleteCustomer((Customer) item);
        } else if (item instanceof Seller) {
            deletingData.deleteSeller((Seller) item);
        } else if (item instanceof Company) {
            deletingData.deleteCompany((Company)item);
        } else if (item instanceof Good) {
            deletingData.deleteProduct((Good) item);
        } else if (item instanceof DiscountCode) {
            deletingData.deleteDiscount((DiscountCode) item);
        } else if (item instanceof GoodInCart) {
            deletingData.deleteGoodsInCarts((GoodInCart) item);
        } else if (item instanceof Comment) {
            deletingData.deleteComment((Comment) item);
        } else if (item instanceof Off) {
            deletingData.deleteOff((Off) item);
        } else if (item instanceof Rate) {
            deletingData.deleteRate((Rate) item);
        } else if (item instanceof Category) {
            deletingData.deleteCategory((Category) item);
        } else if (item instanceof SubCategory) {
            deletingData.deleteSubCategory((SubCategory) item);
        } else if (item instanceof OrderForSeller) {
            deletingData.deleteOrderForSeller((OrderForSeller) item);
        } else if (item instanceof OrderForCustomer) {
            deletingData.deleteOrderForCustomer((OrderForCustomer) item);
        } else if (item instanceof Request) {
            deletingData.deleteRequest((Request) item);
        } else throw new FileCantBeDeletedException();
    }

    public void deleteItem(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws FileCantBeDeletedException {
        deletingData.deleteProductInfo(infoAboutGood, goodId);
    }

    public void saveItem(Object item) throws IOException, FileCantBeSavedException {
        if (item instanceof Manager) {
            savingData.saveManager((Manager) item);
        } else if (item instanceof Customer) {
            savingData.saveCustomer((Customer) item);
        } else if (item instanceof Seller) {
            savingData.saveSeller((Seller) item);
        } else if (item instanceof Company) {
            savingData.saveCompany((Company) item);
        } else if (item instanceof Good) {
            savingData.saveProduct((Good) item);
        } else if (item instanceof DiscountCode) {
            savingData.saveDiscount((DiscountCode) item);
        } else if (item instanceof GoodInCart) {
            savingData.saveGoodsInCarts((GoodInCart) item);
        } else if (item instanceof Comment) {
            savingData.saveComment((Comment) item);
        } else if (item instanceof Off) {
            savingData.saveOff((Off) item);
        } else if (item instanceof Rate) {
            savingData.saveRate((Rate) item);
        } else if (item instanceof Category) {
            savingData.saveCategory((Category) item);
        } else if (item instanceof SubCategory) {
            savingData.saveSubCategory((SubCategory) item);
        } else if (item instanceof OrderForSeller) {
            savingData.saveOrderForSeller((OrderForSeller) item);
        } else if (item instanceof OrderForCustomer) {
            savingData.saveOrderForCustomer((OrderForCustomer) item);
        } else if (item instanceof Request) {
            savingData.saveRequest((Request) item);
        } else throw new FileCantBeSavedException();
    }

    public void saveItem(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws IOException, FileCantBeSavedException {
        savingData.saveInfoAboutGood(infoAboutGood, goodId);
    }

}