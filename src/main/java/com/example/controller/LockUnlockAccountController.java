package com.example.controller;

import com.example.helper.AccountManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class LockUnlockAccountController {

    @FXML
    private Label titleLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label messageLabel;

    private boolean isLockAction; // true for lock, false for unlock

    public void setLockAction(boolean isLockAction) {
        this.isLockAction = isLockAction;
        titleLabel.setText(isLockAction ? "Lock Account" : "Unlock Account");
    }

    @FXML
    private void handleConfirm() {
        String email = emailField.getText().trim();

        // Validate email input
        if (email.isEmpty()) {
            messageLabel.setText("Email cannot be empty.");
            return;
        }

        // Check if account exists
        if (!AccountManager.getInstance().isAccountExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Tài khoản chưa tồn tại");
            return;
        }

        // Confirm action
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle(isLockAction ? "Lock Account" : "Unlock Account");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Bạn có chắc muốn thực hiện thao tác này?");
        Optional<ButtonType> confirmation = confirmDialog.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            // Perform lock/unlock action
            boolean success = isLockAction
                    ? AccountManager.getInstance().lockAccount(email)
                    : AccountManager.getInstance().unlockAccount(email);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        isLockAction ? "Đã khóa tài khoản thành công" : "Mở khóa tài khoản thành công");
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        isLockAction ? "Failed to lock account" : "Failed to unlock account");
            }
        } else {
            // User chose No or closed the dialog
            showAlert(Alert.AlertType.INFORMATION, "Info", "Thao tác đã bị hủy");
        }
    }

    @FXML
    private void handleCancel() {
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
}
