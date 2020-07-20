package modelTest.productThingsTest;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ApProject_OnlineShop.testThings.TestShop;

import java.util.ArrayList;

public class CategoryTest {

    @BeforeClass
    public static void load(){
        Database.getInstance().loadTestFolders();
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("abootsZ",details);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub",details2);
        Shop.getInstance().addCategory(category);
        Shop.getInstance().addSubCategory(subCategory);
        category.addSubCategory(subCategory);
    }

    @Test
    public void findSubCategoryByName(){
        Category category=Shop.getInstance().findCategoryByName("abootsZ");
        SubCategory subCategory=Shop.getInstance().findSubCategoryByName("sub");
        Assert.assertEquals(category.findSubCategoryByName("sub"),subCategory);
    }


    @Test
    public void toStringSubCateogry(){
        SubCategory subCategory=Shop.getInstance().findSubCategoryByName("sub");
        String output="sub of abootsZ category\n" +
                "properties =\n" +
                "1- p1\n" +
                "2- p2\n" +
                "3- hi1\n" +
                "4- hi2\n" +
                "Products =\n";
        Assert.assertEquals(output,subCategory.toString());
    }
    @AfterClass
    public static void delete(){
        TestShop.clearShop();
    }
}
