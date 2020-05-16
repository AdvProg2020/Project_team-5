package model.database;

import exception.FileCantBeDeletedException;
import exception.FileCantBeSavedException;
import model.category.Category;
import model.category.SubCategory;
import model.orders.OrderForCustomer;
import model.orders.OrderForSeller;
import model.persons.Company;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import model.productThings.*;
import model.requests.Request;

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

    public void initializeShop() throws IOException { //TODO : Kheili naghese bayad fekr konm
        loadingData.loadManager();
        loadingData.loadCustomer();
        loadingData.loadSeller();
        loadingData.loadCategory();
        loadingData.loadSubCategory();
        loadingData.loadProduct();
        loadingData.loadComment();
        loadingData.loadRate();
        loadingData.loadDiscount();
        loadingData.loadOff();
        loadingData.loadOrderForSeller();
        loadingData.loadOrderForCustomer();
        loadingData.loadRequests();
    }

    public void deleteItem(Object item) throws FileCantBeDeletedException {
        if (item instanceof Manager) {
            deletingData.deleteManager((Manager) item);
        } else if (item instanceof Customer) {
            deletingData.deleteCustomer((Customer) item);
        } else if (item instanceof Seller) {
            deletingData.deleteSeller((Seller) item);
        } else if (item instanceof Good) {
            deletingData.deleteProduct((Good) item);
        } else if (item instanceof SellerRelatedInfoAboutGood) {
            //deletingData.deleteProductInfo((SellerRelatedInfoAboutGood)item);
        } else if (item instanceof DiscountCode) {
            deletingData.deleteDiscount((DiscountCode) item);
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
        } else if (item instanceof SellerRelatedInfoAboutGood) {
            //savingData.saveInfoAboutGood()
        } else if (item instanceof DiscountCode) {
            savingData.saveDiscount((DiscountCode) item);
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
}
