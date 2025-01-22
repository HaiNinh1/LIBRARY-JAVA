package com.example.controller;

import com.example.helper.Navigator;
import com.example.model.Account;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LibrarianMenuController {

    public void onChangePassword(ActionEvent event) {
        // Logic to change password
    }

    public void onAddBook(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoScene("Add Book", Navigator.ADD_BOOK_SCENE);
    }

    public void onBookList(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoScene("Add Book", Navigator.BOOKLIST);
    }

    public void onBorrowedBookList(ActionEvent event) {
        // Logic to display borrowed books
    }

    public void onSwitchAccount(ActionEvent event) {
        // Logic to switch accounts
    }

    public void onExit(ActionEvent event) {
        // Logic to exit the program
    }

    private Account currentAccount;

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;

        // Perform any initialization or setup with the account
        System.out.println("Logged-in account: " + account.getEmail());
    }
}
