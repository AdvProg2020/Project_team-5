package controllerTest;

import controller.MainController;
import exception.FileCantBeSavedException;
import exception.userExceptions.MainManagerAlreadyRegistered;
import exception.userExceptions.PasswordIncorrectException;
import exception.userExceptions.UsernameIsTakenAlreadyException;
import exception.userExceptions.UsernameNotFoundException;
import model.Shop;
import model.persons.Manager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import view.accountArea.accountAreaForManager.ManageAllProductsMenu;

import java.io.IOException;
import java.util.ArrayList;

public class LoginRegisterControllerTest {


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
    public void createAcoountTest2(){
        Shop.getInstance().addPerson(new Manager("managertest","dadad","adasd","adasd"
                ,"adasd","adasdas"));
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
    public void createAccount3(){
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
    public void loginUser(){
        Shop.getInstance().addPerson(new Manager("managertest","dadad","adasd","adasd"
                ,"adasd","adasdas"));
        try {
            MainController.getInstance().getLoginRegisterController().loginUser("managertest","adasdas");
            Assert.assertEquals(MainController.getInstance().getCurrentPerson().getUsername(),"managertest");
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (PasswordIncorrectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginUser2(){
        Shop.getInstance().addPerson(new Manager("managertest","dadad","adasd","adasd"
                ,"adasd","adasdas"));
        try {
            MainController.getInstance().getLoginRegisterController().loginUser("managertest","wrong");
            Assert.assertTrue(false);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (PasswordIncorrectException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void logoutUser(){
        MainController.getInstance().getLoginRegisterController().logoutUser();
        Assert.assertEquals(MainController.getInstance().getCurrentPerson(),null);
    }

    @After
    public void delete() {
        Shop.getInstance().removePerson(Shop.getInstance().findUser("test!!!!"));
        Shop.getInstance().removePerson(Shop.getInstance().findUser("managertest"));
    }
}
