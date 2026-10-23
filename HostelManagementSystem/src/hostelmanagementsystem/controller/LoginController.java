package hostelmanagementsystem.controller;

import hostelmanagementsystem.database.DBConnection;
import hostelmanagementsystem.view.AdminDashboard;
import hostelmanagementsystem.view.LoginForm;
import hostelmanagementsystem.view.StudentDashboard;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 * LAYER 5 – CONTROLLER
 * Queries 'users' table. If match found, determines Role (Admin vs Student).
 * Decides which Dashboard to open.
 */
public class LoginController {

    private final LoginForm view;

    public LoginController(LoginForm view) {
        this.view = view;
    }

    public void handleLogin() {
        String user = view.getUsername();
        String pass = view.getPassword();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter username and password.");
            return;
        }

        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT role FROM users WHERE username=? AND password=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                view.dispose();

                if ("Admin".equalsIgnoreCase(role)) {
                    new AdminDashboard().setVisible(true);
                } else {
                    new StudentDashboard(user).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Invalid username or password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Database error: " + e.getMessage());
        }
    }
}
