package controller;

import model.Shop;
import model.persons.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controller {
    private Shop shop;
    private Person currentPerson;

    public Controller() {
        this.shop = Shop.getInstance();
        this.currentPerson = null;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public static Boolean isPhoneNumberValid(String phoneNumber) {
        return Pattern.compile("^\\d{11}$").matcher(phoneNumber).find();
    }

    public static Boolean isEmailValid(String email){
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email).find();
    }

}
