package com.example.helper;

import com.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    private static BookManager instance;

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public boolean isBookExists(String bookCode) {
        String query = "SELECT COUNT(*) FROM books WHERE book_code = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addNewBook(String bookCode, String bookName, int quantity) {
        String query = "INSERT INTO books (book_code, book_name, total_quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookCode);
            stmt.setString(2, bookName);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBookQuantity(String bookCode, int quantity) {
        String query = "UPDATE books SET total_quantity = total_quantity + ? WHERE book_code = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, bookCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("book_code"),
                        rs.getString("book_name"),
                        rs.getInt("total_quantity"),
                        rs.getInt("borrowed_quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public String borrowBook(String bookCode, String studentName, int quantity) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            // Check book availability
            String checkQuery = "SELECT book_name, (total_quantity - borrowed_quantity) AS available_quantity FROM books WHERE book_code = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, bookCode);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int availableQuantity = rs.getInt("available_quantity");
                        String bookName = rs.getString("book_name");

                        if (availableQuantity >= quantity) {
                            // Borrow the book
                            conn.setAutoCommit(false);

                            // Update books table
                            String updateBooksQuery = "UPDATE books SET borrowed_quantity = borrowed_quantity + ? WHERE book_code = ?";
                            try (PreparedStatement updateBooksStmt = conn.prepareStatement(updateBooksQuery)) {
                                updateBooksStmt.setInt(1, quantity);
                                updateBooksStmt.setString(2, bookCode);
                                updateBooksStmt.executeUpdate();
                            }

                            // Insert into borrowed_books table
                            String insertBorrowedBooksQuery = "INSERT INTO borrowed_books (book_code, student_name, borrowed_quantity) VALUES (?, ?, ?)";
                            try (PreparedStatement insertBorrowedBooksStmt = conn.prepareStatement(insertBorrowedBooksQuery)) {
                                insertBorrowedBooksStmt.setString(1, bookCode);
                                insertBorrowedBooksStmt.setString(2, studentName);
                                insertBorrowedBooksStmt.setInt(3, quantity);
                                insertBorrowedBooksStmt.executeUpdate();
                            }

                            conn.commit();
                            return "Bạn đã mượn " + bookName + " - Số lượng: " + quantity;
                        } else {
                            return "Hiện sách không sẵn có. Mời bạn quay lại sau.";
                        }
                    } else {
                        return "Không tìm thấy mã sách.";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Có lỗi xảy ra khi mượn sách.";
        }
    }


    public String returnBook(String bookCode, String studentName, int quantity) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            // Check if the student has borrowed the book
            String checkQuery = "SELECT borrowed_quantity FROM borrowed_books WHERE book_code = ? AND student_name = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, bookCode);
                checkStmt.setString(2, studentName);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int borrowedQuantity = rs.getInt("borrowed_quantity");

                        if (borrowedQuantity < quantity) {
                            return "Bạn không thể trả nhiều hơn số sách đã mượn.";
                        } else {
                            // Update the borrowed_books table
                            String updateBorrowedBooksQuery;
                            if (borrowedQuantity == quantity) {
                                // If returning all borrowed books, remove the record
                                updateBorrowedBooksQuery = "DELETE FROM borrowed_books WHERE book_code = ? AND student_name = ?";
                            } else {
                                // Otherwise, just reduce the borrowed quantity
                                updateBorrowedBooksQuery = "UPDATE borrowed_books SET borrowed_quantity = borrowed_quantity - ? WHERE book_code = ? AND student_name = ?";
                            }

                            try (PreparedStatement updateStmt = conn.prepareStatement(updateBorrowedBooksQuery)) {
                                if (borrowedQuantity == quantity) {
                                    updateStmt.setString(1, bookCode);
                                    updateStmt.setString(2, studentName);
                                } else {
                                    updateStmt.setInt(1, quantity);
                                    updateStmt.setString(2, bookCode);
                                    updateStmt.setString(3, studentName);
                                }
                                updateStmt.executeUpdate();
                            }

                            // Update the books table
                            String updateBooksQuery = "UPDATE books SET borrowed_quantity = borrowed_quantity - ? WHERE book_code = ?";
                            try (PreparedStatement updateBooksStmt = conn.prepareStatement(updateBooksQuery)) {
                                updateBooksStmt.setInt(1, quantity);
                                updateBooksStmt.setString(2, bookCode);
                                updateBooksStmt.executeUpdate();
                            }

                            return "Bạn đã trả thành công " + quantity + " sách.";
                        }
                    } else {
                        return "Bạn chưa mượn sách này.";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Có lỗi xảy ra khi trả sách.";
        }
    }


}
