package modelTest;

import model.Shop;
import model.persons.Customer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

public class ShopTest {
    @BeforeClass
    public static void load(){
        Shop.getInstance().getAllPersons().add(new Customer("yasaman1", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman2", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman3", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman4", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman5", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
    }


    @Test
    public void generateRandomDiscountCodeTest(){
        Shop.getInstance().generatePeriodRandomDiscountCodes(LocalDate.now().plusMonths(2));
        Customer customer= (Customer) Shop.getInstance().findUser("yasaman1");
        Assert.assertTrue(customer.getDiscountCodes() != null);
    }

}
