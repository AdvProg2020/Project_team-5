package controller.accountArea;

import exception.DiscountCodeCantBeEditedException;
import exception.DiscountCodeCantCreatedException;
import exception.DiscountCodeNotFoundException;
import exception.UsernameNotFoundException;
import model.Shop;
import model.persons.Customer;
import model.persons.Person;
import model.productThings.DiscountCode;

import java.nio.file.DirectoryIteratorException;
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
}
