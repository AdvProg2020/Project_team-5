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
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (phoneNumber.charAt(i) > '9' || phoneNumber.charAt(i) < '0') {
                return false;
            }
        }
        if (phoneNumber.length() != 11)
            return false;
        return true;
    }

    public static Boolean isEmailValid(String email){
        Pattern pattern= Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(email);
        return matcher.find();
    }

}
