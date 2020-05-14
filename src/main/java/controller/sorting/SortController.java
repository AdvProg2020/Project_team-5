package controller.sorting;

import controller.MainController;
import model.orders.Order;
import model.persons.Person;
import model.productThings.DiscountCode;
import model.productThings.Off;

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
        allDiscountCodes.sort((DiscountCode discountCode1,DiscountCode discountCode2) ->
                discountCode2.getEndDate().toString().compareTo(discountCode1.getEndDate().toString()));
        return allDiscountCodes;
    }

    public List<Order> sortByPrice(List<Order> orders){
        orders.sort(Comparator.comparingLong(Order::getPrice));
        return orders;
    }

    public List<Order> sortByDate(List<Order> orders){
        orders.sort(Comparator.comparing(Order::getDate));
        Collections.reverse(orders);
        return orders;
    }

    public List<Off> sortByEndDateOffs(List<Off> offs){
        offs.sort(Comparator.comparing(Off::getEndDate));
        return offs;
    }

    public List<Off> sortByOffPercent(List<Off> offs){
        offs.sort(Comparator.comparing(Off::getDiscountPercent));
        Collections.reverse(offs);
        return offs;
    }

    public List<Off> sortByMaxDiscountAmountOffs(List<Off> offs){
        offs.sort(Comparator.comparing(Off::getMaxDiscount));
        Collections.reverse(offs);
        return offs;
    }

}