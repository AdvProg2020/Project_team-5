package model.productThings;

import model.category.SubCategory;
import model.persons.Seller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Good {
    private long goodId;
    private GoodStatus goodStatus;
    private String name;
    private String brand;
    private double averageRate;
    private SubCategory subCategory;
    private ArrayList<SellerRelatedInfoAboutGood> sellerRelatedInfoAboutGoods = new ArrayList<>();
    private String details;
    private ArrayList<Comment> comments;
    private int seenNumber;
    private LocalDate modificationDate;
    private HashMap<String, Object> categoryProperties;

    public Good(String name, String brand, SubCategory subCategory, String details, HashMap<String, Object> categoryProperties, Seller seller, long price, int availableNumber) {
        this.name = name;
        this.brand = brand;
        this.subCategory = subCategory;
        this.details = details;
        this.categoryProperties = categoryProperties;
        this.goodStatus = GoodStatus.BUILTPROCESSING;
        sellerRelatedInfoAboutGoods.add(new SellerRelatedInfoAboutGood(seller, price, availableNumber));
        this.comments = new ArrayList<>();
        this.modificationDate = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public long getGoodId() {
        return goodId;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public ArrayList<SellerRelatedInfoAboutGood> getSellerRelatedInfoAboutGoods() {
        return sellerRelatedInfoAboutGoods;
    }

    public void AddSeller(SellerRelatedInfoAboutGood newSellerRelatedInfo){
        this.sellerRelatedInfoAboutGoods.add(newSellerRelatedInfo);
    }

    public void removeSeller(SellerRelatedInfoAboutGood sellerRelatedInfo){
        this.sellerRelatedInfoAboutGoods.remove(sellerRelatedInfo);
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public long getPriceBySeller(Seller seller) {
        for (SellerRelatedInfoAboutGood goodInfo : sellerRelatedInfoAboutGoods) {
            if (goodInfo.getSeller().equals(seller))
                return goodInfo.getPrice();
        }
        return 0;
    }
}

enum GoodStatus {
    BUILTPROCESSING, EDITTINGPROCESSING, CONFIRMED
}