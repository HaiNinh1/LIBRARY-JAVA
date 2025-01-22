package com.example.controller;

import com.example.helper.BookManager;

import com.example.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookListController {

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> bookCodeColumn;

    @FXML
    private TableColumn<Book, String> bookNameColumn;

    @FXML
    private TableColumn<Book, Integer> totalQuantityColumn;

    @FXML
    private TableColumn<Book, Integer> availableQuantityColumn;

    @FXML
    private TableColumn<Book, Integer> borrowedQuantityColumn;

    @FXML
    public void initialize() {
        bookCodeColumn.setCellValueFactory(cellData -> cellData.getValue().bookCodeProperty());
        bookNameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        totalQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().totalQuantityProperty().asObject());
        availableQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().availableQuantityProperty().asObject());
        borrowedQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().borrowedQuantityProperty().asObject());

        // Load books from database into the table
        ObservableList<Book> books = FXCollections.observableArrayList(BookManager.getInstance().getBooks());
        bookTable.setItems(books);
    }
}
