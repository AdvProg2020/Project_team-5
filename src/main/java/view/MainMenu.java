package view;

import controller.MainController;
import exception.FileCantBeSavedException;
import database.Database;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import view.accountArea.acountAreaForCustomer.AccountAreaForCustomer;
import view.accountArea.accountAreaForSeller.AccountAreaForSeller;
import view.accountArea.accountAreaForManager.AccountAreaForManager;
import view.productsPage.AllProductsPage;

import java.io.IOException;

public class MainMenu extends Menu{


    public MainMenu(){
        super("Main Menu", null);
        if(MainController.getInstance().getCurrentPerson() == null)
            submenus.add(new LoginRegisterMenu("Account area",this));
        else if (MainController.getInstance().getCurrentPerson()instanceof Customer)
            submenus.add(new AccountAreaForCustomer(this));
        else if (MainController.getInstance().getCurrentPerson()instanceof Seller)
            submenus.add(new AccountAreaForSeller(this));
        else if (MainController.getInstance().getCurrentPerson()instanceof Manager)
            submenus.add(new AccountAreaForManager(this));
        submenus.add(new AllProductsPage(this));
        submenus.add(new OffsPage(this));
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {

    }

    @Override
    public void execute(){
        help();
        Menu nextMenu = null;
        int chosenMenu =this.getInput();
        if (chosenMenu == submenus.size() + 1) {
            if (this.parentMenu == null) {
                try {
                    Database.getInstance().saveShop();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FileCantBeSavedException e) {
                    e.printStackTrace();
                }
                System.exit(1);
            }
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = submenus.get(chosenMenu-1);
        nextMenu.execute();
    }
}

