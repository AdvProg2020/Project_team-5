package model.requests;

import model.Shop;
import model.productThings.Good;

import java.util.HashMap;

public class EditingGoodRequest extends Request {
    private long goodId;
    private HashMap<String, String> editedFields;

    public EditingGoodRequest(long goodId, HashMap<String, String> editedFields) {
        this.goodId = goodId;
        this.editedFields = editedFields;
    }

    public long getGoodId() {
        return goodId;
    }

    @Override
    public String toString() {
        return "EdittingGoodRequest\n" +
                "goodId : " + goodId +
                "\nfieldsWithValuesForEditing : " + editedFields.toString();
    }

    @Override
    public void acceptRequest() {
        Good good = Shop.getInstance().findGoodById(goodId);
        for (String field : editedFields.keySet()) {
            //ToDo
            /*
            if (field.equalsIgnoreCase("name")) {

            } else if (field.equalsIgnoreCase("brand")) {

            } else if (field.equalsIgnoreCase("subCategory")) {

            } else if (field.equalsIgnoreCase("details")) {

            }

             */
        }
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
    }
}
