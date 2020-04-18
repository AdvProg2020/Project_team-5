package controllerTest;

import controller.MainController;
import org.junit.Assert;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void checkAValidEmailTest() {
        Assert.assertTrue(MainController.isEmailValid("Mosadeghmajidi80@yahoo.com"));
    }

    @Test
    public void checkEmailWithoutAtMark() {
        Assert.assertFalse(MainController.isEmailValid("abcdef.gmail.com"));
    }

    @Test
    public void checkSpecialCharactersInEmail() {
        Assert.assertFalse(MainController.isEmailValid("sadegh$sd@gmail.com"));
        Assert.assertFalse(MainController.isEmailValid("sadEg3]ds@gmail.com"));
        Assert.assertFalse(MainController.isEmailValid("sadegh@dj*d.com"));
        Assert.assertFalse(MainController.isEmailValid("sadegh@hotmail.co)m"));
    }

    @Test
    public void checkTopLevelDomainLengthOfEmail() {
        Assert.assertFalse(MainController.isEmailValid("sadegh@gmail.c"));
        Assert.assertFalse(MainController.isEmailValid("sadegh@gmail.comirco"));
    }

    @Test
    public void checkPhoneNumberLength() {
        Assert.assertTrue(MainController.isPhoneNumberValid("09361457810"));
        Assert.assertFalse(MainController.isPhoneNumberValid("0936145781"));
        Assert.assertFalse(MainController.isPhoneNumberValid("6545"));
        Assert.assertFalse(MainController.isPhoneNumberValid("093614578101"));
    }

    @Test
    public void checkIfNonDigitCharactersNotExistInPhoneNumber() {
        Assert.assertFalse(MainController.isPhoneNumberValid("+9361457810"));
        Assert.assertFalse(MainController.isPhoneNumberValid("0936145a810"));
    }

}
