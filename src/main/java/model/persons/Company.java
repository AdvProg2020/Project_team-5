package model.persons;

public class Company {
    private static long companyIdCounter = 1;
    private long id;
    private String name;
    private String website;
    private String phoneNumber;
    private String faxNumber;
    private String address;

    public Company(String name, String website, String phoneNumber, String faxNumber, String address) {
        this.id = companyIdCounter++;
        this.name = name;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.address = address;
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

    @Override
    public String toString() {
        return "############\n" +
                "Company" + "\n" +
                "name = " + name + "\n" +
                "website = " + website + "\n" +
                "phoneNumber = " + phoneNumber + "\n" +
                "faxNumber = " + faxNumber + "\n" +
                "address = " + address + "\n############";
    }
}
