package modelTest;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ApProject_OnlineShop.testThings.TestShop;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShopTest {
    @BeforeClass
    public static void load(){
        Database.getInstance().loadTestFolders();
        Shop.getInstance().getAllPersons().add(new Customer("yasaman1", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman2", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman3", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman4", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Customer("yasaman5", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
    }


    @Test
    public void generateRandomDiscountCodeTest() throws IOException, FileCantBeSavedException {
        Shop.getInstance().generatePeriodRandomDiscountCodes(LocalDateTime.now().plusMonths(2));
        Customer customer= (Customer) Shop.getInstance().findUser("yasaman1");
        Assert.assertTrue(customer.getDiscountCodes() != null);
    }

    @AfterClass
    public static void delete(){
        TestShop.clearShop();
    }

}
