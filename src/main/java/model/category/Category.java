package model.category;

import model.Shop;
import model.productThings.Good;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> details;
    private ArrayList<String> subCategories;

    public Category(String name, ArrayList<String> details) {
        this.name = name;
        this.details = details;
        this.subCategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public ArrayList<SubCategory> getSubCategories() {
        ArrayList<SubCategory> subCategories2=new ArrayList<>();
        for (String subCategory : this.subCategories) {
            subCategories2.add(Shop.getInstance().findSubCategoryByName(subCategory));
        }
        return subCategories2;
    }

    public void addSubCategory(SubCategory subCategory) {
        this.getSubCategories().add(subCategory);
        subCategory.setParentCategory(this);
    }

    public void deleteSubCategory(SubCategory subCategory) {
        for (int i = 0; i < subCategory.getGoods().size(); ) {
            subCategory.deleteGood(subCategory.getGoods().get(0));
        }
        removeSubCategoryFromList(subCategory);
    }

    public void removeSubCategoryFromList(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
    }

    public Good findGoodInSubCategories(long goodId) {
        for (SubCategory subCategory : this.getSubCategories()) {
            if (subCategory.findGoodById(goodId) != null)
                return subCategory.findGoodById(goodId);
        }
        return null;
    }

    public SubCategory findSubCategoryByName(String subcategoryName) {
        for (SubCategory subCategory : this.getSubCategories()) {
            if (subCategory.getName().equals(subcategoryName))
                return subCategory;
        }
        return null;
    }

    @Override
    public String toString() {
        String categoryStr = "Name of Category = " + getName() + "\nSubcategories =";
        for (int i = 0; i < subCategories.size(); i++) {
            categoryStr += ("\n" + (i + 1) + "- " + subCategories.get(i));
        }
        categoryStr += "\nSpecial properties :";
        for (int i = 0; i < details.size(); i++) {
            categoryStr += ("\n" + (i + 1) + "- " + details.get(i));
        }
        return (categoryStr + "\n");
    }

}
