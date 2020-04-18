package controllerTest;

import controller.Controller;
import org.junit.Assert;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void checkAValidEmailTest() {
        Assert.assertTrue(Controller.isEmailValid("Mosadeghmajidi80@yahoo.com"));
    }

    @Test
    public void checkEmailWithoutAtMark() {
        Assert.assertFalse(Controller.isEmailValid("abcdef.gmail.com"));
    }

    @Test
    public void checkSpecialCharactersInEmail() {
        Assert.assertFalse(Controller.isEmailValid("sadegh$sd@gmail.com"));
        Assert.assertFalse(Controller.isEmailValid("sadEg3]ds@gmail.com"));
        Assert.assertFalse(Controller.isEmailValid("sadegh@dj*d.com"));
        Assert.assertFalse(Controller.isEmailValid("sadegh@hotmail.co)m"));
    }

    @Test
    public void checkTopLevelDomainLengthOfEmail() {
        Assert.assertFalse(Controller.isEmailValid("sadegh@gmail.c"));
        Assert.assertFalse(Controller.isEmailValid("sadegh@gmail.comirco"));
    }

    @Test
    public void checkPhoneNumberLength() {
        Assert.assertTrue(Controller.isPhoneNumberValid("09361457810"));
        Assert.assertFalse(Controller.isPhoneNumberValid("0936145781"));
        Assert.assertFalse(Controller.isPhoneNumberValid("6545"));
        Assert.assertFalse(Controller.isPhoneNumberValid("093614578101"));
    }

    @Test
    public void checkIfNonDigitCharactersNotExistInPhoneNumber() {
        Assert.assertFalse(Controller.isPhoneNumberValid("+9361457810"));
        Assert.assertFalse(Controller.isPhoneNumberValid("0936145a810"));
    }

}
