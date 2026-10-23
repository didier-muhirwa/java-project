package hostelmanagementsystem.view;

import hostelmanagementsystem.controller.LoginController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private final JTextField     txtUsername = UITheme.field(20);
    private final JPasswordField txtPassword = UITheme.passField(20);
    private final JButton        btnLogin    = UITheme.button("Login", UITheme.ACCENT);
    private final JButton        btnRegister = UITheme.button("Register", UITheme.TEXT_MUTED);

    public LoginForm() {
        setTitle("Hostel Management System");
        setSize(780, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new GridLayout(1, 2));
        add(buildLeftPanel());
        add(buildRightPanel());

        LoginController controller = new LoginController(this);
        btnLogin.addActionListener(e    -> controller.handleLogin());
        btnRegister.addActionListener(e -> new RegisterForm().setVisible(true));

        // Allow Enter key to trigger login
        getRootPane().setDefaultButton(btnLogin);
    }

    private JPanel buildLeftPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(UITheme.BG_DARK);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);

        JLabel icon = new JLabel("🏠", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("HOSTEL", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("MANAGEMENT SYSTEM", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(180, 180, 210));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(160, 1));
        sep.setForeground(new Color(80, 80, 120));

        JLabel tagline = new JLabel("<html><center>Your home away<br>from home</center></html>", SwingConstants.CENTER);
        tagline.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        tagline.setForeground(new Color(150, 150, 190));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        inner.add(icon);
        inner.add(Box.createVerticalStrut(12));
        inner.add(title);
        inner.add(sub);
        inner.add(Box.createVerticalStrut(16));
        inner.add(sep);
        inner.add(Box.createVerticalStrut(16));
        inner.add(tagline);

        p.add(inner);
        return p;
    }

    private JPanel buildRightPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(320, 340));

        JLabel heading = new JLabel("Welcome Back");
        heading.setFont(UITheme.FONT_TITLE);
        heading.setForeground(UITheme.BG_DARK);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Sign in to your account");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(UITheme.TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnLogin.setPreferredSize(new Dimension(Integer.MAX_VALUE, 42));
        btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnRegister.setBackground(new Color(240, 240, 250));
        btnRegister.setForeground(UITheme.ACCENT);

        JLabel lblUser = UITheme.label("Username");
        JLabel lblPass = UITheme.label("Password");
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegister.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel orLabel = new JLabel("Don't have an account?", SwingConstants.CENTER);
        orLabel.setFont(UITheme.FONT_SMALL);
        orLabel.setForeground(UITheme.TEXT_MUTED);
        orLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(heading);
        form.add(Box.createVerticalStrut(4));
        form.add(sub);
        form.add(Box.createVerticalStrut(24));
        form.add(lblUser);
        form.add(Box.createVerticalStrut(4));
        form.add(txtUsername);
        form.add(Box.createVerticalStrut(14));
        form.add(lblPass);
        form.add(Box.createVerticalStrut(4));
        form.add(txtPassword);
        form.add(Box.createVerticalStrut(24));
        form.add(btnLogin);
        form.add(Box.createVerticalStrut(10));
        form.add(orLabel);
        form.add(Box.createVerticalStrut(6));
        form.add(btnRegister);

        p.add(form);
        return p;
    }

    public String getUsername() { return txtUsername.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
}
