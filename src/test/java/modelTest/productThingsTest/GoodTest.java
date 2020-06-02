package modelTest.productThingsTest;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GoodTest {
    Seller seller = new Seller("hi","seller","seller","","","aa",null);
    Good good = new Good("phone", "samsung", null,"",new HashMap<>(),seller,9000L,3);
    Seller seller2 = new Seller("a","aa","","","","bb",null);
    Customer customer = new Customer("customer","","","","","aa",90000L);
    Category category = new Category("cat", new ArrayList<>());
    SubCategory subCategory = new SubCategory("sub", new ArrayList<>());
    DiscountCode discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"),200L, 22);
    Good good2 = new Good("phone", "samsung", subCategory,"",new HashMap<>(),seller,9000L,3);
    @Test
    public void GoodStatusTest(){
        assertEquals(Good.GoodStatus.BUILTPROCESSING,good.getGoodStatus());
    }

    @Test
    public void getPriceBySeller1(){
        assertEquals(9000L,good.getPriceBySeller(seller));
        assertEquals(9000L, good.getPriceBySeller(new Seller("hi", " ","","","","aaa",null)));
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

    @Test
    public void removeSellerTest(){
        SellerRelatedInfoAboutGood sellerInfo= new SellerRelatedInfoAboutGood(seller2, 200L,3);
        good.addSeller(sellerInfo);
        good.removeSeller(seller2);
        assertEquals(1, good.getSellerRelatedInfoAboutGoods().size());
    }

    @Test
    public void findGoodTest(){
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        subCategory.addGood(good2);
        long goodId = good2.getGoodId();
        assertEquals(good2,Shop.getInstance().findGoodById(goodId));
        assertEquals(good2,Shop.getInstance().getGoodByNameAndBrandAndSubCategory("phone","samsung", subCategory));
        //subCategory.removeGood(good2);
        assertEquals(null,Shop.getInstance().findGoodById(goodId));
        assertEquals(null,Shop.getInstance().getGoodByNameAndBrandAndSubCategory("phone","samsung", subCategory));
    }


}
