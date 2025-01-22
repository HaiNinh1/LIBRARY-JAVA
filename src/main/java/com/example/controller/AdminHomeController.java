package com.example.controller;

import com.example.helper.AccountManager;
import com.example.helper.Navigator;
import com.example.model.Account;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnLock;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUnlock;

    @FXML
    private TableView<Account> tcAccount;

    @FXML
    private TableColumn<Account, String> tcEmail;

    @FXML
    private TableColumn<Account, String> tcStatus;

    @FXML
    private TableColumn<Account, String> tcType;

    private ObservableList<Account> listData;

    private Account currentAccount;

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;

        // Perform any initialization or setup with the account
        System.out.println("Logged-in account: " + account.getEmail());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the data list
        listData = FXCollections.observableArrayList();
        listData.addAll(AccountManager.getInstance().getListAccounts());

        tcAccount.setItems(listData);

        tcEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        tcStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isStatus() ? "Locked" : "Active"));
        tcType.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTypeProperty().get()));
    }

    @FXML
    void onAdd(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/AddAccount.fxml"));
            Parent root = loader.load();


            // Create and show the new stage
            Stage stage = new Stage();
            stage.setTitle("Reset Password");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait for the reset window to close
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for debugging
            System.out.println("Failed to load reset password UI.");
        }
    }


    @FXML
    void onResetPassword(ActionEvent event) {
        try {
            // Load the reset_password.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/reset_password.fxml"));
            Parent root = loader.load();

            // Get the ResetPasswordController instance
            ResetPasswordController controller = loader.getController();

            // Pass the current account to the ResetPasswordController
            controller.setCurrentAccount(currentAccount);

            // Create and show the new stage
            Stage stage = new Stage();
            stage.setTitle("Reset Password");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait for the reset window to close
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for debugging
            System.out.println("Failed to load reset password UI.");
        }
    }

//    @FXML
//    void onResetPassword(ActionEvent event) {
//        System.out.println("Reset button clicked."); // Debug message
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/reset_password.fxml"));
//            Parent root = loader.load();
//
//            System.out.println("FXML loaded successfully."); // Debug message
//
//            ResetPasswordController controller = loader.getController();
//            controller.setCurrentAccount(currentAccount);
//
//            Stage stage = new Stage();
//            stage.setTitle("Reset Password");
//            stage.setScene(new Scene(root));
//            stage.showAndWait();
//
//            System.out.println("Reset password window displayed."); // Debug message
//        } catch (IOException e) {
//            e.printStackTrace(); // Print detailed error for debugging
//            System.out.println("Failed to load reset password UI."); // Debug message
//        }
//    }


    @FXML
    void onLogout(ActionEvent event) {
        // Show logout confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have been logged out successfully.");
        alert.showAndWait(); // Wait for the user to close the alert

        // Redirect to login scene using Navigator
        try {
            Navigator.getInstance().gotoLogin();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the login scene.");
        }
    }

//    @FXML
//    void onLogout(ActionEvent event) {
//        // Show logout confirmation
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Đăng xuất");
//        alert.setHeaderText(null);
//        alert.setContentText("Bạn đã đăng xuất khỏi hệ thống");
//        alert.showAndWait(); // Wait for the user to close the alert
//
//        // Redirect to login scene
//        try {
//            // Load the Login.fxml file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/Login.fxml"));
//            Parent root = loader.load();
//
//            // Create a new scene with the loaded FXML
//            Scene loginScene = new Scene(root);
//
//            // Get the current stage (window) and set the new scene
//            Stage stage = (Stage) btnLogout.getScene().getWindow();
//            stage.setScene(loginScene);
//            stage.setTitle("Login");
//            stage.show(); // Show the login scene
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load login scene.");
//        }
//    }

    @FXML
    void onLockAccount(ActionEvent event) {
        openLockUnlockUI(true);
    }

    @FXML
    void onUnlockAccount(ActionEvent event) {
        openLockUnlockUI(false);
    }

    private void openLockUnlockUI(boolean isLockAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/LockUnlockAccount.fxml"));
            Parent root = loader.load();

            LockUnlockAccountController controller = loader.getController();
            controller.setLockAction(isLockAction); // Set the action type

            Stage stage = new Stage();
            stage.setTitle(isLockAction ? "Lock Account" : "Unlock Account");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
