package model.requests;

import model.Shop;
import model.productThings.Off;

import java.util.HashMap;

public class EditingOffRequest extends Request {
    private int offId;
    private HashMap<String, String> editedFields;

    public EditingOffRequest(int offId, HashMap<String, String> editedFields) {
        this.offId = offId;
        this.editedFields = editedFields;
    }

    @Override
    public void acceptRequest() {
        Off off= Shop.getInstance().findOffById(offId);
        for (String field : editedFields.keySet()) {
            if(field.equalsIgnoreCase("endDate")){
              //  off.setEndDate(SimpleDateFormat.parse(editedFields.get(field)); how to parse date?
            }else if (field.equalsIgnoreCase("maxDiscount"))
                off.setMaxDiscount(Long.parseLong(editedFields.get(field)));
            else if (field.equalsIgnoreCase("discountPercent"))
                off.setDiscountPercent(Integer.parseInt(editedFields.get(field)));
        }
        off.setOffStatus(Off.OffStatus.Accepted);
    }

    @Override
    public String toString() {
        return "EditingOffRequest :\n" +
                 Shop.getInstance().findOffById(offId) +
                "editedFields =\n" + editedFields ;
    }
}
