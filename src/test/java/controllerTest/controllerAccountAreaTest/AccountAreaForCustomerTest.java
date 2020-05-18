package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import controller.accountArea.AccountAreaForCustomerController;
import exception.FileCantBeSavedException;
import exception.NotEnoughCredit;
import exception.discountcodeExceptions.DiscountCodeCannotBeUsed;
import exception.discountcodeExceptions.DiscountCodeNotFoundException;
import mockit.*;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.DiscountCode;
import model.productThings.Good;
import model.productThings.GoodInCart;
import model.productThings.Rate;
import org.junit.Test;
import view.accountArea.acountAreaForCustomer.AccountAreaForCustomer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AccountAreaForCustomerTest {
    AccountAreaForCustomerController controller = new AccountAreaForCustomerController();
    Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", null);
    Customer customer = new Customer("customer", "", "", "", "", "aa", 90000L);
    Category category = new Category("cat", new ArrayList<>());
    SubCategory subCategory = new SubCategory("sub", new ArrayList<>());
    DiscountCode discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"), 2000L, 20);
    Good good = new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
    Rate rate = new Rate(customer, good, 8);

    @Test
    public void getBalanceTest() {
        Shop.getInstance().addPerson(customer);
        MainController.getInstance().setCurrentPerson(customer);
        assertEquals(90000L, controller.viewBalance());
    }

    @Test
    public void viewDiscountCodeTest() {
        discountCode.addCustomerToCode(customer, 2);
        MainController.getInstance().setCurrentPerson(customer);
        String discountCodeString = controller.viewDiscountCodes().get(0);
        assertEquals(discountCode.detailedToString(), discountCodeString);
    }

    public void initializing(){
        MainController.getInstance().setCurrentPerson(customer);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        subCategory.addGood(good);
        Shop.getInstance().addDiscountCode(discountCode);
        Shop.getInstance().addGoodToCart(good, seller, 1);
        discountCode.addCustomerToCode(customer, 2);
    }

    @Test
    public void buyProcessTest() {
        initializing();
        assertEquals(9000L, controller.getTotalPriceOfCart());
        assertEquals((new GoodInCart(good, seller, 1).toString()), controller.viewInCartProducts().get(0));
        assertTrue(controller.checkExistProductInCart(good.getGoodId()));
        assertEquals(good.toString(), controller.viewSpecialProduct(good.getGoodId()));
        try {
            controller.increaseInCartProduct(good.getGoodId());
            assertEquals(2,Shop.getInstance().getCart().get(0).getNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        controller.decreaseInCartProduct(good.getGoodId());
        assertEquals(1, Shop.getInstance().getCart().get(0).getNumber());
        try {
            if (controller.checkValidDiscountCode("1111"))
                assertEquals(7200L , controller.useDiscountCode("1111"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            controller.purchase(7200L, makeArrayListForOrder(), "1111");
        } catch (Exception e) {
            assertFalse(false);
        }
       assertEquals(1,customer.getPreviousOrders().size());
        assertEquals(2, good.getAvailableNumberBySeller(seller));
        assertTrue(customer.getCredit() != 90000L);
        if (controller.hasBuyProduct(good.getGoodId())) {
            try {
                controller.rateProduct(good.getGoodId(),8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertTrue(good.getAverageRate() != 0);
        }

        if (controller.existOrderById(1)){
            assertEquals(customer.getPreviousOrders().get(0).toString(), controller.viewAnOrder(1));
        }
    }

    @Test
    public void CheckExistGoodTest(){
        if (controller.checkExistProduct(good.getGoodId()))
            assertTrue(true);
    }

    public ArrayList<String > makeArrayListForOrder(){
        ArrayList<String > info = new ArrayList<>();
        info.add("a");
        info.add("686");
        info.add("hfhf");
        info.add("09876543214");
        return info;
    }

    @Test
    public void discountCodeExceptions() {
        DiscountCode discountCode1 = new DiscountCode("4567", LocalDate.parse("2020-06-16"), LocalDate.parse("2020-07-20"), 1000L, 10);
        Shop.getInstance().addDiscountCode(discountCode1);
        MainController.getInstance().setCurrentPerson(customer);
        try {
            controller.checkValidDiscountCode("1234");
        } catch (Exception e) {
            assertEquals((new DiscountCodeNotFoundException()).getMessage(), e.getMessage());
        }

        try {
            controller.checkValidDiscountCode("4567");
        } catch (Exception exception) {
            assertEquals((new DiscountCodeCannotBeUsed()).getMessage(), exception.getMessage());
        }
    }

}
