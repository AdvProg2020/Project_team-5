package model;

import model.category.Category;
import model.persons.Person;
import model.persons.Seller;
import model.productThings.*;
import model.requests.Request;

import java.util.ArrayList;

public class Shop {
    private static Shop ourInstance = new Shop();
    private ArrayList<Category> allCategories;
    private ArrayList<Off> offs;
    private ArrayList<Person> allPersons;
    private ArrayList<Request> allRequest;
    private ArrayList<DiscountCode> allDiscountCodes;
    private ArrayList<Rate> allRates;
    private ArrayList<GoodInCart> cart;

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

    public void removePerson(Person user){
        allPersons.remove(user);
    }

    public void addPerson(Person user){
        allPersons.add(user);
    }

    public void removeProduct(Good good){
        good.getSubCategory().removeGood(good);
    }

    public void addProduct(Good good){
        good.getSubCategory().addGood(good);
    }

    public Person findUser(String userName){
        for (Person person : allPersons) {
            if (person.getUsername().equals(userName))
                return person;
        }
        return null;
    }

    public Good findGoodById(long goodId){
        for (Category category : allCategories) {
            if (category.findGoodInSubCategories(goodId) != null)
                return category.findGoodInSubCategories(goodId);
        }
        return null;
    }

    public void removeDiscountCode(DiscountCode discountCode){
        allDiscountCodes.remove(discountCode);
    }

    public Request findReQuestById(long requestId){
        for (Request request : allRequest) {
            if (request.getRequestId() == requestId)
                return request;
        }
        return null;
    }

    public void removeRequest(Request request){
        allRequest.remove(request);
    }

    public void addRequest(Request request){
        allRequest.add(request);
    }

    public void addCategory(Category category){
        allCategories.add(category);
    }

    public void removeCategory(Category category){
        allCategories.remove(category);
    }

    public ArrayList<Rate> getRatesOfAGood(Good good){
        ArrayList<Rate> ratesOfGood = new ArrayList<>();
        for (Rate rate : allRates) {
            if (rate.getGood().equals(good))
                ratesOfGood.add(rate);
        }
        return ratesOfGood;
    }

    public void addGoodToCart(Good good,Seller seller){
        cart.add(new GoodInCart(good, seller, 1));
    }

    public void reduceGoodInCartNumber(Good good){
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().equals(good)){
                goodInCart.setNumber(goodInCart.getNumber()-1);
                if(goodInCart.getNumber() == 0)
                    cart.remove(goodInCart);
                return;
            }
        }
    }

    public void increaseGoodInCartNumber(Good good){
        for (GoodInCart goodInCart : cart) {
            if (goodInCart.getGood().equals(good)) {
                goodInCart.setNumber(goodInCart.getNumber() + 1);
                return;
            }
        }
    }
}
