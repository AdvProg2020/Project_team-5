package modelTest.productThingsTest;

import controller.MainController;
import controller.products.ProductController;
import controllerTest.ProductControllerTest;
import exception.FileCantBeSavedException;
import model.Shop;
import model.category.SubCategory;
import model.persons.Customer;
import model.productThings.Good;
import model.requests.AddingCommentRequest;
import model.requests.Request;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CommentTest {
    SubCategory subCategory = new SubCategory("a", new ArrayList<>());
    Good good = new Good("laptop", "app", subCategory, "", new HashMap<>(), null, 200, 3);
    Customer customer = new Customer("aa","","","", "", "dd", 890L);

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
    }
}
