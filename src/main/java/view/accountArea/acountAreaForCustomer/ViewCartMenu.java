package view.accountArea.acountAreaForCustomer;

import controller.MainController;
import exception.ProductNotExistInCart;
import view.LoginRegisterMenu;
import view.Menu;

import java.util.List;
import java.util.regex.Pattern;

public class ViewCartMenu extends Menu {
    public ViewCartMenu(Menu parentMenu) {
        super("view cart", parentMenu);
        submenus.add(new PurchaseMenu(this));
        submenus.add(new LoginRegisterMenu(this));
    }

    @Override
    protected void setCommandNames() {
        commandNames.add("- show products");
        commandNames.add("- view a product");
        commandNames.add("- increase product");
        commandNames.add("- decrease product");
        commandNames.add("- show total price");
    }

    @Override
    public void execute() {
        int chosenCommand = getInput();
        Menu nextMenu;
        if (chosenCommand <= submenus.size())
            nextMenu = submenus.get(chosenCommand - 1);
        else if (chosenCommand == submenus.size() + commandNames.size() + 1)
            nextMenu = parentMenu;
        else {
            if (chosenCommand == submenus.size() + 1)
                showProducts();
            if (chosenCommand == submenus.size() + 2)
                showASpecialProduct();
            if (chosenCommand == submenus.size() + 3)
                increaseProduct();
            if (chosenCommand == submenus.size() + 4)
                decreaseProduct();
            if (chosenCommand == submenus.size() + 5)
                showTotalPrice();
                nextMenu = this;
        }
        nextMenu.help();
        nextMenu.execute();
    }

    private void showProducts() {
        List<String> cart = MainController.getInstance().getAccountAreaForCustomerController().viewInCartProducts();
        if (cart.size() == 0)
            System.out.println("cart is empty!");
        else {
            for (String good : cart) {
                System.out.println(good);
            }
        }
    }

    private void showASpecialProduct() {
        try {
            long productId = getProductId();
            System.out.println(MainController.getInstance().getAccountAreaForCustomerController().viewSpecialProduct(productId));
        } catch (ProductNotExistInCart productNotExistInCart) {
            System.out.println(productNotExistInCart.getMessage());
        }
    }

    private void increaseProduct() {
        try {
            long productId = getProductId();
            MainController.getInstance().getAccountAreaForCustomerController().increaseInCartProduct(productId);
        } catch (ProductNotExistInCart productNotExistInCart) {
            System.out.println(productNotExistInCart.getMessage());
        }
    }

    private void decreaseProduct() {
        try {
            long productId = getProductId();
            MainController.getInstance().getAccountAreaForCustomerController().decreaseInCartProduct(productId);
        } catch (ProductNotExistInCart productNotExistInCart) {
            System.out.println(productNotExistInCart.getMessage());
        }
    }

    private void showTotalPrice(){
        System.out.println(MainController.getInstance().getAccountAreaForCustomerController().getTotalPriceOfCart() + " Rial");
    }

    private long getProductId() throws ProductNotExistInCart {
        System.out.println("enter product ID :");
        String input = scanner.nextLine();
        if (!Pattern.matches("[\\d]+", input))
            throw new ProductNotExistInCart();
        if (!MainController.getInstance().getAccountAreaForCustomerController().checkExistProductInCart(Long.parseLong(input)))
            throw new ProductNotExistInCart();
        return Long.parseLong(input);
    }
}
