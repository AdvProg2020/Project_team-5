package ApProject_OnlineShop.GUI.accountArea.accountAreaForManager;

import ApProject_OnlineShop.GUI.ErrorPageFxController;
import ApProject_OnlineShop.GUI.FxmlController;
import ApProject_OnlineShop.GUI.SuccessPageFxController;
import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.RequestNotFoundException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.DiscountCode;
import ApProject_OnlineShop.server.RequestForServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageAllUsersPageController extends FxmlController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private TableView<Person> usersTable;
    @FXML
    private TableColumn<Person, String> usernameColumn;
    @FXML
    private TableColumn<Person, String> roleColumn;
    @FXML
    private Button removeButton;

    private ObservableList<DiscountCode> usernameData;
    private ObservableList<String> roleData;
    private String selectedUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableView(Shop.getInstance().getAllPersons());
    }

    private void updateTableView(List<Person> persons) {
        usernameData = FXCollections.observableArrayList();
        roleData = FXCollections.observableArrayList();
        usersTable.getItems().clear();
        for (Person person : persons) {
            usersTable.getItems().add(person);
        }
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        if (persons.size() * 27 > 400) {
            usersTable.setPrefHeight(persons.size() * 27);
        }
    }

    public void onLogoutIconClicked() {
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            setScene("mainMenuLayout.fxml", "Main menu");
        }
    }

    public void onBackButtonPressed() {
        setScene("accountAreaForManager.fxml", "account area");
    }

    public void onRemovePressed(ActionEvent e) {
        Optional<ButtonType> result = new FxmlController().showAlert
                (Alert.AlertType.CONFIRMATION, "delete", "User Delete", "are you sure to delete this user?");
        if (result.get() == ButtonType.OK) {
//            try {
//                MainController.getInstance().getAccountAreaForManagerController().removeUser(selectedUsername);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(selectedUsername);
            String serverResponse = connectToServer(new RequestForServer("AccountAreaForManagerController", "removeUser", getToken(), inputs));
            if (serverResponse.equals("user removed successfully")) {
                File file = new File("Resources\\UserImages\\" + selectedUsername + ".jpg");
                if (file.exists())
                    file.delete();
                this.selectedUsername = "";
                updateTableView(Shop.getInstance().getAllPersons());
                removeButton.setDisable(true);
                clearLabels();
                SuccessPageFxController.showPage("delete user", "user deleted successfully");
            } else {
                ErrorPageFxController.showPage("error", serverResponse);
            }
//            } catch (Exception ex) {
//                ErrorPageFxController.showPage("error", ex.getMessage());
//            }
        } else
            e.consume();
    }

    private void clearLabels() {
        usernameLabel.setText("");
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        emailLabel.setText("");
        phoneNumberLabel.setText("");
    }

    public void onRowSelected() {
        this.selectedUsername = usersTable.getSelectionModel().getSelectedItem().getUsername();
        usernameLabel.setText(usersTable.getSelectionModel().getSelectedItem().getUsername());
        firstNameLabel.setText(usersTable.getSelectionModel().getSelectedItem().getFirstName());
        lastNameLabel.setText(usersTable.getSelectionModel().getSelectedItem().getLastName());
        emailLabel.setText(usersTable.getSelectionModel().getSelectedItem().getEmail());
        phoneNumberLabel.setText(usersTable.getSelectionModel().getSelectedItem().getPhoneNumber());
        removeButton.setDisable(false);
    }

    public void onAddNewManagerPressed() {
        setScene("addManagerPage.fxml", "add new manager");
    }

    public void addNewSupporter(ActionEvent actionEvent) {
        setScene("addSupporter.fxml", "add new manager");
    }
}
