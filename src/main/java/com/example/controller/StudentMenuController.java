package com.example.controller;

import com.example.helper.Navigator;
import javafx.event.ActionEvent;

import java.io.IOException;

public class StudentMenuController {

    public void onViewBooks(ActionEvent event) throws IOException {
        // Open Book List scene
        Navigator.getInstance().gotoScene("Danh sách sách trong kho", "/com/example/demo1/BookList.fxml");
    }

    public void onBorrowBook(ActionEvent event) throws IOException {
        // Open Borrow Book scene
        Navigator.getInstance().gotoScene("Mượn sách", "/com/example/demo1/BorrowBook.fxml");
    }

    public void onReturnBook(ActionEvent event) throws IOException {
        // Open Return Book scene
        Navigator.getInstance().gotoScene("Trả sách", "/com/example/demo1/ReturnBook.fxml");
    }

    public void onViewBorrowedBooks(ActionEvent event) throws IOException {
        // Open Borrowed Book List scene
        Navigator.getInstance().gotoScene("Danh sách sách đã mượn", "/com/example/demo1/BorrowedBookList.fxml");
    }

    public void onLogout(ActionEvent event) throws IOException {
        // Logout and return to login screen
        Navigator.getInstance().gotoLogin();
    }
}
