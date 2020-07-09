package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscountCode {
    private static long discountCodeCount = 1;
    private long id;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long maxDiscountAmount;
    private int discountPercent;
    private HashMap<String, Integer> includedCustomers;

    public DiscountCode(String code, LocalDate startDate, LocalDate endDate, Long maxDiscountAmount, int discountPercent) {
        this.id = discountCodeCount++;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscountAmount = maxDiscountAmount;
        this.discountPercent = discountPercent;
        this.includedCustomers = new HashMap<>();
    }

    public HashMap<String, Integer> getOriginalIncludedCustomers() {
        return this.includedCustomers;
    }

    public void addCustomerToCode(Customer customer, int numberOfUse) {
        this.includedCustomers.put(customer.getUsername(), numberOfUse);
        customer.addDiscountCode(this);
    }

    public long getId() {
        return id;
    }

    public void addAllCustomers(HashMap<Customer, Integer> allCustomers) throws IOException, FileCantBeSavedException {
        for (Customer customer : allCustomers.keySet()) {
            this.includedCustomers.put(customer.getUsername(), allCustomers.get(customer));
            Database.getInstance().saveItem(this);
        }
        for (Customer customer : allCustomers.keySet()) {
            customer.addDiscountCode(this);
            Database.getInstance().saveItem(customer);
        }
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

    public static void setDiscountCodeCount(long discountCodeCount) {
        DiscountCode.discountCodeCount = discountCodeCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public HashMap<Customer, Integer> getIncludedCustomers() {
        HashMap<Customer, Integer> includedCustomers2 = new HashMap<>();
        for (String customerUserName : this.includedCustomers.keySet()) {
            includedCustomers2.put((Customer) Shop.getInstance().findUser(customerUserName),
                    this.includedCustomers.get(customerUserName));
        }
        return includedCustomers2;
    }

    public void removeCustomer(String username) {
        this.includedCustomers.remove(username);
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

    public void reduceNumberOfDiscountCodeForCostumer(Customer customer) throws IOException, FileCantBeSavedException {
        for (Customer customers : this.getIncludedCustomers().keySet()) {
            if (customers.getUsername().equals(customer.getUsername())) {
                includedCustomers.put(customers.getUsername(), includedCustomers.get(customers.getUsername()) - 1);
                if (includedCustomers.get(customers.getUsername()) == 0){
                    customer.removeDiscountCode(this);
                    includedCustomers.remove(customers.getUsername());}
                Database.getInstance().saveItem(this);
                Database.getInstance().saveItem(customer);
            }
        }
    }

    @Override
    public String toString() {
        return "discount code:" + code + "  \t start date: " + getStartDate().toString() + "  \t end date: " + endDate;
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
        for (Customer includedCustomer : this.getIncludedCustomers().keySet()) {
            if (includedCustomer.equals(customer)) {
                int remainedNumberOfUse = includedCustomers.get(includedCustomer.getUsername());
                if (remainedNumberOfUse > 1) {
                    includedCustomers.replace(includedCustomer.getUsername(), remainedNumberOfUse - 1);
                } else {
                    includedCustomers.remove(includedCustomer.getUsername());
                    includedCustomer.removeDiscountCode(this);
                    if(includedCustomers.size() == 0) {
                        Database.getInstance().deleteItem(this);
                        Shop.getInstance().removeDiscountCode(this);
                        Database.getInstance().saveItem(customer);
                        return;
                    }
                }
                Database.getInstance().saveItem(this);
                Database.getInstance().saveItem(customer);
                return;
            }
        }
        throw new Exception("CustomerNotFoundException");
    }


    public boolean isDiscountCodeExpired() {
        return LocalDate.now().isAfter(this.endDate);
    }

    public String getPrintableProperties() {
        return "code: " + code +
                "\nstartDate: " + startDate +
                "\nendDate: " + endDate +
                "\ndiscountPercent=" + discountPercent +
                "\n##################";
    }

    public List<String> getAllDetails() {
        ArrayList<String> details = new ArrayList<>();
        details.add(getCode());
        details.add(getStartDate().toString());
        details.add(getEndDate().toString());
        details.add("" + getDiscountPercent());
        details.add(getMaxDiscountAmount().toString());
        return details;
    }
}
