package hostelmanagementsystem.view;

import hostelmanagementsystem.database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterForm extends JFrame {

    private final JTextField     txtName     = UITheme.field(20);
    private final JTextField     txtUsername = UITheme.field(20);
    private final JPasswordField txtPassword = UITheme.passField(20);
    private final JButton        btnRegister = UITheme.button("Create Account", UITheme.ACCENT_GREEN);
    private final JButton        btnCancel   = UITheme.button("Cancel", UITheme.TEXT_MUTED);

    public RegisterForm() {
        setTitle("Register – New Student Account");
        setSize(460, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(UITheme.BG_DARK);

        JPanel card = UITheme.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(360, 340));

        JLabel title = new JLabel("Create Account");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.BG_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Register as a new student");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(UITheme.TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnCancel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnCancel.setBackground(new Color(240, 240, 250));
        btnCancel.setForeground(UITheme.TEXT_MUTED);

        for (JComponent c : new JComponent[]{txtName, txtUsername, txtPassword, btnRegister, btnCancel})
            ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(makeRow("Full Name", txtName));
        card.add(Box.createVerticalStrut(10));
        card.add(makeRow("Username", txtUsername));
        card.add(Box.createVerticalStrut(10));
        card.add(makeRow("Password", txtPassword));
        card.add(Box.createVerticalStrut(20));
        card.add(btnRegister);
        card.add(Box.createVerticalStrut(8));
        card.add(btnCancel);

        root.add(card);
        add(root);

        btnRegister.addActionListener(e -> handleRegistration());
        btnCancel.addActionListener(e   -> dispose());
    }

    private JPanel makeRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = UITheme.label(label);
        row.add(lbl,   BorderLayout.NORTH);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private void handleRegistration() {
        String name = txtName.getText().trim();
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection c = DBConnection.getConnection()) {
            String sqlUser = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Student')";
            try (PreparedStatement ps = c.prepareStatement(sqlUser)) {
                ps.setString(1, user); ps.setString(2, pass); ps.executeUpdate();
            }
            String sqlStudent = "INSERT INTO students (name, status) VALUES (?, 'Active')";
            try (PreparedStatement ps = c.prepareStatement(sqlStudent)) {
                ps.setString(1, name); ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Account created! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
