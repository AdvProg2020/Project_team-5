package controller.products;

import controller.MainController;
import exception.ProductWithThisIdNotExist;
import model.Shop;
import model.category.Category;
import model.productThings.Good;

import java.util.ArrayList;
import java.util.List;

public class AllProductsController {

    public String showCategories() {
        ArrayList<Category> allCategories = Shop.getInstance().getAllCategories();
        String output = "";
        for (Category category : allCategories) {
            output += category.toString();
        }
        return output;
    }

    public void showAProduct(long id) throws ProductWithThisIdNotExist {
        if (Shop.getInstance().findGoodById(id) == null) {
            throw new ProductWithThisIdNotExist();
        } else {
            MainController.getInstance().getProductController().setGood(Shop.getInstance().findGoodById(id));
        }
    }

    public String showProducts() {
        List<Good> goodList = MainController.getInstance().getControllerForFiltering().showProducts();
        String output = "";
        for (Good good : goodList) {
            if (goodList.get(goodList.size() - 1).equals(good))
                output += good.toString();
            else
                output += (good.toString() + "\n");
        }
        return output;
    }
}
