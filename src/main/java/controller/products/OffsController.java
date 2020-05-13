package controller.products;

import controller.MainController;
import exception.ProductWithThisIdNotExist;
import exception.ThisProductIsnotInAnyOff;
import model.Shop;
import model.productThings.Good;
import model.productThings.Off;

import java.time.LocalDate;

public class OffsController {

    public String showOffProducts() {
        String output = "";
        for (Off off : Shop.getInstance().getOffs()) {
            if ((off.getEndDate().isBefore(LocalDate.now()) || off.getStartDate().isAfter(LocalDate.now())))
                continue;
            if (off.getOffStatus().equals(Off.OffStatus.ACCEPTED)) {
                output += getOffDetail(off);
            }
        }
        return output;
    }

    private String getOffDetail(Off off) {
        String output = "";
        output += ("offs by : " + off.getSeller().getUsername() + "\n");
        int i = 1;
        for (Good good : off.getOffGoods()) {
            output += ((i++) + "-" + good.getName() + " :" + " id = " + good.getGoodId() + "\tprice before off : " + good.getPriceBySeller(off.getSeller())
                    + "\tprice after off :" + off.getPriceAfterOff(good, off.getSeller()) + "\n");
        }
        return output;
    }

    public void showAProduct(long id) throws ProductWithThisIdNotExist, ThisProductIsnotInAnyOff {
        if (Shop.getInstance().findGoodById(id) == null) {
            throw new ProductWithThisIdNotExist();
        } else if (!Shop.getInstance().getOffGoods().contains(Shop.getInstance().findGoodById(id))) {
            throw new ThisProductIsnotInAnyOff();
        } else {
            MainController.getInstance().getProductController().setGood(Shop.getInstance().findGoodById(id));
        }
    }
}
