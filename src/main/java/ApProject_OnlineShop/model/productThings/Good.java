package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Seller;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "Product")
public class Good implements Serializable {
    @Transient
    private static long goodsCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", nullable = false, unique = true)
    private long goodId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ProductStatus", nullable = false)
    private GoodStatus goodStatus;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "AverageRate", nullable = false)
    private double averageRate;

    @ManyToOne
    @JoinColumn(name = "SubCategoryId", referencedColumnName = "SubCategoryId")
    private SubCategory subCategory;

    private ArrayList<SellerRelatedInfoAboutGood> sellerRelatedInfoAboutGoods;

    @Column(name = "Description")
    private String details;

    private ArrayList<Comment> comments;

    @Column(name = "SeenNumber", nullable = false)
    private int seenNumber;

    @Column(name = "ModificationDate")
    private LocalDateTime modificationDate;

    private HashMap<String, String> categoryProperties;

    public enum GoodStatus {
        BUILTPROCESSING, CONFIRMED, NOTAVAILABLE,
        EDITINGPROCESSING
    }

    public GoodStatus getGoodStatus() {
        return goodStatus;
    }

    public Good(String name, String brand, SubCategory subCategory, String details,
                HashMap<String, String> categoryProperties, Seller seller, long price, int availableNumber) {
        this.name = name;
        this.brand = brand;
        this.subCategory = subCategory;
        this.details = details;
        this.categoryProperties = categoryProperties;
        this.goodStatus = GoodStatus.BUILTPROCESSING;
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = new SellerRelatedInfoAboutGood(seller, price, availableNumber);
        Shop.getInstance().addSellerRelatedInfoAboutGood(sellerRelatedInfoAboutGood);
        sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood);
        this.sellerRelatedInfoAboutGoods = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.modificationDate = LocalDateTime.now();
    }

    public Good() {
        this.sellerRelatedInfoAboutGoods = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
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
        return this.sellerRelatedInfoAboutGoods;
        /*ArrayList<SellerRelatedInfoAboutGood> sellerRelatedInfoAboutGoods1 = new ArrayList<>();
        for (Long infoAboutGoodId : this.sellerRelatedInfoAboutGoods) {
            sellerRelatedInfoAboutGoods1.add(Shop.getInstance().getAllSellerRelatedInfoAboutGood().get(infoAboutGoodId));
        }
        return sellerRelatedInfoAboutGoods1;*/
    }

    public SubCategory getSubCategory() {
        return this.subCategory;
    }

    public void addSeller(SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood) {
        this.sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood);
        //this.sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood.getSellerRelatedInfoAboutGoodId());
    }

    public void removeSeller(Seller seller) {
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = null;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : getSellerRelatedInfoAboutGoods()) {
            if (relatedInfoAboutGood.getSeller().equals(seller))
                sellerRelatedInfoAboutGood = relatedInfoAboutGood;
        }
        if (sellerRelatedInfoAboutGood != null)
            this.sellerRelatedInfoAboutGoods.remove(sellerRelatedInfoAboutGood);
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

    public HashMap<String, String> getCategoryProperties() {
        return categoryProperties;
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

    public LocalDateTime getModificationDate() {
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
        return this.comments;
        /*ArrayList<Comment> allComments = new ArrayList<>();
        for (Long commentId : comments) {
            allComments.add(Shop.getInstance().getAllComments().get(commentId));
        }
        return allComments;*/
    }

    public static void setGoodsCount(long goodsCount) {
        Good.goodsCount = goodsCount;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        //this.comments.add(comment.getId());
    }

    public void reduceAvailableNumber(Seller seller, int reductionNumber) {
        int goodNumber =0 ;
        for (SellerRelatedInfoAboutGood sellerInfo : getSellerRelatedInfoAboutGoods()) {
            if (sellerInfo.getSeller() == seller) {
                sellerInfo.setAvailableNumber(sellerInfo.getAvailableNumber() - reductionNumber);
            }
            goodNumber += sellerInfo.getAvailableNumber();
        }
        if (goodNumber == 0)
            goodStatus = GoodStatus.NOTAVAILABLE;
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

    public Off getThisGoodOff(){
        for (Off off : Shop.getInstance().getOffs()) {
            if (off.getOffGoods().contains(this))
                return off;
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

