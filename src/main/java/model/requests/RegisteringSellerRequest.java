package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.persons.Company;
import model.persons.Seller;

import java.io.IOException;

public class RegisteringSellerRequest extends Request {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String companyName;
    private String companyWebsite;
    private String companyPhoneNumber;
    private String companyFaxNumber;
    private String companyAddress;


    public RegisteringSellerRequest(String username, String firstName, String lastName, String email, String phoneNumber,
                                    String password, String companyName, String companyWebsite, String companyPhoneNumber,
                                    String companyFaxNumber, String companyAddress) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.companyName = companyName;
        this.companyWebsite = companyWebsite;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyFaxNumber = companyFaxNumber;
        this.companyAddress = companyAddress;
    }


    @Override
    public String toString() {
        return "RegisteringSellerRequest\n" +
                "request id = " + super.getRequestId() +
                "\nseller Username = " + username + "\n";
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Company company = new Company(companyName, companyWebsite, companyPhoneNumber, companyFaxNumber, companyAddress);
        Seller seller = new Seller(username, firstName, lastName, email, phoneNumber, password, company);
        Shop.getInstance().addPerson(seller);
        Shop.getInstance().addCompany(company);
        Database.getInstance().saveItem(seller);
        Database.getInstance().saveItem(company);
    }
}
