package ApProject_OnlineShop.controller.accountArea;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.productExceptions.FieldCantBeEditedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.Order;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAreaController {
    public ArrayList<String> showCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Shop.getInstance().getAllCategories()) {
            String thisCategory = "* subcategories of " + category.getName() + "\n";
            int i = 1;
            for (SubCategory subCategory : category.getSubCategories()) {
                thisCategory += (i++) + "- " + subCategory.getName() + "\n";
            }
            categories.add(thisCategory);
        }
        Collections.sort(categories);
        return categories;
    }

    public ArrayList<String> getUserPersonalInfo(Person person) {
        ArrayList<String> personalInfo = new ArrayList<>();
        personalInfo.add(person.getUsername());
        personalInfo.add(person.getFirstName());
        personalInfo.add(person.getLastName());
        personalInfo.add(person.getEmail());
        personalInfo.add(person.getPhoneNumber());
        if (person instanceof Customer) {
            Customer customer = (Customer) person;
            personalInfo.add("" + customer.getCredit());
        }
        return personalInfo;
    }

    public void editField(int chosenField, String newValue, Person person) throws FieldCantBeEditedException, Exception {
        if (chosenField == 1) {
            person.setFirstName(newValue);
        } else if (chosenField == 2) {
            person.setLastName(newValue);
        } else if (chosenField == 3) {
            if (MainController.isEmailValid(newValue))
                person.setEmail(newValue);
            else
                throw new FieldCantBeEditedException("email", "input has not correct format for email");
        } else if (chosenField == 4) {
            if (MainController.isPhoneNumberValid(newValue))
                person.setPhoneNumber(newValue);
            else
                throw new FieldCantBeEditedException("phone number", "input has not correct format for phone number");
        } else if (chosenField == 5) {
            if (!newValue.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})"))
                throw new FieldCantBeEditedException("password", "input has not conditions of a valid password");
            if (newValue.equals(person.getPassword()))
                throw new FieldCantBeEditedException("password", "new password and old password are identical");
            person.setPassword(newValue);
        } else throw new Exception("no valid field selected.");
        Database.getInstance().saveItem(person);
    }

    public List<String> getSortedOrders(int chosenSort, List<Order> orders) {
        List<String> ordersString = new ArrayList<>();
        if (chosenSort == 0)
            ordersString = orders.stream().map(order -> order.briefString()).collect(Collectors.toList());
        if (chosenSort == 1)
            ordersString = MainController.getInstance().getSortController().sortByDate(orders).stream().map(order -> order.briefString()).collect(Collectors.toList());
        if (chosenSort == 2)
            ordersString = MainController.getInstance().getSortController().sortByPrice(orders).stream().map(order -> order.briefString()).collect(Collectors.toList());
        return ordersString;
    }

    public List<String> getOrderDetails(long id, String username, String role) {
        if (role.equals("customer")) {
            Customer customer = (Customer) Shop.getInstance().findUser(username);
            return customer.findOrderById(id).getDetails();
        } else if (role.equals("seller")) {
            Seller seller = (Seller) Shop.getInstance().findUser(username);
            return seller.findOrderById(id).getDetails();
        }
        return null;
    }
}
