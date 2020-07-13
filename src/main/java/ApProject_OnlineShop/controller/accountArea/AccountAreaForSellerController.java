package ApProject_OnlineShop.controller.accountArea;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.OffNotFoundException;
import ApProject_OnlineShop.exception.productExceptions.ProductNotFoundExceptionForSeller;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.orders.Order;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Auction;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.Off;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import ApProject_OnlineShop.model.requests.AddingGoodRequest;
import ApProject_OnlineShop.model.requests.AddingOffRequest;
import ApProject_OnlineShop.model.requests.EditingGoodRequest;
import ApProject_OnlineShop.model.requests.EditingOffRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AccountAreaForSellerController extends AccountAreaController {

    public void removeProduct(long productId, Person person) throws ProductNotFoundExceptionForSeller, IOException, FileCantBeSavedException, FileCantBeDeletedException {
        Seller seller = (Seller) person;
        Good good = seller.findProductOfSeller(productId);
        if (!seller.hasThisProduct(productId))
            throw new ProductNotFoundExceptionForSeller();
        if (good.getSellerRelatedInfoAboutGoods().size() == 1) {
            Database.getInstance().deleteItem(good);
        } else {
            for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
                if (infoAboutGood.getSeller().equals(seller)) {
                    Database.getInstance().deleteItem(infoAboutGood, good.getGoodId());
                    break;
                }
            }
            for (Off activeOff : seller.getActiveOffs()) {
                if (activeOff.doesHaveThisProduct(good)) {
                    activeOff.removeGood(good);
                    Database.getInstance().saveItem(activeOff);
                }
            }
            good.removeSeller(seller);
            seller.removeFromActiveGoods(good.getGoodId());
            Database.getInstance().saveItem(seller);
            Database.getInstance().saveItem(good);
        }
    }

    public ArrayList<String> getCompanyInfo(Person person) {
        ArrayList<String> companyInfo = new ArrayList<>();
        Company company = ((Seller) person).getCompany();
        companyInfo.add(company.getName());
        companyInfo.add(company.getWebsite());
        companyInfo.add(company.getPhoneNumber());
        companyInfo.add(company.getFaxNumber());
        companyInfo.add(company.getAddress());
        return companyInfo;
    }

//    public List<String> getSalesLog() {
//        return ((Seller) MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().
//                map(OrderForSeller::toString).collect(Collectors.toList());
//    }

    public List<String> getSortedLogs(int chosenSort, Person person) {
        List<Order> orders = ((Seller) person).getPreviousSells().stream().map(order -> (Order) order).collect(Collectors.toList());
        return getSortedOrders(chosenSort, orders);
    }

    public long viewBalance(Person person) {
        return ((Seller) person).balance();
    }

    public ArrayList<String> buyersOfProduct(long productId, Person person) throws ProductNotFoundExceptionForSeller {
        if (!((Seller) person).hasThisProduct(productId))
            throw new ProductNotFoundExceptionForSeller();
        HashSet<String> hashSet = new HashSet<>(((Seller) person).buyersOfAGood(Shop.getInstance().findGoodById(productId)));
        ArrayList<String> buyerList = new ArrayList<>(hashSet);
        return buyerList;
    }

//    public String viewProduct(long productId) throws ProductNotFoundExceptionForSeller {
//        if (!((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(productId))
//            throw new ProductNotFoundExceptionForSeller();
//        return ((Seller) MainController.getInstance().getCurrentPerson()).findProductOfSeller(productId).toString();
//    }

    public List<String> getAllOffs(Person person) {
        return ((Seller) person).getActiveOffs().stream().map(Off::getBriefSummery).collect(Collectors.toList());
    }

    public List<String> getSortedOffs(int chosenSort, Person person) {
        List<Off> offs = ((Seller) person).getActiveOffs();
        List<String> offsString = new ArrayList<>();
        if (chosenSort == 1)
            offsString = MainController.getInstance().getSortController().sortByEndDateOffs(offs).stream().map(Off::getBriefSummery).collect(Collectors.toList());
        if (chosenSort == 2)
            offsString = MainController.getInstance().getSortController().sortByOffPercent(offs).stream().map(Off::getBriefSummery).collect(Collectors.toList());
        if (chosenSort == 3)
            offsString = MainController.getInstance().getSortController().sortByMaxDiscountAmountOffs(offs).stream().map(Off::getBriefSummery).collect(Collectors.toList());
        return offsString;
    }

//    public String viewOff(long offId) throws OffNotFoundException {
//        if (Shop.getInstance().findOffById(offId) == null)
//            throw new OffNotFoundException();
//        else {
//            return Shop.getInstance().findOffById(offId).toString();
//        }
//    }

    public ArrayList<String> viewOffGUI(long offId) {
        Off off = Shop.getInstance().findOffById(offId);
        ArrayList<String> details = new ArrayList<>();
        details.add(off.getOffId() + "");
        details.add(off.getStartDate() + "");
        details.add(off.getEndDate() + "");
        details.add(off.getMaxDiscount() + "");
        details.add(off.getDiscountPercent() + "");
        return details;
    }

    public boolean isSubCategoryCorrect(String subCategory) {
        return Shop.getInstance().findSubCategoryByName(subCategory) != null;
    }

    public List<String> getSubcategoryDetails(String subcategory) {
        List<String> details = new ArrayList<>();
        details.addAll(Shop.getInstance().findSubCategoryByName(subcategory).getParentCategory().getDetails());
        details.addAll(Shop.getInstance().findSubCategoryByName(subcategory).getDetails());
        return details;
    }

    public void addProduct(ArrayList<String> productInfo, HashMap<String, String> subcategoryDetailsValue, Person person) throws IOException, FileCantBeSavedException {
        AddingGoodRequest addingGoodRequest = new AddingGoodRequest(productInfo.get(0), productInfo.get(1),
                Shop.getInstance().findSubCategoryByName(productInfo.get(5)),
                productInfo.get(4), subcategoryDetailsValue, Long.parseLong(productInfo.get(2)), Integer.parseInt(productInfo.get(3)),
                person.getUsername());
        Shop.getInstance().addRequest(addingGoodRequest);
        Database.getInstance().saveItem(addingGoodRequest);
    }

//    public boolean checkValidProductNumber(int number) {
//        return number <= ((Seller) MainController.getInstance().getCurrentPerson()).getActiveGoods().size();
//    }

//    public boolean checkValidDate(String date, int a, String startDate) {
//        Matcher matcher = getMatcher("(\\d{4})-(\\d{2})-(\\d{2})", date);
//        return (Integer.parseInt(matcher.group(2)) >= 1 && Integer.parseInt(matcher.group(2)) <= 12 && Integer.parseInt(matcher.group(3)) >= 1
//                && Integer.parseInt(matcher.group(3)) <= 30 && (!LocalDate.now().isAfter(LocalDate.parse(date))) && checkEndDateIsAfterStart(date, a, startDate));
//    }

    public boolean checkValidProductId(long productId, Person person) {
        return ((Seller) person).hasThisProduct(productId);
    }

//    private Matcher getMatcher(String regex, String mainString) {
//        Pattern datePattern = Pattern.compile(regex);
//        Matcher matcher = datePattern.matcher(mainString);
//        matcher.find();
//        return matcher;
//    }

    public void addOff(ArrayList<String> offDetails, ArrayList<Long> offProducts, Person person) throws IOException, FileCantBeSavedException {
        AddingOffRequest addingOffRequest = new AddingOffRequest(getProductsByIds(offProducts),
                LocalDate.parse(offDetails.get(0)), LocalDate.parse(offDetails.get(1)),
                Long.parseLong(offDetails.get(2)), Integer.parseInt(offDetails.get(3)),
                ((Seller) person));
        Shop.getInstance().addRequest(addingOffRequest);
        Database.getInstance().saveItem(addingOffRequest);
    }

    public List<Good> getProductsByIds(ArrayList<Long> productIds) {
        return productIds.stream().map(productId -> Shop.getInstance().findGoodById(productId)).collect(Collectors.toList());
    }

//    public boolean checkEndDateIsAfterStart(String endDate, int a, String startDate) {
//        if (a == 0)
//            return true;
//        else {
//            return LocalDate.parse(startDate).isBefore(LocalDate.parse(endDate));
//        }
//    }

    public void editOff(String field, String key, long id) throws IOException, FileCantBeSavedException {
        HashMap<String, String> editedFields = new HashMap<>();
        editedFields.put(field, key);
        Shop.getInstance().findOffById(id).setOffStatus(Off.OffStatus.EDITING);
        EditingOffRequest editingOffRequest = new EditingOffRequest(id, editedFields);
        Shop.getInstance().addRequest(editingOffRequest);
        Database.getInstance().saveItem(editingOffRequest);
    }

//    public boolean doesSellerHaveThisOff(long id) {
//        if (Shop.getInstance().findOffById(id) == null) {
//            return false;
//        }
//        Off off = Shop.getInstance().findOffById(id);
//        return off.getSeller().equals(MainController.getInstance().getCurrentPerson());
//    }

    public void editProduct(String field, String key, long id, Person person) throws IOException, FileCantBeSavedException {
        HashMap<String, String> editedFields = new HashMap<>();
        editedFields.put(field, key);
        Shop.getInstance().findGoodById(id).setGoodStatus(Good.GoodStatus.EDITINGPROCESSING);
        EditingGoodRequest editingGoodRequest = new EditingGoodRequest(id, (Seller) person, editedFields);
        Shop.getInstance().addRequest(editingGoodRequest);
        Database.getInstance().saveItem(editingGoodRequest);
    }

    public List<Good> sort(int chosenSort) {
        Seller seller = (Seller) MainController.getInstance().getCurrentPerson();
        List<Good> goods = null;
        if (chosenSort == 0) {
            goods = seller.getActiveGoods();
        } else if (chosenSort == 1) {
            goods = MainController.getInstance().getControllerForSorting().showSortByVisitNumber(seller.getActiveGoods());
        } else if (chosenSort == 2) {
            goods = MainController.getInstance().getControllerForSorting().showSortByAverageRate(seller.getActiveGoods());
        } else if (chosenSort == 3) {
            goods = MainController.getInstance().getControllerForSorting().showSortByDate(seller.getActiveGoods());
        } else if (chosenSort == 4) {
            goods = MainController.getInstance().getSortController().sortProductsByPrice(seller.getActiveGoods());
        } else if (chosenSort == 5) {
            goods = MainController.getInstance().getSortController().sortProductsByAvailableNumber(seller.getActiveGoods());
        }
        return goods;
    }

//    public String viewSellersProducts(int chosenSort) {
//        String output = "-------------------------\nyour products:";
//        List<Good> goods = sort(chosenSort);
//        if (goods != null) {
//            for (Good good : goods) {
//                output += ("\n" + good.toString());
//            }
//        }
//        return output;
//    }

    public List<Long> viewProducts(int chosenSort) {
        return sort(chosenSort).stream().map(Good::getGoodId).collect(Collectors.toList());
    }

    public boolean isInOff(long productId, Person person) {
        Seller seller = Shop.getInstance().findGoodById(productId).getSellerThatPutsThisGoodOnOff();
        if (seller == null || !seller.getUsername().equals(person.getUsername())) {
            return false;
        }
        return true;
    }

    public void createAuction(ArrayList<String> fields, long goodId, Person person) throws IOException, FileCantBeSavedException, ProductNotFoundExceptionForSeller {
        if (checkValidProductId(goodId, person)) {
            Auction auction = new Auction(Shop.getInstance().findGoodById(goodId), (Seller) person, fields.get(0), fields.get(1),
                    LocalDate.parse(fields.get(2)), LocalDate.parse(fields.get(3)));
            ((Seller) person).addAuction(auction);
            Shop.getInstance().addAuction(auction);
            Database.getInstance().saveItem(person);
            Database.getInstance().saveItem(auction);
        } else throw new ProductNotFoundExceptionForSeller();
    }
}
