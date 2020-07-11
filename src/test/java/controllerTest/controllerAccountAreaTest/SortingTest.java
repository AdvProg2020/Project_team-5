package controllerTest.controllerAccountAreaTest;

import ApProject_OnlineShop.controller.sorting.SortController;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForSorting;
import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SortingTest {
    @BeforeClass
    public static void initialize() {
        Database.getInstance().loadTestFolders();
    }
    public ArrayList<Good> getArrayOfGoods(){
        ArrayList<Good> goods = new ArrayList<>();
        ArrayList<String> details = new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category = new Category("AB", details);
        ArrayList<String> details2 = new ArrayList<>();
        details2.add("hi1");
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
        details2.add("hi2");
        SubCategory subCategory = new SubCategory("subb", details2);
        category.addSubCategory(subCategory);
        Good good1 = new Good("laptop", "app", subCategory, "", new HashMap<>(), seller, 100, 2);
        good1.setAverageRate(4.7);
        good1.setSeenNumber(2);
        Good good2 = new Good("phone", "app", subCategory, "", new HashMap<>(), seller, 400, 2);
        good2.setAverageRate(8.1);
        good2.setSeenNumber(4);
        Good good3 = new Good("headphone", "sam", subCategory, "", new HashMap<>(), seller, 500, 4);
        good3.setAverageRate(5.7);
        good3.setSeenNumber(1);
        Good good4 = new Good("laptop", "app", subCategory, "", new HashMap<>(), seller, 200, 3);
        good4.setAverageRate(2.6);
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        return goods;
    }

    public ArrayList<Off> getArrayOffs(){
        ArrayList<Off> offs = new ArrayList<>();
        Company company=new Company("salam","asfs","asdasd","addasd","999");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa",company);
        Off off1 = new Off(new ArrayList<>(), LocalDate.parse("2020-02-13"), LocalDate.parse("2020-08-15"),8000L, 15, seller);
        Off off2 = new Off(new ArrayList<>(), LocalDate.parse("2020-02-13"), LocalDate.parse("2020-07-15"),9000L, 20, seller);
        Off off3 = new Off(new ArrayList<>(), LocalDate.parse("2020-02-13"), LocalDate.parse("2020-08-27"),6000L, 30, seller);
        offs.add(off1);
        offs.add(off2);
        offs.add(off3);
        return offs;
    }

    @Test
    public void SortAverageRate(){
        ControllerForSorting controller = new ControllerForSorting();
        controller.sortASort(1);
        controller.sortASort(2);
        assertEquals(4, controller.showProducts(getArrayOfGoods()).get(0).getSeenNumber());
        controller.sortASort(3);
        assertEquals(2,controller.showProducts(getArrayOfGoods()).get(0).getSeenNumber());
    }

    @Test
    public void SortOffs(){
        SortController controller = new SortController();
        assertEquals(20, controller.sortByEndDateOffs(getArrayOffs()).get(0).getDiscountPercent());
        assertEquals(30,controller.sortByOffPercent(getArrayOffs()).get(0).getDiscountPercent());
        assertEquals(8000L,controller.sortByMaxDiscountAmountOffs(getArrayOffs()).get(1).getMaxDiscount());
    }
}
