package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import exception.productExceptions.FieldCantBeEditedException;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.orders.Order;
import model.orders.OrderForSeller;
import model.persons.Manager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountAreaTest {
    @BeforeClass
    public static void initializeVariables() {
        ArrayList<String> details = new ArrayList<>();
        details.add("ram");
        details.add("cpu");
        Category category = new Category("laptop", details);
        SubCategory subCategory = new SubCategory("lenovo", details);
        category.addSubCategory(subCategory);
        Shop.getInstance().addCategory(category);
        Manager manager = new Manager("aboot", "aboot", "aboot", "dfdf@dfdf.sd", "23232323232", "Mdf2");
        Shop.getInstance().addPerson(manager);
        MainController.getInstance().setCurrentPerson(manager);
    }

    @Test
    public void showCategoriesTest() {
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForSellerController().showCategories().size());
    }

    @Test
    public void getUserPersonalInfoTest() {
        Assert.assertNotNull(MainController.getInstance().getAccountAreaForManagerController().getUserPersonalInfo());
    }

    @Test
    public void editFieldUserTest() throws Exception {
        MainController.getInstance().getAccountAreaForManagerController().editField(1, "mahdi");
        MainController.getInstance().getAccountAreaForManagerController().editField(2, "torabi");
        Assert.assertThrows(FieldCantBeEditedException.class, () -> MainController.getInstance().getAccountAreaForManagerController().editField(3, "fdfdfd"));
        MainController.getInstance().getAccountAreaForManagerController().editField(3, "dfdfdf@fdfg.ghg");
        Assert.assertThrows(FieldCantBeEditedException.class, () -> MainController.getInstance().getAccountAreaForManagerController().editField(4, "3453"));
        MainController.getInstance().getAccountAreaForManagerController().editField(4, "09361457810");
        Assert.assertThrows(FieldCantBeEditedException.class, () -> MainController.getInstance().getAccountAreaForManagerController().editField(5, "dfdf"));
        Assert.assertThrows(FieldCantBeEditedException.class, () -> MainController.getInstance().getAccountAreaForManagerController().editField(5, "Mdf2"));
        MainController.getInstance().getAccountAreaForManagerController().editField(5, "Mnop13");
        Assert.assertThrows(Exception.class, () -> MainController.getInstance().getAccountAreaForManagerController().editField(7, "dfd"));
    }

    @Test
    public void getSortedOrderTest() {
        List<String> list;
        List<Order> orders = new ArrayList<>();
        //orders.add(new OrderForSeller(2323, null, "dfddf", null));
        list = MainController.getInstance().getAccountAreaForManagerController().getSortedOrders(1, null);
        list = MainController.getInstance().getAccountAreaForManagerController().getSortedOrders(2, null);
        Assert.assertEquals(0, list.size());
    }
}