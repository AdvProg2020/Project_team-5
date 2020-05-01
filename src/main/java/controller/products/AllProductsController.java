package controller.products;

import controller.MainController;
import exception.ProductNotFoundException;
import model.Shop;
import model.category.Category;

import java.util.ArrayList;

public class AllProductsController {

    public String showCategories() {
        ArrayList<Category> allCategories = Shop.getInstance().getAllCategories();
        String output = "";
        for (Category category : allCategories) {
            output += category.toString();
        }
        return output;
    }

    public void showAProduct(long id) throws ProductNotFoundException {
        if (Shop.getInstance().findGoodById(id) == null){
            throw new ProductNotFoundException();
        }else{
            MainController.getInstance().getProductController().setGood(Shop.getInstance().findGoodById(id));
        }
    }
}
