package controllerTest;

import controller.MainController;
import exception.productExceptions.ProductWithThisIdNotExist;
import exception.productExceptions.ThisProductIsnotInAnyOff;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Company;
import model.persons.Seller;
import model.productThings.Comment;
import model.productThings.Good;
import model.productThings.Off;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OffsControllerTest {

    @BeforeClass
    public static void loading() {
        ArrayList<String> details = new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category = new Category("AB", details);
        ArrayList<String> details2 = new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory = new SubCategory("subb", details2);
        category.addSubCategory(subCategory);
        HashMap<String, String> categoryProperty = new HashMap<>();
        categoryProperty.put("p1", "salam1");
        categoryProperty.put("p2", "salam2");
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", company);
        Good good = new Good("phoness", "samsung", subCategory, "details", categoryProperty,
                seller, 9000L, 3);
        List<Good> goods = new ArrayList<>();
        goods.add(good);
        subCategory.addGood(good);
        Off off = new Off(goods, LocalDate.now(), LocalDate.now().plusMonths(2), 200000, 20, seller);
        Shop.getInstance().addOff(off);
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        MainController.getInstance().getControllerForFiltering().setGoodList(false);
    }

    @Test
    public void getGoodDetailTest() {
        String output = "phoness =      \tgood id : " + (Good.getGoodsCount() - 1) + "\t         price before off : 9000              \t price after off : 7200";
        Assert.assertEquals(output, MainController.getInstance().getOffsController().showOffProducts());
    }

    @Test
    public void showAProductTest() {
        try {
            MainController.getInstance().getOffsController().showAProduct(1);
            Assert.assertTrue(false);
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            Assert.assertTrue(true);
        } catch (ThisProductIsnotInAnyOff thisProductIsnotInAnyOff) {
            thisProductIsnotInAnyOff.printStackTrace();
        }
    }

    @AfterClass
    public static void delete() {
        Shop.getInstance().removeOff(Shop.getInstance().findOffById(Off.getOffsCount() - 1));
    }

}
