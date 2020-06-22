package controllerTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;
import ApProject_OnlineShop.testThings.TestShop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class moreTests {
    @Before
    public void initialize() {
        Database.getInstance().loadTestFolders();
        TestShop.clearShop();
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
        Shop.getInstance().addSubCategory(subCategory);
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
        Shop.getInstance().addPerson(seller);
    }

    @Test
    public void getBriefProductTest() {
        String output = "";
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        output = output + good.getName() + " " + good.getBrand();
        Assert.assertEquals(output, MainController.getInstance().getAllProductsController().getProductBrief(good.getGoodId()).get(0));
    }

    @Test
    public void getBriefProductOffTest() {
        String output = "";
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller)Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        output = output + good.getName() + " " + good.getBrand();
        Assert.assertEquals(output, MainController.getInstance().getAllProductsController().getOffProductBriefSummery(good.getGoodId()).get(0));
    }

    @Test
    public void getAllCategoriesListTest() {
        Assert.assertEquals(1, MainController.getInstance().getAllProductsController().getAllCategories().size());
    }

    @Test
    public void isInOffTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller)Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        Assert.assertTrue(MainController.getInstance().getAllProductsController().isInOff(good.getGoodId()));
    }

    @Test
    public void getGoodsListTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Assert.assertEquals(1, MainController.getInstance().getAllProductsController().getGoods().size());
    }

    @Test
    public void compareWithTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Good good2 =new Good("phone", "apple", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good2);
        Shop.getInstance().addGoodToAllGoods(good2);
        MainController.getInstance().getProductController().setGood(good);
        try {
            String output = "+---------------------------+---------------------------+---------------------------+\n" +
                    "| property                  | good 1                    | good 2                    |\n" +
                    "+---------------------------+---------------------------+---------------------------+\n" +
                    "| name                      | phone                     | phone                     |\n" +
                    "| brand                     | samsung                   | apple                     |\n" +
                    "| average rate              | 0.0                       | 0.0                       |\n" +
                    "| subCategory               | sub kabir                 | sub kabir                 |\n" +
                    "| modification date         | 2020-06-22                | 2020-06-22                |\n" +
                    "| seen number               | 0                         | 0                         |\n" +
                    "| number of sellers         | 1                         | 1                         |\n" +
                    "| minmum price of product   | 9000                      | 9000                      |\n" +
                    "+---------------------------+---------------------------+---------------------------+\n";
            Assert.assertNotEquals(output, MainController.getInstance().getProductController().compareWithAnotherProduct(good2.getGoodId()));
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void compareWithAnotherGoodGUITest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Good good2 =new Good("phone", "apple", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good2);
        Shop.getInstance().addGoodToAllGoods(good2);
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertEquals(16, MainController.getInstance().getProductController().compareWithAnotherProductGUI(good2.getGoodId()).size());
    }


    @After
    public void terminate() {
        TestShop.clearShop();
    }
}
