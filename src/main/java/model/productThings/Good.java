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
    private String subCategory;
    private ArrayList<Long> sellerRelatedInfoAboutGoods = new ArrayList<>();
    private String details;
    private ArrayList<Long> comments;
    private int seenNumber;
    private LocalDate modificationDate;
    private HashMap<String, String> categoryProperties;

    public enum GoodStatus {
        BUILTPROCESSING, EDITINGPROCESSING, CONFIRMED, NOTAVAILABLE
    }

    public GoodStatus getGoodStatus() {
        return goodStatus;
    }

    public Good(String name, String brand, SubCategory subCategory, String details,
                HashMap<String, String> categoryProperties, Seller seller, long price, int availableNumber) {
        this.goodId = goodsCount++;
        this.name = name;
        this.brand = brand;
        this.subCategory = subCategory.getName();
        this.details = details;
        this.categoryProperties = categoryProperties;
        this.goodStatus = GoodStatus.BUILTPROCESSING;
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = new SellerRelatedInfoAboutGood(seller, price, availableNumber);
        Shop.getInstance().addSellerRelatedInfoAboutGood(sellerRelatedInfoAboutGood);
        sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood.getSellerRelatedInfoAboutGoodId());
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

    public long getPriceBySeller(Seller seller) {
        for (SellerRelatedInfoAboutGood sellerInfo : getSellerRelatedInfoAboutGoods()) {
            if (sellerInfo.getSeller().equals(seller))
                return sellerInfo.getPrice();
            if (sellerInfo.getSeller().getUsername().equals(seller.getUsername()))
                return sellerInfo.getPrice();
        }
        return 0L;
    }

    public ArrayList<SellerRelatedInfoAboutGood> getSellerRelatedInfoAboutGoods() {
        ArrayList<SellerRelatedInfoAboutGood> sellerRelatedInfoAboutGoods1=new ArrayList<>();
        for (Long infoAboutGoodId : this.sellerRelatedInfoAboutGoods) {
            sellerRelatedInfoAboutGoods1.add(Shop.getInstance().getAllSellerRelatedInfoAboutGood().get(infoAboutGoodId));
        }
        return sellerRelatedInfoAboutGoods1;
    }

    public SubCategory getSubCategory() {
        return Shop.getInstance().getAllSubCategories().get(subCategory);
    }

    public void addSeller(SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood) {
        this.sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood.getSellerRelatedInfoAboutGoodId());
    }

    public void removeSeller(Seller seller) {
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = null;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : getSellerRelatedInfoAboutGoods()) {
            if (relatedInfoAboutGood.getSeller().equals(seller))
                sellerRelatedInfoAboutGood = relatedInfoAboutGood;
        }
        if (sellerRelatedInfoAboutGood != null) this.sellerRelatedInfoAboutGoods.remove(sellerRelatedInfoAboutGood.getSellerRelatedInfoAboutGoodId());
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
        this.subCategory = subCategory.getName();
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public HashMap<String, String> getCategoryProperties() {
        return categoryProperties;
    }

    public void deleteGoodFromSellerList() {
        for (SellerRelatedInfoAboutGood sellerRelatedInfo : getSellerRelatedInfoAboutGoods()) {
            sellerRelatedInfo.getSeller().removeFromActiveGoods(this.getGoodId());
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

    public long getMinimumPrice() {
        long min = 10000000000000L;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : this.getSellerRelatedInfoAboutGoods()) {
            if (relatedInfoAboutGood.getPrice() < min)
                min = relatedInfoAboutGood.getPrice();
        }
        return min;
    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> allComments=new ArrayList<>();
        for (Long commentId : comments) {
            allComments.add(Shop.getInstance().getAllComments().get(commentId));
        }
        return allComments;
    }

    public static void setGoodsCount(long goodsCount) {
        Good.goodsCount = goodsCount;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment.getId());
    }

    public void reduceAvailableNumber(Seller seller, int reductionNumber) {
        for (SellerRelatedInfoAboutGood sellerInfo : getSellerRelatedInfoAboutGoods()) {
            if (sellerInfo.getSeller() == seller) {
                sellerInfo.setAvailableNumber(sellerInfo.getAvailableNumber() - reductionNumber);
                if (sellerInfo.getAvailableNumber() == 0)
                    goodStatus = GoodStatus.NOTAVAILABLE;
            }
        }
    }

    public boolean doesExistInSellerList(Seller seller) {
        for (SellerRelatedInfoAboutGood sellerInfo : getSellerRelatedInfoAboutGoods()) {
            if (sellerInfo.getSeller() == seller)
                return true;
        }
        return false;
    }

    public int getAvailableNumberBySeller(Seller seller) {
        for (SellerRelatedInfoAboutGood sellerInfo : getSellerRelatedInfoAboutGoods()) {
            if (sellerInfo.getSeller() == seller)
                return sellerInfo.getAvailableNumber();
        }
        return 0;
    }

    public void increaseAvailableNumber(Seller seller, int increaseNumber) {
        for (SellerRelatedInfoAboutGood sellerInfo : getSellerRelatedInfoAboutGoods()) {
            if (sellerInfo.getSeller() == seller)
                sellerInfo.setAvailableNumber(sellerInfo.getAvailableNumber() + increaseNumber);
        }
    }

    public Seller getSellerThatPutsThisGoodOnOff() {
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : getSellerRelatedInfoAboutGoods()) {
            for (Off off : relatedInfoAboutGood.getSeller().getActiveOffs()) {
                if (off.getOffGoods().contains(this))
                    return relatedInfoAboutGood.getSeller();
            }
        }
        for (Off off : Shop.getInstance().getOffs()) {
            if (off.getOffGoods().contains(this))
                return off.getSeller();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sellerRelatedInfo = new StringBuilder();
        int i = 1;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : getSellerRelatedInfoAboutGoods()) {
            sellerRelatedInfo.append(i++).append("- ").append(relatedInfoAboutGood.toString()).append("\n");
        }
        return "------------------------------------\n"
                + "GoodId = " + goodId
                + "\nname = " + name
                + "\ngoodStatus = " + goodStatus
                + "\nbrand = " + brand
                + "\naverage rate = " + averageRate
                + "\ncategory = " + getSubCategory().getParentCategory().getName()
                + "\nsubcategory = " + subCategory
                + "\nsellers = " + sellerRelatedInfo.toString()
                + "details =\n" + details
                + "\nmodification date = " + modificationDate.toString()
                + "\nseen number = " + seenNumber + "\n" +
                "------------------------------------";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Good) {
            Good good2 = (Good) obj;
            return this.getGoodId() == (good2.getGoodId());
        }
        return super.equals(obj);
    }
}

