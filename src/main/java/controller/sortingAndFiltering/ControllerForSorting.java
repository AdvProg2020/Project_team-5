package controller.sortingAndFiltering;

import model.orders.Order;
import model.productThings.Good;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ControllerForSorting {
    private String currentSort = "visit number";

    public String getCurrentSort() {
        return currentSort;
    }

    public void disableSort() {
        currentSort = " visit number";
    }

    public void sortASort(int chosenSort) {
        if (chosenSort == 1)
            currentSort = "visit number";
        if (chosenSort == 2)
            currentSort = "average rate";
        if (chosenSort == 3)
            currentSort = "date";
    }

    public List<Good> showProducts(List<Good> allGoods){
        if (currentSort.equals("visit number"))
            return showSortByVisitNumber(allGoods);
        if (currentSort.equals("average rate"))
            return showSortByAverageRate(allGoods);
        if (currentSort.equals("date"))
            return showSortByDate(allGoods);
        return null;
    }

    private List<Good> showSortByVisitNumber(List<Good> allGoods){
        allGoods.sort((Good firstGood, Good secondGood) -> secondGood.getSeenNumber() - firstGood.getSeenNumber());
        return allGoods;
    }

    private List<Good> showSortByAverageRate(List<Good> allGoods){
        allGoods.sort(Comparator.comparingDouble(good -> good.getAverageRate()));
        Collections.reverse(allGoods);
        return allGoods;
    }

    private List<Good> showSortByDate(List<Good> allGoods){
        allGoods.sort((Good firstGood, Good secondGood) -> secondGood.getModificationDate().toString().
                compareTo(firstGood.getModificationDate().toString()));
        return allGoods;
    }

    class SortByVisitNumber implements Comparator<Good> {
        public int compare(Good firstGood, Good secondGood) {
            return secondGood.getSeenNumber() - firstGood.getSeenNumber();
        }
    }

    class SortByAverageRate implements Comparator<Good> {
        public int compare(Good firstGood, Good secondGood) {
            if (secondGood.getAverageRate() - firstGood.getAverageRate() > 0)
                return 1;
            return (-1);
        }
    }

    class SortGoodByDate implements Comparator<Good> {
        public int compare(Good firstGood, Good secondGood) {
            if (secondGood.getModificationDate().isAfter(firstGood.getModificationDate()))
                return 1;
            return (-1);
        }
    }
}
