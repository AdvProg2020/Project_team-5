package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.Main;
import ApProject_OnlineShop.controller.MainController;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
        category.setOnAction(e -> setCategory(category.getValue().toString()));
        nameFilterValue.setPromptText(MainController.getInstance().getControllerForFiltering().getName());
        sellerValueFilter.setPromptText(MainController.getInstance().getControllerForFiltering().getSeller());
        brandValueFilter.setPromptText(MainController.getInstance().getControllerForFiltering().getBrand());
        startPriceValue.setPromptText(MainController.getInstance().getControllerForFiltering().getStartPrice());
        endPriceValue.setPromptText(MainController.getInstance().getControllerForFiltering().getEndPrice());
        if (!MainController.getInstance().getControllerForFiltering().getCategory().equals("")) {
            Label subCategoryText = new Label("subcategory:");
            subCategoryText.setPrefWidth(150);
            subCategoryText.setFont(Font.font("Times New Roman",14));
            VBox.setMargin(subCategoryText,new Insets(8,0,0,0));
            categoryRelatedVBox.getChildren().add(subCategoryText);
            ChoiceBox subCategory = new ChoiceBox();
            VBox.setMargin(subCategory,new Insets(2,0,0,0));
            subCategory.setPrefWidth(150);
            subCategory.setPrefHeight(32);
            subCategory.setStyle("-fx-background-color: #dab3ff;   -fx-background-radius: 8px;   -fx-margin: 4px 2px;  -fx-border-radius: 8px;  -fx-border-color: #600080; -fx-border-width: 2 2 2 2; -fx-text-color:#000000;");
            subCategory.setItems(FXCollections.observableArrayList(MainController.getInstance().getControllerForFiltering().getSubcategories()));
            subCategory.setValue(MainController.getInstance().getControllerForFiltering().getSubCategory());
            subCategory.setOnAction(e -> setSubCategory(subCategory.getValue().toString()));
            categoryRelatedVBox.getChildren().add(subCategory);
            for (String property : MainController.getInstance().getControllerForFiltering().getCategoryProperties()) {
                Label propertyText = new Label(property);
                propertyText.setPrefWidth(150);
                propertyText.setFont(Font.font("Times New Roman",14));
                VBox.setMargin(propertyText,new Insets(8,0,0,0));
                categoryRelatedVBox.getChildren().add(propertyText);
                
            }
        }
    }

    public void setSubCategory(String subCategory){
        MainController.getInstance().getControllerForFiltering().addSubCategoryFilter(subCategory);
        setScene("allProduct.fxml", "all products page");
    }

    public void setCategory(String category){
        MainController.getInstance().getControllerForFiltering().addCategoryFilter(category);
        setScene("allProduct.fxml", "all products page");
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
            MainController.getInstance().getControllerForFiltering().setOffProductsFilter();
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
