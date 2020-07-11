package controllerTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import org.junit.*;
import ApProject_OnlineShop.testThings.TestShop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AllProductsControllerTest {

    @Before
    public void load(){
        Database.getInstance().loadTestFolders();
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
    public void showCategoriesTest(){
        String output="Name of Category = aboots\n" +
                "Subcategories =\n" +
                "1- sub kabir\n" +
                "Special properties :\n" +
                "1- p1\n" +
                "2- p2\n";
        System.out.println(Shop.getInstance().findCategoryByName("aboots").toString());
        Assert.assertEquals(MainController.getInstance().getAllProductsController().showCategories(),output);
    }

    @Test
    public void showAProductTest(){
        try {
            MainController.getInstance().getAllProductsController().showAProduct(Good.getGoodsCount()-1);
            Assert.assertFalse(false);
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void showProducts(){
        String output="------------------------------------\n" +
                "GoodId = 0\n" +
                "name = phone\n" +
                "goodStatus = BUILTPROCESSING\n" +
                "brand = samsung\n" +
                "average rate = 0.0\n" +
                "category = aboots\n" +
                "subcategory = sub kabir\n" +
                "sellers = 1- seller = hi\tprice = 9000\tavailableNumber = 3\n" +
                "details =\n" +
                "\n" +
                "modification date = "+ LocalDate.now().toString() + "\n" +
                "seen number = 0\n" +
                "------------------------------------";
        Assert.assertEquals(output,MainController.getInstance().getAllProductsController().showProducts());
    }

    @After
    public  void delete(){
        TestShop.clearShop();
    }
}
