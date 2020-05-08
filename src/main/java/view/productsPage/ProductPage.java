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
    }

    private void addGoodToCart() {
        System.out.println("enter numbers you want");
        int number = Integer.parseInt(getValidInput("\\d+", "you must enter a number"));
        System.out.println("select seller:");
        System.out.println(MainController.getInstance().getProductController().getSellersOfAGood());
        int sellerNumber = getInputForSellers();
        try {
            MainController.getInstance().getProductController().addGoodToCart(number, sellerNumber);
            System.out.println("succesfully added!");
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
                if (output <= MainController.getInstance().getProductController().numbersOfSellers
                        (MainController.getInstance().getProductController().getGood()))
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

    private void attributes() {
        System.out.println(MainController.getInstance().getProductController().attributes());
    }

    private void compareWithAnotherProduct(){
        System.out.println("enter product id");
        long id=Long.parseLong(getValidInput("\\d+","you must enter a number"));

    }

    @Override
    public void execute() {
        Menu nextMenu = this;
        int input = getInput();
        if (input == 1) {
            digest();
        } else if (input == 2) {
            addGoodToCart();
        } else if (input == 3) {
            attributes();
        } else if (input == 4){
            compareWithAnotherProduct();
        }
        System.out.println("press enter to continue");
        scanner.nextLine();
    }
}
