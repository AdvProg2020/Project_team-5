package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import controller.accountArea.AccountAreaForCustomerController;
import controller.accountArea.AccountAreaForSellerController;
import exception.FileCantBeSavedException;
import exception.productExceptions.ProductNotFoundExceptionForSeller;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.DiscountCode;
import model.productThings.Good;
import model.productThings.Rate;
import org.junit.Assert;
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
    Good good2 = new Good("coat","Gucci",subCategory,"",new HashMap<>(),seller,2000L,5);

    @Test
    public void checkValidDateTest(){
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-11-08",1,"2020-11-04"));
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-12-08",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-14-08",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-10-37",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2019-12-08",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2021-10-08",1,"2021-11-08"));
    }



    @Test
    public void addAndRemoveGood(){
        initializing();
        try {
            controller.addProduct(makeArrayListForGoodCreation(),new HashMap<>());
            assertEquals(1,Shop.getInstance().getAllRequest().size());
            Shop.getInstance().getAllRequest().get(0).acceptRequest();
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertEquals(1,seller.getActiveGoods().size());
        if (controller.checkValidProductId(seller.getActiveGoods().get(0).getGoodId())) {
            try {
                controller.removeProduct(seller.getActiveGoods().get(0).getGoodId());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            assertEquals(0,seller.getActiveGoods().size());
        }
    }
    public void initializing(){
        MainController.getInstance().setCurrentPerson(seller);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        subCategory.addGood(good);
    }
    public ArrayList<String> makeArrayListForGoodCreation(){
        ArrayList<String> info = new ArrayList<>();
        info.add("name");
        info.add("brand");
        info.add("350");
        info.add("3");
        info.add("");
        info.add("sub");
        return info;
    }
}
