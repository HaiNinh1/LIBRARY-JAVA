package com.example.helper;

import com.example.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDbHelper {
    public static Account getAccountByEmail(String email, String password) throws SQLException {
        Connection conn = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM account WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String Email = rs.getString("email");
                    String passwordColumn = rs.getString("password");
                    int type = rs.getInt("type");
                    boolean lock_status = rs.getBoolean("lock_status");
                    return new Account(Email, passwordColumn, type, lock_status);
                }else{
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void updateAccountPassword(String email, String newPassword) {
        String sql = "UPDATE account SET password = ? WHERE email = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword); // Set the new password
            stmt.setString(2, email); // Specify the email
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully for email: " + email);
            } else {
                System.out.println("No account found with email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Failed to update password.");
            e.printStackTrace();
        }
    }


}
