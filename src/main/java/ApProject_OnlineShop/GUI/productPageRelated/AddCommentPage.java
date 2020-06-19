package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class AddCommentPage extends FxmlController {
    @FXML
    public TextField title, content;

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("commentsPage.fxml","comments page");
    }

    public void onAddComment(ActionEvent actionEvent) {
        if (!title.getText().equals("") && !content.getText().equals("")) {
            try {
                MainController.getInstance().getProductController().addComment(title.getText(), content.getText());
                SuccessPageFxController.showPage("comment request sent", "your comment will be added soon if managers accept it");
                setScene("commentsPage.fxml","comments page");
            } catch (Exception e) {
                ErrorPageFxController.showPage("error happened for adding comment", e.getMessage());
            }
        } else {
            ErrorPageFxController.showPage("error happened","fields should be filled!");
        }
    }

    public void onAccountAreaIconClicked(MouseEvent mouseEvent) {
        if (MainController.getInstance().getCurrentPerson() == null) {
            setScene("login.fxml", "login");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
            setScene("accountAreaForManager.fxml", "account area");
        }
    }
}
