package controllerTest;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.testThings.TestShop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class moreTests {
    @Before
    public void initialize() {
        Database.getInstance().loadTestFolders();
        TestShop.clearShop();
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("aboots",details);
        Shop.getInstance().addCategory(category);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub kabir",details2);
        category.addSubCategory(subCategory);
        Shop.getInstance().addSubCategory(subCategory);
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
        Good good=new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
        subCategory.addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
    }

    @Test
    public void getBriefProductTest() {
        String output = "";
    }
    @After
    public void terminate() {

    }
}
