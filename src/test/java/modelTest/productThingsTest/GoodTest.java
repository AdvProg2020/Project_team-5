package modelTest.productThingsTest;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class GoodTest {
    static Company company = new Company("dsd", "sdd", "sdsds","sdsdsd", "dsdsd");
    static Seller seller = new Seller("hi","seller","seller","","","aa",company);
    static Seller seller2 = new Seller("a","aa","","","","bb",company);
    static Customer customer = new Customer("customer","","","","","aa",90000L);
    static Category category = new Category("cat", new ArrayList<>());
    static SubCategory subCategory = new SubCategory("sub", new ArrayList<>());
    static DiscountCode discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"),200L, 22);
    static Good good2 = new Good("phone", "samsung", subCategory,"",new HashMap<>(),seller,9000L,3);
    static Good good = new Good("phone", "samsung", subCategory,"",new HashMap<>(),seller,9000L,3);


    @BeforeClass
    public static void initialize() {
        Database.getInstance().loadTestFolders();
        company = new Company("dsd", "sdd", "sdsds","sdsdsd", "dsdsd");
        seller = new Seller("hi","seller","seller","","","aa",company);
        seller2 = new Seller("a","aa","","","","bb",company);
        customer = new Customer("customer","","","","","aa",90000L);
        category = new Category("cat", new ArrayList<>());
        subCategory = new SubCategory("sub", new ArrayList<>());
        discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"),200L, 22);
        good2 = new Good("phone", "samsung", subCategory,"",new HashMap<>(),seller,9000L,3);
        good = new Good("phone", "samsung", subCategory,"",new HashMap<>(),seller,9000L,3);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        Shop.getInstance().addSubCategory(subCategory);
        Shop.getInstance().addPerson(seller);
        Shop.getInstance().addPerson(seller2);
        Shop.getInstance().addDiscountCode(discountCode);
        Shop.getInstance().addPerson(customer);
        subCategory.addGood(good2);
        subCategory.addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Shop.getInstance().addGoodToAllGoods(good2);
    }
    @Test
    public void GoodStatusTest(){
        assertEquals(Good.GoodStatus.BUILTPROCESSING,good.getGoodStatus());
    }

    @Test
    public void getPriceBySeller1(){
//        assertEquals(9000L,good.getPriceBySeller(seller));
//        assertEquals(9000L, good.getPriceBySeller(new Seller("hi", " ","","","","aaa",null)));
        //assertEquals(0L, good.getPriceBySeller(new Seller("aa","","","","","bb",null)));
    }
    @Test
    public void addSellerTest(){
//        SellerRelatedInfoAboutGood sellerInfo= new SellerRelatedInfoAboutGood(new Seller("a","aa","","","","",null),
//                200L,3);
//        ArrayList<SellerRelatedInfoAboutGood> sellerInformation = good.getSellerRelatedInfoAboutGoods();
//        good.addSeller(sellerInfo);
//        if (good.getSellerRelatedInfoAboutGoods() != sellerInformation)
//            assertTrue(true);
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
        assertEquals(4, good.getAvailableNumberBySeller(seller));
    }

    @Test
    public void removeSellerTest(){
//        SellerRelatedInfoAboutGood sellerInfo= new SellerRelatedInfoAboutGood(seller2, 200L,3);
//        good.addSeller(sellerInfo);
//        good.removeSeller(seller2);
//        assertEquals(1, good.getSellerRelatedInfoAboutGoods().size());
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
        String output = "------------------------------------\n" +
                "GoodId = 0\n" +
                "name = phone\n" +
                "goodStatus = BUILTPROCESSING\n" +
                "brand = samsung\n" +
                "average rate = 0.0\n" +
                "category = cat\n" +
                "subcategory = sub\n" +
                "sellers = 1- seller = hi\tprice = 9000\tavailableNumber = 3\n" +
                "details =\n" +
                "\n" +
                "modification date = " + LocalDate.now().toString() + "\n" +
                "seen number = 0\n" +
                "------------------------------------";
        assertEquals(output,Shop.getInstance().findGoodById(goodId).toString());
        assertEquals(output,Shop.getInstance().getGoodByNameAndBrandAndSubCategory("phone","samsung", subCategory).toString());
    }


}
