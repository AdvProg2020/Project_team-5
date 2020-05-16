package modelTest.productThingsTest;

import model.persons.Customer;
import model.productThings.DiscountCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.HashMap;

public class DiscountCodeTest {
    private static Customer customer;
    private static DiscountCode discountCode;

    @Before
    public void initializeRequiredValuesForUsingDiscountCodeTest() {
        customer = new Customer("admin", "sadegh", "majidi",
                "sadegh@gmail.com", "09360000000", "1234", 12212);
        discountCode = new DiscountCode("asdSDhjk43iu3tr",
                LocalDate.now(), LocalDate.now(), 300L, 20);
        discountCode.addCustomerToCode(customer,4);
    }

    @Test
    public void decreaseNumberOfRemainedChancesAfterUsingDiscountCodeForValidCustomerTest() throws Exception {
        int expectedValue = 3;
        discountCode.discountBeUsedForCustomer(customer);
        int actualValue = discountCode.getIncludedCustomers().get(customer);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void RandomDiscountCodeStringLengthTest() {
        int expectedLength = discountCode.getCode().length();
        int actualLength = DiscountCode.generateRandomDiscountCode().length();
        Assert.assertEquals(expectedLength, actualLength);
    }
}
