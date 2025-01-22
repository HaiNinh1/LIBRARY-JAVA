package com.example.controller;

import com.example.helper.AccountDbHelper;
import com.example.helper.Navigator;
import com.example.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() throws SQLException {
        String email = usernameField.getText();
        String password = passwordField.getText();

        Account acc = AccountDbHelper.getAccountByEmail(email, password);
        if (acc == null) {
            showAlert(Alert.AlertType.ERROR, "Login failed", "Email or password is incorrect");
            return;
        }
        if (acc.isStatus()) {
            showAlert(Alert.AlertType.ERROR, "Login failed", "Account is locked");
            return;
        }

        try {
            if(acc.getType() == 0){
                Navigator.getInstance().gotoAdminMenu(acc);
            } else if(acc.getType() == 1){
                Navigator.getInstance().gotoLibrarianMenu(acc);
            } else {
                Navigator.getInstance().gotoStudentMenu(acc);
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the next scene.");
        }
    }





    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

}
