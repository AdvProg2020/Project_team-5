package ApProject_OnlineShop.model.requests;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        off.getSeller().addOff(off);
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        Database.getInstance().saveItem(off);
        Database.getInstance().saveItem(off.getSeller());
    }

    @Override
    public String toString() {
        return "Type: Adding Off Request\n"
                + "request id: " + super.getRequestId() + "\n"
                + "id of off goods: " +offGoods + "\nstart date: "+ startDate
                + "\nend date:" +endDate + "\nmax discount amount: " + maxDiscount
                + "\ndiscount percent: " + discountPercent + "\nseller: " + seller;
    }
}
