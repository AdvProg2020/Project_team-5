package view.accountArea.AccountAreaForSeller;

import controller.MainController;
import model.Shop;
import model.persons.Seller;
import view.Menu;

import java.util.ArrayList;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("manage products", parentMenu);
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand == 4)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == 1)
                viewProduct();
            if (chosenCommand == 2)
                viewBuyers();
            if (chosenCommand == 3)
                editProduct();
            nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    @Override
    public void setCommandNames() {
        commandNames.add("- view product");
        commandNames.add("- view buyers");
        commandNames.add("- edit product");
    }

    private void viewProduct(){

    }

    private void viewBuyers(){

    }

   private void editProduct(){

   }

}
