package controllerTest;

import controller.MainController;
import exception.productExceptions.ProductWithThisIdNotExist;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Seller;
import model.productThings.Good;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductControllerTest {

    @BeforeClass
    public static void load(){
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("aboots",details);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub kabir",details2);
        category.addSubCategory(subCategory);
        HashMap<String,String> categoryProperty=new HashMap<>();
        categoryProperty.put("p1","salam1");
        categoryProperty.put("p2","salam2");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", null);
        Good good=new Good("phone", "samsung", subCategory, "details", categoryProperty,
                seller, 9000L, 3);
        MainController.getInstance().getProductController().setGood(good);
        Good good2=new Good("laptop", "apple", subCategory, "details 2", categoryProperty,
                seller, 122000L, 20);
    }

    @Test
    public void setGoodTest(){
        Assert.assertNotNull(MainController.getInstance().getProductController().getGood());
        Assert.assertTrue(MainController.getInstance().getProductController().getGood() != null);
    }

    @Test
    public void digestTest(){
        String output="------------------------------------\n" +
                "GoodId = 1\n" +
                "name = phone\n" +
                "goodStatus = BUILTPROCESSING\n" +
                "brand = samsung\n" +
                "average rate = 0.0\n" +
                "category = aboots\n" +
                "subcategory = sub kabir\n" +
                "sellers = 1- seller = hi\tprice = 9000\tavailableNumber = 3\n" +
                "details =\n" +
                "details\n" +
                "modification date = 2020-05-19\n" +
                "seen number = 1\n" +
                "------------------------------------";
        Assert.assertEquals(output,MainController.getInstance().getProductController().digest());
    }

    @Test
    public void getSellersOfProductTest(){
        String output="1-hi";
        Assert.assertEquals(output,MainController.getInstance().getProductController().getSellersOfAGood());
    }

    @Test
    public void attirbutesTest(){
        String output="details\n" +
                "p1 : salam1\n" +
                "p2 : salam2";
        Assert.assertEquals(output,MainController.getInstance().getProductController().attributes());
    }

    @Test
    public void compareWithAnotherProduct(){
        String actual="";
        try {
            actual=MainController.getInstance().getProductController().compareWithAnotherProduct(10000);
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            Assert.assertTrue(true);
        }
    }

    @AfterClass
    public static void delete(){
        MainController.getInstance().getProductController().setGood(null);
    }
}
