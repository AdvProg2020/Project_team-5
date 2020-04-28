package model.productThings;

import controller.MainController;
import exception.DiscountCodeNotFoundException;
import exception.UsernameNotFoundException;
import model.Shop;
import model.persons.Customer;
import model.persons.Person;

import java.time.LocalDate;
import java.util.HashMap;

public class DiscountCode {
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long MaxDiscountAmount;
    private int discountPercent;
    private HashMap<Customer, Integer> includedCustomers;

    public DiscountCode(String code, LocalDate startDate, LocalDate endDate, Long maxDiscountAmount, int discountPercent, HashMap<Customer, Integer> includedCustomers) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.MaxDiscountAmount = maxDiscountAmount;
        this.discountPercent = discountPercent;
        this.includedCustomers = includedCustomers;
        Shop.getInstance().addDiscountCode(this);
    }

    public static void addCustomerToCode(String code, String customerUsername, int numberOfUse) throws DiscountCodeNotFoundException, UsernameNotFoundException {
        DiscountCode discountCode = Shop.getInstance().findDiscountCode(code);
        if (discountCode != null) {
            Person person = Shop.getInstance().findUser(customerUsername);
            if (person instanceof Customer) {
                discountCode.includedCustomers.put((Customer)person, numberOfUse);
            } else throw new UsernameNotFoundException();
        } else throw new DiscountCodeNotFoundException();
    }

    public String getCode() {
        return code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getMaxDiscountAmount() {
        return MaxDiscountAmount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public HashMap<Customer, Integer> getIncludedCustomers() {
        return includedCustomers;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setMaxDiscountAmount(Long maxDiscountAmount) {
        MaxDiscountAmount = maxDiscountAmount;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public static String generateRandomDiscountCode() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder randomCode = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            randomCode.append(AlphaNumericString.charAt(index));
        }
        return randomCode.toString();
    }

    public void discountBeUsedForCustomer(Customer customer) throws Exception {
        for (Customer includedCustomer : includedCustomers.keySet()) {
            if (includedCustomer.equals(customer)) {
                int remainedNumberOfUse = includedCustomers.get(includedCustomer);
                if (remainedNumberOfUse > 1) {
                    includedCustomers.replace(includedCustomer, remainedNumberOfUse - 1);
                } else {
                    includedCustomers.remove(includedCustomer);
                }
                return;
            }
        }
        throw new Exception("CustomerNotFoundException");
    }
}
