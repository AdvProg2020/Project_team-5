package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;

import javax.persistence.*;

@Entity
@Table(name = "ProductAndSeller")
public class SellerRelatedInfoAboutGood {
    @Transient
    private static long sellerRelatedInfoAboutGoodCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductAndSellerId", nullable = false, unique = true)
    private long sellerRelatedInfoAboutGoodId;

    @ManyToOne
    @JoinColumn(name = "SellerId", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Good good;

    @Column(name = "Price", nullable = false)
    private long price;

    @Column(name = "NumberOfAvailables", nullable = false)
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

    @Override
    public String toString() {
        return "seller = " + seller +
                "\tprice = " + price +
                "\tavailableNumber = " + availableNumber;
    }
}