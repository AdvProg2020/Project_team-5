package controller.accountArea;

import controller.MainController;
import exception.FileCantBeSavedException;
import exception.discountcodeExceptions.DiscountCodeCannotBeUsed;
import exception.discountcodeExceptions.DiscountCodeExpired;
import exception.discountcodeExceptions.DiscountCodeNotFoundException;
import exception.NotEnoughCredit;
import model.Shop;
import model.database.Database;
import model.orders.Order;
import model.orders.OrderForCustomer;
import model.orders.OrderForSeller;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.DiscountCode;
import model.productThings.Good;
import model.productThings.GoodInCart;
import model.productThings.SellerRelatedInfoAboutGood;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAreaForCustomerController extends AccountAreaController {
    public long viewBalance() {
        return ((Customer) MainController.getInstance().getCurrentPerson()).getCredit();
    }

    public List<String> viewDiscountCodes() {
        return ((Customer) MainController.getInstance().getCurrentPerson()).getDiscountCodes().stream().
                map(DiscountCode::detailedToString).collect(Collectors.toList());
    }

    public boolean checkExistProductInCart(long productId) {
        return Shop.getInstance().checkExistProductInCart(productId);
    }

    public List<String> viewInCartProducts() {
        return Shop.getInstance().getCart().stream().map(GoodInCart::toString).collect(Collectors.toList());
    }

    public String viewSpecialProduct(long productId) {
        return Shop.getInstance().findGoodById(productId).toString();
    }

    public void increaseInCartProduct(long productId) throws Exception {
        Shop.getInstance().increaseGoodInCartNumber(productId);
    }

    public void decreaseInCartProduct(long productId) {
        Shop.getInstance().reduceGoodInCartNumber(productId);
    }

    public long getTotalPriceOfCart() {
        return finalPriceOfAList(Shop.getInstance().getCart());
    }

    public boolean checkExistProduct(long productId) {
        return Shop.getInstance().findGoodById(productId) != null;
    }

    public boolean hasBuyProduct(long productId) {
        return ((Customer) MainController.getInstance().getCurrentPerson()).hasBuyProduct(productId);
    }

    public void rateProduct(long productId, int rate) throws IOException, FileCantBeSavedException {
        Shop.getInstance().addRate(((Customer) MainController.getInstance().getCurrentPerson()), productId, rate);
        Shop.getInstance().findGoodById(productId).updateRate();
        Database.getInstance().saveItem(Shop.getInstance().findGoodById(productId));
    }

    public List<String> getBriefSummeryOfOrders(){
        return ((Customer)MainController.getInstance().getCurrentPerson()).getPreviousOrders().stream().
                map(OrderForCustomer::briefString).collect(Collectors.toList());
    }

    public List<String> getSortedCustomerOrders(int chosenSort){
        List<Order> orders = ((Customer)MainController.getInstance().getCurrentPerson()).getPreviousOrders().stream().map(order -> (Order)order).collect(Collectors.toList());
        return getSortedOrders(chosenSort, orders);
    }

    public List<String> getSortedDiscountCode(int chosenSort){
        ArrayList<DiscountCode> discountCodes = ((Customer)MainController.getInstance().getCurrentPerson()).getDiscountCodes();
        List<String> discountCodeString = new ArrayList<>();
        if (chosenSort == 1 )
            discountCodeString = MainController.getInstance().getSortController().sortByDiscountPercent(discountCodes).stream().map(DiscountCode::toString).collect(Collectors.toList());
        if (chosenSort == 2 )
            discountCodeString = MainController.getInstance().getSortController().sortByEndDate(discountCodes).stream().map(DiscountCode::toString).collect(Collectors.toList());
        if (chosenSort == 3 )
            discountCodeString = MainController.getInstance().getSortController().sortByMaxDiscountAmount(discountCodes).stream().map(DiscountCode::toString).collect(Collectors.toList());
        return discountCodeString;
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
        if (finalPriceOfAList(Shop.getInstance().getCart()) * ((double)discountCode.getDiscountPercent()/100) <= discountCode.getMaxDiscountAmount())
            return finalPriceOfAList(Shop.getInstance().getCart())*(100 - discountCode.getDiscountPercent())/100;
        return finalPriceOfAList(Shop.getInstance().getCart()) - discountCode.getMaxDiscountAmount();
    }

    public void purchase(long totalPrice, ArrayList<String> customerInfo, String usedDiscountCode) throws NotEnoughCredit, IOException, FileCantBeSavedException {
        if (((Customer) MainController.getInstance().getCurrentPerson()).getCredit() < totalPrice)
            throw new NotEnoughCredit();
        if (usedDiscountCode != null)
            reduceNumberOfDiscountCode(usedDiscountCode);
        finalBuyProcess(totalPrice, customerInfo);
    }

    public void reduceNumberOfDiscountCode(String discountCode) throws IOException, FileCantBeSavedException {
        Customer customer= (Customer) MainController.getInstance().getCurrentPerson();
        customer.findDiscountCode(discountCode).reduceNumberOfDiscountCodeForCostumer(customer);
        Database.getInstance().saveItem(customer);
        Database.getInstance().saveItem(customer.findDiscountCode(discountCode));
    }

    public void finalBuyProcess(long price, ArrayList<String> customerInfo) throws IOException, FileCantBeSavedException {
        ArrayList<GoodInCart> cart = new ArrayList<>(Shop.getInstance().getCart());
        Customer currentUser = (Customer) MainController.getInstance().getCurrentPerson();
        OrderForCustomer orderForCustomer=new OrderForCustomer(cart, price, customerInfo.get(0), customerInfo.get(1),
                customerInfo.get(2), customerInfo.get(3));
        currentUser.addOrder(orderForCustomer);
        for (GoodInCart goodInCart : Shop.getInstance().getCart()) {
            Shop.getInstance().getAllGoodInCarts().put(goodInCart.getGoodInCartId(),goodInCart);
            Database.getInstance().saveItem(goodInCart);
        }
        Shop.getInstance().addOrder(orderForCustomer);
        orderForCustomer.setOrderStatus(Order.OrderStatus.RECEIVED);
        Database.getInstance().saveItem(orderForCustomer);
        currentUser.setCredit(currentUser.getCredit() - price);
        Database.getInstance().saveItem(currentUser);
        makeOrderForSeller(customerInfo.get(0));
        reduceAvailableNumberOfGoodsAfterPurchase();
        Shop.getInstance().clearCart();
    }

    public void makeOrderForSeller(String customerName) throws IOException, FileCantBeSavedException {
        Set<Seller> sellerSet = new HashSet<>();
        ArrayList<GoodInCart> cart = Shop.getInstance().getCart();
        for (GoodInCart good : cart) {
            sellerSet.add(good.getSeller());
        }
        for (Seller seller : sellerSet) {
            List<GoodInCart> sellerProduct = cart.stream().filter(good -> good.getSeller() == seller).collect(Collectors.toList());
            OrderForSeller orderForSeller = new OrderForSeller(finalPriceOfAList(sellerProduct), seller, customerName, sellerProduct);
            seller.addOrder(orderForSeller);
            Shop.getInstance().addOrder(orderForSeller);
            orderForSeller.setOrderStatus(Order.OrderStatus.SENT);
            Database.getInstance().saveItem(orderForSeller);
            Database.getInstance().saveItem(seller);
        }
    }

    public long finalPriceOfAList(List<GoodInCart> products) {
        return products.stream().map(GoodInCart::getFinalPrice).reduce(0L, (ans, i) -> ans + i);
    }

    public void reduceAvailableNumberOfGoodsAfterPurchase() throws IOException, FileCantBeSavedException {
        ArrayList<GoodInCart> cart = Shop.getInstance().getCart();
        for (GoodInCart good : cart) {
            good.getGood().reduceAvailableNumber(good.getSeller(), good.getNumber());
            for (SellerRelatedInfoAboutGood infoAboutGood : good.getGood().getSellerRelatedInfoAboutGoods()) {
                if (infoAboutGood.getSeller().equals(good.getSeller())) {
                    Database.getInstance().saveItem(infoAboutGood, good.getGood().getGoodId());
                    break;
                }
            }
        }
    }
}
