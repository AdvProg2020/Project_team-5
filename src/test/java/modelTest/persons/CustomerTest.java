package modelTest.persons;

import model.orders.OrderForCustomer;
import model.persons.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void initializeValues() {
        customer=new Customer("aboots","mahdi","abootorabi",
                "mahdi.abootorabi2@gmail.com","09193377991","123456",1212);
        OrderForCustomer order = new OrderForCustomer(null, 1000, "dfdfdf", "454445", "dsfdfdfdfdfdf", "dfdffdf");
        customer.addOrder(order);
        order = new OrderForCustomer(null, 1000, "derdf", "454445", "dsfdfdfdfdfdf", "09361457810");
        customer.addOrder(order);
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
        Assert.assertNull(customer.findOrderById(1000000));
    }

}
