package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

    private final StringProperty bookCode; // The unique book code
    private final StringProperty bookName; // The name of the book
    private final IntegerProperty totalQuantity; // The total number of books
    private final IntegerProperty borrowedQuantity; // The number of books currently borrowed

    // Constructor
    public Book(String bookCode, String bookName, int totalQuantity, int borrowedQuantity) {
        this.bookCode = new SimpleStringProperty(bookCode);
        this.bookName = new SimpleStringProperty(bookName);
        this.totalQuantity = new SimpleIntegerProperty(totalQuantity);
        this.borrowedQuantity = new SimpleIntegerProperty(borrowedQuantity);
    }

    // Getters and Setters for Book Code
    public String getBookCode() {
        return bookCode.get();
    }

    public void setBookCode(String bookCode) {
        this.bookCode.set(bookCode);
    }

    public StringProperty bookCodeProperty() {
        return bookCode;
    }

    // Getters and Setters for Book Name
    public String getBookName() {
        return bookName.get();
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    // Getters and Setters for Total Quantity
    public int getTotalQuantity() {
        return totalQuantity.get();
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity.set(totalQuantity);
    }

    public IntegerProperty totalQuantityProperty() {
        return totalQuantity;
    }

    // Getters and Setters for Borrowed Quantity
    public int getBorrowedQuantity() {
        return borrowedQuantity.get();
    }

    public void setBorrowedQuantity(int borrowedQuantity) {
        this.borrowedQuantity.set(borrowedQuantity);
    }

    public IntegerProperty borrowedQuantityProperty() {
        return borrowedQuantity;
    }

    // Derived Property: Available Quantity
    public int getAvailableQuantity() {
        return getTotalQuantity() - getBorrowedQuantity();
    }

    public IntegerProperty availableQuantityProperty() {
        return new SimpleIntegerProperty(getAvailableQuantity());
    }

    @Override
    public String toString() {
        return bookName.get() + " - Tổng: " + totalQuantity.get() + ", Đã cho mượn: " + borrowedQuantity.get();
    }
}
