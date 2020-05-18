package modelTest.productThingsTest;

import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class CategoryTest {

    @BeforeClass
    public static void load(){
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("abootsZ",details);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub",details2);
        Shop.getInstance().addCategory(category);
        category.addSubCategory(subCategory);
    }

    @Test
    public void findSubCategoryByName(){
        Category category=Shop.getInstance().findCategoryByName("abootsZ");
        SubCategory subCategory=Shop.getInstance().findSubCategoryByName("sub");
        Assert.assertEquals(category.findSubCategoryByName("sub"),subCategory);
    }

    @AfterClass
    public static void delete(){
        Shop.getInstance().removeCategory(Shop.getInstance().findCategoryByName("abootsZ"));
    }
}
