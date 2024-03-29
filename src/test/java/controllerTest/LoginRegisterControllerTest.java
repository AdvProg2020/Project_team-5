package controllerTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.userExceptions.MainManagerAlreadyRegistered;
import ApProject_OnlineShop.exception.userExceptions.PasswordIncorrectException;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Manager;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ApProject_OnlineShop.testThings.TestShop;

import java.io.IOException;
import java.util.ArrayList;

public class LoginRegisterControllerTest {

    @BeforeClass
    public static void initialize() {
        Database.getInstance().loadTestFolders();
    }

    @Test
    public void createAccountTest() {
        ArrayList<String> details = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            details.add("hi");
        try {
            MainController.getInstance().getLoginRegisterController().createAccount("manager", "test!!!!", details);
            Assert.assertTrue(Shop.getInstance().findUser("test!!!!") != null);
        } catch (UsernameIsTakenAlreadyException e) {
            e.printStackTrace();
        } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {
            mainManagerAlreadyRegistered.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAcoountTest2() {
        Shop.getInstance().addPerson(new Manager("managertest", "dadad", "adasd", "adasd"
                , "adasd", "adasdas"));
        ArrayList<String> details = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            details.add("hi");
        try {
            MainController.getInstance().getLoginRegisterController().createAccount("manager", "test!!!!", details);
            Assert.assertTrue(false);
        } catch (UsernameIsTakenAlreadyException e) {
        } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {
            Assert.assertTrue(true);
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAccount3() {
        ArrayList<String> details = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            details.add("999");
        try {
            MainController.getInstance().getLoginRegisterController().createAccount("customer", "test", details);
            Assert.assertTrue(Shop.getInstance().findUser("test") != null);
        } catch (UsernameIsTakenAlreadyException e) {
            e.printStackTrace();
        } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {
            mainManagerAlreadyRegistered.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginUser() {
        Shop.getInstance().addPerson(new Manager("managertest", "dadad", "adasd", "adasd"
                , "adasd", "adasdas"));
        try {
            MainController.getInstance().getLoginRegisterController().loginUser("managertest", "adasdas");
            Assert.assertEquals(MainController.getInstance().getCurrentPerson().getUsername(), "managertest");
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (PasswordIncorrectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginUser2() {
        Shop.getInstance().addPerson(new Manager("managertest", "dadad", "adasd", "adasd"
                , "adasd", "adasdas"));
        try {
            MainController.getInstance().getLoginRegisterController().loginUser("managertest", "wrong");
            Assert.assertTrue(false);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (PasswordIncorrectException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void logoutUser() {
//        MainController.getInstance().getLoginRegisterController().logoutUser();
        Assert.assertEquals(MainController.getInstance().getCurrentPerson(), null);
    }

    @Test
    public void CreateAccountTest4() {
        ArrayList<String> details = new ArrayList<>();
        for (int i = 1; i < 12; i++)
            details.add("999");
        try {
            MainController.getInstance().getLoginRegisterController().createAccount("seller", "seller22", details);
            Assert.assertTrue(!Shop.getInstance().getAllRequest().isEmpty());
        } catch (UsernameIsTakenAlreadyException e) {
            e.printStackTrace();
        } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {
            mainManagerAlreadyRegistered.printStackTrace();
        } catch (FileCantBeSavedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void delete() {
        TestShop.clearShop();
        MainController.getInstance().setCurrentPerson(null);
    }
}
