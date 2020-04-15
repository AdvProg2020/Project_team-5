package model.productThings;

import model.persons.Seller;

import java.time.LocalDate;
import java.util.ArrayList;

public class Off {
    private static long offsCount = 1;
    private long offId;
    private ArrayList<Good> offGoods;
    private OffStatus offStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private long maxDiscount;
    private int discountPercent;
    private Seller seller;

    public enum OffStatus {
        VALIDATING,
        EDITING,
        ACCEPTED
    }

    public Off(ArrayList<Good> offGoods, LocalDate startDate, LocalDate endDate, long maxDiscount, int discountPercent, Seller seller) {
        this.offId = offsCount++;
        this.offGoods = offGoods;
        this.offStatus = OffStatus.VALIDATING;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscount = maxDiscount;
        this.discountPercent = discountPercent;
        this.seller = seller;
    }

    public long getOffId() {
        return offId;
    }

    public ArrayList<Good> getOffGoods() {
        return offGoods;
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
        return seller;
    }

    public void setOffGoods(ArrayList<Good> offGoods) {
        this.offGoods = offGoods;
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

    public long getPriceAfterOff(Good good) {
        return this.offGoods.stream().filter(offGood -> offGood.equals(good)).map(offGood -> (long) (offGood.getPriceBySeller(seller)
                * (1 - discountPercent / (double) 100))).findAny().orElse(0L);
    }

    public void addGood(Good good){
        offGoods.add(good);
    }

    public void removeGood(Good good){
        offGoods.remove(good);
    }

    @Override
    public String toString() {
        return String.format("Off Id : %d\nStart Date : %s\n" +
                        "End Date : %s\nMax Discount : %d\nDiscount Percent : %s\nSeller : %s", this.offId,
                this.startDate.toString(), this.endDate.toString(), this.maxDiscount, this.discountPercent, this.seller.getUsername());
    }
}
