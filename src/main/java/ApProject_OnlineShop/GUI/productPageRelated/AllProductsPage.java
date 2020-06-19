package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.controller.MainController;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AllProductsPage {

    public RadioButton availableProducts;
    public RadioButton offProductsButton;
    public TextField nameFilterValue;

    public void availableProductsFilter() {
        if (MainController.getInstance().getControllerForFiltering().isAvailableProduct()) {
            MainController.getInstance().getControllerForFiltering().removeAvailableProductsFilter();
            availableProducts.setSelected(false);
        }
        if (!availableProducts.isSelected()) {
            MainController.getInstance().getControllerForFiltering().addAvailableProduct();
            availableProducts.setSelected(true);
        }
    }
    
    public void offProductsFilter(){
        
    }

    public void ableNameFilter(){
        MainController.getInstance().getControllerForFiltering().addNameFiltering(nameFilterValue.getText());
    }

    public void disableNameFilter(){

    }
}
