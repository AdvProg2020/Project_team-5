package ApProject_OnlineShop.GUI.accountArea.accountAreaForSeller;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.StageController;
import ApProject_OnlineShop.model.RequestForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFileProductForSellerController extends FxmlController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField description;
    @FXML
    private Button submitButton;

    private File selectedFile = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        setScene("accountAreaForSeller.fxml", "account area");
    }

    public void onLogoutIconClicked(MouseEvent mouseEvent) {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
//            MainController.getInstance().getLoginRegisterController().logoutUser();
//            Shop.getInstance().clearCart();
            connectToServer(new RequestForServer("LoginRegisterController", "logoutUser", getToken(), getInputsForServer()));
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(getId() + "");
            connectToServer(new RequestForServer("AccountAreaForCustomerController", "clearCart", null, inputs));
            FxmlController.setId(Long.parseLong(connectToServer(new RequestForServer("###cart", null, null, null))));
            setToken(null);
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onBrowseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(StageController.getStage());
        this.submitButton.setDisable(false);
    }

    public void onAddProduct(ActionEvent actionEvent) {
        if (price.getText().isEmpty() || name.getText().isEmpty()) {
            ErrorPageFxController.showPage("can not add good", "fields should be filled!");
            return;
        }
        if (!price.getText().matches("\\d+")) {
            ErrorPageFxController.showPage("can not add good", "price has incorrect format!");
            this.name.clear();
            this.price.clear();
            this.description.clear();
            return;
        }
        if (this.selectedFile == null) {
            ErrorPageFxController.showPage("can not add good", "file of product should be added!");
            this.name.clear();
            this.price.clear();
            this.description.clear();
            return;
        }
        String name = this.name.getText();
        String description = this.description.getText();
        String price = this.price.getText();
        if (description.isEmpty())
            description = "empty!!";
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(selectedFile.getName());
        inputs.add(price);
        inputs.add(description);
        String serverResponse = connectToServer(new RequestForServer("AccountAreaForSellerController", "addFileProduct", getToken(), inputs));
        if (serverResponse.equals("file product successfully added")) {
            System.out.println(serverResponse);
        } else {
            ErrorPageFxController.showPage("can not add good", serverResponse);
            return;
        }
        ArrayList<String> inputs2 = new ArrayList<>();
        String fileName = selectedFile.getName();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        inputs2.add(fileName);
        inputs2.add(extension);
        byte[] file = null;
        try {
            file = Files.readAllBytes(selectedFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            ErrorPageFxController.showPage("can not add good", e.getMessage());
            return;
        }
        String serverResponse2 = connectToFileTransferServer(new RequestForServer("fileTransfer", "uploadFile", getToken(), inputs2), file);
    }
}
