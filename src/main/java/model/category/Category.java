package model.category;

import model.productThings.Good;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> details;
    private ArrayList<SubCategory> subCategories;

    public Category(String name, ArrayList<String> details, ArrayList<SubCategory> subCategories) {
        this.name = name;
        this.details = details;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
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
        for (SubCategory subCategory : subCategories) {
            if (subCategory.findGoodById(goodId) != null)
                return subCategory.findGoodById(goodId);
        }
        return null;
    }

    public SubCategory findSubCategoryByName(String subcategoryName){
        for (SubCategory subCategory : subCategories) {
            if (subCategory.getName().equals(subcategoryName))
                return subCategory;
        }
        return null;
    }

    @Override
    public String toString() {
        String categoryStr = "Name of Category = " + getName() + "\nSubcategories =";
        for (int i = 0; i < subCategories.size(); i++) {
            categoryStr += ("\n" + (i + 1) + "- " + subCategories.get(i).getName());
        }
        categoryStr += "\nSpecial properties :";
        for (int i = 0; i < details.size(); i++) {
            categoryStr += ("\n" + (i + 1) + "- " + details.get(i));
        }
        return (categoryStr + "\n");
    }

}
