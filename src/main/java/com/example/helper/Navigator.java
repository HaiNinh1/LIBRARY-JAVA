package com.example.helper;

import com.example.controller.AdminHomeController;
import com.example.model.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    private static Navigator navigator;
    private Stage state;

    public static final String ADMIN_MENU = "/com/example/demo1/AdminView.fxml";
    public static final String LOGIN_SCENE = "/com/example/demo1/Login.fxml";
    public static final String LIBRARIAN_MENU = "/com/example/demo1/LibrarianMenu.fxml";
    public static final String STUDENT_MENU = "/com/example/demo1/StudentMenu.fxml";
    public static final String ADD_BOOK_SCENE = "/com/example/demo1/AddBook.fxml";
    public static final String BOOKLIST = "/com/example/demo1/BookList.fxml";

    private Navigator() {}

    public static Navigator getInstance() {
        if (navigator == null) {
            navigator = new Navigator();
        }
        return navigator;
    }

    public void setState(Stage state) {
        this.state = state;
    }

    public void gotoLogin() throws IOException {
        gotoScene("Login", LOGIN_SCENE);
    }

    public void gotoAdminMenu(Account account) throws IOException {
        gotoSceneAndSetController("Admin Menu", ADMIN_MENU, account);
    }

    public void gotoLibrarianMenu(Account account) throws IOException {
        gotoSceneAndSetController("Librarian Menu", LIBRARIAN_MENU, account);
    }

    public void gotoStudentMenu(Account account) throws IOException {
        gotoSceneAndSetController("Student Menu", STUDENT_MENU, account);
    }


    public void gotoScene(String title, String URL) throws IOException {
        if (state == null) {
            throw new IllegalStateException("Navigator state is not set. Call setState() before navigating.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(URL));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            state.setTitle(title);
            state.setScene(scene);
            state.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to load FXML file: " + URL, e);
        }
    }

    private void gotoSceneAndSetController(String title, String URL, Account account) throws IOException {
        if (state == null) {
            throw new IllegalStateException("Navigator state is not set. Call setState() before navigating.");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(URL));
        Parent root = loader.load();

        // Dynamically check the type of the controller
        Object controller = loader.getController();
        if (controller instanceof AdminHomeController) {
            ((AdminHomeController) controller).setCurrentAccount(account);
        } else if (controller instanceof com.example.controller.LibrarianMenuController) {
            ((com.example.controller.LibrarianMenuController) controller).setCurrentAccount(account);
        } else if (controller instanceof com.example.controller.StudentMenuController) {
            ((com.example.controller.StudentMenuController) controller).setCurrentAccount(account);
        } else {
            throw new IllegalStateException("Unknown controller type for URL: " + URL);
        }

        Scene scene = new Scene(root);
        state.setTitle(title);
        state.setScene(scene);
        state.show();
    }



}
