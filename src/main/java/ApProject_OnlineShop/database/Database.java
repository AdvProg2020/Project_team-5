package ApProject_OnlineShop.database;

import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.ShopBankAccount;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.OrderFileProductForCustomer;
import ApProject_OnlineShop.model.orders.OrderFileProductForSeller;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.Request;

import java.io.File;
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

    public void initializeShop() throws IOException, FileCantBeDeletedException, FileCantBeSavedException {
        loadingData.loadManager();
        loadingData.loadCustomer();
        loadingData.loadSeller();
        loadingData.loadSupporter();
        loadingData.loadCompany();
        loadingData.loadCategory();
        loadingData.loadSubCategory();
        loadingData.loadRequests();
        loadingData.loadFileProduct();
        loadingData.loadProduct();
        loadingData.loadInfoAboutGood();
        loadingData.loadComment();
        loadingData.loadRate();
        loadingData.loadDiscount();
        loadingData.loadOff();
        loadingData.loadGoodsInCarts();
        loadingData.loadOrderForSeller();
        loadingData.loadOrderFileProductForSeller();
        loadingData.loadOrderFileProductForCustomer();
        loadingData.loadOrderForCustomer();
        loadingData.loadShopBankAccount();
        loadingData.loadAuctions();
        for (Request request : Shop.getInstance().getAllRequest()) {
            request.setName();
        }
        for (Person person : Shop.getInstance().getAllPersons()) {
            person.setRole();
        }
        Shop.getInstance().donatePeriodRandomDiscountCodes();
        Shop.getInstance().expireItemsThatTheirTimeIsFinished();
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
        } else if (item instanceof OrderFileProductForSeller) {
            deletingData.deleteOrderFileProductForSeller((OrderFileProductForSeller) item);
        } else if (item instanceof OrderFileProductForCustomer) {
            deletingData.deleteOrderFileProductForCustomer((OrderFileProductForCustomer) item);
        } else if (item instanceof Request) {
            deletingData.deleteRequest((Request) item);
        } else if (item instanceof Auction) {
            deletingData.deleteAuction((Auction) item);
        } else if (item instanceof FileProduct) {
          deletingData.deleteFileProduct((FileProduct)item);
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
        } else if (item instanceof Supporter) {
            savingData.saveSupporter((Supporter) item);
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
        } else if (item instanceof OrderFileProductForSeller) {
            savingData.saveOrderFileProductForSeller((OrderFileProductForSeller) item);
        } else if (item instanceof OrderFileProductForCustomer) {
            savingData.saveOrderFileProductForCustomer((OrderFileProductForCustomer) item);
        } else if (item instanceof Request) {
            savingData.saveRequest((Request) item);
        } else if(item instanceof ShopBankAccount){
            savingData.saveShopBankAccount((ShopBankAccount)item);
        } else if (item instanceof Auction) {
            savingData.saveAuction((Auction) item);
        } else if (item instanceof FileProduct) {
            savingData.saveFileProduct((FileProduct)item);
        } else throw new FileCantBeSavedException();
    }

    public void saveItem(SellerRelatedInfoAboutGood infoAboutGood, long goodId) throws IOException, FileCantBeSavedException {
        savingData.saveInfoAboutGood(infoAboutGood, goodId);
    }

    public void loadTestFolders() {
        SavingData.setTestMode(true);
        DeletingData.setTestMode(true);
        loadFolder("TestResources");
        loadFolder("TestResources\\Users");
        loadFolder("TestResources\\Orders");
        loadFolder("TestResources\\Requests");
        loadFolder("TestResources\\Orders\\OrderForCustomers");
        loadFolder("TestResources\\Orders\\OrderForSellers");
        loadFolder("TestResources\\SubCategories");
        loadFolder("TestResources\\Categories");
        loadFolder("TestResources\\Rates");
        loadFolder("TestResources\\Offs");
        loadFolder("TestResources\\Comments");
        loadFolder("TestResources\\GoodsInCarts");
        loadFolder("TestResources\\Discounts");
        loadFolder("TestResources\\Products");
        loadFolder("TestResources\\ProductsInfo");
        loadFolder("TestResources\\Companies");
        loadFolder("TestResources\\Users\\Sellers");
        loadFolder("TestResources\\Users\\Customers");
        loadFolder("TestResources\\Users\\Managers");
        loadFolder("TestResources\\Auctions");
    }

    private void loadFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists())
            file.mkdir();
        else if (folderPath.equalsIgnoreCase("TestResources"))
            file.delete();
    }

}
