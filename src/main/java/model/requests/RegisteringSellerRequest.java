package model.requests;

import model.Shop;
import model.persons.Seller;

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
                "seller Username : " + seller.getUsername() + "\n";
    }

    @Override
    public void acceptRequest() {
        Shop.getInstance().addPerson(seller);
    }
}
