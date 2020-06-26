package controllerTest;

import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Comment;
import ApProject_OnlineShop.model.productThings.Good;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ApProject_OnlineShop.testThings.TestShop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductControllerTest {

    @BeforeClass
    public static void load(){
        Database.getInstance().loadTestFolders();
        ArrayList<String> details=new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category=new Category("aboots",details);
        ArrayList<String> details2=new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory=new SubCategory("sub kabir",details2);
        subCategory.setParentCategory(category);
        category.addSubCategory(subCategory);
        HashMap<String,String> categoryProperty=new HashMap<>();
        categoryProperty.put("p1","salam1");
        categoryProperty.put("p2","salam2");
        Shop.getInstance().addCategory(category);
        Shop.getInstance().addSubCategory(subCategory);
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", company);
        Good good=new Good("phone", "samsung", subCategory, "details", categoryProperty,
                seller, 9000L, 3);
        MainController.getInstance().getProductController().setGood(good);
        Comment comment=new Comment(seller,good,"title","content",false);
        good.addComment(comment);
        subCategory.addGood(good);
        Shop.getInstance().addPerson(seller);
        Shop.getInstance().addAComment(comment);
        Shop.getInstance().addGoodToAllGoods(good);
    }

    @Test
    public void setGoodTest(){
        Assert.assertNotNull(MainController.getInstance().getProductController().getGood());
        Assert.assertTrue(MainController.getInstance().getProductController().getGood() != null);
    }

    @Test
    public void digestTest(){
        String output="------------------------------------\n" +
                "GoodId = "+ MainController.getInstance().getProductController().getGood().getGoodId()+"\n" +
                "name = phone\n" +
                "goodStatus = BUILTPROCESSING\n" +
                "brand = samsung\n" +
                "average rate = 0.0\n" +
                "category = aboots\n" +
                "subcategory = sub kabir\n" +
                "sellers = 1- seller = hi\tprice = 9000\tavailableNumber = 3\n" +
                "details =\n" +
                "details\n" +
                "modification date = " + LocalDate.now() +"\n" +
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
    public void compareWithAnotherProductTest(){
        try {
            MainController.getInstance().getProductController().compareWithAnotherProduct(10000);
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void showComment(){
        String output="--------------------------------------------\n" +
                "average rate of this product is = 0.0\n" +
                "--------------------------------------------\n" +
                "Commenter Username : hi\n" +
                "Product Id : "+ MainController.getInstance().getProductController().getGood().getGoodId()+"\n" +
                "Product Name : phone\n" +
                "Title : title\n" +
                "Content : content\n" +
                "--------------------------------------------";
        Assert.assertEquals(output,MainController.getInstance().getProductController().showComments());
    }

    @AfterClass
    public static void delete(){
        MainController.getInstance().getProductController().setGood(null);
        TestShop.clearShop();
    }
}
