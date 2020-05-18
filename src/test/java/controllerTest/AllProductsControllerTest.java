package controllerTest;

import controller.MainController;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import org.junit.*;

import java.util.ArrayList;

public class AllProductsControllerTest {

    @BeforeClass
    public static void load(){
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("aboots",details);
        Shop.getInstance().addCategory(category);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        category.addSubCategory(new SubCategory("sub kabir",details2));
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

    @AfterClass
    public static void delete(){
        Shop.getInstance().removeCategory(Shop.getInstance().findCategoryByName("aboots"));
    }
}
