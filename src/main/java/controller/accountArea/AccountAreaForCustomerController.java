package controller.accountArea;

import controller.MainController;
import exception.discountcodeExceptions.DiscountCodeCannotBeUsed;
import exception.discountcodeExceptions.DiscountCodeExpired;
import exception.discountcodeExceptions.DiscountCodeNotFoundException;
import exception.NotEnoughCredit;
import model.Shop;
import model.orders.OrderForCustomer;
import model.persons.Customer;
import model.productThings.DiscountCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAreaForCustomerController extends AccountAreaController {
    public long viewBalance() {
        return ((Customer) MainController.getInstance().getCurrentPerson()).getCredit();
    }

    public List<String> viewDiscountCodes() {
        return ((Customer) MainController.getInstance().getCurrentPerson()).getDiscountCodes().stream().
                map(discountCode -> discountCode.detailedToString()).collect(Collectors.toList());
    }

    public boolean checkExistProductInCart(long productId) {
        return Shop.getInstance().checkExistProductInCart(productId);
    }

    public List<String> viewInCartProducts() {
        return Shop.getInstance().getCart().stream().map(goodInCart -> goodInCart.toString()).collect(Collectors.toList());
    }

    public String viewSpecialProduct(long productId) {
        return Shop.getInstance().findGoodById(productId).toString();
    }

    public void increaseInCartProduct(long productId) {
        Shop.getInstance().increaseGoodInCartNumber(productId);
    }

    public void decreaseInCartProduct(long productId) {
        Shop.getInstance().reduceGoodInCartNumber(productId);
    }

    public long getTotalPriceOfCart() {
        return Shop.getInstance().getCart().stream().map(goodInCart -> Shop.getInstance().getFinalPriceOfAGood(goodInCart.getGood(),
                goodInCart.getSeller())).reduce(0L, (ans, i) -> ans + i);
    }

    public boolean checkExistProduct(long productId) {
        return Shop.getInstance().findGoodById(productId) != null;
    }

    public boolean hasBuyProduct(long productId) {
        return ((Customer) MainController.getInstance().getCurrentPerson()).hasBuyProduct(productId);
    }

    public void rateProduct(long productId, int rate) {
        Shop.getInstance().addRate(((Customer) MainController.getInstance().getCurrentPerson()), productId, rate);
    }

    public boolean existOrderById(long orderId) {
        return !((Customer) MainController.getInstance().getCurrentPerson()).getPreviousOrders().stream().
                filter(order -> order.getOrderId() == orderId).collect(Collectors.toList()).isEmpty();
    }

    public String viewAnOrder(long orderId) {
        return ((Customer) MainController.getInstance().getCurrentPerson()).findOrderById(orderId).toString();
    }

    public boolean checkValidDiscountCode(String discountCode) throws Exception {
        if (!Shop.getInstance().checkExistDiscountCode(discountCode))
            throw new DiscountCodeNotFoundException();
        if (((Customer) MainController.getInstance().getCurrentPerson()).findDiscountCode(discountCode) == null)
            throw new DiscountCodeCannotBeUsed();
        return true;
    }

    public long useDiscountCode(String code) throws Exception {
        DiscountCode discountCode = ((Customer) MainController.getInstance().getCurrentPerson()).findDiscountCode(code);
        if (discountCode.getEndDate().isBefore(LocalDate.now()))
            throw new DiscountCodeExpired();
        return calculateFinalPrice(discountCode);
    }

    public long calculateFinalPrice(DiscountCode discountCode) {
        if (getTotalPriceOfCart() <= discountCode.getMaxDiscountAmount())
            return getTotalPriceOfCart() * (100 - discountCode.getDiscountPercent()) / 100;
        return getTotalPriceOfCart() - (discountCode.getMaxDiscountAmount() * discountCode.getDiscountPercent() / 100);
    }

    public void purchase(long totalPrice, ArrayList<String> customerInfo) throws NotEnoughCredit {
        if (((Customer) MainController.getInstance().getCurrentPerson()).getCredit() < totalPrice)
            throw new NotEnoughCredit();
        finalBuyProcess(totalPrice, customerInfo);
    }

    public void finalBuyProcess(long price, ArrayList<String> customerInfo) {
        Customer currentUser = (Customer) MainController.getInstance().getCurrentPerson();
        currentUser.addOrder(new OrderForCustomer(Shop.getInstance().getCart(), price, customerInfo.get(0), customerInfo.get(1),
                customerInfo.get(2), customerInfo.get(3)));
        currentUser.setCredit(currentUser.getCredit() - price);
        Shop.getInstance().clearCart();
    }
}
