package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSupporter.AccountAreaForSupporter;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.RequestForServer;
import ApProject_OnlineShop.model.persons.Supporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;


public class AddCommentPage extends FxmlController {
    @FXML
    public TextField title, content;

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("commentsPage.fxml", "comments page");
    }

    public void onAddComment(ActionEvent actionEvent) {
        if (!title.getText().equals("") && !content.getText().equals("")) {
//            try {
//                MainController.getInstance().getProductController().addComment(title.getText(), content.getText());
            ArrayList<String> inputs2 = new ArrayList<>();
            inputs2.add(title.getText());
            inputs2.add(content.getText());
            inputs2.add(CommentsPage.getGoodId() + "");
            String serverResponse = connectToServer(new RequestForServer("ProductController", "getMainInfo", getToken(), inputs2));
            if (serverResponse.equals("comment request created successfully")) {
                SuccessPageFxController.showPage("comment request sent", "your comment will be added soon if managers accept it");
                setScene("commentsPage.fxml", "comments page");
            } else {
                ErrorPageFxController.showPage("error happened for adding comment", serverResponse);
            }
//            } catch (Exception e) {
//                ErrorPageFxController.showPage("error happened for adding comment", e.getMessage());
//            }
        } else {
            ErrorPageFxController.showPage("error happened", "fields should be filled!");
        }
    }

    public void onAccountAreaIconClicked(MouseEvent mouseEvent) {
        if (getCurrentPerson() == null) {
            LoginController.setPathBack("addComment.fxml", "add comment");
            LoginController.setPathAfterLogin(null, null);
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("addComment.fxml", "add comment");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("addComment.fxml", "add comment");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("addComment.fxml", "add comment");
            setScene("accountAreaForManager.fxml", "account area");
        }else if (getCurrentPerson() instanceof Supporter){
            AccountAreaForSupporter.setPathBack("addComment.fxml", "add comment");
            setScene("accountAreaForSupporter.fxml", "account area");
        }
    }
}
