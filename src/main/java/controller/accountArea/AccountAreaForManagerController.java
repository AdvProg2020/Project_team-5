package controller.accountArea;

import controller.MainController;
import exception.*;
import exception.categoryExceptions.CategoryNotFoundException;
import exception.categoryExceptions.SubCategoryNotFoundException;
import exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
import exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import exception.discountcodeExceptions.DiscountCodeNotFoundException;
import exception.productExceptions.ProductWithThisIdNotExist;
import exception.userExceptions.UserCantBeRemovedException;
import exception.userExceptions.UsernameIsTakenAlreadyException;
import exception.userExceptions.UsernameNotFoundException;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.database.Database;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Person;
import model.productThings.DiscountCode;
import model.productThings.Good;
import model.requests.Request;

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
        Database.getInstance().saveItem(discountCode);
    }

    private ArrayList<String> getAllDiscountCodesInfo(ArrayList<DiscountCode> discountCodes) {
        ArrayList<String> allDiscountCodes = new ArrayList<>();
        for (DiscountCode discountCode : discountCodes) {
            allDiscountCodes.add(discountCode.toString());
        }
        return allDiscountCodes;
    }

    public ArrayList<String> getAllDiscountCodeWithSort(int input) {
        ArrayList<DiscountCode> discountCodes = new ArrayList<>();
        if (input == 0)
            discountCodes = Shop.getInstance().getAllDiscountCodes();
        if (input == 1)
            discountCodes = MainController.getInstance().getSortController().
                    sortByDiscountPercent(Shop.getInstance().getAllDiscountCodes());
        if(input ==2)
            discountCodes = MainController.getInstance().getSortController().
                    sortByEndDate(Shop.getInstance().getAllDiscountCodes());
        if (input == 3)
            discountCodes = MainController.getInstance().getSortController().
                    sortByMaxDiscountAmount(Shop.getInstance().getAllDiscountCodes());
        return getAllDiscountCodesInfo(discountCodes);
    }

    public String viewDiscountCode(String code) throws DiscountCodeNotFoundException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
            throw new DiscountCodeNotFoundException();
        return discountCode.getPrintableProperties();
    }

    public void editDiscountCode(String code, String field, String newValue)
            throws DiscountCodeNotFoundException, DiscountCodeCantBeEditedException, IOException, FileCantBeSavedException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) != null) {
            if (field.equalsIgnoreCase("startDate")) {
                if (!newValue.matches("\\d{4}-\\d{2}-\\d{2}") || LocalDate.parse(newValue).isBefore(LocalDate.now()) || LocalDate.parse(newValue).isAfter(discountCode.getEndDate()))
                    throw new DiscountCodeCantBeEditedException("new start date value");
                discountCode.setStartDate(LocalDate.parse(newValue));
            } else if (field.equalsIgnoreCase("endDate")) {
                if (!newValue.matches("\\d{4}-\\d{2}-\\d{2}") || LocalDate.parse(newValue).isBefore(discountCode.getStartDate()))
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

    public void removeDiscountCode(String code) throws DiscountCodeNotFoundException, FileCantBeDeletedException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
            throw new DiscountCodeNotFoundException();
        for (Customer customer : discountCode.getIncludedCustomers().keySet())
            customer.removeDiscountCode(discountCode);
        Shop.getInstance().removeDiscountCode(discountCode);
        Database.getInstance().deleteItem(discountCode);
    }

    public ArrayList<String> getAllRequestsInfo() {
        ArrayList<String> requests = new ArrayList<>();
        for (Request request : Shop.getInstance().getAllRequest()) {
            requests.add(request.getBriefInfo());
        }
        return requests;
    }

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

    public void declineRequest(String requestId) throws RequestNotFoundException, IOException, FileCantBeDeletedException {
        Request request;
        if (requestId.length() > 15 || (request = Shop.getInstance().findRequestById(Long.parseLong(requestId))) == null)
            throw new RequestNotFoundException();
        Shop.getInstance().removeRequest(request);
        Database.getInstance().deleteItem(request);
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Shop.getInstance().getAllCategories()) {
            categories.add(category.toString());
        }
        Collections.sort(categories);
        return categories;
    }

    public ArrayList<String> getCategorySubCatsNames(String categoryName) {
        ArrayList<String> subCategories = new ArrayList<>();
        for (SubCategory subCategory : Shop.getInstance().findCategoryByName(categoryName).getSubCategories()) {
            subCategories.add(subCategory.getName());
        }
        return subCategories;
    }

    public ArrayList<String> getCategoryProperties(String categoryName) {
        return Shop.getInstance().findCategoryByName(categoryName).getDetails();
    }

    public void editCategory(String name, String property, String newValue)
            throws PropertyNotFoundException, IOException, FileCantBeSavedException {
        Category category = Shop.getInstance().findCategoryByName(name);
        for (int i = 0; i < category.getDetails().size(); i++) {
            if (category.getDetails().get(i).equalsIgnoreCase(property)) {
                category.getDetails().set(i, newValue);
                Database.getInstance().saveItem(category);
                return;
            }
        }
        throw new PropertyNotFoundException();
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
            throws CategoryNotFoundException, FileCantBeDeletedException {
        Category category;
        if ((category = Shop.getInstance().findCategoryByName(categoryName)) == null)
            throw new CategoryNotFoundException();
        Shop.getInstance().removeCategory(category);
        Database.getInstance().deleteItem(category);
    }

    public void addSubcategory(String categoryName, String subcategoryName, ArrayList<String> properties)
            throws IOException, FileCantBeSavedException {
        Category category = Shop.getInstance().findCategoryByName(categoryName);
        SubCategory subCategory = new SubCategory(subcategoryName, properties);
        category.addSubCategory(subCategory);
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
        category.deleteSubCategory(subCategory);
        Database.getInstance().saveItem(category);
        Database.getInstance().deleteItem(subCategory);
    }

    public ArrayList<String> getSubCategoryProperties(String subcategoryName) {
        return Shop.getInstance().findSubCategoryByName(subcategoryName).getDetails();
    }

    public void editSubcategory(String subCategoryName, String property, String newValue) throws PropertyNotFoundException, IOException, FileCantBeSavedException {
        SubCategory subCategory = Shop.getInstance().findSubCategoryByName(subCategoryName);
        for (int i = 0; i < subCategory.getDetails().size(); i++) {
            if (subCategory.getDetails().get(i).equalsIgnoreCase(property)) {
                subCategory.getDetails().add(i, newValue);
                Database.getInstance().saveItem(subCategory.getParentCategory());
                Database.getInstance().saveItem(subCategory);
                return;
            }
        }
        throw new PropertyNotFoundException();
    }

    public ArrayList<String> getAllUsersList() {
        ArrayList<String> allUsersList = new ArrayList<>();
        for (Person person : Shop.getInstance().getAllPersons()) {
            allUsersList.add(person.getUsername());
        }
        Collections.sort(allUsersList, String.CASE_INSENSITIVE_ORDER);
        return allUsersList;
    }

    public String viewUserInfo(String username) throws UsernameNotFoundException {
        Person person;
        if ((person = Shop.getInstance().findUser(username)) == null)
            throw new UsernameNotFoundException();
        return person.toString();
    }

    public void removeUser(String username)
            throws UsernameNotFoundException, UserCantBeRemovedException, FileCantBeDeletedException {
        Person person;
        if ((person = Shop.getInstance().findUser(username)) == null)
            throw new UsernameNotFoundException();
        if (person instanceof Manager)
            throw new UserCantBeRemovedException();
        Shop.getInstance().removePerson(person);
        Database.getInstance().deleteItem(person);
    }

    public void createManagerAccount(String username, ArrayList<String> details) throws UsernameIsTakenAlreadyException, IOException, FileCantBeSavedException {
        if (Shop.getInstance().findUser(username) != null) {
            throw new UsernameIsTakenAlreadyException();
        }
        Manager manager = new Manager(username, details.get(0), details.get(1), details.get(2), details.get(3), details.get(4));
        Shop.getInstance().addPerson(manager);
        Database.getInstance().saveItem(manager);
    }

    public void removeProduct(String productId) throws ProductWithThisIdNotExist, FileCantBeDeletedException {
        Good good = Shop.getInstance().findGoodById(Long.parseLong(productId));
        if (good == null)
            throw new ProductWithThisIdNotExist();
        Shop.getInstance().removeProduct(good);
        Database.getInstance().deleteItem(good);
    }

    public ArrayList<String> sortUsers() {
        ArrayList<String> allUsers = getAllUsersList();
        allUsers.sort(String::compareTo);
        return allUsers;
    }

    public List<String> sortDiscountCodes(String field) throws Exception {
        ArrayList<DiscountCode> discountCodes = Shop.getInstance().getAllDiscountCodes();
        if (field.equalsIgnoreCase("startDate")) {
            discountCodes.sort((discount1, discount2) -> {
                if (discount1.getStartDate().isAfter(discount2.getStartDate()))
                    return 1;
                else if (discount1.getStartDate().isBefore(discount2.getStartDate()))
                    return -1;
                else
                    return 0;
            });
        } else if (field.equalsIgnoreCase("endDate")) {
            discountCodes.sort((discount1, discount2) -> {
                if (discount1.getEndDate().isAfter(discount2.getEndDate()))
                    return 1;
                else if (discount1.getEndDate().isBefore(discount2.getEndDate()))
                    return -1;
                else
                    return 0;
            });
        } else if (field.equalsIgnoreCase("maxDiscountAmount")) {
            discountCodes.sort(Comparator.comparing(DiscountCode::getMaxDiscountAmount));
        } else if (field.equalsIgnoreCase("discountPercent")) {
            discountCodes.sort(Comparator.comparing(DiscountCode::getDiscountPercent));
        } else throw new Exception("invalid field selected.");
        return discountCodes.stream().map(DiscountCode::getCode).collect(Collectors.toList());
    }
}
