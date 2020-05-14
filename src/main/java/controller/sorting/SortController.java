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

    public ArrayList<DiscountCode> sortByMaxDiscountAmount(ArrayList<DiscountCode> allDiscountCodes){
        allDiscountCodes.sort(Comparator.comparingLong(DiscountCode::getMaxDiscountAmount));
        Collections.reverse(allDiscountCodes);
        return allDiscountCodes;
    }

    public ArrayList<DiscountCode> sortByEndDate(ArrayList<DiscountCode> allDiscountCodes){
        allDiscountCodes.sort((DiscountCode discountcode1,DiscountCode discountcode2) ->
                discountcode2.getEndDate().toString().compareTo(discountcode1.getEndDate().toString()));
        return allDiscountCodes;
    }

}
