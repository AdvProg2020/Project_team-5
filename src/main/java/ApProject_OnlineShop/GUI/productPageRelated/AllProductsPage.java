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
import ApProject_OnlineShop.model.RequestForServer;
import com.google.gson.Gson;
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
        if (getCurrentPerson() instanceof Manager || getCurrentPerson() instanceof Seller)
            shoppingBag.setVisible(false);
        handleSorts();
        if (connectToServer(new RequestForServer("FilteringController", "isAvailableProduct", null, getInputsForServer())).equals("true"))
            availableProducts.setSelected(true);
        if (connectToServer(new RequestForServer("FilteringController", "isOffProductsFilter", null, getInputsForServer())).equals("true"))
            offProductsButton.setSelected(true);
        List<String> categories = convertStringToArraylist(connectToServer(new RequestForServer("AllProductsController", "getAllCategories", getToken(), null)));
//       List<String> categories = MainController.getInstance().getAllProductsController().getAllCategories();
        categories.add("none");
        category.setItems(FXCollections.observableList(categories));
        category.setStyle("-fx-background-color: #dab3ff;   -fx-background-radius: 8px;   -fx-margin: 4px 2px;  -fx-border-radius: 8px;  -fx-border-color: #600080; -fx-border-width: 2 2 2 2; -fx-text-color:#000000;");
        category.setValue(connectToServer(new RequestForServer("FilteringController", "getCategory", null, getInputsForServer())));
        category.setOnAction(e -> setCategory(category.getValue().toString()));
        nameFilterValue.setPromptText(connectToServer(new RequestForServer("FilteringController", "getName", null, getInputsForServer())));
        sellerValueFilter.setPromptText(connectToServer(new RequestForServer("FilteringController", "getSeller", null, getInputsForServer())));
        brandValueFilter.setPromptText(connectToServer(new RequestForServer("FilteringController", "getBrand", null, getInputsForServer())));
        startPriceValue.setPromptText(connectToServer(new RequestForServer("FilteringController", "getStartPrice", null, getInputsForServer())));
        endPriceValue.setPromptText(connectToServer(new RequestForServer("FilteringController", "getEndPrice", null, getInputsForServer())));
        if (!connectToServer(new RequestForServer("FilteringController", "getCategory", null, getInputsForServer())).equals("")) {
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
//            List<String> subcategories = MainController.getInstance().getControllerForFiltering().getSubcategories();
            List<String> subcategories = convertStringToArraylist(connectToServer(new RequestForServer("FilteringController", "getSubcategories", null, getInputsForServer())));
            subcategories.add("none");
            subCategory.setStyle("-fx-background-color: #dab3ff;   -fx-background-radius: 8px;   -fx-margin: 4px 2px;  -fx-border-radius: 8px;  -fx-border-color: #600080; -fx-border-width: 2 2 2 2; -fx-text-color:#000000;");
            subCategory.setItems(FXCollections.observableArrayList(subcategories));
            subCategory.setValue(connectToServer(new RequestForServer("FilteringController", "getSubCategory", null, getInputsForServer())));
            subCategory.setOnAction(e -> setSubCategory(subCategory.getValue().toString()));
            categoryRelatedVBox.getChildren().add(subCategory);
            List<String> categoryProperties = convertStringToArraylist(connectToServer(new RequestForServer("FilteringController", "getCategoryProperties", null, getInputsForServer())));
            for (String property : categoryProperties) {
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
                ArrayList<String> inputs22 = new ArrayList<>();
                inputs22.add(getId() + "");
                inputs22.add(property);
                propertyValue.setPromptText(connectToServer(new RequestForServer("FilteringController", "getValueOfProperty", null, inputs22)));
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
            if (!connectToServer(new RequestForServer("FilteringController", "getSubCategory", null, getInputsForServer())).equals("")) {
                List<String> subCategoryProperties = convertStringToArraylist(connectToServer(new RequestForServer("FilteringController", "getSubCategoryProperties", null, getInputsForServer())));
                for (String property : subCategoryProperties) {
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
                    ArrayList<String> inputs22 = new ArrayList<>();
                    inputs22.add(getId() + "");
                    inputs22.add(property);
                    propertyValue.setPromptText(connectToServer(new RequestForServer("FilteringController", "getValueOfProperty", null, inputs22)));
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
        ArrayList<String> inputs0 = new ArrayList<>();
        inputs0.add(getId() + "");
        ArrayList<String> products = convertStringToArraylist(connectToServer(new RequestForServer("AllProductsController", "getGoods", getToken(), inputs0)));
        ArrayList<Long> productsIds = new ArrayList<>();
        for (String product : products) {
            productsIds.add(Long.parseLong(product));
        }
        for (Long productId : productsIds) {
            ArrayList<String> input = new ArrayList<>();
            input.add(productId + "");
            if ((connectToServer(new RequestForServer("AllProductsController", "isInOff", getToken(), input))).equals("true")) {
                VBox vbox = new ProductBriefSummery().offProductBriefSummery(productId);
                productsPart.add(vbox, num % 3, row);
                num++;
                vbox.setCursor(Cursor.HAND);
                vbox.setOnMouseClicked(e -> showProduct(productId));
            }
            if (!((connectToServer(new RequestForServer("AllProductsController", "isInOff", getToken(), input))).equals("true"))) {
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
        ArrayList<String> inputs11 = new ArrayList<>();
        inputs11.add(getId() + "");
        String currentSort1 = connectToServer(new RequestForServer("SortingController", "getCurrentSort", null, inputs11));
        if (currentSort1.equals("visit number"))
            viewsSort.setTextFill(Color.BLUE);
        if (currentSort1.equals("average rate"))
            rateSort.setTextFill(Color.BLUE);
        if (currentSort1.equals("date"))
            dateSort.setTextFill(Color.BLUE);
    }

    public void sort(int chosenSort) {
        ArrayList<String> inputs11 = new ArrayList<>();
        inputs11.add(getId() + "");
        inputs11.add(chosenSort + "");
        connectToServer(new RequestForServer("SortingController", "sortASort", null, inputs11));
//        MainController.getInstance().getControllerForSorting().sortASort(chosenSort);
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
//            MainController.getInstance().getControllerForFiltering().addPropertiesFilter(property, value);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            inputs.add(property);
            inputs.add(value);
            connectToServer(new RequestForServer("FilteringController", "addPropertiesFilter", null, inputs));
            setScene("allProducts.fxml", "all products page");
        }
    }

    public void setSubCategory(String subCategory) {
        if (subCategory.equals("none")) {
//            MainController.getInstance().getControllerForFiltering().disableSubcategoryFilter();
            connectToServer(new RequestForServer("FilteringController", "disableSubcategoryFilter", null, getInputsForServer()));
        } else {
            MainController.getInstance().getControllerForFiltering().addSubCategoryFilter(subCategory);
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void setCategory(String category) {
        if (category.equals("none")) {
            //            MainController.getInstance().getControllerForFiltering().disableCategoryFilter();
            connectToServer(new RequestForServer("FilteringController", "disableCategoryFilter", null, getInputsForServer()));
        } else {
            MainController.getInstance().getControllerForFiltering().addCategoryFilter(category);
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void availableProductsFilter() {
        if (connectToServer(new RequestForServer("FilteringController", "isAvailableProduct", null, getInputsForServer())).equals("true")) {
//            MainController.getInstance().getControllerForFiltering().removeAvailableProductsFilter();
            connectToServer(new RequestForServer("FilteringController", "removeAvailableProductsFilter", null, getInputsForServer()));
        } else if (connectToServer(new RequestForServer("FilteringController", "isAvailableProduct", null, getInputsForServer())).equals("true")) {
//            MainController.getInstance().getControllerForFiltering().addAvailableProduct();
            connectToServer(new RequestForServer("FilteringController", "addAvailableProduct", null, getInputsForServer()));
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void offProductsFilter() {
        if (connectToServer(new RequestForServer("FilteringController", "isOffProductsFilter", null, getInputsForServer())).equals("true")) {
//            MainController.getInstance().getControllerForFiltering().removeOffProductsFilter();
            connectToServer(new RequestForServer("FilteringController", "removeOffProductsFilter", null, getInputsForServer()));
        } else if (connectToServer(new RequestForServer("FilteringController", "isOffProductsFilter", null, getInputsForServer())).equals("false")) {
            connectToServer(new RequestForServer("FilteringController", "setOffProductsFilter", null, getInputsForServer()));
//            MainController.getInstance().getControllerForFiltering().setOffProductsFilter();
        }
        setScene("allProducts.fxml", "all products page");
    }

    public void ableNameFilter() {
//        MainController.getInstance().getControllerForFiltering().addNameFiltering(nameFilterValue.getText());
        ArrayList<String> inputs11 = new ArrayList<>();
        inputs11.add(getId() + "");
        inputs11.add(nameFilterValue.getText());
        connectToServer(new RequestForServer("SortingController", "addNameFiltering", null, inputs11));
        setScene("allProducts.fxml", "all products page");
    }

    public void disableNameFilter() {
//        MainController.getInstance().getControllerForFiltering().addNameFiltering("");
        ArrayList<String> inputs11 = new ArrayList<>();
        inputs11.add(getId() + "");
        inputs11.add("");
        connectToServer(new RequestForServer("SortingController", "addNameFiltering", null, inputs11));
        setScene("allProducts.fxml", "all products page");
    }

    public void filterBySeller() {
//        MainController.getInstance().getControllerForFiltering().addSellerFilter(sellerValueFilter.getText());
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(getId() + "");
        inputs.add(sellerValueFilter.getText());
        connectToServer(new RequestForServer("FilteringController", "addSellerFilter", null, inputs));
        setScene("allProducts.fxml", "all products page");
    }

    public void filterByBrand() {
//        MainController.getInstance().getControllerForFiltering().addBrandFiltering(brandValueFilter.getText());
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(getId() + "");
        inputs.add(brandValueFilter.getText());
        connectToServer(new RequestForServer("FilteringController", "addBrandFiltering", null, inputs));
        setScene("allProducts.fxml", "all products page");
    }

    public void disableBrandFilter() {
//        MainController.getInstance().getControllerForFiltering().addBrandFiltering("");
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(getId() + "");
        inputs.add("");
        connectToServer(new RequestForServer("FilteringController", "addBrandFiltering", null, inputs));
        setScene("allProducts.fxml", "all products page");
    }

    public void disableSellerFilter() {
//        MainController.getInstance().getControllerForFiltering().addSellerFilter("");
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(getId() + "");
        inputs.add("");
        connectToServer(new RequestForServer("FilteringController", "addSellerFilter", null, inputs));
        setScene("allProducts.fxml", "all products page");
    }

    public void filterByPrice() {
        if (Pattern.matches("[\\d]+", startPriceValue.getText()) && Pattern.matches("[\\d]+", endPriceValue.getText())) {
//            MainController.getInstance().getControllerForFiltering().addPriceFiltering(startPriceValue.getText(), endPriceValue.getText());
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            inputs.add(startPriceValue.getText());
            inputs.add(endPriceValue.getText());
            connectToServer(new RequestForServer("FilteringController", "addPriceFiltering", null, inputs));
        } else
            ErrorPageFxController.showPage("wrong price value", "price value must be number");
        setScene("allProducts.fxml", "all products page");
    }

    public void disablePriceFilter() {
        connectToServer(new RequestForServer("FilteringController", "disablePriceFiltering", null, getInputsForServer()));
//        MainController.getInstance().getControllerForFiltering().disablePriceFiltering();
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
        if (getCurrentPerson() == null) {
            LoginController.setPathBack("allProducts.fxml", "All products");
            LoginController.setPathAfterLogin(null, null);
            setScene("login.fxml", "login");
        } else if (getCurrentPerson() instanceof Customer) {
            AccountAreaForCustomerController.setPathBack("allProducts.fxml", "all products");
            setScene("accountAreaForCustomer.fxml", "account area");
        } else if (getCurrentPerson() instanceof Seller) {
            AccountAreaForSellerController.setPathBack("allProducts.fxml", "all products");
            setScene("accountAreaForSeller.fxml", "account area");
        } else if (getCurrentPerson() instanceof Manager) {
            AccountAreaForManagerFxController.setPathBack("allProducts.fxml", "all products");
            setScene("accountAreaForManager.fxml", "account area");
        }
    }


}
