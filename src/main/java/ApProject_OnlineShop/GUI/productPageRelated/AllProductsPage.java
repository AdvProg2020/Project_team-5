package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForCustomer.AccountAreaForCustomerController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForManager.AccountAreaForManagerFxController;
import ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller.AccountAreaForSellerController;
import ApProject_OnlineShop.GUI.loginRegister.LoginController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public Label viewsSort;
    public Label rateSort;
    public Label dateSort;
    public ImageView shoppingBag;
    public GridPane mainGridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playMusicBackGround(false, true, false);
        if (MainController.getInstance().getCurrentPerson() instanceof Manager || MainController.getInstance().getCurrentPerson() instanceof Seller)
            shoppingBag.setVisible(false);
        handleSorts();
        if (MainController.getInstance().getControllerForFiltering().isAvailableProduct())
            availableProducts.setSelected(true);
        if (MainController.getInstance().getControllerForFiltering().isOffProductsFilter())
            offProductsButton.setSelected(true);
        List<String> categories = convertStringToArraylist(connectToServer(new RequestForServer("AllProductsController", "getAllCategories", getToken(), null)));
//       List<String> categories = MainController.getInstance().getAllProductsController().getAllCategories();
        categories.add("none");
        category.setItems(FXCollections.observableList(categories));
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
            subCategoryText.setFont(Font.font("Times New Roman", 14));
            VBox.setMargin(subCategoryText, new Insets(8, 0, 0, 0));
            categoryRelatedVBox.getChildren().add(subCategoryText);
            ChoiceBox subCategory = new ChoiceBox();
            subCategory.setCursor(Cursor.HAND);
            VBox.setMargin(subCategory, new Insets(2, 0, 0, 0));
            subCategory.setPrefWidth(150);
            subCategory.setPrefHeight(32);
            List<String> subcategories = MainController.getInstance().getControllerForFiltering().getSubcategories();
            subcategories.add("none");
            subCategory.setStyle("-fx-background-color: #dab3ff;   -fx-background-radius: 8px;   -fx-margin: 4px 2px;  -fx-border-radius: 8px;  -fx-border-color: #600080; -fx-border-width: 2 2 2 2; -fx-text-color:#000000;");
            subCategory.setItems(FXCollections.observableArrayList(subcategories));
            subCategory.setValue(MainController.getInstance().getControllerForFiltering().getSubCategory());
            subCategory.setOnAction(e -> setSubCategory(subCategory.getValue().toString()));
            categoryRelatedVBox.getChildren().add(subCategory);
            for (String property : MainController.getInstance().getControllerForFiltering().getCategoryProperties()) {
                Label propertyText = new Label(property);
                propertyText.setPrefWidth(150);
                propertyText.setFont(Font.font("Times New Roman", 14));
                VBox.setMargin(propertyText, new Insets(8, 0, 0, 0));
                categoryRelatedVBox.getChildren().add(propertyText);
                HBox propertyHBox = new HBox();
                propertyHBox.setAlignment(Pos.CENTER);
                propertyHBox.setPrefWidth(200);
                propertyHBox.setPrefHeight(40);
                ImageView remove = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/cross.png").toString()));
                remove.setPickOnBounds(true);
                remove.setPreserveRatio(true);
                remove.setCursor(Cursor.HAND);
                remove.setFitHeight(20);
                remove.setFitWidth(20);
                remove.setOnMouseClicked(e -> removeCategoryProperty(property));
                HBox.setMargin(remove, new Insets(0, 2, 0, 0));
                propertyHBox.getChildren().add(remove);
                TextField propertyValue = new TextField();
                propertyValue.setMinHeight(30);
                propertyValue.setMinWidth(150);
                propertyValue.setMaxWidth(150);
                propertyValue.setMaxWidth(30);
                propertyValue.setPromptText(MainController.getInstance().getControllerForFiltering().getValueOfProperty(property));
                propertyHBox.getChildren().add(propertyValue);
                ImageView search = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/search.png").toString()));
                search.setPickOnBounds(true);
                search.setPreserveRatio(true);
                search.setFitHeight(30);
                search.setFitWidth(30);
                search.setOnMouseClicked(e -> addCategoryProperty(property, propertyValue.getText()));
                search.setCursor(Cursor.HAND);
                propertyHBox.getChildren().add(search);
                categoryRelatedVBox.getChildren().add(propertyHBox);
            }
            if (!MainController.getInstance().getControllerForFiltering().getSubCategory().equals("")) {
                for (String property : MainController.getInstance().getControllerForFiltering().getSubCategoryProperties()) {
                    Label propertyText = new Label(property + ":");
                    propertyText.setPrefWidth(150);
                    propertyText.setFont(Font.font("Times New Roman", 14));
                    VBox.setMargin(propertyText, new Insets(8, 0, 0, 0));
                    categoryRelatedVBox.getChildren().add(propertyText);
                    HBox propertyHBox = new HBox();
                    propertyHBox.setAlignment(Pos.CENTER);
                    propertyHBox.setPrefWidth(200);
                    propertyHBox.setPrefHeight(40);
                    ImageView remove = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/cross.png").toString()));
                    remove.setCursor(Cursor.HAND);
                    remove.setFitHeight(20);
                    remove.setFitWidth(20);
                    remove.setOnMouseClicked(e -> removeCategoryProperty(property));
                    HBox.setMargin(remove, new Insets(0, 2, 0, 0));
                    propertyHBox.getChildren().add(remove);
                    TextField propertyValue = new TextField();
                    propertyValue.setMinHeight(30);
                    propertyValue.setMinWidth(150);
                    propertyValue.setMaxWidth(150);
                    propertyValue.setMaxWidth(30);
                    propertyValue.setPromptText(MainController.getInstance().getControllerForFiltering().getValueOfProperty(property));
                    propertyHBox.getChildren().add(propertyValue);
                    ImageView search = new ImageView(new Image(getClass().getClassLoader().getResource("pictures/search.png").toString()));
                    search.setFitHeight(30);
                    search.setFitWidth(30);
                    search.setOnMouseClicked(e -> addCategoryProperty(property, propertyValue.getText()));
                    search.setCursor(Cursor.HAND);
                    propertyHBox.getChildren().add(search);
                    categoryRelatedVBox.getChildren().add(propertyHBox);
                }
            }
        }
        setProducts();
    }

    public void setProducts() {
        int num = 0;
        int row = 0;
        ArrayList<String> products = convertStringToArraylist(connectToServer(new RequestForServer("AllProductsController", "getGoods", getToken(), null)));
        ArrayList<Long> productsIds = new ArrayList<>();
        for (String product : products) {
            productsIds.add(Long.parseLong(product));
        }
        for (Long productId : productsIds) {
            if (MainController.getInstance().getAllProductsController().isInOff(productId)) {
                VBox vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                productsPart.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> showProduct(productId));
            }
            if (!MainController.getInstance().getAllProductsController().isInOff(productId)) {
                VBox vbox = new ProductBriefSummery().getProductForAllProductsPage(productId);
                productsPart.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> showProduct(productId));
            }
            if (num % 3 == 0)
                row++;
        }
        if (productsIds.size() == 0)
            return;
        if ((productsIds.size() % 3 != 0)) {
            if ((((productsIds.size() / 3) + 1) * 250) > 1067) {
                mainGridPane.setPrefHeight((((productsIds.size() / 3) + 1) * 250) + 133);
            }
        } else {
            if ((((productsIds.size() / 3) + 0) * 250) > 1067) {
                mainGridPane.setPrefHeight((((productsIds.size() / 3) + 0) * 250) + 133);
            }
        }
    }

    public void handleSorts() {
        viewsSort.setOnMouseClicked(e -> sort(1));
        rateSort.setOnMouseClicked(e -> sort(2));
        dateSort.setOnMouseClicked(e -> sort(3));
        if (MainController.getInstance().getControllerForSorting().getCurrentSort().equals("visit number"))
            viewsSort.setTextFill(Color.BLUE);
        if (MainController.getInstance().getControllerForSorting().getCurrentSort().equals("average rate"))
            rateSort.setTextFill(Color.BLUE);
        if (MainController.getInstance().getControllerForSorting().getCurrentSort().equals("date"))
            dateSort.setTextFill(Color.BLUE);
    }

    public void sort(int chosenSort) {
        MainController.getInstance().getControllerForSorting().sortASort(chosenSort);
        setScene("allProducts.fxml", "all products page");
    }

    private void showProduct(Long productId) {
        ProductPage.setProductId(productId);
        ProductPage.setPathBack("allProducts.fxml", "all products");
        setScene("productPage.fxml", "product page");
    }

    public void removeCategoryProperty(String property) {
        MainController.getInstance().getControllerForFiltering().removeProperty(property);
        setScene("allProducts.fxml", "all products page");
    }

    public void addCategoryProperty(String property, String value) {
        if (!value.equals("")) {
            MainController.getInstance().getControllerForFiltering().addPropertiesFilter(property, value);
            setScene("allProducts.fxml", "all products page");
        }
    }

    public void setSubCategory(String subCategory) {
        if (subCategory.equals("none"))
            MainController.getInstance().getControllerForFiltering().disableSubcategoryFilter();
        else {
            MainController.getInstance().getControllerForFiltering().addSubCategoryFilter(subCategory);
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void setCategory(String category) {
        if (category.equals("none"))
            MainController.getInstance().getControllerForFiltering().disableCategoryFilter();
        else {
            MainController.getInstance().getControllerForFiltering().addCategoryFilter(category);
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void availableProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().removeAvailableProductsFilter();
        } else if (!MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().addAvailableProduct();
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void offProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isOffProductsFilter()) {
            MainController.getInstance().getControllerForFiltering().removeOffProductsFilter();
        } else if (!MainController.getInstance().getControllerForFiltering().isOffProductsFilter()) {
            MainController.getInstance().getControllerForFiltering().setOffProductsFilter();
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void ableNameFilter() {
        MainController.getInstance().getControllerForFiltering().addNameFiltering(nameFilterValue.getText());
        setScene("allProducts.fxml", "all products page");
    }

    public void disableNameFilter() {
        MainController.getInstance().getControllerForFiltering().addNameFiltering("");
        setScene("allProducts.fxml", "all products page");
    }

    public void filterBySeller() {
        MainController.getInstance().getControllerForFiltering().addSellerFilter(sellerValueFilter.getText());
        setScene("allProducts.fxml", "all products page");
    }

    public void filterByBrand() {
        MainController.getInstance().getControllerForFiltering().addBrandFiltering(brandValueFilter.getText());
        setScene("allProducts.fxml", "all products page");
    }

    public void disableBrandFilter() {
        MainController.getInstance().getControllerForFiltering().addBrandFiltering("");
        setScene("allProducts.fxml", "all products page");
    }

    public void disableSellerFilter() {
        MainController.getInstance().getControllerForFiltering().addSellerFilter("");
        setScene("allProducts.fxml", "all products page");
    }

    public void filterByPrice() {
        if (Pattern.matches("[\\d]+", startPriceValue.getText()) && Pattern.matches("[\\d]+", endPriceValue.getText()))
            MainController.getInstance().getControllerForFiltering().addPriceFiltering(startPriceValue.getText(), endPriceValue.getText());
        else
            ErrorPageFxController.showPage("wrong price value", "price value must be number");
        setScene("allProducts.fxml", "all products page");
    }

    public void disablePriceFilter() {
        MainController.getInstance().getControllerForFiltering().disablePriceFiltering();
        setScene("allProducts.fxml", "all products page");
    }

    public void backAction(MouseEvent mouseEvent) {
        setScene("mainMenuLayout.fxml", "main menu");
    }

    public void onCart(MouseEvent mouseEvent) {
        Cart.setPathBack("allProducts.fxml", "all prdocuts");
        setScene("cart.fxml", "cart");
    }

    public void onAccountArea(MouseEvent mouseEvent) {
        if (MainController.getInstance().getCurrentPerson() == null) {
            LoginController.setPathBack("allProducts.fxml", "All products");
            LoginController.setPathAfterLogin(null, null);
            setScene("login.fxml", "login");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("allProducts.fxml", "all products");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("allProducts.fxml", "all products");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (MainController.getInstance().getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("allProducts.fxml", "all products");
            setScene("accountAreaForManager.fxml", "account area");
        }
    }
}
