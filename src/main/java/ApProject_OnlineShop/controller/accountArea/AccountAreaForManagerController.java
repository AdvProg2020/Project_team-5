package ApProject_OnlineShop.controller.accountArea;

import ApProject_OnlineShop.exception.*;
import ApProject_OnlineShop.exception.categoryExceptions.CategoryNotFoundException;
import ApProject_OnlineShop.exception.categoryExceptions.SubCategoryNotFoundException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.exception.userExceptions.UserCantBeRemovedException;
import ApProject_OnlineShop.exception.userExceptions.UsernameIsTakenAlreadyException;
import ApProject_OnlineShop.exception.userExceptions.UsernameNotFoundException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.model.orders.Order;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Supporter;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import ApProject_OnlineShop.model.requests.Request;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AccountAreaForManagerController extends AccountAreaController {
    public void createNewDiscountCode(ArrayList<String> fields) throws DiscountCodeCantCreatedException, IOException, FileCantBeSavedException {
        if (fields.get(0).length() > 15)
            throw new DiscountCodeCantCreatedException("code length");
        if (LocalDate.parse(fields.get(1)).isBefore(LocalDate.now()))
            throw new DiscountCodeCantCreatedException("start date");
        if (LocalDate.parse(fields.get(2)).isBefore(LocalDate.parse(fields.get(1))))
            throw new DiscountCodeCantCreatedException("end date");
        if (Integer.parseInt(fields.get(4)) > 100 || Integer.parseInt(fields.get(4)) <= 0)
            throw new DiscountCodeCantCreatedException("discount percent");
        DiscountCode discountCode = new DiscountCode(fields.get(0), LocalDate.parse(fields.get(1)), LocalDate.parse(fields.get(2)),
                Long.parseLong(fields.get(3)), Integer.parseInt(fields.get(4)));
        Shop.getInstance().addDiscountCode(discountCode);
        Database.getInstance().saveItem(discountCode);
    }

    public void addIncludedCustomerToDiscountCode(String code, String username, String numberOfUse)
            throws DiscountCodeCantCreatedException, UsernameNotFoundException, DiscountCodeNotFoundException, IOException, FileCantBeSavedException {
        Person person;
        int number;
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
            throw new DiscountCodeNotFoundException();
        if ((person = Shop.getInstance().findUser(username)) == null)
            throw new UsernameNotFoundException();
        if (!(person instanceof Customer))
            throw new DiscountCodeCantCreatedException("customer");
        try {
            number = Integer.parseInt(numberOfUse);
        } catch (Exception e) {
            throw new DiscountCodeCantCreatedException("number of use");
        }
        discountCode.addCustomerToCode((Customer) person, number);
        Customer customer = (Customer) person;
        Database.getInstance().saveItem(discountCode);
        Database.getInstance().saveItem(customer);
    }

//    public ArrayList<String> getAllDiscountCodesInfo(ArrayList<DiscountCode> discountCodes) {
//        ArrayList<String> allDiscountCodes = new ArrayList<>();
//        for (DiscountCode discountCode : discountCodes) {
//            allDiscountCodes.add(discountCode.toString());
//        }
//        return allDiscountCodes;
//    }
//
//    public ArrayList<String> getAllDiscountCodeWithSort(int input) {
//        ArrayList<DiscountCode> discountCodes = new ArrayList<>();
//        if (input == 0)
//            discountCodes = Shop.getInstance().getAllDiscountCodes();
//        if (input == 1)
//            discountCodes = MainController.getInstance().getSortController().
//                    sortByDiscountPercent(Shop.getInstance().getAllDiscountCodes());
//        if (input == 2)
//            discountCodes = MainController.getInstance().getSortController().
//                    sortByEndDate(Shop.getInstance().getAllDiscountCodes());
//        if (input == 3)
//            discountCodes = MainController.getInstance().getSortController().
//                    sortByMaxDiscountAmount(Shop.getInstance().getAllDiscountCodes());
//        return getAllDiscountCodesInfo(discountCodes);
//    }

//    public String viewDiscountCode(String code) throws DiscountCodeNotFoundException {
//        DiscountCode discountCode;
//        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
//            throw new DiscountCodeNotFoundException();
//        return discountCode.getPrintableProperties();
//    }

    public void editDiscountCode(String code, String field, String newValue)
            throws DiscountCodeNotFoundException, DiscountCodeCantBeEditedException, IOException, FileCantBeSavedException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) != null) {
            if (field.equalsIgnoreCase("startDate")) {
                if (!newValue.matches("\\d{4}.\\d{2}.\\d{2}") || LocalDate.parse(newValue).isBefore(LocalDate.now()) || LocalDate.parse(newValue).isAfter(discountCode.getEndDate()))
                    throw new DiscountCodeCantBeEditedException("new start date value");
                discountCode.setStartDate(LocalDate.parse(newValue));
            } else if (field.equalsIgnoreCase("endDate")) {
                if (!newValue.matches("\\d{4}.\\d{2}.\\d{2}") || LocalDate.parse(newValue).isBefore(discountCode.getStartDate()))
                    throw new DiscountCodeCantBeEditedException("new end date value");
                discountCode.setEndDate(LocalDate.parse(newValue));
            } else if (field.equalsIgnoreCase("maxDiscountAmount")) {
                if (!newValue.matches("\\d{1,15}"))
                    throw new DiscountCodeCantBeEditedException("new discount code amount value");
                discountCode.setMaxDiscountAmount(Long.parseLong(newValue));
            } else if (field.equalsIgnoreCase("discountPercent")) {
                if (!newValue.matches("\\d{1,2}"))
                    throw new DiscountCodeCantBeEditedException("new discount percent value");
                discountCode.setDiscountPercent(Integer.parseInt(newValue));
            } else throw new DiscountCodeCantBeEditedException("field name for edit");
            Database.getInstance().saveItem(discountCode);
        } else throw new DiscountCodeNotFoundException();
    }

    public void removeCustomerFromDiscount(String code, String username) throws Exception {
        DiscountCode discountCode = Shop.getInstance().findDiscountCode(code);
        Customer customerToRemove = null;
        for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
            if (customer.getUsername().equals(username)) {
                customerToRemove = customer;
                break;
            }
        }
        if (customerToRemove != null) {
            customerToRemove.removeDiscountCode(discountCode);
            discountCode.removeCustomer(customerToRemove.getUsername());
            Database.getInstance().saveItem(discountCode);
            Database.getInstance().saveItem(customerToRemove);
        } else {
            throw new Exception("discount code does not include customer with this username");
        }
    }

    public void removeDiscountCode(String code) throws DiscountCodeNotFoundException, FileCantBeDeletedException, IOException, FileCantBeSavedException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
            throw new DiscountCodeNotFoundException();
        for (Customer customer : discountCode.getIncludedCustomers().keySet()) {
            customer.removeDiscountCode(discountCode);
        }
        Database.getInstance().deleteItem(discountCode);
        Shop.getInstance().removeDiscountCode(discountCode);
    }

//    public ArrayList<String> getAllRequestsInfo() {
//        ArrayList<String> requests = new ArrayList<>();
//        for (Request request : Shop.getInstance().getAllRequest()) {
//            requests.add(request.getBriefInfo());
//        }
//        return requests;
//    }

    public String viewRequestDetails(String requestId) throws RequestNotFoundException {
        Request request;
        if (requestId.length() > 15 || (request = Shop.getInstance().findRequestById(Long.parseLong(requestId))) == null)
            throw new RequestNotFoundException();
        return request.toString();
    }

    public void acceptRequest(String requestId) throws RequestNotFoundException, IOException, FileCantBeSavedException, FileCantBeDeletedException {
        Request request;
        if (requestId.length() > 15 || (request = Shop.getInstance().findRequestById(Long.parseLong(requestId))) == null)
            throw new RequestNotFoundException();
        request.acceptRequest();
        Shop.getInstance().removeRequest(request);
        Database.getInstance().deleteItem(request);
    }

    public void declineRequest(String requestId) throws RequestNotFoundException, FileCantBeDeletedException, IOException, FileCantBeSavedException {
        Request request;
        if (requestId.length() > 15 || (request = Shop.getInstance().findRequestById(Long.parseLong(requestId))) == null)
            throw new RequestNotFoundException();
        Shop.getInstance().removeRequest(request);
        Database.getInstance().deleteItem(request);
    }

//    public ArrayList<String> getAllCategories() {
//        ArrayList<String> categories = new ArrayList<>();
//        for (Category category : Shop.getInstance().getAllCategories()) {
//            categories.add(category.toString());
//        }
//        Collections.sort(categories);
//        return categories;
//    }

    public ArrayList<String> getCategorySubCatsNames(String categoryName) {
        ArrayList<String> subCategories = new ArrayList<>();
        for (SubCategory subCategory : Shop.getInstance().findCategoryByName(categoryName).getSubCategories()) {
            subCategories.add(subCategory.getName());
        }
        return subCategories;
    }

//    public ArrayList<String> getCategoryProperties(String categoryName) {
//        return Shop.getInstance().findCategoryByName(categoryName).getDetails();
//    }

    public void editCategory(String name, String property, String newValue)
            throws PropertyNotFoundException, IOException, FileCantBeSavedException {
        Category category = Shop.getInstance().findCategoryByName(name);
        for (int i = 0; i < category.getDetails().size(); i++) {
            if (category.getDetails().get(i).equalsIgnoreCase(property)) {
                category.getDetails().set(i, newValue);
                Database.getInstance().saveItem(category);
                updatePropertyForProducts(Shop.getInstance().findCategoryByName(name),property, newValue);
                return;
            }
        }
        throw new PropertyNotFoundException();
    }

    private void updatePropertyForProducts(Category category, String property, String newValue) throws IOException, FileCantBeSavedException {
        for (Good good : Shop.getInstance().getAllGoods()) {
            if (good.getSubCategory().getParentCategory().getName().equalsIgnoreCase(category.getName())) {
                String value = good.getCategoryProperties().get(property);
                good.getCategoryProperties().remove(property);
                good.getCategoryProperties().put(newValue, value);
                Database.getInstance().saveItem(good);
            }
        }
    }

    private void updatePropertySubCategoryForProducts(SubCategory subCategory, String property, String newValue) throws IOException, FileCantBeSavedException {
        for (Good good : Shop.getInstance().getAllGoods()) {
            if (good.getSubCategory().getName().equalsIgnoreCase(subCategory.getName())) {
                String value = good.getCategoryProperties().get(property);
                good.getCategoryProperties().remove(property);
                good.getCategoryProperties().put(newValue, value);
                Database.getInstance().saveItem(good);
            }
        }
    }

    public boolean isExistCategoryWithThisName(String name) {
        return Shop.getInstance().findCategoryByName(name) != null;
    }

    public boolean isExistSubcategoryWithThisName(String subcategoryName) {
        return Shop.getInstance().findSubCategoryByName(subcategoryName) != null;
    }

    public void addCategory(String name, ArrayList<String> properties) throws IOException, FileCantBeSavedException {
        Category category = new Category(name, properties);
        Shop.getInstance().addCategory(category);
        Database.getInstance().saveItem(category);
    }

    public void removeCategory(String categoryName)
            throws CategoryNotFoundException, FileCantBeDeletedException, IOException, FileCantBeSavedException {
        Category category;
        if ((category = Shop.getInstance().findCategoryByName(categoryName)) == null)
            throw new CategoryNotFoundException();
        Database.getInstance().deleteItem(category);
        Shop.getInstance().removeCategory(category);
    }

    public void addSubcategory(String categoryName, String subcategoryName, ArrayList<String> properties)
            throws IOException, FileCantBeSavedException {
        Category category = Shop.getInstance().findCategoryByName(categoryName);
        SubCategory subCategory = new SubCategory(subcategoryName, properties);
        category.addSubCategory(subCategory);
        Shop.getInstance().addSubCategory(subCategory);
        Database.getInstance().saveItem(category);
        Database.getInstance().saveItem(subCategory);
    }

    public void removeSubCategory(String categoryName, String subCategoryName)
            throws CategoryNotFoundException, SubCategoryNotFoundException, IOException, FileCantBeSavedException, FileCantBeDeletedException {
        Category category;
        if ((category = Shop.getInstance().findCategoryByName(categoryName)) == null)
            throw new CategoryNotFoundException();
        SubCategory subCategory;
        if ((subCategory = Shop.getInstance().findSubCategoryByName(subCategoryName)) == null)
            throw new SubCategoryNotFoundException();
        Database.getInstance().deleteItem(subCategory);
        category.deleteSubCategory(subCategory);
        Shop.getInstance().removeSubCategory(subCategory);
        Database.getInstance().saveItem(category);
    }

//    public ArrayList<String> getSubCategoryProperties(String subcategoryName) {
//        return Shop.getInstance().findSubCategoryByName(subcategoryName).getDetails();
//    }

    public void editSubcategory(String subCategoryName, String property, String newValue) throws PropertyNotFoundException, IOException, FileCantBeSavedException {
        SubCategory subCategory = Shop.getInstance().findSubCategoryByName(subCategoryName);
        for (int i = 0; i < subCategory.getDetails().size(); i++) {
            if (subCategory.getDetails().get(i).equalsIgnoreCase(property)) {
                subCategory.getDetails().set(i, newValue);
                Database.getInstance().saveItem(subCategory);
                updatePropertySubCategoryForProducts(Shop.getInstance().findSubCategoryByName(subCategoryName), property, newValue);
                return;
            }
        }
        throw new PropertyNotFoundException();
    }

//    public ArrayList<String> getAllUsersList() {
//        ArrayList<String> allUsersList = new ArrayList<>();
//        for (Person person : Shop.getInstance().getAllPersons()) {
//            allUsersList.add(person.getUsername());
//        }
//        Collections.sort(allUsersList, String.CASE_INSENSITIVE_ORDER);
//        return allUsersList;
//    }

//    public String viewUserInfo(String username) throws UsernameNotFoundException {
//        Person person;
//        if ((person = Shop.getInstance().findUser(username)) == null)
//            throw new UsernameNotFoundException();
//        return person.toString();
//    }

    public void removeUser(String username)
            throws UsernameNotFoundException, UserCantBeRemovedException, FileCantBeDeletedException, IOException, FileCantBeSavedException {
        Person person;
        if ((person = Shop.getInstance().findUser(username)) == null)
            throw new UsernameNotFoundException();
        if (person instanceof Manager)
            throw new UserCantBeRemovedException();
        Database.getInstance().deleteItem(person);
        Shop.getInstance().removePerson(person);
    }

    public void createManagerAccount(String username, ArrayList<String> details) throws UsernameIsTakenAlreadyException, IOException, FileCantBeSavedException {
        if (Shop.getInstance().findUser(username) != null) {
            throw new UsernameIsTakenAlreadyException();
        }
        Manager manager = new Manager(username, details.get(0), details.get(1), details.get(2), details.get(3), details.get(4));
        Shop.getInstance().addPerson(manager);
        Database.getInstance().saveItem(manager);
    }

//    public ArrayList<String> getAllGoodsInfo() {
//        ArrayList<String> info = new ArrayList<>();
//        for (Good good : Shop.getInstance().getAllGoods()) {
//            info.add("- good id: " + good.getGoodId() + " name: " + good.getName());
//        }
//        return info;
//    }

    public void createSupporter(String username, ArrayList<String> details) throws Exception{
        if (Shop.getInstance().findUser(username) != null) {
            throw new UsernameIsTakenAlreadyException();
        }
        Supporter supporter = new Supporter(username, details.get(0), details.get(1), details.get(2), details.get(3), details.get(4));
        Shop.getInstance().addPerson(supporter);
        Database.getInstance().saveItem(supporter);
    }

    public void removeProduct(String productId) throws ProductWithThisIdNotExist, FileCantBeDeletedException, IOException, FileCantBeSavedException {
        Good good = Shop.getInstance().findGoodById(Long.parseLong(productId));
        if (good == null)
            throw new ProductWithThisIdNotExist();
        Shop.getInstance().removeProductsFromOffs(good);
        Shop.getInstance().removeRatesOfAGood(good);
        Database.getInstance().deleteItem(good);
    }

    public List<String> getAllCategoriesName() {
        return Shop.getInstance().getAllCategories().stream().map(Category::getName).collect(Collectors.toList());
    }

    public List<String> getAllSubCategoriesNamesOfCategory(String category) {
        return Shop.getInstance().findCategoryByName(category).getSubCategories().stream().map(SubCategory::getName).collect(Collectors.toList());
    }

    public void addPropertyToCategory(String categoryName, String property) throws IOException, FileCantBeSavedException {
        Shop.getInstance().findCategoryByName(categoryName).getDetails().add(property);
        Database.getInstance().saveItem(Shop.getInstance().findCategoryByName(categoryName));
        for (Good good : Shop.getInstance().getAllGoods()) {
            if (good.getSubCategory().getParentCategory().getName().equalsIgnoreCase(categoryName)) {
                good.getCategoryProperties().put(property, "empty");
                Database.getInstance().saveItem(good);
            }
        }
    }

    public void addPropertyToSubCategory(String subCategoryName, String property) throws IOException, FileCantBeSavedException {
        Shop.getInstance().findSubCategoryByName(subCategoryName).getDetails().add(property);
        Database.getInstance().saveItem(Shop.getInstance().findSubCategoryByName(subCategoryName));
        for (Good good : Shop.getInstance().getAllGoods()) {
            if (good.getSubCategory().getName().equalsIgnoreCase(subCategoryName)) {
                good.getCategoryProperties().put(property, "empty");
                Database.getInstance().saveItem(good);
            }
        }
    }

    public void setBankingFeePercent(String percent){
        Shop.getInstance().getShopBankAccount().setBankingFeePercent(Integer.parseInt(percent));
    }

    public void setMinimumAmountForWallet(String minimumAmount){
        Shop.getInstance().getShopBankAccount().setMinimumAmount(Integer.parseInt(minimumAmount));
    }

    public List<String> getCustomersOrders(){
        HashMap<Long, Order> allOrders = Shop.getInstance().getAllOrders();
        List<String> customersOrder = new ArrayList<>();
        for (Order order : allOrders.values()) {
            if (order instanceof OrderForCustomer)
                customersOrder.add( order.briefString());
        }
        return customersOrder;
    }
}
