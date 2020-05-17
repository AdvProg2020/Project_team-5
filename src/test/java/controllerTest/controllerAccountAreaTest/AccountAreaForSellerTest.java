package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class AccountAreaForSellerTest {

    @Test
    public void checkValidDateTest(){
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-11-08",1,"2020-11-04"));
        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-12-08",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-14-08",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2020-10-37",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2019-12-08",0,""));
        Assert.assertFalse(MainController.getInstance().getAccountAreaForSellerController()
                .checkValidDate("2021-10-08",1,"2021-11-08"));
    }
}
