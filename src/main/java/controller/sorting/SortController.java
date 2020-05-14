package controller.sorting;

import controller.MainController;
import model.persons.Person;
import model.productThings.DiscountCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortController {

    public ArrayList<DiscountCode> sortByDiscountPercent(ArrayList<DiscountCode> allDiscountCodes){
        allDiscountCodes.sort(Comparator.comparingInt(DiscountCode::getDiscountPercent));
        Collections.reverse(allDiscountCodes);
        return allDiscountCodes;
    }


}
