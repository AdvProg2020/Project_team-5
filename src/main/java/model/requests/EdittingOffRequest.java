package model.requests;

import model.Shop;
import model.productThings.Off;
import model.*;

import java.util.HashMap;

public class EdittingOffRequest extends Request {
    private int offId;
    private HashMap<String, String> edittedFields;

    public EdittingOffRequest(int offId, HashMap<String, String> edittedFields) {
        this.offId = offId;
        this.edittedFields = edittedFields;
    }

    @Override
    public void acceptRequest() {
        //ToDo after shop class created
      /*  Off off= Shop.getInstance().findOffById(offId);
        for (String field : edittedFields.keySet()) {
            if(field.equalsIgnoreCase("endDate")){
              //  off.setEndDate(SimpleDateFormat.parse(edittedFields.get(field)); how to parse date?
            }else if (field.equalsIgnoreCase("maxDiscount"))
                off.setMaxDiscount(Long.parseLong(edittedFields.get(field)));
            else if (field.equalsIgnoreCase("discountPercent"))
                off.setDiscountPercent(Integer.parseInt(edittedFields.get(field)));
        }
        off.setOffStatus(Off.OffStatus.Accepted); */
    }

    @Override
    public String toString() {
        //ToDo after shop class created
        return "EdittingOffRequest :\n" +
                 offId +
                ", edittedFields=" + edittedFields +
                '}';
    }
}
