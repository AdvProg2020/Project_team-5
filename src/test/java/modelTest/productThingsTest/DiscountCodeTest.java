package modelTest.productThingsTest;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;

public class DiscountCodeTest {

    @Test
    public void decreaseNumberOfRemainedChancesAfterUsingDiscountCodeForValidCustomerTest() throws Exception {
        Database.getInstance().loadTestFolders();
        Customer customer = new Customer("admin", "sadegh", "majidi",
                "sadegh@gmail.com", "09360000000", "1234", 12212);
        DiscountCode discountCode = new DiscountCode("asdSDhjk43iu3tr",
                LocalDate.parse("2020-06-09"), LocalDate.parse("2020-07-10"), 300L, 20);
        Shop.getInstance().getAllDiscountCodes().add(discountCode);
        Shop.getInstance().getAllPersons().add(customer);
        discountCode.addCustomerToCode(customer,4);
        int expectedValue = 3;
        discountCode.discountBeUsedForCustomer(customer);
        int actualValue = discountCode.getIncludedCustomers().get(customer);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void RandomDiscountCodeStringLengthTest() {
        int expectedLength = 15;
        int actualLength = DiscountCode.generateRandomDiscountCode().length();
        Assert.assertEquals(expectedLength, actualLength);
    }
}
