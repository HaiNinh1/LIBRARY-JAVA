package com.example.controller;

import com.example.helper.BookManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddBookController {

    @FXML
    private TextField bookCodeField;

    @FXML
    private TextField bookNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label messageLabel;

    @FXML
    private void onAddBook() {
        String bookCode = bookCodeField.getText().trim();
        String bookName = bookNameField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (bookCode.isEmpty() || quantityText.isEmpty()) {
            messageLabel.setText("Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);

            if (BookManager.getInstance().isBookExists(bookCode)) {
                BookManager.getInstance().addBookQuantity(bookCode, quantity);
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Số lượng sách đã được cập nhật.");
            } else {
                BookManager.getInstance().addNewBook(bookCode, bookName, quantity);
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Sách mới đã được thêm vào.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Số lượng phải là số nguyên.");
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
