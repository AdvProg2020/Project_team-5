package modelTest.productThingsTest;

import model.persons.Customer;
import model.productThings.DiscountCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;
import java.util.HashMap;

public class DiscountCodeTest {
    private static Customer customer;
    private static DiscountCode discountCode;

    @Before
    public void initializeRequiredValuesForUsingDiscountCodeTest() {
        customer = new Customer("admin", "sadegh", "majidi",
                "sadegh@gmail.com", "09360000000", "1234");
        HashMap<Customer, Integer> includedCustomers = new HashMap<>();
        includedCustomers.put(customer, 4);
        discountCode = new DiscountCode("asdSDhjk43iu3tr",
                new Date(), new Date(), 300L, 20, includedCustomers);
    }

    @Test
    public void decreaseNumberOfRemainedChancesAfterUsingDiscountCodeForValidCustomerTest() {
        try {
            int expectedValue = 3;
            discountCode.discountBeUsedForCustomer(customer);
            int actualValue = discountCode.getIncludedCustomers().get(customer);
            Assert.assertEquals(expectedValue, actualValue);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void RandomDiscountCodeStringLengthTest() {
        int expectedLength = discountCode.getCode().length();
        int actualLength = DiscountCode.generateRandomDiscountCode().length();
        Assert.assertEquals(expectedLength, actualLength);
    }
}
