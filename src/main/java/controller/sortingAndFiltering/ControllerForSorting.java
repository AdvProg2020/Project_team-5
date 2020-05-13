package controller.sortingAndFiltering;

import model.productThings.Good;

import java.util.Comparator;

public class ControllerForSorting{

    class SortByVisitNumber implements Comparator<Good>{
        public int compare(Good firstGood, Good secondGood){
            return secondGood.getSeenNumber() - firstGood.getSeenNumber();
        }
    }

    class SortByAverageRate implements Comparator<Good>{
        public int compare(Good firstGood, Good secondGood){
            if (secondGood.getAverageRate() - firstGood.getAverageRate() > 0)
                return 1;
            return (-1) ;
        }
    }

    class SortByDate implements Comparator<Good>{
        public int compare(Good firstGood, Good secondGood){
            if (secondGood.getModificationDate().isAfter(secondGood.getModificationDate()))
                return 1;
            return (-1);
        }
    }
}
