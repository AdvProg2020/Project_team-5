package view.accountArea.accountAreaForSeller;

import controller.MainController;
import exception.ProductNotFoundExceptionForSeller;
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
        try {
            System.out.println("Enter product ID :");
            String product = MainController.getInstance().getAccountAreaForSellerController().
                    viewProduct(Long.parseLong(getValidInput("[0-9]+", "Not valid product ID")));
            System.out.println(product);
        }catch (ProductNotFoundExceptionForSeller exception){
            System.out.println(exception.getMessage());
        }
    }

    private void viewBuyers(){
        try {
            System.out.println("Enter product ID :");
            ArrayList<String> buyers = MainController.getInstance().getAccountAreaForSellerController().
                    buyersOfProduct(Long.parseLong(getValidInput("[0-9]+", "Not valid product ID")));
            for (String buyer : buyers) {
                System.out.println(buyer);
            }
        }catch (ProductNotFoundExceptionForSeller exception){
            System.out.println(exception.getMessage());
        }
    }

   private void editProduct(){

   }
}
