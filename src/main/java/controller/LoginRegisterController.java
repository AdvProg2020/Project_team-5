package controller;

import exception.MainManagerAlreadyRegistered;
import exception.PasswordIncoreectException;
import exception.UsernameIsTakenAlreadyException;
import exception.UsernameNotFoundException;
import model.Shop;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Person;
import model.persons.Seller;
import model.requests.RegisteringSellerRequest;

import java.util.ArrayList;

public class LoginRegisterController {

    public void createAccount(String role, String username, ArrayList<String> details) throws UsernameIsTakenAlreadyException, MainManagerAlreadyRegistered {
        if (Shop.getInstance().findUser(username) != null) {
            throw new UsernameIsTakenAlreadyException();
        }
        if (role.equals("customer")) {
            Shop.getInstance().addPerson(new Customer(username, details.get(0), details.get(1), details.get(2),
                    details.get(3), details.get(4), Long.parseLong(details.get(5))));
        } else if (role.equals("seller")) {
            Shop.getInstance().addRequest(new RegisteringSellerRequest(new Seller(username, details.get(0), details.get(1)
                    , details.get(2), details.get(3), details.get(4))));
        } else {
            if (Shop.getInstance().didManagerRegistered())
                throw new MainManagerAlreadyRegistered();
            else
                Shop.getInstance().addPerson(new Manager(username, details.get(0), details.get(1)
                        , details.get(2), details.get(3), details.get(4)));
        }
    }

    public void loginUser(String username, String password) throws UsernameNotFoundException, PasswordIncoreectException {
        if (Shop.getInstance().findUser(username) == null)
            throw new UsernameNotFoundException();
        Person person = Shop.getInstance().findUser(username);
        if (!(person.getPassword().equals(password)))
            throw new PasswordIncoreectException();
        MainController.getInstance().setCurrentPerson(person);
    }
}
