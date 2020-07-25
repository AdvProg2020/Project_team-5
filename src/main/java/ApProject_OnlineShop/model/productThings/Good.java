package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Seller;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Product")
public class Good implements Serializable {
    @Transient
    private static long goodsCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", nullable = false, unique = true)
    @Expose
    private long goodId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ProductStatus", nullable = false)
    @Expose
    private GoodStatus goodStatus;

    @Column(name = "Name", nullable = false)
    @Expose
    private String name;

    @Column(name = "Brand")
    @Expose
    private String brand;

    @Column(name = "AverageRate", nullable = false)
    @Expose
    private double averageRate;

    @ManyToOne
    @JoinColumn(name = "SubCategoryId", referencedColumnName = "SubCategoryId")
    @Expose
    private SubCategory subCategory;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SellerRelatedInfoAboutGood> sellerRelatedInfoAboutGoods;

    @Column(name = "Description")
    @Expose
    private String details;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments;

    @Column(name = "SeenNumber", nullable = false)
    @Expose
    private int seenNumber;

    @Column(name = "ModificationDate")
    @Expose
    private LocalDate modificationDate;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @CollectionTable(name = "ValueOfEachCategoryProperty", joinColumns = @JoinColumn(name = "ProductID"))
    @Column(name = "Value")
    @MapKeyColumn(name = "Property", nullable = false)
    @Expose
    private Map<String, String> categoryProperties;

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
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = new SellerRelatedInfoAboutGood(seller,this, price, availableNumber);
        Shop.getInstance().addSellerRelatedInfoAboutGood(sellerRelatedInfoAboutGood);
        this.sellerRelatedInfoAboutGoods = new ArrayList<>();
        sellerRelatedInfoAboutGoods.add(sellerRelatedInfoAboutGood);
        this.comments = new ArrayList<>();
        this.modificationDate = LocalDate.now();
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

    synchronized public static long getGoodsCount() {
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

    public List<SellerRelatedInfoAboutGood> getSellerRelatedInfoAboutGoods() {
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

    public Map<String, String> getCategoryProperties() {
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

    public List<Comment> getComments() {
        return this.comments;
        /*ArrayList<Comment> allComments = new ArrayList<>();
        for (Long commentId : comments) {
            allComments.add(Shop.getInstance().getAllComments().get(commentId));
        }
        return allComments;*/
    }

    synchronized public static void setGoodsCount(long goodsCount) {
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
                if (off.getOffGoods().contains(this) && !off.getEndDate().isBefore(LocalDate.now()) && !off.getStartDate().isAfter(LocalDate.now()))
                    return relatedInfoAboutGood.getSeller();
            }
        }
        for (Off off : Shop.getInstance().getOffs()) {
            if (off.getOffGoods().contains(this) && !off.getEndDate().isBefore(LocalDate.now()) && !off.getStartDate().isAfter(LocalDate.now()))
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

    public String getSubCategoryNameString(){
        return subCategory.getName();
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

