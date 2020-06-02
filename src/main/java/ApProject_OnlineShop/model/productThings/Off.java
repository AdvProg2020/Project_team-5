package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Off {
    private static long offsCount = 1;
    private long offId;
    private List<Long> offGoods;
    private OffStatus offStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private long maxDiscount;
    private int discountPercent;
    private String seller;

    public enum OffStatus {
        VALIDATING,
        ACCEPTED,
        EDITING
    }

    public Off(List<Good> offGoods, LocalDate startDate, LocalDate endDate, long maxDiscount, int discountPercent, Seller seller) {
        this.offId = offsCount++;
        this.offGoods = new ArrayList<>();
        for (Good offGood : offGoods) {
            this.offGoods.add(offGood.getGoodId());
        }
        this.offStatus = OffStatus.VALIDATING;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscount = maxDiscount;
        this.discountPercent = discountPercent;
        this.seller = seller.getUsername();
    }

    public long getOffId() {
        return offId;
    }

    public static long getOffsCount() {
        return offsCount;
    }

    public List<Good> getOffGoods() {
        List<Good> activeGoods = new ArrayList<>();
        for (Long offGood : this.offGoods) {
            activeGoods.add(Shop.getInstance().findGoodById(offGood));
        }
        return activeGoods;
    }

    public OffStatus getOffStatus() {
        return offStatus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getMaxDiscount() {
        return maxDiscount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(seller);
    }

    public void setOffStatus(OffStatus offStatus) {
        this.offStatus = offStatus;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public long getPriceAfterOff(Good good, Seller productSeller) {
        if (!productSeller.getUsername().equals(seller))
            return 0L;
        long price = this.getOffGoods().stream().filter(offGood -> offGood.equals(good))
                .map(offGood -> offGood.getPriceBySeller(getSeller())).findAny().orElse(0L);
        if (price * (discountPercent / (double) 100) > maxDiscount)
            return price - maxDiscount;
        else
            return (long) (price * (1 - discountPercent / (double) 100));
    }

    public void addGood(Good good) {
        offGoods.add(good.getGoodId());
    }

    public void removeGood(Good good) {
        offGoods.remove(good.getGoodId());
    }

    public boolean doesHaveThisProduct(Good good) {
        if (good == null) return false;
        return offGoods.contains(good.getGoodId());
    }

    public static void setOffsCount(long offsCount) {
        Off.offsCount = offsCount;
    }

    public String getBriefSummery() {
        return "off ID: " + offId + "\t off percent: " + discountPercent;
    }

    public boolean isOffExpired() {
        return LocalDate.now().isAfter(this.endDate);
    }

    @Override
    public String toString() {
        return String.format("Off Id : %d\nStart Date : %s\n" +
                        "End Date : %s\nMax Discount : %d\nDiscount Percent : %s\nSeller : %s", this.offId,
                this.startDate.toString(), this.endDate.toString(), this.maxDiscount, this.discountPercent, this.seller);
    }
}
