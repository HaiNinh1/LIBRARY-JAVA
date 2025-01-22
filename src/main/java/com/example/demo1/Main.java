package com.example.demo1;

import com.example.helper.AccountManager;
import com.example.helper.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the primary stage in Navigator
        Navigator.getInstance().setState(primaryStage);

        AccountManager.getInstance().AddDefaultAccount();

        // Start with the login scene
        try {
            Navigator.getInstance().gotoLogin();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load login scene.");
        }
    }


    public static void main(String[] args) {
        launch();
    }
}