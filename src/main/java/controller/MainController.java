package controller;

import controller.accountArea.AccountAreaForCustomerController;
import controller.accountArea.AccountAreaForManagerController;
import controller.accountArea.AccountAreaForSellerController;
import controller.products.AllProductsController;
import controller.products.OffsController;
import controller.products.ProductController;
import controller.sortingAndFiltering.ControllerForFiltering;
import controller.sortingAndFiltering.ControllerForSorting;
import model.Shop;
import model.persons.Person;

import java.util.regex.Pattern;

public class MainController {
    private static MainController ourInstance = new MainController();
    private Shop shop;
    private Person currentPerson;
    private LoginRegisterController loginRegisterController;
    private AccountAreaForCustomerController accountAreaForCustomerController;
    private AccountAreaForManagerController accountAreaForManagerController;
    private AccountAreaForSellerController accountAreaForSellerController;
    private ControllerForFiltering controllerForFiltering;
    private ControllerForSorting controllerForSorting;
    private AllProductsController allProductsController;
    private OffsController offsController;
    private ProductController productController;

    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
        this.shop = Shop.getInstance();
        this.currentPerson = null;
        this.loginRegisterController=new LoginRegisterController();
        this.accountAreaForCustomerController= new AccountAreaForCustomerController();
        this.accountAreaForSellerController=new AccountAreaForSellerController();
        this.accountAreaForManagerController=new AccountAreaForManagerController();
        this.controllerForFiltering=new ControllerForFiltering();
        this.controllerForSorting=new ControllerForSorting();
        this.allProductsController=new AllProductsController();
        this.offsController=new OffsController();
        this.productController = new ProductController();
    }

    public LoginRegisterController getLoginRegisterController() {
        return loginRegisterController;
    }

    public AccountAreaForCustomerController getAccountAreaForCustomerController() {
        return accountAreaForCustomerController;
    }

    public AccountAreaForManagerController getAccountAreaForManagerController() {
        return accountAreaForManagerController;
    }

    public AccountAreaForSellerController getAccountAreaForSellerController() {
        return accountAreaForSellerController;
    }



    public ControllerForFiltering getControllerForFiltering() {
        return controllerForFiltering;
    }

    public ControllerForSorting getControllerForSorting() {
        return controllerForSorting;
    }

    public AllProductsController getAllProductsController() {
        return allProductsController;
    }

    public OffsController getOffsController() {
        return offsController;
    }

    public ProductController getProductController() {
        return productController;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person person) {
        currentPerson = person;
    }

    public static Boolean isPhoneNumberValid(String phoneNumber) {
        return Pattern.compile("^\\d{11}$").matcher(phoneNumber).find();
    }

    public static Boolean isEmailValid(String email) {
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email).find();
    }

}
