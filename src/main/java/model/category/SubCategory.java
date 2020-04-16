package model.category;

import model.productThings.Good;
import model.productThings.SellerRelatedInfoAboutGood;

import java.util.ArrayList;

public class SubCategory {
    private String name;
    private Category parentCategory;
    private ArrayList<String> details;
    private ArrayList<Good> goods;

    public SubCategory(String name, ArrayList<String> details) {
        this.name = name;
        this.details = details;
        this.goods = new ArrayList<>();
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public ArrayList<Good> getGoods() {
        return goods;
    }

    public void addGood(Good good) {
        this.goods.add(good);
    }

    public void removeGood(Good good) {
        this.goods.remove(good);
    }

    public void deleteGood(Good good) {
        good.deleteGoodFromSellerList();
        this.removeGood(good);
    }

    public Good findGoodById(long goodId) {
        for (Good good : goods) {
            if (good.getGoodId() == goodId)
                return good;
        }
        return null;
    }

    @Override
    public String toString() {
        String subCategoryStr = getName() + " of " + parentCategory.getName() + " category";
        subCategoryStr += "\nproperties =";
        for (int i = 0; i < details.size() + parentCategory.getDetails().size(); i++) {
            subCategoryStr += ("\n" + (i + 1) + "- ");
            if (i < parentCategory.getDetails().size())
                subCategoryStr += parentCategory.getDetails().get(i);
            else
                subCategoryStr += details.get(i - parentCategory.getDetails().size());
        }
        subCategoryStr += "\nProducts =\n";
        for (Good good : goods) {
            subCategoryStr += (good.toString());
        }
        return subCategoryStr;
    }
}
