package modelTest.productThingsTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.controller.products.ProductController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.requests.AddingCommentRequest;
import ApProject_OnlineShop.model.requests.Request;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CommentTest {
    SubCategory subCategory = new SubCategory("a", new ArrayList<>());
    Company company=new Company("salam","asfs","asdasd","addasd","999");
    Seller seller = new Seller("aa","","", "", "","",company);
    Good good = new Good("laptop", "app", subCategory, "", new HashMap<>(), seller, 200, 3);
    Customer customer = new CustomerTest("aa","","","", "", "dd", 890L);

    @Test
    public void commentTesting(){
        MainController.getInstance().setCurrentPerson(customer);
        ProductController controller = new ProductController();
        controller.setGood(good);
        try {
            controller.addComment("hi","bb");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (Request request : Shop.getInstance().getAllRequest()) {
            if (request instanceof AddingCommentRequest){
                try {
                    request.acceptRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                assertTrue(true);
                Shop.getInstance().removeRequest(request);
                break;
            }
        }
        Shop.getInstance().removePerson(customer);
    }
}

class CustomerTest extends Customer{
    public CustomerTest(String username, String firstName, String lastName, String email, String phoneNumber, String password, long credit) {
        super(username, firstName, lastName, email, phoneNumber, password, credit);
        Shop.getInstance().addPerson(this);
    }
}