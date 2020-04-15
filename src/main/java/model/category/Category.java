package model.category;

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

    public void removeSubCategory(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
    }

    @Override
    public String toString() {
        String categoryStr = "Name of Category : " + getName() + "\nSubcategories :";
        for (int i = 0; i < subCategories.size(); i++) {
            categoryStr += "\n" + (i + 1) + "- " + subCategories.get(i).getName();
        }
        categoryStr += "\nSpecial properties :";
        for (int i = 0; i < details.size(); i++) {
            categoryStr += "\n" + (i + 1) + "- " + details.get(i);
        }

        return categoryStr;
    }

}
