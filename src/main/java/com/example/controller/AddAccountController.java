package com.example.controller;

import com.example.helper.AccountManager;
import com.example.model.Account;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class AddAccountController {

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        // Initialize the account type options
        accountTypeComboBox.setItems(FXCollections.observableArrayList("Librarian", "Student"));
    }

    @FXML
    private void handleAddAccount() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String accountType = accountTypeComboBox.getValue(); // Get the selected account type

        if (email.isEmpty() || password.isEmpty() || accountType == null) {
            messageLabel.setText("Please fill out all fields and select an account type.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address (example@domain.com).");
            return;
        }

        // Validate password strength
        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Weak Password", "Password must be at least 6 characters long, containing both letters and numbers.");
            return;
        }

        // Check if the account already exists
        if (AccountManager.getInstance().isAccountExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Tài khoản đã tồn tại");
            closeWindow(); // Close the window and return to the menu
            return;
        }

        // (1 for Librarian, 2 for Student)
        int type = accountType.equals("Librarian") ? 1 : 2;

        // Add the new account to the database
        Account newAccount = new Account(email, password, type, true);
        AccountManager.getInstance().addAccount(newAccount);

        // Show success message
        showAlert(Alert.AlertType.INFORMATION, "Success",
                type == 1 ? "Đã tạo tài khoản thủ thư thành công" : "Đã tạo tài khoản học viên thành công");

        closeWindow();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage currentStage = (Stage) emailField.getScene().getWindow();
        currentStage.close();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }


    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        return Pattern.matches(passwordRegex, password);
    }
}
