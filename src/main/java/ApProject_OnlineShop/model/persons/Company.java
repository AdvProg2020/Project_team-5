package ApProject_OnlineShop.model.persons;

import javax.persistence.*;

@Entity
@Table(name = "Company")
public class Company {
    @Transient
    private static long companyIdCounter = 1;

    @Id
    @Column(name = "CompanyID", unique = true)
    private long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Website")
    private String website;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "FaxNumber")
    private String faxNumber;

    @Column(name = "Address")
    private String address;

    public Company(String name, String website, String phoneNumber, String faxNumber, String address) {
        this.id = companyIdCounter++;
        this.name = name;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.address = address;
    }

    public Company() {
    }

    public static void setCompanyIdCounter(long companyIdCounter) {
        Company.companyIdCounter = companyIdCounter;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "["  + name + ", " + website + ", " + phoneNumber + ", " + faxNumber + ", " + address + "]";
    }
}
