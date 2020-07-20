package controllerTest.controllerAccountAreaTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.controller.accountArea.AccountAreaForSellerController;
import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.OffNotFoundException;
import ApProject_OnlineShop.exception.productExceptions.ProductNotFoundExceptionForSeller;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.AddingOffRequest;
import ApProject_OnlineShop.model.requests.EditingGoodRequest;
import ApProject_OnlineShop.model.requests.EditingOffRequest;
import ApProject_OnlineShop.model.requests.Request;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ApProject_OnlineShop.testThings.TestShop;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountAreaForSellerTest {
    Company company=new Company("salam","asfs","asdasd","addasd","999");
    Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
    Customer customer = new Customer("customer", "", "", "", "", "aa", 90000L);
    Category category = new Category("cat", new ArrayList<>());
    SubCategory subCategory = new SubCategory("sub", new ArrayList<>());
    DiscountCode discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"), 2000L, 20);
    Good good = new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
    AccountAreaForSellerController controller = MainController.getInstance().getAccountAreaForSellerController();
    Good good2 = new Good("coat", "Gucci", subCategory, "", new HashMap<>(), seller, 2000L, 5);

    @Test
    public void checkValidDateTest() {
//        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
//                .checkValidDate("2020-11-08", 1, "2020-11-04"));
//        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
//                .checkValidDate("2020-12-08", 0, ""));
//        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
//                .checkValidDate("2020-14-08", 0, ""));
//        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
//                .checkValidDate("2020-10-37", 0, ""));
//        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
//                .checkValidDate("2019-12-08", 0, ""));
//        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
//                .checkValidDate("2021-10-08", 1, "2021-11-08"));
    }

    @Before
    public void initializing() {
        Database.getInstance().loadTestFolders();
        Shop.getInstance().addPerson(seller);
        MainController.getInstance().setCurrentPerson(seller);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        subCategory.addGood(good);
        Shop.getInstance().addPerson(seller);
        Shop.getInstance().addGoodToAllGoods(good);
        Shop.getInstance().addCategory(category);
        Shop.getInstance().addSubCategory(subCategory);
        seller.addToActiveGoods(good.getGoodId());
    }


    @Test
    public void addAndRemoveGood() {
        try {
//            controller.addProduct(makeArrayListForGoodCreation(), new HashMap<>());
            assertEquals(1, Shop.getInstance().getAllRequest().size());
            Shop.getInstance().getAllRequest().get(0).acceptRequest();
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        try {
//            controller.editProduct("price","1000",seller.getActiveGoods().get(0).getGoodId());
            Request editReq = null;
            for (Request request : Shop.getInstance().getAllRequest()) {
                if (request instanceof EditingGoodRequest){
                    request.acceptRequest();
                    editReq = request;
                }
            }
            Shop.getInstance().removeRequest(editReq);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(1000,seller.getActiveGoods().get(0).getPriceBySeller(seller));
        assertEquals(2, seller.getActiveGoods().size());
//        if (controller.checkValidProductId(seller.getActiveGoods().get(0).getGoodId())) {
//            try {
////                controller.removeProduct(seller.getActiveGoods().get(0).getGoodId());
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            assertEquals(1, seller.getActiveGoods().size());
//        }
    }


    public ArrayList<String> makeArrayListForGoodCreation() {
        ArrayList<String> info = new ArrayList<>();
        info.add("name");
        info.add("brand");
        info.add("350");
        info.add("3");
        info.add("");
        info.add("sub");
        return info;
    }

    @Test
    public void showGood(){
//        try {
//            assertEquals(good.toString(),controller.viewProduct(good.getGoodId()));
//        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
//            productNotFoundExceptionForSeller.printStackTrace();
//        }
//        try {
//            controller.viewProduct(4);
//        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
//            assertTrue(true);
//        }
    }

    @Test
    public void testCompany(){
        Company company = new Company("A-Z", "AtoZ.com", "09876543241","80898","shiraz");
        Seller seller1 = new Seller("abc","","","", "", "aa",company);
        Shop.getInstance().addCompany(company);
        Shop.getInstance().addPerson(seller1);
        MainController.getInstance().setCurrentPerson(seller1);
//        assertEquals(company.toString(),controller.getCompanyInfo().toString());
    }

    @Test
    public void getBalanceTest(){
//        assertEquals(0L,controller.viewBalance());
    }

    @Test
    public void checkValidProductNumber(){
        ArrayList<String> details = new ArrayList<>(category.getDetails());
        details.addAll(subCategory.getDetails());
        if (controller.isSubCategoryCorrect("sub"));
        assertEquals(details,controller.getSubcategoryDetails("sub"));
//        if (controller.checkValidProductNumber(1))
            assertTrue(true);
    }

    @Test
    public void AddingOff(){
//        try {
//            controller.addOff(makeArrayListForOff(),makeListOfGood());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        Request requestTemp = null;
        for (Request request : Shop.getInstance().getAllRequest()) {
            if (request instanceof AddingOffRequest){
                assertTrue(true);
                try {
                    request.acceptRequest();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                requestTemp =request;
            }
        }
        Shop.getInstance().removeRequest(requestTemp);
//        assertTrue(controller.getAllOffs().size() != 0);
        assertTrue(Shop.getInstance().findOffById(Off.getOffsCount()-1) != null);
//        assertTrue(controller.doesSellerHaveThisOff(Off.getOffsCount()-1));
        try {
            controller.editOff("max discount","150", Off.getOffsCount()-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (Request request : Shop.getInstance().getAllRequest()) {
            if (request instanceof EditingOffRequest){
                try {
                    request.acceptRequest();
                    assertTrue(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requestTemp = request;
            }
        }
        Shop.getInstance().removeRequest(requestTemp);
//        try {
//            assertTrue(controller.viewOff(Off.getOffsCount()-1) != null);
//        } catch (OffNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
    }

    public ArrayList<String> makeArrayListForOff(){
        ArrayList<String> info = new ArrayList<>();
        info.add("2020-05-08");
        info.add("2020-06-18");
        info.add("20000");
        info.add("20");
        return info;
    }

    public ArrayList<Long> makeListOfGood(){
        ArrayList<Long> product = new ArrayList<>();
        product.add(good.getGoodId());
        return product;
    }

    @Test
    public void offException(){
//        try {
//            controller.viewOff(Off.getOffsCount()+10);
//        } catch (OffNotFoundException e) {
//            assertTrue(true);
//        }
    }

    @After
    public void terminating() {
        MainController.getInstance().setCurrentPerson(null);
        category.removeSubCategoryFromList(subCategory);
        TestShop.clearShop();
    }

    @Test
    public void showProductsTest(){
       SellerTest seller = new SellerTest("aa","","","","","bb",new CompanyTest());
       AccountAreaForSellerController controller =new AccountAreaForSellerController();
       MainController.getInstance().setCurrentPerson(seller);
       assertTrue(controller.sort(1).get(0).getSeenNumber() == 4);
       assertTrue(controller.sort(2).get(0).getSeenNumber() == 1);
       assertTrue(controller.sort(3).get(0).getSeenNumber() == 2);
       assertTrue(controller.sort(4).get(0).getPriceBySeller(seller) == 500);
       assertEquals("sam",controller.sort(5).get(0).getBrand());
       assertTrue(controller.sort(0).get(0).getSeenNumber() == 2);
       Shop.getInstance().removePerson(seller);
    }
}

class SellerTest extends Seller{
    public SellerTest(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company) {
        super(username, firstName, lastName, email, phoneNumber, password, company);
        Shop.getInstance().addPerson(this);
    }

    @Override
    public ArrayList<Good> getActiveGoods(){
        ArrayList<Good> goods =new ArrayList<>();
        Category category = new Category("aa",new ArrayList<>());
        SubCategory subCategory2 = new SubCategory("b", new ArrayList<>());
        SubCategory subCategory = new SubCategory("a", new ArrayList<>());
        category.addSubCategory(subCategory);
        category.addSubCategory(subCategory2);
        Good good1 = new Good("laptop", "app", subCategory, "", new HashMap<>(), this, 100, 2);
        good1.setAverageRate(4.7);
        good1.setSeenNumber(2);
        Good good2 = new Good("phone", "app", subCategory, "", new HashMap<>(), this, 400, 2);
        good2.setAverageRate(5.1);
        good2.setSeenNumber(4);
        Good good3 = new Good("headphone", "sam", subCategory2, "", new HashMap<>(), this, 500, 4);
        good3.setAverageRate(5.7);
        good3.setSeenNumber(1);
        Good good4 = new Good("laptop", "app", subCategory2, "", new HashMap<>(), this, 200, 3);
        good4.setAverageRate(2.6);
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        return goods;
    }
}

class  CompanyTest extends Company {
    public CompanyTest() {
        super("", "website", "phoneNumber", "faxNumber", "address");
    }

    @Override
    public long getId(){
        return 1;
    }
}