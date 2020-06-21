package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AllProductsPage extends FxmlController implements Initializable {

    public RadioButton availableProducts;
    public RadioButton offProductsButton;
    public TextField nameFilterValue;
    public TextField sellerValueFilter;
    public TextField brandValueFilter;
    public TextField startPriceValue;
    public TextField endPriceValue;
    public VBox categoryRelatedVBox;
    public ChoiceBox category;
    public GridPane productsPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (MainController.getInstance().getControllerForFiltering().isAvailableProduct())
            availableProducts.setSelected(true);
        if (MainController.getInstance().getControllerForFiltering().isOffProductsFilter())
            offProductsButton.setSelected(true);
        category.setItems(FXCollections.observableList(MainController.getInstance().getAllProductsController().getAllCategories()));
        category.setStyle("-fx-background-color: #dab3ff;   -fx-background-radius: 8px;   -fx-margin: 4px 2px;  -fx-border-radius: 8px;  -fx-border-color: #600080; -fx-border-width: 2 2 2 2; -fx-text-color:#000000;");
        category.setValue(MainController.getInstance().getControllerForFiltering().getCategory());
        category.setOnAction(e -> MainController.getInstance().getControllerForFiltering().addCategoryFilter(category.getValue().toString()));
        if (MainController.getInstance().getControllerForFiltering().getCategory().equals("")){

        }
    }

    public void availableProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().removeAvailableProductsFilter();
        } else if (!MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().addAvailableProduct();
        }
        setScene("allProduct.fxml", "all products page");
    }

    public void offProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isOffProductsFilter()) {
            MainController.getInstance().getControllerForFiltering().removeOffProductsFilter();
        } else if (!MainController.getInstance().getControllerForFiltering().isOffProductsFilter()) {
            MainController.getInstance().getControllerForFiltering().setOffProductsFilter(); }
        setScene("allProduct.fxml", "all products page");
    }

    public void ableNameFilter() {
        MainController.getInstance().getControllerForFiltering().addNameFiltering(nameFilterValue.getText());
        setScene("allProduct.fxml", "all products page");
    }

    public void disableNameFilter() {
        MainController.getInstance().getControllerForFiltering().addNameFiltering("");
        setScene("allProduct.fxml", "all products page");
    }

    public void filterBySeller() {
        MainController.getInstance().getControllerForFiltering().addSellerFilter(sellerValueFilter.getText());
        setScene("allProduct.fxml", "all products page");
    }

    public void filterByBrand() {
        MainController.getInstance().getControllerForFiltering().addBrandFiltering(brandValueFilter.getText());
        setScene("allProduct.fxml", "all products page");
    }

    public void disableBrandFilter() {
        MainController.getInstance().getControllerForFiltering().addBrandFiltering("");
        setScene("allProduct.fxml", "all products page");
    }

    public void disableSellerFilter() {
        MainController.getInstance().getControllerForFiltering().addSellerFilter("");
        setScene("allProduct.fxml", "all products page");
    }

    public void filterByPrice() {
        if (Pattern.matches("[\\d]+", startPriceValue.getText()) && Pattern.matches("[\\d]+", endPriceValue.getText()))
            MainController.getInstance().getControllerForFiltering().addPriceFiltering(startPriceValue.getText(), endPriceValue.getText());
        else
            ErrorPageFxController.showPage("wrong price value", "price value must be number");
        setScene("allProduct.fxml", "all products page");
    }

    public void disablePriceFilter() {
        MainController.getInstance().getControllerForFiltering().disablePriceFiltering();
        setScene("allProduct.fxml", "all products page");
    }
}
