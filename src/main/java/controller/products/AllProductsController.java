package controller.products;

import model.Shop;
import model.category.Category;

import java.util.ArrayList;

public class AllProductsController {

    public String showCategories() {
        ArrayList<Category> allCategories = Shop.getInstance().getAllCategories();
        String output ="";
        for (Category category : allCategories) {
            output +=category.toString();
        }
        return output;
    }
}
