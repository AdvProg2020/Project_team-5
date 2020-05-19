package controllerTest;

import controller.MainController;
import exception.productExceptions.ProductWithThisIdNotExist;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Company;
import model.persons.Seller;
import model.productThings.Good;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProductsControllerTest {



    @Before
    public  void load(){
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
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
        Good good=new Good("phone", "samsung", subCategory, "", new HashMap<>(), seller, 9000L, 3);
        subCategory.addGood(good);
    }

    @Test
    public void showCategoriesTest(){
        String output="Name of Category = aboots\n" +
                "Subcategories =\n" +
                "1- sub kabir\n" +
                "Special properties :\n" +
                "1- p1\n" +
                "2- p2\n";
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
        String output="";
        Assert.assertEquals(output,MainController.getInstance().getAllProductsController().showProducts());
    }

    @After
    public  void delete(){
        Shop.getInstance().getHashMapOfCategories().remove("aboots");
    }
}
