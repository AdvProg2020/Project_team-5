package controllerTest.controllerAccountAreaTest;

import controller.accountArea.AccountAreaForCustomerController;
import mockit.*;
import model.Shop;
import model.category.SubCategory;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.Good;
import org.junit.Test;
import view.accountArea.acountAreaForCustomer.AccountAreaForCustomer;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountAreaForCustomerTest {

    AccountAreaForCustomerController controller;
////    SubCategory subCategory = new SubCategory("1",new ArrayList<>());
////    Good good = new Good("aa","bb",subCategory,"",new HashMap<>(),null, 9000L, 4);
//    @Mocked
//    Shop shop;

//    @Test
//    public void CheckExistProductInCartTest(){
//        new Expectations(){
//            {
//                shop.checkExistProductInCart(anyLong); result = "true";
//            }
//        };
//        String  result = controller.checkExistProductInCart(9822);
//        assertTrue(result,"true"); }
//    @Mocked
//    Customer person;
//
//    @Test
//    public void viewBalanceTest(){
//        new NonStrictExpectations(){
//            Customer person;
//            {
//                person.getCredit();
//                result= 900L;
//            }
//        };
//        long result = controller.viewBalance();
//        assertEquals(900L, result);
//    }


}
