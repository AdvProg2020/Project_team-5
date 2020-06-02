package ApProject_OnlineShop.view;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.view.accountArea.acountAreaForCustomer.AccountAreaForCustomer;
import ApProject_OnlineShop.view.accountArea.accountAreaForSeller.AccountAreaForSeller;
import ApProject_OnlineShop.view.accountArea.accountAreaForManager.AccountAreaForManager;
import ApProject_OnlineShop.view.productsPage.AllProductsPage;

public class MainMenu extends Menu{


    public MainMenu(){
        super("ApProject_OnlineShop.Main Menu", null);
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
                System.exit(1);
            }
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = submenus.get(chosenMenu-1);
        nextMenu.execute();
    }
}

