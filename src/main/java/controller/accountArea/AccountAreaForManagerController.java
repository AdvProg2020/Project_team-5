package controller.accountArea;

import exception.*;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Customer;
import model.persons.Person;
import model.productThings.DiscountCode;
import model.requests.Request;

import java.time.LocalDate;
import java.util.ArrayList;

public class AccountAreaForManagerController extends AccountAreaController {
    public void createNewDiscountCode(ArrayList<String> fields) throws DiscountCodeCantCreatedException {
        if (fields.get(0).length() > 15)
            throw new DiscountCodeCantCreatedException("code length");
        if (LocalDate.parse(fields.get(1)).isBefore(LocalDate.now()))
            throw new DiscountCodeCantCreatedException("start date");
        if (LocalDate.parse(fields.get(2)).isBefore(LocalDate.now()))
            throw new DiscountCodeCantCreatedException("end date");
        if (Integer.parseInt(fields.get(4)) > 100 && Integer.parseInt(fields.get(4)) <= 0)
            throw new DiscountCodeCantCreatedException("discount percent");
        Shop.getInstance().addDiscountCode(new DiscountCode(fields.get(0), LocalDate.parse(fields.get(1)), LocalDate.parse(fields.get(2)),
                Long.parseLong(fields.get(3)), Integer.parseInt(fields.get(4))));
    }

    public void addIncludedCustomerToDiscountCode(String code, String username, String numberOfUse)
            throws DiscountCodeCantCreatedException, UsernameNotFoundException, DiscountCodeNotFoundException {
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
    }

    public ArrayList<String> getAllDiscountCodesInfo() {
        ArrayList<String> allDiscountCodes = new ArrayList<>();
        for (DiscountCode discountCode : Shop.getInstance().getAllDiscountCodes()) {
            allDiscountCodes.add(discountCode.toString());
        }
        return allDiscountCodes;
    }

    public String viewDiscountCode(String code) throws DiscountCodeNotFoundException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
            throw new DiscountCodeNotFoundException();
        return discountCode.getPrintableProperties();
    }

    public void editDiscountCode(String code, String field, String newValue)
            throws DiscountCodeNotFoundException, DiscountCodeCantBeEditedException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) != null) {
            if (field.equalsIgnoreCase("startDate")) {
                if (!newValue.matches("\\d{4}-\\d{2}-\\d{2}") || LocalDate.parse(newValue).isBefore(LocalDate.now()))
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
        } else throw new DiscountCodeNotFoundException();
    }

    public void removeDiscountCode(String code) throws DiscountCodeNotFoundException {
        DiscountCode discountCode;
        if ((discountCode = Shop.getInstance().findDiscountCode(code)) == null)
            throw new DiscountCodeNotFoundException();
        for (Customer customer : discountCode.getIncludedCustomers().keySet())
            customer.removeDiscountCode(discountCode);
        Shop.getInstance().removeDiscountCode(discountCode);
        //Delete discount code from external files
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

    public void acceptRequest(String requestId) throws RequestNotFoundException {
        Request request;
        if (requestId.length() > 15 || (request = Shop.getInstance().findRequestById(Long.parseLong(requestId))) == null)
            throw new RequestNotFoundException();
        request.acceptRequest();
        Shop.getInstance().removeRequest(request);
    }

    public void declineRequest(String requestId) throws RequestNotFoundException {
        Request request;
        if (requestId.length() > 15 || (request = Shop.getInstance().findRequestById(Long.parseLong(requestId))) == null)
            throw new RequestNotFoundException();
        Shop.getInstance().removeRequest(request);
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Shop.getInstance().getAllCategories()) {
            categories.add(category.toString());
        }
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

    public void editCategory(String name, String property, String newValue) throws PropertyOfCategoryNotFound {
        Category category = Shop.getInstance().findCategoryByName(name);
        for (int i = 0; i < category.getDetails().size(); i++) {
            if (category.getDetails().get(i).equalsIgnoreCase(property)) {
                category.getDetails().add(i, newValue);
                return;
            }
        }
        throw new PropertyOfCategoryNotFound();
    }

    public boolean isExistCategoryWithThisName(String name) {
        return Shop.getInstance().findCategoryByName(name) != null;
    }

    public boolean isExistSubcategoryWithThisName(String subcategoryName) {
        return Shop.getInstance().findSubCategoryByName(subcategoryName) != null;
    }

    public void addCategory(String name, ArrayList<String> properties) {
        Category category = new Category(name, properties, null);
        Shop.getInstance().addCategory(category);
    }

    public void removeCategory(String categoryName) throws CategoryNotFoundException {
        Category category;
        if ((category = Shop.getInstance().findCategoryByName(categoryName)) == null)
            throw new  CategoryNotFoundException();
        Shop.getInstance().removeCategory(category);
    }

    public void addSubcategory(String categoryName, String subcategoryName, ArrayList<String> properties) {
        Category category = Shop.getInstance().findCategoryByName(categoryName);
        SubCategory subCategory = new SubCategory(subcategoryName, properties);
        category.addSubCategory(subCategory);
    }

    public void removeSubCategory(String categoryName, String subCategoryName)
            throws CategoryNotFoundException, SubCategoryNotFoundException {
        Category category;
        if ((category = Shop.getInstance().findCategoryByName(categoryName)) == null)
            throw new  CategoryNotFoundException();
        SubCategory subCategory;
        if ((subCategory = Shop.getInstance().findSubCategoryByName(subCategoryName)) == null)
            throw new SubCategoryNotFoundException();
        category.deleteSubCategory(subCategory);
    }
}
