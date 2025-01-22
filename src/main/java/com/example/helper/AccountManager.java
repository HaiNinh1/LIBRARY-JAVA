package com.example.helper;

import com.example.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    List<Account> listAcc = new ArrayList<Account>();

    private static AccountManager accountManager;

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (accountManager == null) {
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    public List<Account> getListAccounts() {
        return listAcc;
    }

    public void AddDefaultAccount() {
        String url = "jdbc:postgresql://localhost:5432/java_lession"; // PostgreSQL JDBC URL
        String username = "postgres"; // Replace with your PostgreSQL username
        String password = "123"; // Replace with your PostgreSQL password

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM account")) {

            listAcc.clear(); // Clear existing accounts

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String passwordColumn = rs.getString("password");
                int type = rs.getInt("type");
                boolean lock_status = rs.getBoolean("lock_status"); // Retrieve as boolean

                listAcc.add(new Account(email, passwordColumn, type, lock_status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to PostgreSQL", e);
        }
    }

    public boolean isAccountExists(String email) {
        String query = "SELECT COUNT(*) FROM account WHERE email = ?"; // Correct table name
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if account exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to check account existence", e);
        }
        return false;
    }

    public void addAccount(Account account) {
        String query = "INSERT INTO account (email, password, type, lock_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getPassword());
            stmt.setInt(3, account.getType());
            stmt.setBoolean(4, account.isStatus());
            stmt.executeUpdate();

            listAcc.add(account); // Add to the list
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add account", e);
        }
    }

    public boolean lockAccount(String email) {
        String query = "UPDATE account SET lock_status = true WHERE email = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to lock account", e);
        }
    }

    public boolean unlockAccount(String email) {
        String query = "UPDATE account SET lock_status = false WHERE email = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to unlock account", e);
        }
    }




}
