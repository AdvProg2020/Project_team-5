package view.productsPage;

import controller.MainController;
import exception.DontHaveEnoughNumberOfThisProduct;
import view.Menu;

public class ProductPage extends Menu {

    public ProductPage(Menu parentMenu) {
        super("page of a special product", parentMenu);

    }

    @Override
    protected void setCommandNames() {
        commandNames.add("digest");
        commandNames.add("add to cart");
        commandNames.add("attributes");
        commandNames.add("compare [productID]");
        commandNames.add("show comments");
        commandNames.add("add comments");
    }

    private void digest() {
        System.out.println(MainController.getInstance().getProductController().digest());
        System.out.println("press enter to continue");
        scanner.nextLine();
    }

    private void addGoodToCart() {
        System.out.println("enter numbers you want");
        int number = Integer.parseInt(getValidInput("\\d+", "you must enter a number"));
        System.out.println("select seller:");
        System.out.println(MainController.getInstance().getProductController().getSellersOfAGood());
        int sellerNumber = getInputForSellers();
        try {
            MainController.getInstance().getProductController().addGoodToCart(number, sellerNumber);
        } catch (DontHaveEnoughNumberOfThisProduct dontHaveEnoughNumberOfThisProduct) {
            dontHaveEnoughNumberOfThisProduct.getMessage();
            System.out.println("this seller just have " +
                    MainController.getInstance().getProductController().getAvailableNumberOfAProductByASeller(sellerNumber)
                    + "of this product");
            addGoodToCart();
        }
    }

    private int getInputForSellers() {
        boolean flag = false;
        int output = 0;
        do {
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                output = Integer.parseInt(input);
                if (output <= MainController.getInstance().getProductController().numbersOfSellers())
                    flag = true;
                else {
                    System.out.println("enter valid number please!");
                }
            } else {
                System.out.println("you must enter a number!");
            }
        } while (!flag);
        return output;
    }

    @Override
    public void execute() {
        Menu nextMenu = null;
        int input = getInput();
        if (input == 1) {
            digest();
        } else if (input == 2) {
            addGoodToCart();
        }
    }
}
