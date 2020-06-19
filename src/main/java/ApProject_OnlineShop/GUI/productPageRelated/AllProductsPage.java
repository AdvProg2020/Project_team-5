package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.controller.MainController;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AllProductsPage extends FxmlController implements Initializable {

    public RadioButton availableProducts;
    public RadioButton offProductsButton;
    public TextField nameFilterValue;
    public TextField sellerValueFilter;
    public TextField brandValueFilter;

    public void availableProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().removeAvailableProductsFilter();
            availableProducts.setSelected(false);
        } else if (!MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().addAvailableProduct();
            availableProducts.setSelected(true);
        }
        setScene("allProduct.fxml", "all products page");
    }

    public void offProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isOffProductsFilter()) {
            MainController.getInstance().getControllerForFiltering().removeOffProductsFilter();
            offProductsButton.setSelected(false);
        }else if (!MainController.getInstance().getControllerForFiltering().isOffProductsFilter()){
            MainController.getInstance().getControllerForFiltering().setOffProductsFilter();
            offProductsButton.setSelected(true);
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
