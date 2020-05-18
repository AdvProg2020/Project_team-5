package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import controller.accountArea.AccountAreaForCustomerController;
import controller.accountArea.AccountAreaForSellerController;
import exception.FileCantBeSavedException;
import exception.OffNotFoundException;
import exception.productExceptions.ProductNotFoundExceptionForSeller;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Company;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.DiscountCode;
import model.productThings.Good;
import model.productThings.Off;
import model.productThings.Rate;
import model.requests.AddingOffRequest;
import model.requests.EditingGoodRequest;
import model.requests.EditingOffRequest;
import model.requests.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountAreaForSellerTest {
    Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", null);
    Customer customer = new Customer("customer", "", "", "", "", "aa", 90000L);
    Category category = new Category("cat", new ArrayList<>());
    SubCategory subCategory = new SubCategory("sub", new ArrayList<>());
    DiscountCode discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"), 2000L, 20);
    Good good = new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
    AccountAreaForSellerController controller = MainController.getInstance().getAccountAreaForSellerController();
    Good good2 = new Good("coat", "Gucci", subCategory, "", new HashMap<>(), seller, 2000L, 5);

    @Test
    public void checkValidDateTest() {
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-11-08", 1, "2020-11-04"));
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-12-08", 0, ""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-14-08", 0, ""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-10-37", 0, ""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2019-12-08", 0, ""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2021-10-08", 1, "2021-11-08"));
    }

    @Before
    public void initializing() {
        MainController.getInstance().setCurrentPerson(seller);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        subCategory.addGood(good);
        seller.addToActiveGoods(good);
    }


    @Test
    public void addAndRemoveGood() {
        try {
            controller.addProduct(makeArrayListForGoodCreation(), new HashMap<>());
            assertEquals(1, Shop.getInstance().getAllRequest().size());
            Shop.getInstance().getAllRequest().get(0).acceptRequest();
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        try {
            controller.editProduct("price","1000",seller.getActiveGoods().get(0).getGoodId());
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
        if (controller.checkValidProductId(seller.getActiveGoods().get(0).getGoodId())) {
            try {
                controller.removeProduct(seller.getActiveGoods().get(0).getGoodId());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            assertEquals(1, seller.getActiveGoods().size());
        }
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
        try {
            assertEquals(good.toString(),controller.viewProduct(good.getGoodId()));
        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
            productNotFoundExceptionForSeller.printStackTrace();
        }
        try {
            controller.viewProduct(4);
        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
            assertTrue(true);
        }
    }

    @Test
    public void testCompany(){
        Company company = new Company("A-Z", "AtoZ.com", "09876543241","80898","shiraz");
        Seller seller1 = new Seller("abc","","","", "", "aa",company);
        MainController.getInstance().setCurrentPerson(seller1);
        assertEquals(company.toString(),controller.getCompanyInfo());
    }

    @Test
    public void getBalanceTest(){
        assertEquals(0L,controller.viewBalance());
    }

    @Test
    public void checkValidProductNumber(){
        ArrayList<String> details = new ArrayList<>(category.getDetails());
        details.addAll(subCategory.getDetails());
        if (controller.isSubCategoryCorrect("sub"));
        assertEquals(details,controller.getSubcategoryDetails("sub"));
        if (controller.checkValidProductNumber(1))
            assertTrue(true);
    }

    @Test
    public void AddingOff(){
        try {
            controller.addOff(makeArrayListForOff(),makeListOfGood());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        assertTrue(controller.getAllOffs().size() != 0);
        assertTrue(Shop.getInstance().findOffById(1) != null);
        assertTrue(controller.doesSellerHaveThisOff(1));
        try {
            controller.editOff("max discount","150", 1);
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
        try {
            assertTrue(controller.viewOff(1) != null);
        } catch (OffNotFoundException e) {
            System.out.println(e.getMessage());
        }
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
        try {
            controller.viewOff(5);
        } catch (OffNotFoundException e) {
            assertTrue(true);
        }
    }

    @After
    public void terminating() {
        MainController.getInstance().setCurrentPerson(null);
        subCategory.removeGood(good);
        category.removeSubCategoryFromList(subCategory);
        Shop.getInstance().removeCategory(category);
        seller.removeFromActiveGoods(good);
    }
}
