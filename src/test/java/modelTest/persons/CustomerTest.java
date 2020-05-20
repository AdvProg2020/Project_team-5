package modelTest.persons;

import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.orders.Order;
import model.orders.OrderForCustomer;
import model.persons.Company;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.Good;
import model.productThings.GoodInCart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testThings.TestShop;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void initializeValues() {
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
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
        Good good=new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
        subCategory.addGood(good);
        customer=new Customer("aboots","mahdi","abootorabi",
                "mahdi.abootorabi2@gmail.com","09193377991","123456",1212);
        ArrayList<GoodInCart> goodInCart=new ArrayList<>();
        goodInCart.add(new GoodInCart(good,seller,1));
        OrderForCustomer order = new OrderForCustomer(goodInCart, 1000, "dfdfdf", "454445", "dsfdfdfdfdfdf", "dfdffdf");
        customer.addOrder(order);
        Shop.getInstance().addGoodInCart(goodInCart.get(0));
        order = new OrderForCustomer(goodInCart, 1000, "derdf", "454445", "dsfdfdfdfdfdf", "09361457810");
        customer.addOrder(order);
        Shop.getInstance().addPerson(customer);
        Shop.getInstance().addOrder(order);
    }

    @Test
    public void testToString(){
        Assert.assertEquals("-------------------\n"+
                "username = aboots\n" +"firstName = mahdi\n"+ "lastName = abootorabi\n" +
                "email = mahdi.abootorabi2@gmail.com\n" + "phoneNumber = 09193377991\n" + "credit = 1212\n"
                + "-------------------"
                ,customer.toString());
    }

    @Test
    public void findOrderByIdTest() {
        Assert.assertNull(customer.findOrderById(Order.getOrdersCount()-2));
    }

    @AfterEach
    public void delete(){
        TestShop.clearShop();
    }

}
