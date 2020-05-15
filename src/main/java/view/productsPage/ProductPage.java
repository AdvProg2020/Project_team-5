package view.productsPage;

import controller.MainController;
import exception.productExceptions.DontHaveEnoughNumberOfThisProduct;
import exception.productExceptions.ProductWithThisIdNotExist;
import view.LoginRegisterMenu;
import view.Menu;

public class ProductPage extends Menu {

    public ProductPage(Menu parentMenu) {
        super("page of a special product", parentMenu);
        submenus.add(new LoginRegisterMenu(this));
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

    private void compareWithAnotherProduct() {
        System.out.println("enter product id");
        long id = Long.parseLong(getValidInput("\\d+", "you must enter a number"));
        try {
            System.out.println(MainController.getInstance().getProductController().compareWithAnotherProduct(id));
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            productWithThisIdNotExist.getMessage();
            System.out.println("press enter to continue");
            scanner.nextLine();
            compareWithAnotherProduct();
        }
    }

    private void showComments(){
        System.out.println(MainController.getInstance().getProductController().showComments());
    }

    private void addComment(){
        if (MainController.getInstance().getCurrentPerson() == null){
            Menu nextmenu=new LoginRegisterMenu(this);
            nextmenu.help();
            nextmenu.execute();
        }else{
            System.out.println("title:");
            String title=scanner.nextLine();
            System.out.println("content:");
            String content=scanner.nextLine();
            MainController.getInstance().getProductController().addComment(title,content);
            System.out.println("your comment will be added soon if managers accept it");
        }
    }

    @Override
    public void execute() {
        help();
        Menu nextMenu = this;
        int input = getInput();
        if(input == 1){
            nextMenu=submenus.get(0);
        }
        if (input == 2) {
            digest();
        } else if (input == 3) {
            addGoodToCart();
        } else if (input == 4) {
            attributes();
        } else if (input == 5) {
            compareWithAnotherProduct();
        } else if (input == 6) {
            showComments();
        } else if (input == 7) {
            addComment();
        } else if (input == 8) {
            nextMenu = this.getParentMenu();
        }
        if (input != 8){
            System.out.println("press enter to continue");
            scanner.nextLine();
        }
        nextMenu.execute();
    }
}
