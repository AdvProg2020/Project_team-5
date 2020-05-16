package model.productThings;

import model.Shop;
import model.persons.Customer;

import java.time.LocalDate;
import java.util.HashMap;

public class DiscountCode {
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long maxDiscountAmount;
    private int discountPercent;
    private HashMap<Customer, Integer> includedCustomers;

    public DiscountCode(String code, LocalDate startDate, LocalDate endDate, Long maxDiscountAmount, int discountPercent) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscountAmount = maxDiscountAmount;
        this.discountPercent = discountPercent;
        this.includedCustomers = new HashMap<>();
    }

    public void addCustomerToCode(Customer customer, int numberOfUse) {
        this.includedCustomers.put(customer, numberOfUse);
        customer.addDiscountCode(this);
    }

    public void addAllCustomers (HashMap<Customer,Integer> allCustomers) {
        this.includedCustomers.putAll(allCustomers);
        for (Customer customer : allCustomers.keySet())
            customer.addDiscountCode(this);
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
        return maxDiscountAmount;
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
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void reduceNumberOfDiscountCodeForCostumer(Customer customer){
        includedCustomers.put(customer, includedCustomers.get(customer) - 1);
        if (includedCustomers.get(customer) == 0)
            includedCustomers.remove(customer);
    }

    public String detailedToString(){
        return "discount code : " + code + "\nstart date : " + startDate + "\nend date : " + endDate + "\nmaximum supported amount : "
                + maxDiscountAmount + "\ndiscount percent :" + discountPercent ;
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
                    includedCustomer.removeDiscountCode(this);
                }
                return;
            }
        }
        throw new Exception("CustomerNotFoundException");
    }



    public boolean isDiscountCodeExpired(){
        return LocalDate.now().isAfter(this.endDate);
    }

    public String getPrintableProperties() {
        return "code: " + code +
                "\nstartDate: " + startDate +
                "\nendDate: " + endDate +
                "\ndiscountPercent=" + discountPercent +
                "\n##################";
    }

    @Override
    public String toString() {
        return "code: " + code;
    }
}
