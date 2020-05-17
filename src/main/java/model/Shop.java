package model;

import exception.FileCantBeDeletedException;
import exception.FileCantBeSavedException;
import exception.productExceptions.NotEnoughAvailableProduct;
import model.category.Category;
import model.category.SubCategory;
import model.database.Database;
import model.orders.Order;
import model.orders.OrderForCustomer;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Person;
import model.persons.Seller;
import model.productThings.*;
import model.requests.Request;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Shop {
    private static Shop ourInstance = new Shop();
    private ArrayList<Category> allCategories;
    private HashMap <Long,Off> offs;
    private ArrayList<Person> allPersons;
    private ArrayList<Request> allRequest;
    private HashMap<Long,DiscountCode> allDiscountCodes;
    private ArrayList<Rate> allRates;
    private ArrayList<GoodInCart> cart;
    private HashMap<Long, Good> allGoods;
    private HashMap<Long, Order> allOrders;
    private LocalDate lastRandomPeriodDiscountCodeCreatedDate;

    public static Shop getInstance() {
        return ourInstance;
    }

    private Shop() {
        this.allCategories = new ArrayList<>();
        this.allDiscountCodes = new HashMap<>();
        this.allPersons = new ArrayList<>();
        this.allRates = new ArrayList<>();
        this.allRequest = new ArrayList<>();
        this.offs = new HashMap<>();
        this.cart = new ArrayList<>();
        this.allGoods = new HashMap<>();
        this.allOrders = new HashMap<>();
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<Rate> getAllRates() {
        return allRates;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return new ArrayList<>(allDiscountCodes.values());
    }

    public ArrayList<Request> getAllRequest() {
        return allRequest;
    }

    public ArrayList<Person> getAllPersons() {
        return allPersons;
    }

    public HashMap<Long, Order> getHasMapOfOrders() {
        return allOrders;
    }

    public void addOrder(Order order){
        this.allOrders.put(order.getOrderId(),order);
    }

    public void removePerson(Person user) {
        allPersons.remove(user);
    }

    public void addPerson(Person user) {
        allPersons.add(user);
    }

    public void removeProduct(Good good) {
        good.getSubCategory().deleteGood(good);
    }

    public void addProduct(Good good) {
        good.getSubCategory().addGood(good);
    }

    public Person findUser(String userName) {
        for (Person person : allPersons) {
            if (person.getUsername().equals(userName))
                return person;
        }
        return null;
    }

    public Good findGoodById(long goodId) {
        for (Category category : allCategories) {
            if (category.findGoodInSubCategories(goodId) != null)
                return category.findGoodInSubCategories(goodId);
        }
        return null;
    }

    public DiscountCode findDiscountCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes.values()) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public HashMap<Long,Off> getHashMapOfOffs(){
        return this.offs;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.remove(discountCode);
    }

    public void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.put(discountCode.getId(),discountCode);
    }

    public Request findRequestById(long requestId) {
        for (Request request : allRequest) {
            if (request.getRequestId() == requestId)
                return request;
        }
        return null;
    }

    public HashMap<Long,DiscountCode> getHashMapOfDiscountCodes(){
        return this.allDiscountCodes;
    }

    public void removeRequest(Request request) {
        allRequest.remove(request);
    }

    public void addRequest(Request request) {
        allRequest.add(request);
    }

    public void addCategory(Category category) {
        allCategories.add(category);
    }

    public void removeCategory(Category category) {
        allCategories.remove(category);
    }

    public ArrayList<Rate> getRatesOfAGood(Good good) {
        ArrayList<Rate> ratesOfGood = new ArrayList<>();
        for (Rate rate : allRates) {
            if (rate.getGood().equals(good))
                ratesOfGood.add(rate);
        }
        return ratesOfGood;
    }

    public void addRate(Customer customer, long productId, int rate) throws IOException, FileCantBeSavedException {
        Rate rateToAdd = new Rate(customer, findGoodById(productId), rate);
        allRates.add(rateToAdd);
        //Database.getInstance().saveItem(rateToAdd);
    }

    public void addRate(Rate rate) {
        allRates.add(rate);
    }

    public ArrayList<GoodInCart> getCart() {
        return cart;
    }

    public boolean checkExistProductInCart(long productId) {
        return !cart.stream().filter(goodInCart -> goodInCart.getGood().getGoodId() == productId).findAny().isEmpty();
    }

    public void addGoodToCart(Good good, Seller seller, int number) {
        cart.add(new GoodInCart(good, seller, number));
    }

    public void reduceGoodInCartNumber(long productId) {
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().getGoodId() == productId) {
                goodInCart.setNumber(goodInCart.getNumber() - 1);
                if (goodInCart.getNumber() == 0)
                    cart.remove(goodInCart);
                return;
            }
        }
    }

    public void increaseGoodInCartNumber(long productId) throws NotEnoughAvailableProduct {
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().getGoodId() == productId) {
                if (goodInCart.getNumber() >= goodInCart.getGood().getAvailableNumberBySeller(goodInCart.getSeller()))
                    throw new NotEnoughAvailableProduct();
                goodInCart.setNumber(goodInCart.getNumber() + 1);
                return;
            }
        }
    }

    public Off findOffById(long offId) {
        for (Off off : offs.values()) {
            if (off.getOffId() == offId)
                return off;
        }
        return null;
    }

    public void addOff(Off off) {
        offs.put(off.getOffId(),off);
    }

    public void removeOff(Off off) {
        offs.remove(off);
    }

    public void deleteCategory(Category category) {
        for (int i = 0; i < category.getSubCategories().size(); i++) {
            category.deleteSubCategory(category.getSubCategories().get(0));
        }
        removeCategory(category);
    }

    public void generatePeriodRandomDiscountCodes(LocalDate endDate) {
        String code = DiscountCode.generateRandomDiscountCode();
        DiscountCode discountCode = new DiscountCode(code, LocalDate.now(), endDate, 100000L, 20);
        discountCode.addAllCustomers(randomCustomers(5, 1, discountCode));
        allDiscountCodes.put(discountCode.getId(),discountCode);
        this.lastRandomPeriodDiscountCodeCreatedDate = LocalDate.now();
    }

    private HashMap<Customer, Integer> randomCustomers(int customerNumbers, int repeatingTimes, DiscountCode discountCode) {
        HashMap<Customer, Integer> randomCustomers = new HashMap<>();
        while (randomCustomers.size() < customerNumbers) {
            int randomNumber = ((int) (Math.random() * 1000000)) % allPersons.size();
            Person person = allPersons.get(randomNumber);
            if (person instanceof Customer) {
                randomCustomers.put((Customer) person, repeatingTimes);
            }
        }
        return randomCustomers;
    }

    public long getFinalPriceOfAGood(Good good, Seller seller) {
        if (seller == null)
            seller = good.getSellerRelatedInfoAboutGoods().get(0).getSeller();
        for (Off off : offs.values()) {
            if (off.getPriceAfterOff(good, seller) != 0)
                return off.getPriceAfterOff(good, seller);
        }
        return good.getPriceBySeller(seller);
    }

    public SubCategory findSubCategoryByName(String name) {
        for (Category category : allCategories) {
            for (SubCategory subCategory : category.getSubCategories()) {
                if (subCategory.getName().equalsIgnoreCase(name))
                    return subCategory;
            }
        }
        return null;
    }

    public Category findCategoryByName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }

    public Good getGoodByNameAndBrandAndSubCategory(String name, String brand, SubCategory subCategory) {
        for (Good good : subCategory.getGoods()) {
            if (good.getBrand().equalsIgnoreCase(brand) && good.getName().equalsIgnoreCase(name))
                return good;
        }
        return null;
    }

    public boolean didManagerRegistered() {
        for (Person person : allPersons) {
            if (person instanceof Manager)
                return true;
        }
        return false;
    }

    public boolean checkExistDiscountCode(String code) {
        return allDiscountCodes.values().stream().filter(discountCode -> discountCode.getCode().equals(code)).count() != 0;
    }

    public void clearCart() {
        this.cart.clear();
    }

    public ArrayList<Off> getOffs() {
        return new ArrayList<>(offs.values());
    }

    public List<Good> getAllGoods() {
        return new ArrayList<>(this.allGoods.values());
    }

    public HashMap<Long, Good> getHashMapOfGoods() {
        return this.allGoods;
    }

    public List<Good> getOffGoods() {
        Set<Good> offGoods = new HashSet<>();
        for (Off off : offs.values()) {
            if ((off.getEndDate().isBefore(LocalDate.now()) || off.getStartDate().isAfter(LocalDate.now())))
                continue;
            if (off.getOffStatus().equals(Off.OffStatus.ACCEPTED)) {
                offGoods.addAll(off.getOffGoods());
            }
        }
        return new ArrayList<>(offGoods);
    }

    public void donatePeriodRandomDiscountCodes() {
        LocalDate localDate = LocalDate.now();
        if (localDate.getDayOfMonth() == 1) {
            if (!localDate.equals(lastRandomPeriodDiscountCodeCreatedDate)) {
                generatePeriodRandomDiscountCodes(LocalDate.now().plusMonths(1));
                lastRandomPeriodDiscountCodeCreatedDate = localDate;
            }
        }
    }


    public void expireItemsThatTheirTimeIsFinished() throws IOException, FileCantBeSavedException, FileCantBeDeletedException {
        for (Off off : this.getOffs()) {
            if (off.isOffExpired()) {
                this.removeOff(off);
                off.getSeller().getActiveOffs().remove(off);
                // Database.getInstance().saveItem(off.getSeller());
                Database.getInstance().deleteItem(off);
            }
        }
        for (DiscountCode discountCode : this.getAllDiscountCodes()) {
            if (discountCode.isDiscountCodeExpired()) {
                this.removeDiscountCode(discountCode);
            }
            for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
                customer.removeDiscountCode(discountCode);
                //  Database.getInstance().saveItem(customer);
            }
            Database.getInstance().deleteItem(discountCode);
        }
    }
}
