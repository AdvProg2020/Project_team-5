package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Off")
public class Off implements Serializable {
    @Transient
    private static long offsCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OffID")
    private long offId;

    private List<Good> offGoods;

    @Enumerated(EnumType.STRING)
    @Column(name = "OffStatus", nullable = false)
    private OffStatus offStatus;

    @Column(name = "StartFrom", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EndTo", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "MaxDiscount", nullable = false)
    private long maxDiscount;

    @Column(name = "DiscountPercent", nullable = false)
    private int discountPercent;

    @ManyToOne
    @JoinColumn(name = "SellerId", referencedColumnName = "PersonId")
    private Seller seller;

    public enum OffStatus {
        VALIDATING,
        ACCEPTED,
        EDITING
    }

    public Off(List<Good> offGoods, LocalDateTime startDate, LocalDateTime endDate, long maxDiscount, int discountPercent, Seller seller) {
        offsCount++;
        this.offGoods = offGoods;
        this.offStatus = OffStatus.VALIDATING;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscount = maxDiscount;
        this.discountPercent = discountPercent;
        this.seller = seller;
    }

    public Off() {
        this.offGoods = new ArrayList<>();
    }

    public long getOffId() {
        return offId;
    }

    public static long getOffsCount() {
        return offsCount;
    }

    public List<Good> getOffGoods() {
        return this.offGoods;
        /*List<Good> activeGoods = new ArrayList<>();
        for (Long offGood : this.offGoods) {
            activeGoods.add(Shop.getInstance().findGoodById(offGood));
        }
        return activeGoods;*/
    }

    public OffStatus getOffStatus() {
        return offStatus;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public long getMaxDiscount() {
        return maxDiscount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public void setOffStatus(OffStatus offStatus) {
        this.offStatus = offStatus;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public long getPriceAfterOff(Good good, Seller productSeller) {
        if (!productSeller.getUsername().equals(seller.getUsername()))
            return 0L;
        long price = this.getOffGoods().stream().filter(offGood -> offGood.equals(good))
                .map(offGood -> offGood.getPriceBySeller(getSeller())).findAny().orElse(0L);
        if (price * (discountPercent / (double) 100) > maxDiscount)
            return price - maxDiscount;
        else
            return (long) (price * (1 - discountPercent / (double) 100));
    }

    public void addGood(Good good) {
        this.offGoods.add(good);
        //offGoods.add(good.getGoodId());
    }

    public void removeGood(Good good) {
        offGoods.remove(good);
        //offGoods.remove(good.getGoodId());
    }

    public boolean doesHaveThisProduct(Good good) {
        if (good == null) return false;
        return offGoods.contains(good);
        //return offGoods.contains(good.getGoodId());
    }

    public static void setOffsCount(long offsCount) {
        Off.offsCount = offsCount;
    }

    public String getBriefSummery() {
        return "off ID: " + offId + "\t off percent: " + discountPercent;
    }

    public boolean isOffExpired() {
        return LocalDateTime.now().isAfter(this.endDate);
    }

    public void setOffId(long offId) {
        this.offId = offId;
    }

    public void setOffGoods(List<Good> offGoods) {
        this.offGoods = offGoods;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return String.format("Off Id: %d\nStart Date: %s\n" +
                        "End Date: %s\nMax Discount: %d\nDiscount Percent: %s\nSeller: %s", this.offId,
                this.startDate.toString(), this.endDate.toString(), this.maxDiscount, this.discountPercent, this.seller);
    }
}
