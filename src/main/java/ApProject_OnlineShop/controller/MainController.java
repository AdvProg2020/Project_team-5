package ApProject_OnlineShop.controller;

import ApProject_OnlineShop.controller.accountArea.*;
import ApProject_OnlineShop.controller.products.AllProductsController;
import ApProject_OnlineShop.controller.products.AuctionsController;
import ApProject_OnlineShop.controller.products.OffsController;
import ApProject_OnlineShop.controller.products.ProductController;
import ApProject_OnlineShop.controller.sorting.SortController;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForFiltering;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForSorting;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Person;

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
    private SortController sortController;
    private BankAccountsController bankAccountsController;
    private BankTransactionsController bankTransactionsController;
    private AccountAreaController accountAreaController;
    private AccountAreaForSupporterController accountAreaForSupporter;
    private AuctionsController auctionsController;

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
        this.sortController = new SortController();
        this.bankAccountsController = new BankAccountsController(173339);
        this.bankTransactionsController = new BankTransactionsController(17339);
        this.accountAreaController = new AccountAreaController();
        this.accountAreaForSupporter = new AccountAreaForSupporterController();
        this.auctionsController = new AuctionsController();
    }

    public AccountAreaController getAccountAreaController() {
        return accountAreaController;
    }

    public AuctionsController getAuctionsController() {
        return auctionsController;
    }

    public AccountAreaForSupporterController getAccountAreaForSupporter() {
        return accountAreaForSupporter;
    }

    public BankAccountsController getBankAccountsController() {
        return bankAccountsController;
    }

    public BankTransactionsController getBankTransactionsController() {
        return bankTransactionsController;
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

    public SortController getSortController() {
        return sortController;
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
