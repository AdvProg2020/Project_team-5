package ApProject_OnlineShop.controller.accountArea;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.productExceptions.FieldCantBeEditedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.Order;
import ApProject_OnlineShop.model.persons.Customer;

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

    public ArrayList<String> getUserPersonalInfo() {
        Customer customer = (Customer) MainController.getInstance().getCurrentPerson();
        ArrayList<String> personalInfo = new ArrayList<>();
        personalInfo.add(customer.getUsername());
        personalInfo.add(customer.getFirstName());
        personalInfo.add(customer.getLastName());
        personalInfo.add(customer.getEmail());
        personalInfo.add(customer.getPhoneNumber());
        personalInfo.add(""+customer.getCredit());
        return personalInfo;
    }

    public void editField(int chosenField, String newValue) throws FieldCantBeEditedException, Exception {
        if (chosenField == 1) {
            MainController.getInstance().getCurrentPerson().setFirstName(newValue);
        } else if (chosenField == 2) {
            MainController.getInstance().getCurrentPerson().setLastName(newValue);
        } else if (chosenField == 3) {
            if (MainController.isEmailValid(newValue))
                MainController.getInstance().getCurrentPerson().setEmail(newValue);
            else
                throw new FieldCantBeEditedException("email", "input has not correct format for email");
        } else if (chosenField == 4) {
            if (MainController.isPhoneNumberValid(newValue))
                MainController.getInstance().getCurrentPerson().setPhoneNumber(newValue);
            else
                throw new FieldCantBeEditedException("phone number", "input has not correct format for phone number");
        } else if (chosenField == 5) {
            if (!newValue.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})"))
                throw new FieldCantBeEditedException("password", "input has not conditions of a valid password");
            if (newValue.equals(MainController.getInstance().getCurrentPerson().getPassword()))
                throw new FieldCantBeEditedException("password", "new password and old password are identical");
            MainController.getInstance().getCurrentPerson().setPassword(newValue);
        } else throw new Exception("no valid field selected.");
        Database.getInstance().saveItem(MainController.getInstance().getCurrentPerson());
    }

    public List<String> getSortedOrders(int chosenSort,List<Order> orders){
        List<String> ordersString = new ArrayList<>();
        if (chosenSort == 1 )
            ordersString = MainController.getInstance().getSortController().sortByDate(orders).stream().map(order -> order.toString()).collect(Collectors.toList());
        if (chosenSort == 2)
            ordersString = MainController.getInstance().getSortController().sortByPrice(orders).stream().map(order -> order.toString()).collect(Collectors.toList());
        return ordersString;
    }
}
