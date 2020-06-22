package controllerTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.productExceptions.ProductNotFoundExceptionForSeller;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import ApProject_OnlineShop.testThings.TestShop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
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
        Customer customer = new Customer("customer", "", "", "", "", "aa", 90000L);
        Shop.getInstance().addPerson(customer);
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

    @Test
    public void getMainInfoTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertEquals(6, MainController.getInstance().getProductController().getMainInfo().size());
    }

    @Test
    public void getSellerInfoTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertNotNull(MainController.getInstance().getProductController().getSellersInfo().get(0));
    }

    @Test
    public void isInOffBySeller() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller)Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertTrue(MainController.getInstance().getProductController().isInOffBySeller((Seller) Shop.getInstance().findUser("hi")));
    }

    @Test
    public void addGoodToCartGUITest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGood(good);
        try {
            MainController.getInstance().getProductController().addGoodToCartGUI("hi");
            Assert.assertEquals(5, Shop.getInstance().getCart().size());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getNumberOfPerSellerTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGoodById(good.getGoodId());
        Assert.assertEquals(3, MainController.getInstance().getProductController().getAvailableNumberOfAProductByASeller(1));
    }

    @Test
    public void removeProductWithMultipleSellers() throws IOException, FileCantBeSavedException {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller)Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Company company=new Company("salam1","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi2", "seller", "seller", "", "", "aa",company);
        Shop.getInstance().addPerson(seller);
        SellerRelatedInfoAboutGood infoAboutGood = new SellerRelatedInfoAboutGood(seller, 30000L, 5);
        seller.addToActiveGoods(good.getGoodId());
        good.addSeller(infoAboutGood);
        MainController.getInstance().setCurrentPerson(seller);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, seller);
        Shop.getInstance().addOff(off);
        seller.addOff(off.getOffId());
        Shop.getInstance().addSellerRelatedInfoAboutGood(infoAboutGood);
        Database.getInstance().saveItem(good);
        Database.getInstance().saveItem(infoAboutGood, good.getGoodId());
        Database.getInstance().saveItem(seller);
        try {
            MainController.getInstance().getAccountAreaForSellerController().removeProduct(good.getGoodId());
        } catch (ProductNotFoundExceptionForSeller | FileCantBeDeletedException productNotFoundExceptionForSeller) {
            Assert.fail();
        }
        Assert.assertEquals(1, Shop.getInstance().findGoodById(good.getGoodId()).getSellerRelatedInfoAboutGoods().size());
    }

    @Test
    public void getSellerSalesLogTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForSellerController().getSalesLog().size());
    }

    @Test
    public void getBuyersOfProductTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller)Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        try {
            Assert.assertEquals(0, MainController.getInstance().getAccountAreaForSellerController().buyersOfProduct(good.getGoodId()).size());
        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
            Assert.fail();
        }
    }

    @Test
    public void getSortedLogsTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForSellerController().getSortedLogs(1).size());
    }

    @Test
    public void getSortedOffsTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller)Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(2);
        MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(3);
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(1).size());
    }

    @Test
    public void viewOffGUITest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller)Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertEquals(5, MainController.getInstance().getAccountAreaForSellerController().viewOffGUI(off.getOffId()).size());
    }

    @Test
    public void viewSellersProductTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller)Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        String output = "-------------------------\n" +
                "your products:\n" +
                "------------------------------------\n" +
                "GoodId = 0\n" +
                "name = phone\n" +
                "goodStatus = BUILTPROCESSING\n" +
                "brand = samsung\n" +
                "average rate = 0.0\n" +
                "category = aboots\n" +
                "subcategory = sub kabir\n" +
                "sellers = 1- seller = hi\tprice = 9000\tavailableNumber = 3\n" +
                "details =\n" +
                "\n" +
                "modification date = 2020-06-22\n" +
                "seen number = 0\n" +
                "------------------------------------";
        MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(2);
        MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(3);
        Assert.assertEquals(output, MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(1));
    }

    @Test
    public void isGoodInOffThisSellerTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller)Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller)Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController().isInOff(good.getGoodId()));
    }

    @Test
    public void viewSortedProductsThisSellerTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller)Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForSellerController().viewProducts(1).size());
    }

    @Test
    public void getGoodInCartInfoTest() {
        Good good=new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller)Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().getProductController().setGood(good);
        try {
            MainController.getInstance().getProductController().addGoodToCartGUI("hi");
            Assert.assertEquals(4, MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(good.getGoodId()).size());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getBriefSummeryOfOrdersTest() {
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForCustomerController().getBriefSummeryOfOrders().size());
    }

    @Test
    public void getSortedOrders() {
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForCustomerController().getSortedCustomerOrders(1).size());
    }

    @After
    public void terminate() {
        TestShop.clearShop();
    }
}
