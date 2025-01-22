package com.example.controller;

import com.example.helper.BookManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReturnBookController {

    @FXML
    private TextField bookCodeField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label messageLabel;

    @FXML
    private void onReturn() {
        String bookCode = bookCodeField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (bookCode.isEmpty() || quantityText.isEmpty()) {
            messageLabel.setText("Vui lòng nhập đủ thông tin.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            String result = BookManager.getInstance().returnBook(bookCode, "Nguyễn Văn A", quantity);
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", result);
        } catch (NumberFormatException e) {
            messageLabel.setText("Số lượng phải là số nguyên.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
