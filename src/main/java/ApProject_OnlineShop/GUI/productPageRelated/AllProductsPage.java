package ApProject_OnlineShop.GUI.productPageRelated;

import ApProject_OnlineShop.controller.MainController;
import javafx.scene.control.RadioButton;

public class AllProductsPage {

    public RadioButton availableProducts;
    public RadioButton offProductsButton;

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
}
