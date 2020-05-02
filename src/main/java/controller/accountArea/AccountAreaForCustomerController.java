package controller.accountArea;

import controller.MainController;
import model.Shop;
import model.persons.Customer;
import model.productThings.GoodInCart;
import model.productThings.Rate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAreaForCustomerController extends AccountAreaController {
    public long viewBalance(){
        return ((Customer)MainController.getInstance().getCurrentPerson()).getCredit();
    }

    public List<String> viewDiscountCodes(){
        return ((Customer)MainController.getInstance().getCurrentPerson()).getDiscountCodes().stream().
                map(discountCode -> discountCode.toString()).collect(Collectors.toList());
    }

    public boolean checkExistProductInCart(long productId){
       return Shop.getInstance().checkExistProductInCart(productId);
    }

    public List<String> viewInCartProducts(){
        return Shop.getInstance().getCart().stream().map(goodInCart -> goodInCart.toString()).collect(Collectors.toList());
    }

    public String viewSpecialProduct(long productId){
        return Shop.getInstance().findGoodById(productId).toString();
    }

    public void increaseInCartProduct(long productId){
        Shop.getInstance().increaseGoodInCartNumber(productId);
    }

    public void decreaseInCartProduct(long productId){
        Shop.getInstance().reduceGoodInCartNumber(productId);
    }

    public long getTotalPriceOfCart(){
        return Shop.getInstance().getCart().stream().map(goodInCart -> Shop.getInstance().getFinalPriceOfAGood(goodInCart.getGood(),
                goodInCart.getSeller())).reduce(0L,(ans,i)-> ans+i);
    }

    public boolean checkExistProduct(long productId){
        return Shop.getInstance().findGoodById(productId) != null;
    }

    public boolean hasBuyProduct(long productId){
       return ((Customer)MainController.getInstance().getCurrentPerson()).hasBuyProduct(productId);
    }

    public void rateProduct(long productId, int rate){
        Shop.getInstance().addRate(((Customer)MainController.getInstance().getCurrentPerson()),productId,rate);
    }
}
