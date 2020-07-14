package ApProject_OnlineShop.controller.sortingAndFilteringForProducts;

import ApProject_OnlineShop.model.productThings.Good;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ControllerForSorting {
    private String currentSort = "visit number";

    public void sortASort(int chosenSort) {
        if (chosenSort == 1)
            currentSort = "visit number";
        if (chosenSort == 2)
            currentSort = "average rate";
        if (chosenSort == 3)
            currentSort = "date";
    }

    public String getCurrentSort() {
        return currentSort;
    }

    public List<Good> showProducts(List<Good> allGoods) {
        if (currentSort.equals("visit number"))
            return showSortByVisitNumber(allGoods);
        if (currentSort.equals("average rate"))
            return showSortByAverageRate(allGoods);
        if (currentSort.equals("date"))
            return showSortByDate(allGoods);
        return allGoods;
    }

    public List<Good> showSortByVisitNumber(List<Good> allGoods) {
        allGoods.sort((Comparator.comparingInt(Good::getSeenNumber)));
        Collections.reverse(allGoods);
        return allGoods;
    }

    public List<Good> showSortByAverageRate(List<Good> allGoods) {
        allGoods.sort(Comparator.comparingDouble(Good::getAverageRate));
        Collections.reverse(allGoods);
        return allGoods;
    }

    public List<Good> showSortByDate(List<Good> allGoods) {
        allGoods.sort((Good firstGood, Good secondGood) -> secondGood.getModificationDate().toString().
                compareTo(firstGood.getModificationDate().toString()));
        return allGoods;
    }

}
