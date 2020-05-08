package model.productThings;

import model.Shop;
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
        BUILTPROCESSING, EDITINGPROCESSING, CONFIRMED, NOTAVAILABLE
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

    public static long getGoodsCount() {
        return goodsCount;
    }

    public static void setGoodsCount(long goodsCount) {
        Good.goodsCount = goodsCount;
    }

    public ArrayList<SellerRelatedInfoAboutGood> getSellerRelatedInfoAboutGoods() {
        return sellerRelatedInfoAboutGoods;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void addSeller(SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood) {
        this.sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood);
    }

    public void removeSeller(Seller seller) {
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = null;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : sellerRelatedInfoAboutGoods) {
            if (relatedInfoAboutGood.getSeller().equals(seller))
                sellerRelatedInfoAboutGood = relatedInfoAboutGood;
        }
        if (sellerRelatedInfoAboutGood != null) this.sellerRelatedInfoAboutGoods.remove(sellerRelatedInfoAboutGood);
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void updateRate() {
        ArrayList<Rate> rates = Shop.getInstance().getRatesOfAGood(this);
        long someRates = 0;
        for (Rate rate : rates) {
            someRates += rate.getRate();
        }
        this.setAverageRate((double) someRates / rates.size());
    }

    public void setGoodStatus(GoodStatus goodStatus) {
        this.goodStatus = goodStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getPriceBySeller(Seller seller) {
        return sellerRelatedInfoAboutGoods.stream()
                .filter((info) -> info.getSeller().equals(seller))
                .map(SellerRelatedInfoAboutGood::getPrice)
                .findAny().orElse(0L);
    }

    public HashMap<String, Object> getCategoryProperties() {
        return categoryProperties;
    }

    public void deleteGoodFromSellerList() {
        for (SellerRelatedInfoAboutGood sellerRelatedInfo : getSellerRelatedInfoAboutGoods()) {
            sellerRelatedInfo.getSeller().removeFromActiveGoods(this);
        }
    }

    public int getSeenNumber() {
        return seenNumber;
    }

    public void setSeenNumber(int seenNumber) {
        this.seenNumber = seenNumber;
    }

    public String getDetails() {
        return details;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public long getMinimumPrice(){
        long min=10000000000000l;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : this.getSellerRelatedInfoAboutGoods()) {
            if(relatedInfoAboutGood.getPrice()<min)
                min=relatedInfoAboutGood.getPrice();
        }
        return min;
    }

    @Override
    public String toString() {
        StringBuilder sellerRelatedInfos = new StringBuilder();
        int i = 1;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : sellerRelatedInfoAboutGoods) {
            sellerRelatedInfos.append(i++).append("- ").append(relatedInfoAboutGood.toString()).append("\n");
        }
        return "------------------------------------\n"
                + "GoodId = " + goodId
                + "\nname = " + name
                + "\ngoodStatus = " + goodStatus
                + "\nbrand = " + brand
                + "\naverage rate = " + averageRate
                + "\ncategory = " + subCategory.getParentCategory().getName()
                + "\nsubcategory = " + subCategory.getName()
                + "\nsellers = " + sellerRelatedInfos.toString()
                + "details =\n" + details
                + "\nmodification date = " + modificationDate.toString()
                + "\nseen number = " + seenNumber + "\n" +
                "------------------------------------";
    }


}

