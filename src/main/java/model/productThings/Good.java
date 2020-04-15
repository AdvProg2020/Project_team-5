package model.productThings;

import model.category.SubCategory;
import model.persons.Seller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Good {
    private static long goodsCount = 1;
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

    public enum GoodStatus {
        BUILTPROCESSING, EDITINGPROCESSING, CONFIRMED
    }

    public Good(String name, String brand, SubCategory subCategory, String details, HashMap<String, Object> categoryProperties, Seller seller, long price, int availableNumber) {
        this.goodId = goodsCount++;
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

    public ArrayList<SellerRelatedInfoAboutGood> getSellerRelatedInfoAboutGoods() {
        return sellerRelatedInfoAboutGoods;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void AddSeller(SellerRelatedInfoAboutGood newSellerRelatedInfo) {
        this.sellerRelatedInfoAboutGoods.add(newSellerRelatedInfo);
    }

    public void removeSeller(SellerRelatedInfoAboutGood sellerRelatedInfo) {
        this.sellerRelatedInfoAboutGoods.remove(sellerRelatedInfo);
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void setGoodStatus(GoodStatus goodStatus) {
        this.goodStatus = goodStatus;
    }

    public long getPriceBySeller(Seller seller) {
        return sellerRelatedInfoAboutGoods.stream().filter((info) -> info.getSeller().equals(seller)).map(SellerRelatedInfoAboutGood::getPrice).findAny().orElse(0L);
    }

    public void deleteGoodFromSellerList() {
        for (SellerRelatedInfoAboutGood sellerRelatedInfo : getSellerRelatedInfoAboutGoods()) {
            sellerRelatedInfo.getSeller().removeFromActiveGoods(this);
        }
    }

    @Override
    public String toString() {
        String sellerRelatedInfos = "";
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : sellerRelatedInfoAboutGoods) {
            sellerRelatedInfos = sellerRelatedInfos + relatedInfoAboutGood.toString() + "\n";
        }
        return "------------------------------------\n"
                + "name = " + name
                + "\ngoodStatus = " + goodStatus
                + "\nbrand = " + brand
                + "\naverage rate = " + averageRate
                + "\ncategory = " + subCategory.getParentCategory().getName()
                + "\nsubcategory = " + subCategory.getName() + "\n" + sellerRelatedInfos
                + "details =\n" + details
                + "\nmodification date = " + modificationDate.toString()
                + "\nseen number = " + seenNumber + "\n" +
                "------------------------------------\n";
    }
}

