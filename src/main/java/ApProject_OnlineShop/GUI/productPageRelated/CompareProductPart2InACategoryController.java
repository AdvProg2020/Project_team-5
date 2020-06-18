package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CompareProductPart2InACategoryController extends FxmlController implements Initializable {
    private static long productId1;
    private long productId2;
    public ImageView image1, image2;
    public GridPane gridpane;
    public VBox vbox1, vbox2;
    public Label name1, name2, brand1, brand2, rate1, rate2, subCategory1, subCategory2, date1, date2, seenNumber1, seenNumber2, sellerNumber1, sellerNumber2, price1, price2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId2 = MainController.getInstance().getProductController().getGood().getGoodId();
        image1.setImage(new Image(Paths.get("Resources/productImages/" + productId1 + ".jpg").toUri().toString()));
        image2.setImage(new Image(Paths.get("Resources/productImages/" + productId2 + ".jpg").toUri().toString()));
        ArrayList<String> details = MainController.getInstance().getProductController().compareWithAnotherProductGUI(productId1);
        name1.setText(details.get(0));
        name2.setText(details.get(1));
        brand1.setText(details.get(2));
        brand2.setText(details.get(3));
        rate1.setText(details.get(4));
        rate2.setText(details.get(5));
        subCategory1.setText(details.get(6));
        subCategory2.setText(details.get(7));
        date1.setText(details.get(8));
        date2.setText(details.get(9));
        seenNumber1.setText(details.get(10));
        seenNumber2.setText(details.get(11));
        sellerNumber1.setText(details.get(12));
        sellerNumber2.setText(details.get(13));
        price1.setText(details.get(14) + " Rials");
        price2.setText(details.get(15) + " Rials");
        setLabelStyle(name1);
        setLabelStyle(name2);
        setLabelStyle(brand1);
        setLabelStyle(brand2);
        setLabelStyle(rate1);
        setLabelStyle(rate2);
        setLabelStyle(subCategory1);
        setLabelStyle(subCategory2);
        setLabelStyle(date1);
        setLabelStyle(date2);
        setLabelStyle(seenNumber1);
        setLabelStyle(seenNumber2);
        setLabelStyle(sellerNumber1);
        setLabelStyle(sellerNumber2);
        setLabelStyle(price1);
        setLabelStyle(price2);
        addCategoryProperties();
    }

    private void addCategoryProperties() {
        HashMap<String, String> categoryPropertiesGood2 = Shop.getInstance().findGoodById(productId2).getCategoryProperties();
        HashMap<String, String> categoryPropertiesGood1 = Shop.getInstance().findGoodById(productId1).getCategoryProperties();
        for (String categoryProperties : categoryPropertiesGood1.keySet()) {
            
        }
    }

    public void goToAccountArea(MouseEvent mouseEvent) {
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

    public void backButton(ActionEvent actionEvent) {
        setScene("allProductsForCompareProduct.fxml", "compare products");
    }

    public static long getProductId1() {
        return productId1;
    }

    public static void setProductId1(long productId1) {
        CompareProductPart2InACategoryController.productId1 = productId1;
    }

    private void setLabelStyle(Label label) {
        label.setFont(Font.font("Times New Roman", 14));
    }
}
