package model.productThings;

import model.persons.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Off {
    private long offId;
    private ArrayList<Good> offGoods;
    private OffStatus offStatus;
    private Date startDate;
    private Date endDate;
    private long maxDiscount;
    private int discountPercent;
    private Seller seller;

    public enum OffStatus {
        ValidationProgress,
        EditingProcess,
        Accepted,
        OutOfDate,
        Rejected
    }

    public Off(ArrayList<Good> offGoods,Date startDate, Date endDate, long maxDiscount, int discountPercent, Seller seller) {
        this.offGoods = offGoods;
        this.offStatus = OffStatus.ValidationProgress;
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public long getPriceAfterOff(Good good) {
        return this.offGoods.stream().filter(offGood -> offGood.equals(good)).map(offGood -> (long)(offGood.getPriceBySeller(seller) * (1 - discountPercent/(double)100))).findAny().orElse(0L);
    }

    @Override
    public String toString() {
        return String.format("Off Id : %d\nStart Date : %s\n" +
                        "End Date : %s\nMax Discount : %d\nDiscount Percent : %s\nSeller : %s", this.offId,
                this.startDate.toString(), this.endDate.toString(), this.maxDiscount, this.discountPercent, this.seller.getUsername());
    }
}
