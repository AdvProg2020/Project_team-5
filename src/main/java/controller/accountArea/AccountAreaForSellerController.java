package controller.accountArea;

import controller.MainController;
import exception.FileCantBeSavedException;
import exception.OffNotFoundException;
import exception.productExceptions.ProductNotFoundExceptionForSeller;
import model.Shop;
import model.database.Database;
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
import view.accountArea.accountAreaForManager.ManageAllProductsMenu;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AccountAreaForSellerController extends AccountAreaController {

    public void removeProduct(long productId) throws ProductNotFoundExceptionForSeller, IOException, FileCantBeSavedException {
        Seller seller = (Seller) MainController.getInstance().getCurrentPerson();
        Good good = seller.findProductOfSeller(productId);
        if (seller.hasThisProduct(productId))
            throw new ProductNotFoundExceptionForSeller();
        if (good.getSellerRelatedInfoAboutGoods().size() == 1) {
            good.getSubCategory().deleteGood(good);
            Shop.getInstance().getAllGoods().remove(good);
        } else {
            good.removeSeller(seller);
            seller.removeFromActiveGoods(good.getGoodId());
        }
        //  Database.getInstance().saveItem(seller);
        //  Database.getInstance().saveItem(good.getSubCategory());
        //  Database.getInstance().saveItem(good.getSubCategory().getParentCategory());
    }

    public String getCompanyInfo() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getCompany().toString();
    }

    public List<String> getSalesLog() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().
                map(OrderForSeller::toString).collect(Collectors.toList());
    }

    public List<String> getSortedLogs(int chosenSort) {
        List<Order> orders = ((Seller) MainController.getInstance().getCurrentPerson()).getPreviousSells().stream().map(order -> (Order) order).collect(Collectors.toList());
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

    public List<String> getAllOffs() {
        return ((Seller) MainController.getInstance().getCurrentPerson()).getActiveOffs().stream().map(Off::getBriefSummery).collect(Collectors.toList());
    }

    public List<String> getSortedOffs(int chosenSort) {
        List<Off> offs = ((Seller) MainController.getInstance().getCurrentPerson()).getActiveOffs();
        List<String> offsString = new ArrayList<>();
        if (chosenSort == 1)
            offsString = MainController.getInstance().getSortController().sortByEndDateOffs(offs).stream().map(Off::getBriefSummery).collect(Collectors.toList());
        if (chosenSort == 2)
            offsString = MainController.getInstance().getSortController().sortByOffPercent(offs).stream().map(Off::getBriefSummery).collect(Collectors.toList());
        if (chosenSort == 3)
            offsString = MainController.getInstance().getSortController().sortByMaxDiscountAmountOffs(offs).stream().map(Off::getBriefSummery).collect(Collectors.toList());
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

    public void addProduct(ArrayList<String> productInfo, HashMap<String, String> subcategoryDetailsValue) throws IOException, FileCantBeSavedException {
        Good good = new Good(productInfo.get(0), productInfo.get(1), Shop.getInstance().findSubCategoryByName(productInfo.get(5)),
                productInfo.get(4), subcategoryDetailsValue, ((Seller) MainController.getInstance().getCurrentPerson()),
                Long.parseLong(productInfo.get(2)), Integer.parseInt(productInfo.get(3)));
        AddingGoodRequest addingGoodRequest = new AddingGoodRequest(productInfo.get(0), productInfo.get(1),
                Shop.getInstance().findSubCategoryByName(productInfo.get(5)),
                productInfo.get(4), subcategoryDetailsValue, Long.parseLong(productInfo.get(2)), Integer.parseInt(productInfo.get(3)),
                MainController.getInstance().getCurrentPerson().getUsername());
        Shop.getInstance().addRequest(addingGoodRequest);
        //  Database.getInstance().saveItem(addingGoodRequest);
    }

    public boolean checkValidProductNumber(int number) {
        return number <= ((Seller) MainController.getInstance().getCurrentPerson()).getActiveGoods().size();
    }

    public boolean checkValidDate(String date, int a, String startDate) {
        Matcher matcher = getMatcher("(\\d{4})-(\\d{2})-(\\d{2})", date);
        return (Integer.parseInt(matcher.group(2)) >= 1 && Integer.parseInt(matcher.group(2)) <= 12 && Integer.parseInt(matcher.group(3)) >= 1
                && Integer.parseInt(matcher.group(3)) <= 30 && (!LocalDate.now().isAfter(LocalDate.parse(date))) && checkEndDateIsAfterStart(date, a, startDate));
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

    public void addOff(ArrayList<String> offDetails, ArrayList<Long> offProducts) throws IOException, FileCantBeSavedException {
        AddingOffRequest addingOffRequest = new AddingOffRequest(getProductsByIds(offProducts),
                LocalDate.parse(offDetails.get(0)), LocalDate.parse(offDetails.get(1)),
                Long.parseLong(offDetails.get(2)), Integer.parseInt(offDetails.get(3)),
                ((Seller) MainController.getInstance().getCurrentPerson()));
        Shop.getInstance().addRequest(addingOffRequest);
        // Database.getInstance().saveItem(addingOffRequest);
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

    public void editOff(String field, String key, long id) throws IOException, FileCantBeSavedException {
        HashMap<String, String> editedFields = new HashMap<>();
        editedFields.put(field, key);
        EditingOffRequest editingOffRequest = new EditingOffRequest(id, editedFields);
        Shop.getInstance().addRequest(editingOffRequest);
        // Database.getInstance().saveItem(editingOffRequest);
    }

    public boolean doesSellerHaveThisOff(long id) {
        if (Shop.getInstance().findOffById(id) == null) {
            return false;
        }
        Off off = Shop.getInstance().findOffById(id);
        return off.getSeller().equals(MainController.getInstance().getCurrentPerson());
    }

    public void editProduct(String field, String key, long id) throws IOException, FileCantBeSavedException {
        HashMap<String, String> editedFields = new HashMap<>();
        editedFields.put(field, key);
        EditingGoodRequest editingGoodRequest = new EditingGoodRequest(id, (Seller) MainController.getInstance().getCurrentPerson(), editedFields);
        Shop.getInstance().addRequest(editingGoodRequest);
        // Database.getInstance().saveItem(editingGoodRequest);
    }

    public String viewSellersProducts(int chosenSort) {
        String output = "-------------------------\nyour products:";
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
        if (goods != null) {
            for (Good good : goods) {
                output += ("\n" + good.toString());
            }
        }
        return output;
    }
}
