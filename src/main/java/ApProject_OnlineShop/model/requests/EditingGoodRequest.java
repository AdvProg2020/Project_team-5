package ApProject_OnlineShop.model.requests;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;

import java.io.IOException;
import java.util.HashMap;

public class EditingGoodRequest extends Request {
    private long goodId;
    private HashMap<String, String> editedFields;
    private String seller;

    public EditingGoodRequest(long goodId, Seller seller, HashMap<String, String> editedFields) {
        this.goodId = goodId;
        this.seller = seller.getUsername();
        this.editedFields = editedFields;
    }

    public long getGoodId() {
        return goodId;
    }

    public Seller getSeller() {
        return (Seller) Shop.getInstance().findUser(seller);
    }

    @Override
    public String toString() {
        return "Type: EditingGoodRequest\n" +
                "request id: " + super.getRequestId() +
                "\ngoodId: " + goodId +
                "\nfieldsWithValuesForEditing: " + editedFields.toString();
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Good good = Shop.getInstance().findGoodById(goodId);
        for (String field : editedFields.keySet()) {
            if (field.equalsIgnoreCase("details")) {
                good.setDetails(editedFields.get("details"));
            } else if (field.equalsIgnoreCase("price")) {
                SellerRelatedInfoAboutGood information = (SellerRelatedInfoAboutGood) good.getSellerRelatedInfoAboutGoods().stream().filter(info -> info.getSeller().equals(getSeller())).toArray()[0];
                information.setPrice(Long.parseLong(editedFields.get("price")));
                Database.getInstance().saveItem(information, goodId);
            } else if (field.equalsIgnoreCase("availableNumber")) {
                SellerRelatedInfoAboutGood information = (SellerRelatedInfoAboutGood) good.getSellerRelatedInfoAboutGoods().stream().filter(info -> info.getSeller().equals(getSeller())).toArray()[0];
                information.setAvailableNumber(Integer.parseInt(editedFields.get("availableNumber")));
                Database.getInstance().saveItem(information, goodId);
            }
            for (String subCategoryProperty : good.getCategoryProperties().keySet()) {
                if (field.equalsIgnoreCase(subCategoryProperty)) {
                    good.getCategoryProperties().replace(subCategoryProperty, editedFields.get(field));
                }
            }
        }
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Database.getInstance().saveItem(good);
    }
}
