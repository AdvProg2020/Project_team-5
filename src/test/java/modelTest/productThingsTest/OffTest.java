package modelTest.productThingsTest;

import model.persons.Seller;
import model.productThings.Good;
import model.productThings.Off;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class OffTest {
    private static Off off;
    private static Good good;
    Seller seller;

    @Before
    public void initializeNecessaryValuesForTest() {
        seller = new Seller("amoo", "sadegh", "majid", "sadegh@gmail.com", "09360000000", "1234",null);
        good = new Good("sosise", "gooshtiran", null, "vanak sosise", null, seller, 50000L, 4);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        off = new Off(offGoods, LocalDate.parse("2020-05-06"), LocalDate.parse("2020-07-06"), 100000L, 25, seller);
    }

    @Test
    public void PriceAfterOffTest() {
        long expectedPrice = 37500L;
        long actualValue = off.getPriceAfterOff(good, seller);
        Assert.assertEquals(expectedPrice, actualValue);
    }

    @Test
    public void doesHaveThisProductTest(){
        Good good2 = new Good("kalbas", "gooshtiran", null, "vanak sosise",
                null, seller, 50000L, 4);
        Assert.assertEquals(true,off.doesHaveThisProduct(good));
        Assert.assertEquals(false,off.doesHaveThisProduct(null));
        Assert.assertEquals(false,off.doesHaveThisProduct(good2));
    }

    @Test
    public void isOffExpiredTest(){
        Assert.assertEquals(false,off.isOffExpired());
    }



}
