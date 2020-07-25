package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GoodInCart")
public class GoodInCart implements Serializable {
    @Transient
    private static long goodInCartCounter = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GoodInCartId", nullable = false, unique = true)
    private long goodInCartId;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductID", nullable = false)
    private Good good;

    @ManyToOne
    @JoinColumn(name = "SellerId", referencedColumnName = "PersonId", nullable = false)
    private Seller seller;

    @Column(name = "numberOfProducts", nullable = false)
    @Expose
    private int number;

    public GoodInCart(Good good, Seller seller, int number) {
        if (seller == null)
            seller = good.getSellerRelatedInfoAboutGoods().get(0).getSeller();
        this.good = good;
        this.seller = seller;
        this.number = number;
        goodInCartCounter++;
    }

    public GoodInCart() {
    }

    public long getFinalPrice() {
        return Shop.getInstance().getFinalPriceOfAGood(getGood(), getSeller()) * number;
    }

    public static void setGoodInCartCounter(long goodInCartCounter) {
        GoodInCart.goodInCartCounter = goodInCartCounter;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Good getGood() {
        return this.good;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public int getNumber() {
        return number;
    }

    public long getGoodInCartId() {
        return goodInCartId;
    }

    public void setGoodInCartId(long goodInCartId) {
        this.goodInCartId = goodInCartId;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        String toString = "name : " + getGood().getName() + "\tbrand : " + getGood().getBrand();
        if (Shop.getInstance().getFinalPriceOfAGood(getGood(), getSeller()) != getGood().getPriceBySeller(getSeller()))
            toString += ("\tprice before off : " + getGood().getPriceBySeller(getSeller())
                    + "\tprice after off : " + Shop.getInstance().getFinalPriceOfAGood(getGood(), getSeller()));
        else
            toString += (("\tprice : " + getGood().getPriceBySeller(getSeller())));
        toString += ("\tnumber :" + getNumber() + "\tseller : " + getSeller().getFirstName() + " " + getSeller().getLastName());
        return toString;
    }

    public String getBriefString(){
        return  "name:  " + getGood().getName() + "     number:  " + getNumber() + "    final price:  " + getFinalPrice();
    }
}