package com.example.controller;

import com.example.helper.AccountDbHelper;
import com.example.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ResetPasswordController {

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    private Account currentAccount;

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }

    @FXML
    void onSubmit() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return;
        }

        if (!currentPassword.equals(currentAccount.getPassword())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Current password is incorrect.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "New passwords do not match.");
            return;
        }

        // Update password in the database
        AccountDbHelper.updateAccountPassword(currentAccount.getEmail(), newPassword);

        // Update the password in the current account object
        currentAccount.setPassword(newPassword);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successfully.");
        closeWindow();
    }

    @FXML
    void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) currentPasswordField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
