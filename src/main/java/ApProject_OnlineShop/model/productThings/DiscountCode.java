package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Discount")
public class DiscountCode implements Serializable {
    @Transient
    private static long discountCodeCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountId", nullable = false, unique = true)
    @Expose
    private long id;

    @Column(name = "DiscountCode", nullable = false, unique = true)
    @Expose
    private String code;

    @Column(name = "StartDate", nullable = false)
    @Expose
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    @Expose
    private LocalDate endDate;

    @Column(name = "MaxDiscountAmount", nullable = false)
    @Expose
    private Long maxDiscountAmount;

    @Column(name = "Percent", nullable = false)
    @Expose
    private int discountPercent;

    @ElementCollection(fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @CollectionTable(name = "DiscountPerson", joinColumns = @JoinColumn(name = "DiscountId"))
    @Column(name = "RemainingNumber", nullable = false)
    @MapKeyJoinColumn(name = "CustomerId")
    private Map<Customer, Integer> includedCustomers;

    public DiscountCode(String code, LocalDate startDate, LocalDate endDate, Long maxDiscountAmount, int discountPercent) {
        discountCodeCount++;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDiscountAmount = maxDiscountAmount;
        this.discountPercent = discountPercent;
        this.includedCustomers = new HashMap<>();
    }

    public DiscountCode() {
        this.includedCustomers = new HashMap<>();
    }

    public void addCustomerToCode(Customer customer, int numberOfUse) {
        this.includedCustomers.put(customer, numberOfUse);
        customer.addDiscountCode(this);
    }

    public long getId() {
        return id;
    }

    public void addAllCustomers(HashMap<Customer, Integer> allCustomers) throws IOException, FileCantBeSavedException {
        for (Customer customer : allCustomers.keySet()) {
            this.includedCustomers.put(customer, allCustomers.get(customer));
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

    public static long getDiscountCodeCount() {
        return discountCodeCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIncludedCustomers(HashMap<Customer, Integer> includedCustomers) {
        this.includedCustomers = includedCustomers;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public Map<Customer, Integer> getIncludedCustomers() {
        return this.includedCustomers;
        /*HashMap<Customer, Integer> includedCustomers2 = new HashMap<>();
        for (Customer customer : this.includedCustomers.keySet()) {
            includedCustomers2.put((Customer) Shop.getInstance().findUser(customerUserName),
                    this.includedCustomers.get(customerUserName));
        }
        return includedCustomers2;*/
    }

    public void removeCustomer(String username) {
        this.includedCustomers.remove((Customer) Shop.getInstance().findUser(username));
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
                includedCustomers.put(customers, includedCustomers.get(customers) - 1);
                if (includedCustomers.get(customers) == 0) {
                    customer.removeDiscountCode(this);
                    includedCustomers.remove(customers);
                }
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
                int remainedNumberOfUse = includedCustomers.get(includedCustomer);
                if (remainedNumberOfUse > 1) {
                    includedCustomers.replace(includedCustomer, remainedNumberOfUse - 1);
                } else {
                    includedCustomers.remove(includedCustomer);
                    includedCustomer.removeDiscountCode(this);
                    if (includedCustomers.size() == 0) {
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
