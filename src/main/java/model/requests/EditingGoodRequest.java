package model.requests;

import model.Shop;
import model.persons.Seller;
import model.productThings.Good;
import model.productThings.SellerRelatedInfoAboutGood;

import java.util.HashMap;

public class EditingGoodRequest extends Request {
    private long goodId;
    private HashMap<String, String> editedFields;
    private Seller seller;

    public EditingGoodRequest(long goodId,Seller seller , HashMap<String, String> editedFields) {
        this.goodId = goodId;
        this.seller = seller;
        this.editedFields = editedFields;
    }

    public long getGoodId() {
        return goodId;
    }

    public Seller getSeller() {
        return seller;
    }

    @Override
    public String toString() {
        return "EditingGoodRequest\n" +
                "goodId : " + goodId +
                "\nfieldsWithValuesForEditing : " + editedFields.toString();
    }

    @Override
    public void acceptRequest() {
        Good good = Shop.getInstance().findGoodById(goodId);
        for (String field : editedFields.keySet()) {
            if (field.equalsIgnoreCase("name")) {
                good.setName(editedFields.get("name"));
            } else if (field.equalsIgnoreCase("brand")) {
                good.setBrand(editedFields.get("brand"));
            } else if (field.equalsIgnoreCase("subCategory")) {
                //TODO
            } else if (field.equalsIgnoreCase("details")) {
                good.setDetails(editedFields.get("details"));
            } else if (field.equalsIgnoreCase("price")) {
                SellerRelatedInfoAboutGood information = (SellerRelatedInfoAboutGood) good.getSellerRelatedInfoAboutGoods().stream().filter(info -> info.getSeller().equals(seller)).toArray()[0];
                information.setPrice(Long.parseLong(editedFields.get("price")));
            } else if (field.equalsIgnoreCase("availableNumber")) {
                SellerRelatedInfoAboutGood information = (SellerRelatedInfoAboutGood) good.getSellerRelatedInfoAboutGoods().stream().filter(info -> info.getSeller().equals(seller)).toArray()[0];
                information.setAvailableNumber(Integer.parseInt(editedFields.get("availableNumber")));
            }
            for (String subCategoryProperty : good.getCategoryProperties().keySet()) {
                if (field.equalsIgnoreCase(subCategoryProperty)) {
                    good.getCategoryProperties().replace(subCategoryProperty, editedFields.get(field));
                }
            }
        }
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
    }
}
