package controller.accountArea;

import controller.MainController;
import exception.OffNotFoundException;
import exception.productExceptions.ProductNotFoundExceptionForSeller;
import model.Shop;
import model.orders.Order;
import model.orders.OrderForSeller;
import model.persons.Customer;
import model.persons.Seller;
import model.productThings.Good;
import model.productThings.Off;
import model.requests.AddingGoodRequest;
import model.requests.AddingOffRequest;
import model.requests.EditingGoodRequest;
import model.requests.EditingOffRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AccountAreaForSellerController extends AccountAreaController {

    public void removeProduct(long productId) throws ProductNotFoundExceptionForSeller {
        Seller seller = (Seller) MainController.getInstance().getCurrentPerson();
        Good good = seller.findProductOfSeller(productId);
        if (seller.hasThisProduct(productId))
            throw new ProductNotFoundExceptionForSeller();
        if (good.getSellerRelatedInfoAboutGoods().size() == 1)
            good.getSubCategory().deleteGood(good);
        else {
            good.removeSeller(seller);
            seller.removeFromActiveGoods(good);
        }
    }

    public String getCompanyInfo() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getCompany().toString();
    }

    public List<String> getSalesLog() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().
                map(OrderForSeller::toString).collect(Collectors.toList());
    }

    public List<String> getSortedLogs(int chosenSort){
        List<Order> orders = ((Seller)MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().map(order -> (Order)order).collect(Collectors.toList());
        return getSortedOrders(chosenSort, orders);
    }

    public long viewBalance() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).balance();
    }

    public ArrayList<String> buyersOfProduct(long productId) throws ProductNotFoundExceptionForSeller {
        if (!((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(productId))
            throw new ProductNotFoundExceptionForSeller();
        return ((Seller) MainController.getInstance().getCurrentPerson()).buyersOfAGood(Shop.getInstance().findGoodById(productId));
    }

    public String viewProduct(long productId) throws ProductNotFoundExceptionForSeller {
        if (!((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(productId))
            throw new ProductNotFoundExceptionForSeller();
        return ((Seller) MainController.getInstance().getCurrentPerson()).findProductOfSeller(productId).toString();
    }

    public List<String> getAllOffs(){
        return ((Seller)MainController.getInstance().getCurrentPerson()).getActiveOffs().stream().map(off -> off.getBriefSummery()).collect(Collectors.toList());
    }

    public List<String> getSortedOffs(int chosenSort){
        List<Off> offs = ((Seller)MainController.getInstance().getCurrentPerson()).getActiveOffs();
        List<String> offsString = new ArrayList<>();
        if (chosenSort == 1)
            offsString = MainController.getInstance().getSortController().sortByEndDateOffs(offs).stream().map(off -> off.getBriefSummery()).collect(Collectors.toList());
        if (chosenSort == 2)
            offsString = MainController.getInstance().getSortController().sortByOffPercent(offs).stream().map(off -> off.getBriefSummery()).collect(Collectors.toList());
        if (chosenSort == 3)
            offsString = MainController.getInstance().getSortController().sortByMaxDiscountAmountOffs(offs).stream().map(off -> off.getBriefSummery()).collect(Collectors.toList());
        return offsString;
    }

    public String viewOff(long offId) throws OffNotFoundException {
        if (Shop.getInstance().findOffById(offId) == null)
            throw new OffNotFoundException();
        else {
            return Shop.getInstance().findOffById(offId).toString();
        }
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

    public void addProduct(ArrayList<String> productInfo, HashMap<String, String> subcategoryDetailsValue) {
        Good good = new Good(productInfo.get(0), productInfo.get(1), Shop.getInstance().findSubCategoryByName(productInfo.get(5)),
                productInfo.get(4), subcategoryDetailsValue, ((Seller) MainController.getInstance().getCurrentPerson()),
                Long.parseLong(productInfo.get(2)), Integer.parseInt(productInfo.get(3)));
        Shop.getInstance().addRequest(new AddingGoodRequest(good, ((Seller) MainController.getInstance().getCurrentPerson())));
    }

    public boolean checkValidProductNumber(int number) {
        return number <= ((Seller) MainController.getInstance().getCurrentPerson()).getActiveGoods().size();
    }

    public boolean checkValidDate(String date, int a, String startDate) {
        Matcher matcher = getMatcher("(\\d\\d\\d\\d)-([\\d]{1,2})-([\\d]{1,2})", date);
        return (Integer.parseInt(matcher.group(2)) >= 1 && Integer.parseInt(matcher.group(2)) <= 12 && Integer.parseInt(matcher.group(3)) >= 1
                && Integer.parseInt(matcher.group(3)) <= 30 && LocalDate.now().isBefore(LocalDate.parse(date)) && checkEndDateIsAfterStart(date, a, startDate));
    }

    public boolean checkValidProductId(long productId) {
        return ((Seller) MainController.getInstance().getCurrentPerson()).hasThisProduct(productId);
    }

    private Matcher getMatcher(String regex, String mainString) {
        Pattern datePattern = Pattern.compile(regex);
        Matcher matcher = datePattern.matcher(mainString);
        matcher.find();
        return matcher;
    }

    public void addOff(ArrayList<String> offDetails, ArrayList<Long> offProducts) {
        Off newOff = new Off(getProductsByIds(offProducts), LocalDate.parse(offDetails.get(0)), LocalDate.parse(offDetails.get(1)),
                Long.parseLong(offDetails.get(2)), Integer.parseInt(offDetails.get(3)), ((Seller) MainController.getInstance().getCurrentPerson()));
        Shop.getInstance().addRequest(new AddingOffRequest(newOff));
    }

    public List<Good> getProductsByIds(ArrayList<Long> productIds) {
        return productIds.stream().map(productId -> Shop.getInstance().findGoodById(productId)).collect(Collectors.toList());
    }

    public boolean checkEndDateIsAfterStart(String endDate, int a, String startDate) {
        if (a == 0)
            return true;
        else {
            return LocalDate.parse(startDate).isBefore(LocalDate.parse(endDate));
        }
    }

    public void editOff(String field, String key, long id) {
        HashMap<String, String> editedFields = new HashMap<>();
        editedFields.put(field,key);
        Shop.getInstance().addRequest(new EditingOffRequest(id, editedFields));
    }

    public boolean doesSellerHaveThisOff(long id) {
        if (Shop.getInstance().findOffById(id) == null) {
            return false;
        }
        Off off = Shop.getInstance().findOffById(id);
        return off.getSeller().equals(MainController.getInstance().getCurrentPerson());
    }

    public void editProduct(String field, String key, long id) {
        HashMap<String, String> editedFields = new HashMap<>();
        editedFields.put(field, key);
        Shop.getInstance().addRequest(new EditingGoodRequest(id, (Seller) MainController.getInstance().getCurrentPerson(), editedFields));
    }
}
