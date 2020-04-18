package view;

import controller.MainController;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import view.accountArea.AccountAreaForCustomer;
import view.accountArea.AccountAreaForManager;
import view.accountArea.AccountAreaForSeller;

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
    public void help() {
    }

    @Override
    public void execute(){
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == submenus.size() + 2) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else if (MainController.getInstance().getCurrentPerson() == null && chosenMenu == 1){
            System.out.println("you must login or register first");
        } else
            nextMenu = submenus.get(chosenMenu-1);
        nextMenu.help();
        nextMenu.execute();
    }
}

