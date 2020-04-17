package controller;

import model.Shop;
import model.persons.Person;

public class MainController{
    private Shop shop;
    private Person currentPerson;
    private LoginRegisterController loginRegisterController;

    public LoginRegisterController getLoginRegisterController() {
        return loginRegisterController;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }
}
