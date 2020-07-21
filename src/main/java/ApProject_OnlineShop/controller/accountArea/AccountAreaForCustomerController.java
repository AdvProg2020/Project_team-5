package ApProject_OnlineShop.controller.accountArea;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.CustomerNotFoundInAuctionException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCannotBeUsed;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeExpired;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.exception.NotEnoughCredit;
import ApProject_OnlineShop.exception.productExceptions.YouRatedThisProductBefore;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.orders.*;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.persons.Supporter;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.server.Server;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAreaForCustomerController extends AccountAreaController {
//    public long viewBalance() {
//        return ((Customer) MainController.getInstance().getCurrentPerson()).getCredit();
//    }

    public List<String> viewDiscountCodes(int sort, Person person) {
        if (sort == 0)
            return ((Customer) person).getDiscountCodes().stream().
                    map(DiscountCode::toString).collect(Collectors.toList());
        else return getSortedDiscountCode(sort, person);
    }

//    public boolean checkExistProductInCart(long productId) {
//        return Shop.getInstance().checkExistProductInCart(productId);
//    }

    public List<Long> viewInCartProducts(long id) {
        return Shop.getInstance().getCart(id).stream().map(goodInCart -> goodInCart.getGood().getGoodId()).collect(Collectors.toList());
    }

    public List<String> viewGoodInCartById(long productId, long id) {
        GoodInCart good = null;
        for (GoodInCart goodInCart : Shop.getInstance().getCart(id)) {
            if (goodInCart.getGood().getGoodId() == productId)
                good = goodInCart;
        }
        List<String> goodInfo = new ArrayList<>();
        goodInfo.add(good.getGood().getName() + " " + good.getGood().getBrand());
        goodInfo.add(good.getSeller().getUsername());
        goodInfo.add("" + good.getNumber());
        goodInfo.add("" + Shop.getInstance().getFinalPriceOfAGood(good.getGood(), good.getSeller()));
        goodInfo.add("" + good.getFinalPrice());
        return goodInfo;
    }

//    public String viewSpecialProduct(long productId) {
//        return Shop.getInstance().findGoodById(productId).toString();
//    }

    public void increaseInCartProduct(long productId, long id) throws Exception {
        Shop.getInstance().increaseGoodInCartNumber(productId, id);
    }

    public void decreaseInCartProduct(long productId, long id) {
        Shop.getInstance().reduceGoodInCartNumber(productId, id);
    }

//    public long getTotalPriceOfCart(long id) {
//        return finalPriceOfAList(Shop.getInstance().getCart(id));
//    }

//    public boolean checkExistProduct(long productId) {
//        return Shop.getInstance().findGoodById(productId) != null;
//    }

//    public boolean hasBuyProduct(long productId) {
//        return ((Customer) MainController.getInstance().getCurrentPerson()).hasBuyProduct(productId);
//    }

    public void rateProduct(long productId, int rate, Person person) throws IOException, FileCantBeSavedException, YouRatedThisProductBefore {
        for (Rate rate2 : Shop.getInstance().getAllRates()) {
            if (rate2.getCustomer().equals(person) && rate2.getGood().equals(Shop.getInstance().findGoodById(productId))) {
                throw new YouRatedThisProductBefore();
            }
        }
        Shop.getInstance().addRate(((Customer) person), productId, rate);
        Shop.getInstance().findGoodById(productId).updateRate();
        Database.getInstance().saveItem(Shop.getInstance().findGoodById(productId));
    }

//    public List<String> getBriefSummeryOfOrders() {
//        return ((Customer) person).getPreviousOrders().stream().
//                map(OrderForCustomer::briefString).collect(Collectors.toList());
//    }

    public List<String> getSortedCustomerOrders(int chosenSort, Person person) {
        List<Order> orders = ((Customer) person).getPreviousOrders().stream().map(order -> (Order) order).collect(Collectors.toList());
        return getSortedOrders(chosenSort, orders);
    }

    public List<String> getSortedDiscountCode(int chosenSort, Person person) {
        ArrayList<DiscountCode> discountCodes = ((Customer) person).getDiscountCodes();
        List<String> discountCodeString = new ArrayList<>();
        if (chosenSort == 1)
            discountCodeString = MainController.getInstance().getSortController().sortByDiscountPercent(discountCodes).stream().map(DiscountCode::toString).collect(Collectors.toList());
        if (chosenSort == 2)
            discountCodeString = MainController.getInstance().getSortController().sortByEndDate(discountCodes).stream().map(DiscountCode::toString).collect(Collectors.toList());
        if (chosenSort == 3)
            discountCodeString = MainController.getInstance().getSortController().sortByMaxDiscountAmount(discountCodes).stream().map(DiscountCode::toString).collect(Collectors.toList());
        return discountCodeString;
    }

//    public boolean existOrderById(long orderId) {
//        return !((Customer) MainController.getInstance().getCurrentPerson()).getPreviousOrders().stream().
//                filter(order -> order.getOrderId() == orderId).collect(Collectors.toList()).isEmpty();
//    }
//
//    public String viewAnOrder(long orderId) {
//        return ((Customer) MainController.getInstance().getCurrentPerson()).findOrderById(orderId).toString();
//    }

    public boolean checkValidDiscountCode(String discountCode, Person person) throws Exception {
        if (!Shop.getInstance().checkExistDiscountCode(discountCode))
            throw new DiscountCodeNotFoundException();
        if (((Customer) person).findDiscountCode(discountCode) == null)
            throw new DiscountCodeCannotBeUsed();
        return true;
    }

    public long useDiscountCode(String code, Person person, long id) throws Exception {
        DiscountCode discountCode = ((Customer) person).findDiscountCode(code);
        if (discountCode.getEndDate().isBefore(LocalDate.now()))
            throw new DiscountCodeExpired();
        return calculateFinalPrice(discountCode, id);
    }

    public long calculateFinalPrice(DiscountCode discountCode, long id) {
        if (finalPriceOfAList(Shop.getInstance().getCart(id)) * ((double) discountCode.getDiscountPercent() / 100) <= discountCode.getMaxDiscountAmount())
            return finalPriceOfAList(Shop.getInstance().getCart(id)) * (100 - discountCode.getDiscountPercent()) / 100;
        return finalPriceOfAList(Shop.getInstance().getCart(id)) - discountCode.getMaxDiscountAmount();
    }

    public void purchaseByWallet(long totalPrice, ArrayList<String> customerInfo, String usedDiscountCode, Person person, long id) throws Exception { //minimum lahaz shavad
        if (((Customer) person).getCredit() < totalPrice)
            throw new NotEnoughCredit();
        if (usedDiscountCode != null)
            reduceNumberOfDiscountCode(usedDiscountCode, person);
        Customer currentUser = (Customer) person;
        currentUser.setCredit(currentUser.getCredit() - totalPrice);
        finalBuyProcess(totalPrice, customerInfo, person, id);
    }

    public void purchaseByBankPortal(String bankAccountUsername, String password, String money, String usedDiscountCode, ArrayList<String> customerInfo, Person person, long id) throws Exception {
        String response = MainController.getInstance().getBankTransactionsController().moveMoneyFromUserToShop(bankAccountUsername, password, money);
        if (!response.equals("done successfully"))
            throw new Exception(response);
        if (usedDiscountCode != null)
            reduceNumberOfDiscountCode(usedDiscountCode, person);
        finalBuyProcess(Long.parseLong(money), customerInfo, person, id);
    }

    public void reduceNumberOfDiscountCode(String discountCode, Person person) throws Exception {
        Customer customer = (Customer) person;
        Shop.getInstance().findDiscountCode(discountCode).discountBeUsedForCustomer(customer);
    }

    public void finalBuyProcess(long price, ArrayList<String> customerInfo, Person person, long id) throws IOException, FileCantBeSavedException {
        ArrayList<GoodInCart> cart = new ArrayList<>(Shop.getInstance().getCart(id));
        Customer currentUser = (Customer) person;
        OrderForCustomer orderForCustomer = new OrderForCustomer(cart, price, customerInfo.get(0), customerInfo.get(1),
                customerInfo.get(2), customerInfo.get(3), id);
        currentUser.addOrder(orderForCustomer);
        for (GoodInCart goodInCart : Shop.getInstance().getCart(id)) {
            Shop.getInstance().getAllGoodInCarts().put(goodInCart.getGoodInCartId(), goodInCart);
            Database.getInstance().saveItem(goodInCart);
        }
        Shop.getInstance().addOrder(orderForCustomer);
        orderForCustomer.setOrderStatus(Order.OrderStatus.PROCESSING);
        Database.getInstance().saveItem(orderForCustomer);
        currentUser.donateDiscountCodeTOBestCustomers();
        Database.getInstance().saveItem(currentUser);
        makeOrderForSeller(person.getUsername(), id);
        reduceAvailableNumberOfGoodsAfterPurchase(id);
        Shop.getInstance().clearCart(id);
    }

    public void makeOrderForSeller(String customerName, long id) throws IOException, FileCantBeSavedException {
        Set<Seller> sellerSet = new HashSet<>();
        ArrayList<GoodInCart> cart = Shop.getInstance().getCart(id);
        for (GoodInCart good : cart) {
            sellerSet.add(good.getSeller());
        }
        for (Seller seller : sellerSet) {
            List<GoodInCart> sellerProduct = cart.stream().filter(good -> good.getSeller() == seller).collect(Collectors.toList());
            OrderForSeller orderForSeller = new OrderForSeller(finalPriceOfAList(sellerProduct), seller, customerName, sellerProduct);
            MainController.getInstance().getBankTransactionsController().payMoneyToSellerAfterPurchaseByWallet("" + finalPriceOfAList(sellerProduct), seller.getUsername());
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

    public void reduceAvailableNumberOfGoodsAfterPurchase(long id) throws IOException, FileCantBeSavedException {
        ArrayList<GoodInCart> cart = Shop.getInstance().getCart(id);
        for (GoodInCart good : cart) {
            good.getGood().reduceAvailableNumber(good.getSeller(), good.getNumber());
            for (SellerRelatedInfoAboutGood infoAboutGood : good.getGood().getSellerRelatedInfoAboutGoods()) {
                if (infoAboutGood.getSeller().equals(good.getSeller())) {
                    Database.getInstance().saveItem(infoAboutGood, good.getGood().getGoodId());
                    break;
                }
            }
            Database.getInstance().saveItem(good.getGood());
        }
    }

    public List<Long> getBoughtProducts(Person person) {
        ArrayList<OrderForCustomer> orders = ((Customer) person).getPreviousOrders();
        ArrayList<Long> goods = new ArrayList<>();
        for (OrderForCustomer order : orders) {
            goods.addAll(order.getGoodsDetails().stream().map(goodInCart -> goodInCart.getGood().getGoodId()).collect(Collectors.toList()));
        }
        HashSet<Long> IDs = new HashSet<>(goods);
        return new ArrayList<>(IDs);
    }

    public List<GoodInCart> getCart(long id) {
        return Shop.getInstance().getCart(id);
    }

    public void clearCart(long id) {
        Shop.getInstance().clearCart(id);
    }

    public List<String> getOnlineSupporters() {
        List<Person> onlineSupporters = new ArrayList<>();
        for (Person person : Server.getOnlineUsers().values()) {
            if (person instanceof Supporter)
                onlineSupporters.add(person);
        }
        ArrayList<Person> supporters = new ArrayList<>(new HashSet<>(onlineSupporters));
        return supporters.stream().map(person -> person.getUsername()).collect(Collectors.toList());
    }

    public long getLastOfferedPriceOfCustomer(Auction auction, Customer customer) throws CustomerNotFoundInAuctionException {
        if (auction.getAllCustomersOffers().containsKey(customer))
            return auction.getAllCustomersOffers().get(customer);
        throw new CustomerNotFoundInAuctionException();
    }

    public List<Massage> getMassages(String owner, String guest) {
        ArrayList<Massage> massages = new ArrayList<>();
        for (Massage massage : Shop.getInstance().getMassages()) {
            if ((massage.getSenderUserName().equals(owner) && massage.getReceiverUserName().equals(guest)) ||
                    (massage.getSenderUserName().equals(guest) && massage.getReceiverUserName().equals(owner)))
                massages.add(massage);
        }
        return massages;
    }

    public void purchaseFileProductByWallet(long fileProductId, String phoneNumber, String discountCode, long price, Person person) throws Exception {
        FileProduct fileProduct = Shop.getInstance().findFileProductById(fileProductId);
        Customer customer = (Customer) person;
        if (!discountCode.equals("")) {
            DiscountCode discountCode1 = Shop.getInstance().findDiscountCode(discountCode);
            discountCode1.discountBeUsedForCustomer(customer);
        }
        OrderFileProductForSeller orderFileProductForSeller = new OrderFileProductForSeller(price, fileProduct.getSeller(), customer.getUsername(), fileProduct);
        Shop.getInstance().addOrder(orderFileProductForSeller);
        orderFileProductForSeller.setOrderStatus(Order.OrderStatus.RECEIVED);
        Database.getInstance().saveItem(orderFileProductForSeller);
        OrderFileProductForCustomer orderFileProductForCustomer = new OrderFileProductForCustomer(fileProduct, price, phoneNumber, fileProduct.getPrice() - price);
        Shop.getInstance().addOrder(orderFileProductForCustomer);
        orderFileProductForCustomer.setOrderStatus(Order.OrderStatus.RECEIVED);
        Database.getInstance().saveItem(orderFileProductForCustomer);
        customer.setCredit(customer.getCredit() - price);
        MainController.getInstance().getBankTransactionsController().payMoneyToSellerAfterPurchaseByWallet(fileProduct.getPrice() + "", fileProduct.getSeller().getUsername());
        Database.getInstance().saveItem(customer);
        Database.getInstance().saveItem(fileProduct.getSeller());
        fileProduct.increaseDownloadNumber();
        Database.getInstance().saveItem(fileProduct);
    }

    public List<String> getFileOrdersOfCustomer(Person person) {
        Customer customer = (Customer) person;
        List<String> orders = new ArrayList<>();
        for (Order order : Shop.getInstance().getAllOrders().values()) {
            if (order instanceof OrderFileProductForSeller && ((OrderFileProductForSeller) order).getCustomerName().equals(customer.getUsername()))
                orders.add("file order Id: " + order.getOrderId() + "     date: " + order.getDate());
        }
        return orders;
    }

}
