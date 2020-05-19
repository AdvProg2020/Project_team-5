package model.requests;

import database.Database;
import exception.FileCantBeSavedException;
import model.Shop;
import model.persons.Seller;
import model.productThings.Good;
import model.productThings.Off;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddingOffRequest extends Request {
    private List<Long> offGoods;
    private LocalDate startDate;
    private LocalDate endDate;
    private long maxDiscount;
    private int discountPercent;
    private String seller;

    public AddingOffRequest(List<Good> offGoods, LocalDate startDate, LocalDate endDate, long maxDiscount, int discountPercent, Seller seller) {
        this.offGoods = new ArrayList<>();
        for (Good good : offGoods) {
            this.offGoods.add(good.getGoodId());
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscount = maxDiscount;
        this.discountPercent = discountPercent;
        this.seller = seller.getUsername();
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        List<Good> offGoods2 = new ArrayList<>();
        for (Long id : this.offGoods) {
            offGoods2.add(Shop.getInstance().findGoodById(id));
        }
        Off off = new Off(offGoods2, startDate, endDate, maxDiscount, discountPercent, (Seller) Shop.getInstance().findUser(seller));
        Shop.getInstance().addOff(off);
        off.getSeller().addOff(off.getOffId());
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        Database.getInstance().saveItem(off);
        Database.getInstance().saveItem(off.getSeller());
    }

    @Override
    public String toString() {
        return "---------------------------\nAddingOffRequest :\n"
                + "request id = " + super.getRequestId() + "\n"
                + "id of off goods : " +offGoods + "\nstart date : "+ startDate
                + "\nend date :" +endDate + "\nmax discount amount :" + maxDiscount
                + "\n discount percent :" + discountPercent + "\nseller : "
                + seller + "\n---------------------------\n";
    }
}
