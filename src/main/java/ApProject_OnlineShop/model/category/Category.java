package ApProject_OnlineShop.model.category;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Good;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name = "Category")
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
            subCategories2.add(Shop.getInstance().getSubCategory(subCategory));
        }
        return subCategories2;
    }

    public void addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory.getName());
        subCategory.setParentCategory(this);
    }

    public void deleteSubCategory(SubCategory subCategory) {
        removeSubCategoryFromList(subCategory);
    }

    public void removeSubCategoryFromList(SubCategory subCategory) {
        this.subCategories.remove(subCategory.getName());
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
