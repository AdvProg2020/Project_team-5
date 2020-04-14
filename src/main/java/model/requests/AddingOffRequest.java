package model.requests;

import model.Shop;
import model.productThings.Off;

public class AddingOffRequest extends Request {
    private Off off;

    public AddingOffRequest(Off off) {
        this.off = off;
    }

    @Override
    public void acceptRequest() {
        Shop.getInstance().addOff(off);
        off.getSeller().addOff(off);
        off.setOffStatus(Off.OffStatus.Accepted);
    }

    @Override
    public String toString() {
        return "AddingOffRequest :\n" + off.toString();
    }
}
