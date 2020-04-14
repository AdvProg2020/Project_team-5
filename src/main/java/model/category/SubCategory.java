package model.category;

import model.productThings.Good;

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

    public void addGood(Good good){
        this.goods.add(good);
    }

    public void removeGood(Good good){
        this.goods.remove(good);
    }
}
