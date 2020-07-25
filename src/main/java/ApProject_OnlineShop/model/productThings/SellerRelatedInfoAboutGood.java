package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "ProductAndSeller")
public class SellerRelatedInfoAboutGood {
    @Transient
    private static long sellerRelatedInfoAboutGoodCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductAndSellerId", nullable = false, unique = true)
    @Expose
    private long sellerRelatedInfoAboutGoodId;

    @ManyToOne
    @JoinColumn(name = "SellerId", nullable = false)
    @Expose
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    @Expose
    private Good good;

    @Column(name = "Price", nullable = false)
    @Expose
    private long price;

    @Column(name = "NumberOfAvailables", nullable = false)
    @Expose
    private int availableNumber;

    public SellerRelatedInfoAboutGood(Seller seller, Good good, long price, int availableNumber) {
        sellerRelatedInfoAboutGoodCount++;
        this.seller = seller;
        this.good = good;
        this.price = price;
        this.availableNumber = availableNumber;
    }

    public SellerRelatedInfoAboutGood() {
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public static void setSellerRelatedInfoAboutGoodCount(long sellerRelatedInfoAboutGoodCount) {
        SellerRelatedInfoAboutGood.sellerRelatedInfoAboutGoodCount = sellerRelatedInfoAboutGoodCount;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public long getSellerRelatedInfoAboutGoodId() {
        return sellerRelatedInfoAboutGoodId;
    }

    public long getPrice() {
        return price;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    public String getSellerUserName() {
        return this.seller.getUsername();
    }

    @Override
    public String toString() {
        return "seller = " + seller +
                "\tprice = " + price +
                "\tavailableNumber = " + availableNumber;
    }
}