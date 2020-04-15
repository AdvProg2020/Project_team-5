package modelTest.persons;

import model.persons.Customer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void testToString(){
         Customer customer=new Customer("aboots","mahdi","abootorabi",
                "mahdi.abootorabi2@gmail.com","09193377991","123456");
        Assert.assertEquals("-------------------\n"+
                "username = aboots\n" +"firstName = mahdi\n"+ "lastName = abootorabi\n" +
                "email = mahdi.abootorabi2@gmail.com\n" + "phoneNumber = 09193377991\n" + "credit = 0\n"
                + "-------------------"
                ,customer.toString());
    }

}
