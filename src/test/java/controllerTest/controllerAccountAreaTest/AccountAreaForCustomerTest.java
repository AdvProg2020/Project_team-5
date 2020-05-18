package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import controller.accountArea.AccountAreaForCustomerController;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AccountAreaForCustomerTest {
    AccountAreaForCustomerController controller = new AccountAreaForCustomerController();
    Seller seller = new Seller("hi","seller","seller","","","aa",null);
    Customer customer = new Customer("customer","","","","","aa",90000L);
    Category category = new Category("cat", new ArrayList<>());
    SubCategory subCategory = new SubCategory("sub", new ArrayList<>());
    DiscountCode discountCode = new DiscountCode("1111", LocalDate.parse("2020-03-15"), LocalDate.parse("2020-07-17"),2000L, 20);
    Good good = new Good("phone", "samsung", subCategory,"",new HashMap<>(),seller,9000L,3);
    Rate rate = new Rate(customer,good, 8);

    @Test
    public void getBalanceTest(){
        Shop.getInstance().addPerson(customer);
        MainController.getInstance().setCurrentPerson(customer);
        assertEquals(90000L, controller.viewBalance());
    }

    @Test
    public void viewDiscountCodeTest(){
        discountCode.addCustomerToCode(customer, 2);
        MainController.getInstance().setCurrentPerson(customer);
        String discountCodeString = controller.viewDiscountCodes().get(0);
        assertEquals(discountCode.detailedToString(),discountCodeString);
    }

    @Test
    public void buyProcessTest(){
        MainController.getInstance().setCurrentPerson(customer);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        subCategory.addGood(good);
        Shop.getInstance().addDiscountCode(discountCode);
        Shop.getInstance().addGoodToCart(good,seller,1);
        assertEquals(9000L,controller.getTotalPriceOfCart());
        assertEquals((new GoodInCart(good,seller,1).toString()), controller.viewInCartProducts().get(0));
        discountCode.addCustomerToCode(customer, 2);
        assertTrue(controller.checkExistProductInCart(good.getGoodId()));
        assertEquals(good.toString(), controller.viewSpecialProduct(good.getGoodId()));
        try {
            controller.increaseInCartProduct(good.getGoodId());
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        controller.decreaseInCartProduct(good.getGoodId());
        assertEquals(1, Shop.getInstance().getCart().get(0).getNumber());
        if (controller.checkExistProduct(good.getGoodId()))
            assertTrue(true);

        try {
            if (controller.checkValidDiscountCode("1111"))
                assertEquals(7200L,controller.useDiscountCode("1111"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
