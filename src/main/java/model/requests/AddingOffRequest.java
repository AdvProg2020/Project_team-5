package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.productThings.Off;

import java.io.IOException;

public class AddingOffRequest extends Request {
    private Off off;

    public AddingOffRequest(Off off) {
        this.off = off;
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Shop.getInstance().addOff(off);
        off.getSeller().addOff(off.getOffId());
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        //Database.getInstance().saveItem(off);
      //  Database.getInstance().saveItem(off.getSeller());
    }

    @Override
    public String toString() {
        return "AddingOffRequest :\n"
                + "request id = " + super.getRequestId() + "\n"
                + off.toString();
    }
}
