package model;

import model.category.Category;
import model.category.SubCategory;
import model.orders.OrderForCustomer;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Person;
import model.persons.Seller;
import model.productThings.*;
import model.requests.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {
    private static Shop ourInstance = new Shop();
    private ArrayList<Category> allCategories;
    private ArrayList<Off> offs;
    private ArrayList<Person> allPersons;
    private ArrayList<Request> allRequest;
    private ArrayList<DiscountCode> allDiscountCodes;
    private ArrayList<Rate> allRates;
    private ArrayList<GoodInCart> cart;
    private LocalDate lastRandomPeriodDiscountCodeCreatedDate;

    public static Shop getInstance() {
        return ourInstance;
    }

    private Shop() {
        this.allCategories = new ArrayList<>();
        this.allDiscountCodes = new ArrayList<>();
        this.allPersons = new ArrayList<>();
        this.allRates = new ArrayList<>();
        this.allRequest = new ArrayList<>();
        this.offs = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public ArrayList<Request> getAllRequest() {
        return allRequest;
    }

    public void removePerson(Person user) {
        allPersons.remove(user);
    }

    public void addPerson(Person user) {
        allPersons.add(user);
    }

    public void removeProduct(Good good) {
        good.getSubCategory().removeGood(good);
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
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.remove(discountCode);
    }

    public void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.add(discountCode);
    }

    public Request findRequestById(long requestId) {
        for (Request request : allRequest) {
            if (request.getRequestId() == requestId)
                return request;
        }
        return null;
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

    public void addRate(Customer customer, long productId, int rate){
        allRates.add(new Rate(customer, findGoodById(productId), rate));
    }

    public ArrayList<GoodInCart> getCart() {
        return cart;
    }

    public boolean checkExistProductInCart(long productId) {
        return !cart.stream().filter(goodInCart -> goodInCart.getGood().getGoodId() == productId).findAny().isEmpty();
    }

    public void addGoodToCart(Good good, Seller seller,int number) {
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

    public void increaseGoodInCartNumber(long productId) {
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().getGoodId() == productId) {
                goodInCart.setNumber(goodInCart.getNumber() + 1);
                return;
            }
        }
    }

    public Off findOffById(long offId) {
        for (Off off : offs) {
            if (off.getOffId() == offId)
                return off;
        }
        return null;
    }

    public void addOff(Off off) {
        offs.add(off);
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
        discountCode.addAllCustomers(randomCustomers(5, 1));
        allDiscountCodes.add(discountCode);
    }

    private HashMap<Customer, Integer> randomCustomers(int customerNumbers, int repeatingTimes) {
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
        for (Off off : offs) {
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

    public boolean didManagerRegistered(){
        for (Person person : allPersons) {
            if (person instanceof Manager)
                return true;
        }
        return false;
    }

    public boolean checkExistDiscountCode(String code){
        return allDiscountCodes.stream().filter(discountCode -> discountCode.getCode().equals(code)).count() != 0;
    }

    public void clearCart(){
        this.cart.clear();
    }

}
