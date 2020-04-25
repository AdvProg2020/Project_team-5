package view.accountArea;

import controller.MainController;
import model.Shop;
import model.orders.OrderForCustomer;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.Good;
import view.LoginRegisterMenu;
import view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AccountAreaForSeller extends Menu {
    ArrayList<String> commandNames = new ArrayList<>();

    public AccountAreaForSeller(Menu parentMenu) {
        super("Account area for seller", parentMenu);
        setCommandNames();
    }

    private void setCommandNames() {
        this.commandNames.add("- view company information");
        this.commandNames.add("- view sales history");
        this.commandNames.add("- add product");
        this.commandNames.add("- remove product");
        this.commandNames.add("- show categories");
        this.commandNames.add("- view balance");
    }

    @Override
    public void help() {
        int i = 1;
        System.out.println("menus of " + this.getName() + ":");
        for (Menu submenu : submenus) {
            if (submenu instanceof LoginRegisterMenu) {
                if (MainController.getInstance().getCurrentPerson() == null) {
                    System.out.println("" + (i++) + "- Login or Register");
                } else {
                    System.out.println("" + (i++) + "- Logout");
                }
            } else
                System.out.println("" + (i++) + "-" + submenu.getName());
        }
        System.out.println("commands of " + this.getName() + ":");
        for (String command : commandNames) {
            System.out.println("" + (i++) + command);
        }
        if (this.parentMenu != null)
            System.out.println((i++) + "- Back");
        else
            System.out.println((i++) + "- Exit");
    }

    @Override
    public void execute() {
        int chosenCommand = Integer.parseInt(getInput());
    }

    private String getInput(){
        String input;
        while (true){
            input = scanner.nextLine();
            if(Pattern.matches("[0-9]+",input))
                if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= commandNames.size() + submenus.size() +1)
                    return input;
            System.out.println("not valid input. please try again");
        }
    }

    public void removeProduct(long productId) throws notHaveThisProduct{
        Seller seller = (Seller)MainController.getInstance().getCurrentPerson();
        Good good = seller.findProductOfSeller(productId);
        if (good != null){
            if (good.getSellerRelatedInfoAboutGoods().size()==1)
                good.getSubCategory().deleteGood(good);
            else {
                good.removeSeller(seller);
                seller.removeFromActiveGoods(good);
            }
        }
        if (good== null)
            throw new notHaveThisProduct();
    }

    public ArrayList<String> buyersOfProduct(long productId){
        return ((Seller)MainController.getInstance().getCurrentPerson()).buyersOfAGood(Shop.getInstance().findGoodById(productId))
    }

    public class notHaveThisProduct extends Exception{
        public notHaveThisProduct() {
            super("You do not have product with this productId");
        }
    }
}