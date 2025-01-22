package com.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {
    private StringProperty email;
    private StringProperty password;
    private int type;
    private boolean status;

    // Constructors
    public Account() {}

    public Account(String email, String password, int type, boolean status) {
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.type = type;
        this.status = status;
    }

    // Getters and Properties
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public int getType() {
        return type;
    }

    public boolean isStatus() {
        return status;
    }

    public StringProperty getTypeProperty() {
        switch (type) {
            case 0:
                return new SimpleStringProperty("Admin");
            case 1:
                return new SimpleStringProperty("Librarian");
            case 2:
                return new SimpleStringProperty("Student");
            default:
                return new SimpleStringProperty("Unknown");
        }
    }

    // Setters
    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
