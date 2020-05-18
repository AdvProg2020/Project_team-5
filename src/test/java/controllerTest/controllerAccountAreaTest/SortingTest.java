package controllerTest.controllerAccountAreaTest;

import controller.sorting.SortController;
import controller.sortingAndFilteringForProducts.ControllerForSorting;
import model.productThings.Good;
import model.productThings.Off;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SortingTest {
    public ArrayList<Good> getArrayOfGoods(){
        ArrayList<Good> goods = new ArrayList<>();
        Good good1 = new Good("laptop", "app", null, "", new HashMap<>(), null, 100, 2);
        good1.setAverageRate(4.7);
        good1.setSeenNumber(2);
        Good good2 = new Good("phone", "app", null, "", new HashMap<>(), null, 400, 2);
        good2.setAverageRate(8.1);
        good2.setSeenNumber(4);
        Good good3 = new Good("headphone", "sam", null, "", new HashMap<>(), null, 500, 4);
        good3.setAverageRate(5.7);
        good3.setSeenNumber(1);
        Good good4 = new Good("laptop", "app", null, "", new HashMap<>(), null, 200, 3);
        good4.setAverageRate(2.6);
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        return goods;
    }

    public ArrayList<Off> getArrayOffs(){
        ArrayList<Off> offs = new ArrayList<>();
        Off off1 = new Off(new ArrayList<>(), LocalDate.parse("2020-02-13"), LocalDate.parse("2020-08-15"),8000L, 15, null);
        Off off2 = new Off(new ArrayList<>(), LocalDate.parse("2020-02-13"), LocalDate.parse("2020-07-15"),9000L, 20, null);
        Off off3 = new Off(new ArrayList<>(), LocalDate.parse("2020-02-13"), LocalDate.parse("2020-08-27"),6000L, 30, null);
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
