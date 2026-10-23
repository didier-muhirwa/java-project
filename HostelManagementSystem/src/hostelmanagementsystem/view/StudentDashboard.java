package hostelmanagementsystem.view;

import hostelmanagementsystem.controller.StudentController;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    public final JTable  bookingTable  = new JTable(new DefaultTableModel(
            new Object[][]{}, new String[]{"Booking ID","Room No","Date","Status"}));
    public final JButton btnCheckRooms = UITheme.button("Check Rooms",    UITheme.ACCENT);
    public final JButton btnApply      = UITheme.button("Apply for Room", UITheme.ACCENT_GREEN);
    public final JButton btnLogout     = UITheme.button("Logout",         UITheme.ACCENT_RED);

    public StudentDashboard(String studentName) {
        setTitle("Hostel Management System – Student Portal");
        setSize(780, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 460));

        setLayout(new BorderLayout());
        add(buildHeader(studentName), BorderLayout.NORTH);
        add(buildContent(),           BorderLayout.CENTER);
        add(buildFooter(),            BorderLayout.SOUTH);

        // Color-code booking status column
        styleStatusColumn();

        new StudentController(this, studentName);
    }

    private JPanel buildHeader(String studentName) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.BG_DARK);
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);

        JLabel lblWelcome = new JLabel("Welcome back, " + studentName);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblWelcome.setForeground(Color.WHITE);

        JLabel lblRole = new JLabel("Student Portal  •  Hostel Management System");
        lblRole.setFont(UITheme.FONT_SMALL);
        lblRole.setForeground(new Color(160, 160, 200));

        left.add(lblWelcome);
        left.add(lblRole);

        JLabel icon = new JLabel("🏠");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        header.add(left, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);
        return header;
    }

    private JPanel buildContent() {
        JPanel outer = new JPanel(new BorderLayout(0, 16));
        outer.setBackground(new Color(245, 246, 250));
        outer.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Info cards row
        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 0));
        cards.setOpaque(false);
        cards.add(infoCard("📋", "My Bookings",    "View your room requests"));
        cards.add(infoCard("🚪", "Available Rooms","Check open rooms"));
        cards.add(infoCard("⏳", "Pending Approval","Admin reviews requests"));

        // Booking history table
        JPanel tableCard = UITheme.card();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.add(UITheme.sectionTitle("My Booking History"), BorderLayout.NORTH);
        tableCard.add(UITheme.scrollPane(bookingTable), BorderLayout.CENTER);

        outer.add(cards,     BorderLayout.NORTH);
        outer.add(tableCard, BorderLayout.CENTER);
        return outer;
    }

    private JPanel infoCard(String emoji, String title, String subtitle) {
        JPanel card = UITheme.card();
        card.setLayout(new BorderLayout(10, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));

        JLabel ico = new JLabel(emoji);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        JPanel text = new JPanel(new GridLayout(2, 1));
        text.setOpaque(false);
        JLabel t = new JLabel(title);
        t.setFont(UITheme.FONT_HEADING);
        t.setForeground(UITheme.BG_DARK);
        JLabel s = new JLabel(subtitle);
        s.setFont(UITheme.FONT_SMALL);
        s.setForeground(UITheme.TEXT_MUTED);
        text.add(t); text.add(s);

        card.add(ico,  BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        footer.setBackground(new Color(245, 246, 250));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        footer.add(btnCheckRooms);
        footer.add(btnApply);
        footer.add(btnLogout);
        return footer;
    }

    private void styleStatusColumn() {
        bookingTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                String val = v == null ? "" : v.toString();
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (!sel) {
                    setBackground(row % 2 == 0 ? UITheme.BG_CARD : UITheme.BG_TABLE_ALT);
                    switch (val) {
                        case "Approved" -> setForeground(UITheme.ACCENT_GREEN);
                        case "Rejected" -> setForeground(UITheme.ACCENT_RED);
                        default         -> setForeground(UITheme.ACCENT_ORANGE);
                    }
                }
                setFont(UITheme.FONT_BTN);
                return this;
            }
        });
    }
}
