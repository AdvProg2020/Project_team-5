package model.category;

import model.Shop;
import model.productThings.Good;
import model.productThings.SellerRelatedInfoAboutGood;

import java.util.ArrayList;

public class SubCategory {
    private String name;
    private String parentCategory;
    private ArrayList<String> details;
    private ArrayList<Long> goods;

    public SubCategory(String name, ArrayList<String> details) {
        this.name = name;
        this.details = details;
        this.goods = new ArrayList<>();
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory= parentCategory.getName();
    }

    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return Shop.getInstance().getHashMapOfCategories().get(this.parentCategory);
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public ArrayList<Good> getGoods() {
        ArrayList<Good> goods1=new ArrayList<>();
        for (Long id : goods) {
            goods1.add(Shop.getInstance().getAvailableGood(id));
        }
        return goods1;
    }

    public void addGood(Good good) {
        this.goods.add(good.getGoodId());
    }

    public void deleteGood(Good good) {
        this.goods.remove(good.getGoodId());
    }

    public Good findGoodById(long goodId) {
        for (Good good : getGoods()) {
            if (good.getGoodId() == goodId)
                return good;
        }
        return null;
    }

    @Override
    public String toString() {
        String subCategoryStr = getName() + " of " + parentCategory + " category";
        subCategoryStr += "\nproperties =";
        for (int i = 0; i < details.size() + getParentCategory().getDetails().size(); i++) {
            subCategoryStr += ("\n" + (i + 1) + "- ");
            if (i < getParentCategory().getDetails().size())
                subCategoryStr += getParentCategory().getDetails().get(i);
            else
                subCategoryStr += details.get(i - getParentCategory().getDetails().size());
        }
        subCategoryStr += "\nProducts =\n";
        for (Good good : getGoods()) {
            subCategoryStr += (good.toString());
        }
        return subCategoryStr;
    }
}
