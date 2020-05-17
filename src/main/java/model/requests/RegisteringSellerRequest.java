package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.persons.Seller;

import java.io.IOException;

public class RegisteringSellerRequest extends Request {
    private Seller seller;

    public RegisteringSellerRequest(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }

    @Override
    public String toString() {
        return "RegisteringSellerRequest\n" +
                "request id = " + super.getRequestId() +
                "\nseller Username = " + seller.getUsername() + "\n";
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Shop.getInstance().addPerson(seller);
       // Database.getInstance().saveItem(this.seller);
    }
}
