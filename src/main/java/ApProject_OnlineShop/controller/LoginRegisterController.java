package ApProject_OnlineShop.controller;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.PasswordIncorrectException;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.requests.RegisteringSellerRequest;

import java.io.IOException;
import java.util.ArrayList;

public class LoginRegisterController {

    public void createAccount(String role, String username, ArrayList<String> details)
            throws UsernameIsTakenAlreadyException, MainManagerAlreadyRegistered, IOException, FileCantBeSavedException {
        if (Shop.getInstance().findUser(username) != null) {
            throw new UsernameIsTakenAlreadyException();
        }
        if (role.equals("customer")) {
            Customer customer = new Customer(username, details.get(0), details.get(1), details.get(2),
                    details.get(3), details.get(4), Long.parseLong(details.get(5)));
            Shop.getInstance().addPerson(customer);
            Database.getInstance().saveItem(customer);
        } else if (role.equals("seller")) {
            RegisteringSellerRequest seller = new RegisteringSellerRequest(username, details.get(0), details.get(1)
                    , details.get(2), details.get(3), details.get(4),
                    details.get(5), details.get(6), details.get(7), details.get(8), details.get(9));
            Shop.getInstance().addRequest(seller);
            Database.getInstance().saveItem(seller);
        } else {
            if (Shop.getInstance().didManagerRegistered())
                throw new MainManagerAlreadyRegistered();
            else {
                Manager manager = new Manager(username, details.get(0), details.get(1)
                        , details.get(2), details.get(3), details.get(4));
                Shop.getInstance().addPerson(manager);
                Database.getInstance().saveItem(manager);
            }
        }
    }

    public void loginUser(String username, String password) throws UsernameNotFoundException, PasswordIncorrectException {
        if (Shop.getInstance().findUser(username) == null)
            throw new UsernameNotFoundException();
        Person person = Shop.getInstance().findUser(username);
        if (!(person.getPassword().equals(password)))
            throw new PasswordIncorrectException();
        MainController.getInstance().setCurrentPerson(person);
    }

    public void logoutUser() {
        MainController.getInstance().setCurrentPerson(null);
    }
}
