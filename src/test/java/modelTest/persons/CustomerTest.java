package modelTest.persons;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.GoodInCart;
import org.junit.*;
import org.junit.jupiter.api.Test;
import ApProject_OnlineShop.testThings.TestShop;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerTest {

    @Before
    public void initializeValues() {
        Database.getInstance().loadTestFolders();
        ArrayList<String> details = new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category = new Category("aboots", details);
        Shop.getInstance().addCategory(category);
        ArrayList<String> details2 = new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory = new SubCategory("sub kabir", details2);
        category.addSubCategory(subCategory);
        Company company = new Company("salam", "asfs", "asdasd", "addasd", "999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", company);
        Good good = new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
        subCategory.addGood(good);
        Customer customer = new Customer("aww", "mahdi", "abootorabi",
                "mahdi.abootorabi2@gmail.com", "09193377991", "123456", 1212);
        ArrayList<GoodInCart> goodInCart = new ArrayList<>();
        goodInCart.add(new GoodInCart(good, seller, 1));
        OrderForCustomer order = new OrderForCustomer(goodInCart, 1000, "dfdfdf", "454445", "dsfdfdfdfdfdf", "dfdffdf");
        customer.addOrder(order);
        Shop.getInstance().addGoodInCart(goodInCart.get(0));
        order = new OrderForCustomer(goodInCart, 1000, "derdf", "454445", "dsfdfdfdfdfdf", "09361457810");
        customer.addOrder(order);
        Shop.getInstance().addOrder(order);
        Shop.getInstance().addPerson(customer);
    }

    @Test
    public void testToString() {
        Customer customer = new Customer("aww", "mahdi", "abootorabi",
                "mahdi.abootorabi2@gmail.com", "09193377991", "123456", 1212);
        Assert.assertEquals("-------------------\n" +
                        "username = aww\n" + "firstName = mahdi\n" + "lastName = abootorabi\n" +
                        "email = mahdi.abootorabi2@gmail.com\n" + "phoneNumber = 09193377991\n" + "credit = 1212\n"
                        + "-------------------"
                , customer.toString());
    }

    @Test
    public void findOrderByIdTest() {
        Assert.assertEquals(0, Shop.getInstance().getAllPersons().size());
//        Customer customer= (Customer) Shop.getInstance().findUser("aww");
//        Assert.assertNull(customer.findOrderById(Order.getOrdersCount() - 2));
    }

    @After
    public void delete() {
        TestShop.clearShop();
    }

}
