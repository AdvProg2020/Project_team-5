package modelTest.productThingsTest;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ApProject_OnlineShop.testThings.TestShop;

import java.time.LocalDate;
import java.util.ArrayList;

public class OffTest {
    private static Off off;
    private static Good good;
    Seller seller;

    @Before
    public void initializeNecessaryValuesForTest() {
        Database.getInstance().loadTestFolders();
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("aboots",details);
        Shop.getInstance().addCategory(category);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub kabir",details2);
        category.addSubCategory(subCategory);
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        seller = new Seller("amoo", "sadegh", "majid", "sadegh@gmail.com", "09360000000", "1234",company);
        good = new Good("sosise", "gooshtiran", subCategory, "vanak sosise", null, seller, 50000L, 4);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        off = new Off(offGoods, LocalDate.parse("2020-05-06"), LocalDate.parse("2020-07-06"), 100000L, 25, seller);
        Shop.getInstance().addOff(off);
        Shop.getInstance().addSubCategory(subCategory);
        Shop.getInstance().addCategory(category);
        Shop.getInstance().addPerson(seller);
        Shop.getInstance().addGoodToAllGoods(good);
    }

    @Test
    public void PriceAfterOffTest() {
        long expectedPrice = 37500L;
        long actualValue = off.getPriceAfterOff(good, seller);
        Assert.assertEquals(expectedPrice, actualValue);
    }

    @Test
    public void doesHaveThisProductTest(){
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("aboots",details);
        Shop.getInstance().addCategory(category);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub kabir",details2);
        category.addSubCategory(subCategory);
        Good good2 = new Good("kalbas", "gooshtiran", subCategory, "vanak sosise",
                null, seller, 50000L, 4);
        Assert.assertEquals(true,off.doesHaveThisProduct(good));
        Assert.assertEquals(false,off.doesHaveThisProduct(null));
        Assert.assertEquals(false,off.doesHaveThisProduct(good2));
    }

    @Test
    public void isOffExpiredTest(){
        Assert.assertEquals(false,off.isOffExpired());
    }

    @After
    public void delete(){
        TestShop.clearShop();
    }
}
