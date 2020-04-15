package model;

import model.category.Category;
import model.persons.Customer;
import model.persons.Person;
import model.persons.Seller;
import model.productThings.*;
import model.requests.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void addGoodToCart(Good good, Seller seller) {
        cart.add(new GoodInCart(good, seller, 1));
    }

    public void reduceGoodInCartNumber(Good good) {
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().equals(good)) {
                goodInCart.setNumber(goodInCart.getNumber() - 1);
                if (goodInCart.getNumber() == 0)
                    cart.remove(goodInCart);
                return;
            }
        }
    }

    public void increaseGoodInCartNumber(Good good) {
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().equals(good)) {
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
        offs.remove(off);
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
        String code=DiscountCode.generateRandomDiscountCode();
        DiscountCode discountCode = new DiscountCode(code,LocalDate.now(),endDate,100000L,20,randomCustomers(5,1));
        allDiscountCodes.add(discountCode);
        for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
            customer.addDiscountCode(discountCode);
        }
    }

    private HashMap<Customer,Integer> randomCustomers(int customerNumbers,int repeatingTimes){
        HashMap<Customer,Integer> randomCustomers=new HashMap<>();
        while(randomCustomers.size() < customerNumbers){
            int randomNumber = ((int)(Math.random() * 1000000)) % allPersons.size();
            Person person = allPersons.get(randomNumber);
            if(person instanceof Customer){
                randomCustomers.put((Customer) person ,repeatingTimes);
            }
        }
        return randomCustomers;
    }
}
