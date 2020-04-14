package model.persons;

public class Company {
    private String name;
    private String website;
    private String phoneNumber;
    private String faxNumber;
    private String address;

    public Company(String name, String website, String phoneNumber, String faxNumber, String address) {
        this.name = name;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        return "############\n" +
                "Company" + "\n" +
                "name = " + name + "\n" +
                "website = " + website + "\n" +
                "phoneNumber = " + phoneNumber + "\n" +
                "faxNumber = " + faxNumber + "\n" +
                "address = " + address + "############";
    }
}
