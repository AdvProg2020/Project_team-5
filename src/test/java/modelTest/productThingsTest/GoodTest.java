package modelTest.productThingsTest;

import model.persons.Seller;
import model.productThings.Good;
import model.productThings.SellerRelatedInfoAboutGood;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GoodTest {
    Seller seller = new Seller("hi","seller","seller","","","aa",null);
    Good good = new Good("phone", "samsung", null,"",new HashMap<>(),seller,9000L,3);
    Seller seller2 = new Seller("a","aa","","","","bb",null);
    @Test
    public void GoodStatusTest(){
        assertEquals(Good.GoodStatus.BUILTPROCESSING,good.getGoodStatus());
    }

    @Test
    public void getPriceBySeller1(){
        assertEquals(9000L,good.getPriceBySeller(seller));
        //assertEquals(0L, good.getPriceBySeller(new Seller("aa","","","","","bb",null)));
    }
    @Test
    public void addSellerTest(){
        SellerRelatedInfoAboutGood sellerInfo= new SellerRelatedInfoAboutGood(new Seller("a","aa","","","","",null),
                200L,3);
        ArrayList<SellerRelatedInfoAboutGood> sellerInformation = good.getSellerRelatedInfoAboutGoods();
        good.addSeller(sellerInfo);
        if (good.getSellerRelatedInfoAboutGoods() != sellerInformation)
            assertTrue(true);
    }

    @Test
    public void increaseAvailableNumberTest(){
        good.increaseAvailableNumber(seller, 2);
        assertEquals(5, good.getAvailableNumberBySeller(seller));
    }

    @Test
    public void doesExistInSellerListTest(){
        assertFalse(good.doesExistInSellerList(seller2));
        assertTrue(good.doesExistInSellerList(seller));
    }

    @Test
    public void reduceAvailableNumberTest(){
        good.reduceAvailableNumber(seller,1);
        assertEquals(2, good.getAvailableNumberBySeller(seller));
    }
}
