package controllerTest.controllerAccountAreaTest;

import model.productThings.Good;

import java.util.ArrayList;
import java.util.HashMap;

public class SortingTest {
    public ArrayList<Good> getArrayOfGoods(){
        ArrayList<Good> goods = new ArrayList<>();
        Good good1 = new Good("laptop", "app", null, "", new HashMap<>(), null, 100, 2);
        good1.setAverageRate(4.7);
        good1.setSeenNumber(2);
        Good good2 = new Good("phone", "app", null, "", new HashMap<>(), null, 400, 2);
        good2.setAverageRate(8.1);
        good2.setSeenNumber(4);
        Good good3 = new Good("headphone", "sam", null, "", new HashMap<>(), null, 500, 4);
        good3.setAverageRate(5.7);
        good3.setSeenNumber(1);
        Good good4 = new Good("laptop", "app", null, "", new HashMap<>(), null, 200, 3);
        good4.setAverageRate(2.6);
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        return goods;
    }
}
