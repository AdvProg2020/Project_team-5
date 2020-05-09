package controller.accountArea;

import controller.MainController;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;

import java.util.ArrayList;

public class AccountAreaController {
    public ArrayList<String> showCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Shop.getInstance().getAllCategories()) {
            String thisCategory = "* subcategories of " + category.getName() + "\n";
            int i = 1;
            for (SubCategory subCategory : category.getSubCategories()) {
                thisCategory += (i++) + "- " + subCategory.getName() + "\n";
            }
            categories.add(thisCategory);
        }
        return categories;
    }

    public String getUserPersonalInfo() {
        return MainController.getInstance().getCurrentPerson().toString();
    }

    public void editField (String field, String newValue) {
        //TODO
    }
}
