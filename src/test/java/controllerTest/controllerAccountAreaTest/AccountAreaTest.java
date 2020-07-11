package controllerTest.controllerAccountAreaTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.productExceptions.FieldCantBeEditedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.Order;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import org.junit.*;
import ApProject_OnlineShop.testThings.TestShop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountAreaTest {
    @Before
    public  void initializeVariables() {
        Database.getInstance().loadTestFolders();
        ArrayList<String> details = new ArrayList<>();
        details.add("ram");
        details.add("cpu");
        Category category = new Category("laptop", details);
        SubCategory subCategory = new SubCategory("lenovo", details);
        category.addSubCategory(subCategory);
        subCategory.setParentCategory(category);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
        Manager manager = new Manager("aboot", "aboot", "aboot", "dfdf@dfdf.sd", "23232323232", "Mdf2");
        Shop.getInstance().addPerson(manager);
        MainController.getInstance().setCurrentPerson(manager);
    }

    @Test
    public void showCategoriesTest() {
//        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForSellerController().showCategories().size());
    }

    @Test
    public void getUserPersonalInfoTest() {
//        Assert.assertNotNull(MainController.getInstance().getAccountAreaForManagerController().getUserPersonalInfo());
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
        Company company = new Company("salam", "asfs", "asdasd", "addasd", "999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", company);
        orders.add(new OrderForSeller(3000, seller, "dfddf", null));
        orders.add(new OrderForSeller(4323, seller, "dfddf", null));
        list = MainController.getInstance().getAccountAreaForSellerController().getSortedOrders(2, orders);
        String output = "[order ID: 1   \t date : "+ LocalDate.now().toString()+", order ID: 2   \t date : "+ LocalDate.now().toString() +"]";
        Assert.assertEquals(output, list.toString());
    }


    @AfterClass
    public static void delete(){
        MainController.getInstance().setCurrentPerson(null);
        TestShop.clearShop();
    }
}
