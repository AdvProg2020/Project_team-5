package model.requests;

import database.Database;
import exception.FileCantBeSavedException;
import model.Shop;
import model.productThings.Off;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class EditingOffRequest extends Request {
    private long offId;
    private HashMap<String, String> editedFields;

    public EditingOffRequest(long offId, HashMap<String, String> editedFields) {
        this.offId = offId;
        this.editedFields = editedFields;
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Off off = Shop.getInstance().findOffById(offId);
        for (String field : editedFields.keySet()) {
            if (field.equalsIgnoreCase("start date")) {
                off.setStartDate(LocalDate.parse(editedFields.get(field)));
            } else if (field.equalsIgnoreCase("end date")) {
                off.setEndDate(LocalDate.parse(editedFields.get(field)));
            } else if (field.equalsIgnoreCase("max discount")) {
                off.setMaxDiscount(Long.parseLong(editedFields.get(field)));
            } else if (field.equalsIgnoreCase("discount percent")) {
                off.setDiscountPercent(Integer.parseInt(editedFields.get(field)));
            } else if (field.equalsIgnoreCase("add good")) {
                off.addGood(Shop.getInstance().findGoodById(Long.parseLong(editedFields.get(field))));
            } else if (field.equalsIgnoreCase("remove good")) {
                off.removeGood(Shop.getInstance().findGoodById(Long.parseLong(editedFields.get(field))));
            }
        }
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        Database.getInstance().saveItem(off);
    }

    @Override
    public String toString() {
        return "EditingOffRequest :\n" +
                "request id = " + super.getRequestId() + "\n" +
                Shop.getInstance().findOffById(offId) +
                "editedFields =\n" + editedFields.toString();
    }
}
