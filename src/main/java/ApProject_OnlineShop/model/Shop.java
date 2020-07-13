package ApProject_OnlineShop.model;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.productExceptions.NotEnoughAvailableProduct;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.Order;
import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.Request;
import ApProject_OnlineShop.server.Server;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Shop {
    private static Shop ourInstance = new Shop();
    private HashMap<String, Category> allCategories;
    private HashMap<Long, Off> offs;
    private ArrayList<Person> allPersons;
    private ArrayList<Request> allRequest;
    private HashMap<Long, DiscountCode> allDiscountCodes;
    private ArrayList<Rate> allRates;
    //    private ArrayList<GoodInCart> cart;
    private HashMap<Long, Good> allGoods;
    private HashMap<Long, Order> allOrders;
    private HashMap<Long, GoodInCart> allGoodInCarts;
    private HashMap<String, SubCategory> allSubCategories;
    private HashMap<Long, Comment> allComments;
    private HashMap<Long, SellerRelatedInfoAboutGood> allSellerRelatedInfoAboutGood;
    private HashMap<Long, Company> allCompanies;
    private HashMap<Integer, Auction> allAuctions;
    private LocalDate lastRandomPeriodDiscountCodeCreatedDate;
    private ShopBankAccount shopBankAccount;

    public static Shop getInstance() {
        return ourInstance;
    }

    private Shop() {
        this.allCategories = new HashMap<>();
        this.allDiscountCodes = new HashMap<>();
        this.allPersons = new ArrayList<>();
        this.allRates = new ArrayList<>();
        this.allSubCategories = new HashMap<>();
        this.allRequest = new ArrayList<>();
        this.offs = new HashMap<>();
//        this.cart = new ArrayList<>();
        this.allGoods = new HashMap<>();
        this.allOrders = new HashMap<>();
        this.allGoodInCarts = new HashMap<>();
        this.allComments = new HashMap<>();
        this.allSellerRelatedInfoAboutGood = new HashMap<>();
        this.allCompanies = new HashMap<>();
        this.allAuctions = new HashMap<>();
    }

    public HashMap<Long, Company> getAllCompanies() {
        return allCompanies;
    }

    public void addCompany(Company company) {
        allCompanies.put(company.getId(), company);
    }

    public ArrayList<Category> getAllCategories() {
        return new ArrayList<>(allCategories.values());
    }

    public HashMap<Long, SellerRelatedInfoAboutGood> getAllSellerRelatedInfoAboutGood() {
        return allSellerRelatedInfoAboutGood;
    }

    public String getShopBankId() {
        return shopBankAccount.getAccountID();
    }

    public void setShopBankAccount(ShopBankAccount shopBankAccount) {
        this.shopBankAccount = shopBankAccount;
        try {
            Database.getInstance().saveItem(shopBankAccount);
        } catch (IOException | FileCantBeSavedException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Auction> getAllAuctions() {
        return allAuctions;
    }

    public void addAuction(Auction auction) {
        this.allAuctions.put(auction.getAuctionId(), auction);
    }

    public void removeAuction(Auction auction) {
        this.allAuctions.remove(auction.getAuctionId());
    }

    public Auction findAuctionById(int auctionId) {
        return this.allAuctions.get(auctionId);
    }

    public List<Auction> getAllAuctionsList() {
        List<Auction> auctions = new ArrayList<>();
        for (Integer id : this.allAuctions.keySet()) {
            auctions.add(this.allAuctions.get(id));
        }
        return auctions;
    }

    public ShopBankAccount getShopBankAccount() {
        return shopBankAccount;
    }

    public HashMap<Long, GoodInCart> getAllGoodInCarts() {
        return allGoodInCarts;
    }

    public HashMap<String, SubCategory> getAllSubCategories() {
        return allSubCategories;
    }

    public HashMap<Long, Comment> getAllComments() {
        return allComments;
    }

    public void addAComment(Comment comment) {
        this.allComments.put(comment.getId(), comment);
    }

    public void addGoodInCart(GoodInCart goodInCart) {
        this.allGoodInCarts.put(goodInCart.getGoodInCartId(), goodInCart);
    }

    public ArrayList<Rate> getAllRates() {
        return allRates;
    }

    public void addSellerRelatedInfoAboutGood(SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood) {
        this.allSellerRelatedInfoAboutGood.put(sellerRelatedInfoAboutGood.getSellerRelatedInfoAboutGoodId(), sellerRelatedInfoAboutGood);
    }

    public HashMap<String, Category> getHashMapOfCategories() {
        return this.allCategories;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return new ArrayList<>(allDiscountCodes.values());
    }

    public ArrayList<Request> getAllRequest() {
        return allRequest;
    }

    public ArrayList<Person> getAllPersons() {
        return allPersons;
    }

    public void addSubCategory(SubCategory subCategory) {
        this.allSubCategories.put(subCategory.getName(), subCategory);
    }

    public void removeSubCategory(SubCategory subCategory) {
        this.allSubCategories.remove(subCategory.getName());
        for (Good good : subCategory.getGoods()) {
            allGoods.remove(good.getGoodId());
        }
    }

    public HashMap<Long, Order> getHasMapOfOrders() {
        return allOrders;
    }

    public void addOrder(Order order) {
        this.allOrders.put(order.getOrderId(), order);
    }

    public void removePerson(Person user) {
        allPersons.remove(user);
    }

    public void addPerson(Person user) {
        allPersons.add(user);
    }

    public void addGoodToAllGoods(Good good) {
        allGoods.put(good.getGoodId(), good);
    }

    public void removeProduct(Good good) throws IOException, FileCantBeSavedException {
        good.getSubCategory().deleteGood(good);
        allGoods.remove(good.getGoodId());
    }

    public void removeRatesOfAGood(Good good) throws FileCantBeSavedException, IOException, FileCantBeDeletedException {
        Iterator<Rate> rates = Shop.getInstance().getAllRates().iterator();
        while (rates.hasNext()) {
            Rate rate = rates.next();
            if (rate.getGood().equals(good)) {
                Database.getInstance().deleteItem(rate);
                rates.remove();
            }
        }
    }

    public void removeProductsFromOffs(Good good) throws IOException, FileCantBeSavedException {
        List<Seller> sellersOfGood = good.getSellerRelatedInfoAboutGoods().stream().map(SellerRelatedInfoAboutGood::getSeller).collect(Collectors.toList());
        for (Seller seller : sellersOfGood) {
            for (Off activeOff : seller.getActiveOffs()) {
                if (activeOff.doesHaveThisProduct(good)) {
                    activeOff.removeGood(good);
                }
                Database.getInstance().saveItem(activeOff);
            }
        }
    }

    public Person findUser(String userName) {
        for (Person person : allPersons) {
            if (person.getUsername().equals(userName))
                return person;
        }
        return null;
    }

    public Good getAvailableGood(long id) {
        return allGoods.get(id);
    }

    public Good findGoodById(long goodId) { 
        for (Category category : this.getAllCategories()) {
            if (category.findGoodInSubCategories(goodId) != null)
                return category.findGoodInSubCategories(goodId);
        }
        return null;
    }

    public DiscountCode findDiscountCode(String code) { //todo
        for (DiscountCode discountCode : allDiscountCodes.values()) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public HashMap<Long, Order> getAllOrders() {
        return allOrders;
    }

    public HashMap<Long, Off> getHashMapOfOffs() {
        return this.offs;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.remove(discountCode.getId());
    }

    public void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.put(discountCode.getId(), discountCode);
    }

    public Request findRequestById(long requestId) {
        for (Request request : allRequest) {
            if (request.getRequestId() == requestId)
                return request;
        }
        return null;
    }

    public HashMap<Long, DiscountCode> getHashMapOfDiscountCodes() {
        return this.allDiscountCodes;
    }

    public void removeRequest(Request request) {
        allRequest.remove(request);
    }

    public void addRequest(Request request) {
        allRequest.add(request);
    }

    public void addCategory(Category category) {
        allCategories.put(category.getName(), category);
    }

    public void removeCategory(Category category) {
        allCategories.remove(category.getName());
        for (SubCategory subCategory : category.getSubCategories()) {
            category.deleteSubCategory(subCategory);
            allSubCategories.remove(subCategory.getName());
        }
    }

    public ArrayList<Rate> getRatesOfAGood(Good good) {
        ArrayList<Rate> ratesOfGood = new ArrayList<>();
        for (Rate rate : allRates) {
            if (rate.getGood().equals(good))
                ratesOfGood.add(rate);
        }
        return ratesOfGood;
    }

    public void addRate(Customer customer, long productId, int rate) throws IOException, FileCantBeSavedException {
        Rate rateToAdd = new Rate(customer, findGoodById(productId), rate);
        allRates.add(rateToAdd);
        Database.getInstance().saveItem(rateToAdd);
    }

    public void addRate(Rate rate) {
        allRates.add(rate);
    }

    public ArrayList<GoodInCart> getCart(long id) {
        return Server.getCarts().get(id);
    }

//    public boolean checkExistProductInCart(long productId) {
//        return !cart.stream().filter(goodInCart -> goodInCart.getGood().getGoodId() == productId).findAny().isEmpty();
//    }

    public void addGoodToCart(Good good, Seller seller, int number, long id) {
        getCart(id).add(new GoodInCart(good, seller, number));
    }

    public void reduceGoodInCartNumber(long productId, long id) {
        for (GoodInCart goodInCart : getCart(id)) {
            if (goodInCart.getGood().getGoodId() == productId) {
                goodInCart.setNumber(goodInCart.getNumber() - 1);
                if (goodInCart.getNumber() == 0)
                    getCart(id).remove(goodInCart);
                return;
            }
        }
    }

    public void increaseGoodInCartNumber(long productId, long id) throws NotEnoughAvailableProduct {
        for (GoodInCart goodInCart : getCart(id)) {
            if (goodInCart.getGood().getGoodId() == productId) {
                if (goodInCart.getNumber() >= goodInCart.getGood().getAvailableNumberBySeller(goodInCart.getSeller()))
                    throw new NotEnoughAvailableProduct();
                goodInCart.setNumber(goodInCart.getNumber() + 1);
                return;
            }
        }
    }

    public Off findOffById(long offId) { //todo
        for (Off off : offs.values()) {
            if (off.getOffId() == offId)
                return off;
        }
        return null;
    }

    public void addOff(Off off) {
        offs.put(off.getOffId(), off);
    }

    public void removeOff(Off off) {
        offs.remove(off.getOffId());
    }

    public void generatePeriodRandomDiscountCodes(LocalDate endDate) throws IOException, FileCantBeSavedException {
        String code = DiscountCode.generateRandomDiscountCode();
        DiscountCode discountCode = new DiscountCode(code, LocalDate.now(), endDate, 100000L, 20);
        discountCode.addAllCustomers(randomCustomers(5, 1, discountCode));
        allDiscountCodes.put(discountCode.getId(), discountCode);
        Database.getInstance().saveItem(discountCode);
        for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
            Database.getInstance().saveItem(customer);
        }
        this.lastRandomPeriodDiscountCodeCreatedDate = LocalDate.now();
    }

    private HashMap<Customer, Integer> randomCustomers(int customerNumbers, int repeatingTimes, DiscountCode discountCode) {
        HashMap<Customer, Integer> randomCustomers = new HashMap<>();
        while (randomCustomers.size() < customerNumbers) {
            int randomNumber = ((int) (Math.random() * 1000000)) % allPersons.size();
            Person person = allPersons.get(randomNumber);
            if (person instanceof Customer) {
                if (!randomCustomers.containsKey(person))
                    randomCustomers.put((Customer) person, repeatingTimes);
            }
        }
        return randomCustomers;
    }

    public long getFinalPriceOfAGood(Good good, Seller seller) { //todo
        if (seller == null)
            seller = good.getSellerRelatedInfoAboutGoods().get(0).getSeller();
        for (Off off : offs.values()) {
            if (off.getPriceAfterOff(good, seller) != 0)
                return off.getPriceAfterOff(good, seller);
        }
        return good.getPriceBySeller(seller);
    }

    public SubCategory getSubCategory(String name) {
        return allSubCategories.get(name);
    }

    public SubCategory findSubCategoryByName(String name) { //todo
        for (Category category : this.getAllCategories()) {
            for (SubCategory subCategory : category.getSubCategories()) {
                if (subCategory.getName().equalsIgnoreCase(name))
                    return subCategory;
            }
        }
        return null;
    }

    public Category findCategoryByName(String name) { //todo
        for (Category category : this.getAllCategories()) {
            if (category.getName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }

    public Good getGoodByNameAndBrandAndSubCategory(String name, String brand, SubCategory subCategory) { //todo
        for (Good good : subCategory.getGoods()) {
            if (good.getBrand().equalsIgnoreCase(brand) && good.getName().equalsIgnoreCase(name))
                return good;
        }
        return null;
    }

    public boolean didManagerRegistered() {
        for (Person person : allPersons) {
            if (person instanceof Manager)
                return true;
        }
        return false;
    }

    public boolean checkExistDiscountCode(String code) {
        return allDiscountCodes.values().stream().filter(discountCode -> discountCode.getCode().equals(code)).count() != 0;
    }

    public void clearCart(long id) {
        getCart(id).clear();
        Server.getCarts().remove(id);
    }

    public ArrayList<Off> getOffs() {
        return new ArrayList<>(offs.values());
    }

    public List<Good> getAllGoods() {
        return new ArrayList<>(this.allGoods.values());
    }

    public HashMap<Long, Good> getHashMapOfGoods() {
        return this.allGoods;
    }

    public List<Good> getOffGoods() {
        Set<Good> offGoods = new HashSet<>();
        for (Off off : offs.values()) {
            if ((off.getEndDate().isBefore(LocalDate.now()) || off.getStartDate().isAfter(LocalDate.now())))
                continue;
            if (off.getOffStatus().equals(Off.OffStatus.ACCEPTED)) {
                offGoods.addAll(off.getOffGoods());
            }
        }
        return new ArrayList<>(offGoods);
    }

    public void donatePeriodRandomDiscountCodes() throws IOException, FileCantBeSavedException {
        LocalDate localDate = LocalDate.now();
        if (localDate.getDayOfMonth() == 1) {
            if (!localDate.equals(lastRandomPeriodDiscountCodeCreatedDate)) {
                generatePeriodRandomDiscountCodes(LocalDate.now().plusMonths(1));
                lastRandomPeriodDiscountCodeCreatedDate = localDate;
            }
        }
    }

    public void expireItemsThatTheirTimeIsFinished() throws IOException, FileCantBeSavedException, FileCantBeDeletedException {
        for (Off off : this.getOffs()) {
            if (off.isOffExpired()) {
                this.removeOff(off);
                off.getSeller().removeFromActiveOffs(off.getOffId());
                Database.getInstance().saveItem(off.getSeller());
                Database.getInstance().deleteItem(off);
            }
        }
        for (DiscountCode discountCode : this.getAllDiscountCodes()) {
            if (discountCode.isDiscountCodeExpired()) {
                this.removeDiscountCode(discountCode);
            }
            for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
                customer.removeDiscountCode(discountCode);
                Database.getInstance().saveItem(customer);
            }
            Database.getInstance().deleteItem(discountCode);
        }
    }
}
