package hostelmanagementsystem.view;

import hostelmanagementsystem.controller.StudentController;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    // ── Students ──────────────────────────────────────────────────────────────
    private final JTextField     txtSName   = UITheme.field(16);
    private final JTextField     txtSRoom   = UITheme.field(10);
    private final JComboBox<String> cboSStatus = UITheme.combo(new String[]{"Active","Inactive"});
    private final JTable studentTable = new JTable(new DefaultTableModel(
            new Object[][]{}, new String[]{"🆔 ID","👤 Name","🚪 Room","📊 Status"}));

    // ── Rooms ─────────────────────────────────────────────────────────────────
    private final JTextField     txtRNo  = UITheme.field(10);
    private final JTextField     txtRCap = UITheme.field(5);
    private final JComboBox<String> cboRStatus = UITheme.combo(new String[]{"Available","Occupied"});
    private final JTable roomTable = new JTable(new DefaultTableModel(
            new Object[][]{}, new String[]{"🆔 ID","🚪 Room No","👥 Capacity","📊 Status"}));

    // ── Users ─────────────────────────────────────────────────────────────────
    private final JTextField     txtUUser = UITheme.field(14);
    private final JTextField     txtUPass = UITheme.field(14);
    private final JComboBox<String> cboURole = UITheme.combo(new String[]{"Student","Admin"});
    private final JTable userTable = new JTable(new DefaultTableModel(
            new Object[][]{}, new String[]{"🆔 ID","👤 Username","🎭 Role"}));

    // ── Bookings ──────────────────────────────────────────────────────────────
    private final JTable bookingTable = new JTable(new DefaultTableModel(
            new Object[][]{}, new String[]{"🆔 ID","👤 Student","🚪 Room","📅 Date","📊 Status"}));

    private final JPanel contentArea = new JPanel(new CardLayout());
    private StudentController controller;
    private JButton activeNavBtn = null;

    public AdminDashboard() {
        setTitle("Hostel Management System – Admin");
        setSize(1040, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(960, 580));

        setLayout(new BorderLayout());
        add(buildSidebar(),  BorderLayout.WEST);
        add(buildHeader(),   BorderLayout.NORTH);
        add(contentArea,     BorderLayout.CENTER);

        contentArea.add(buildStudentsPanel(), "Students");
        contentArea.add(buildRoomsPanel(),    "Rooms");
        contentArea.add(buildUsersPanel(),    "Users");
        contentArea.add(buildBookingsPanel(), "Bookings");
        contentArea.setBackground(UITheme.BG_PAGE);

        controller = new StudentController(this);
        controller.loadAll();

        studentTable.getSelectionModel().addListSelectionListener(e -> fillStudentFields());
        roomTable.getSelectionModel().addListSelectionListener(e    -> fillRoomFields());
        userTable.getSelectionModel().addListSelectionListener(e    -> fillUserFields());

        // Apply status badge renderers
        studentTable.getColumnModel().getColumn(3).setCellRenderer(UITheme.statusBadgeRenderer());
        roomTable.getColumnModel().getColumn(3).setCellRenderer(UITheme.statusBadgeRenderer());
        bookingTable.getColumnModel().getColumn(4).setCellRenderer(UITheme.statusBadgeRenderer());

        showCard("Students");
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));

        JLabel logo = new JLabel("🏠 HMS Admin", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(26, 0, 26, 0));
        logo.setMaximumSize(new Dimension(220, 64));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(180, 1));
        sep.setForeground(new Color(60, 60, 90));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(14));

        String[][] navItems = {
            {"👥  Students",  "Students"},
            {"🚪  Rooms",     "Rooms"},
            {"👤  Users",     "Users"},
            {"📋  Bookings",  "Bookings"}
        };

        for (String[] item : navItems) sidebar.add(navButton(item[0], item[1]));

        sidebar.add(Box.createVerticalGlue());

        JButton btnLogout = new JButton("⏻  Logout");
        btnLogout.setFont(UITheme.FONT_BTN);
        btnLogout.setForeground(new Color(255, 100, 100));
        btnLogout.setBackground(UITheme.BG_SIDEBAR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(220, 46));
        btnLogout.addActionListener(e -> { dispose(); new LoginForm().setVisible(true); });
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(18));

        return sidebar;
    }

    private JButton navButton(String text, String card) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        btn.setForeground(new Color(200, 200, 220));
        btn.setBackground(UITheme.BG_SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(220, 46));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, UITheme.BG_SIDEBAR),
            BorderFactory.createEmptyBorder(0, 20, 0, 0)));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != activeNavBtn) btn.setBackground(new Color(40, 40, 70));
            }
            public void mouseExited(MouseEvent e) {
                if (btn != activeNavBtn) btn.setBackground(UITheme.BG_SIDEBAR);
            }
        });
        btn.addActionListener(e -> {
            if (activeNavBtn != null) {
                activeNavBtn.setBackground(UITheme.BG_SIDEBAR);
                activeNavBtn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 0, UITheme.BG_SIDEBAR),
                    BorderFactory.createEmptyBorder(0, 20, 0, 0)));
            }
            btn.setBackground(UITheme.NAV_ACTIVE_BG);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(255, 193, 7)),
                BorderFactory.createEmptyBorder(0, 20, 0, 0)));
            activeNavBtn = btn;
            showCard(card);
        });
        if (activeNavBtn == null) {
            activeNavBtn = btn;
            btn.setBackground(UITheme.NAV_ACTIVE_BG);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(255, 193, 7)),
                BorderFactory.createEmptyBorder(0, 20, 0, 0)));
        }
        return btn;
    }

    private void showCard(String name) {
        ((CardLayout) contentArea.getLayout()).show(contentArea, name);
    }

    // ── Header ────────────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.BG_DARK);
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 22));

        JLabel title = new JLabel("🏠  Hostel Management System");
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        title.setForeground(Color.WHITE);

        JLabel role = new JLabel("Administrator  •  Full Access");
        role.setFont(UITheme.FONT_SMALL);
        role.setForeground(new Color(160, 160, 200));

        header.add(title, BorderLayout.WEST);
        header.add(role,  BorderLayout.EAST);
        return header;
    }

    // ── Content Panels ────────────────────────────────────────────────────────

    private JPanel buildStudentsPanel() {
        JButton btnSave   = UITheme.button("💾  Save",   UITheme.ACCENT);
        JButton btnUpdate = UITheme.button("✏️  Update", UITheme.ACCENT_ORANGE);
        JButton btnDelete = UITheme.button("🗑️  Delete", UITheme.ACCENT_RED);

        btnSave.addActionListener(e   -> { controller.saveStudent();   clearStudentFields(); });
        btnUpdate.addActionListener(e -> { controller.updateStudent(); clearStudentFields(); });
        btnDelete.addActionListener(e -> {
            if (confirm("Delete selected student?")) { controller.deleteStudent(); clearStudentFields(); }
        });

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(UITheme.statCard("👥", String.valueOf(studentTable.getRowCount()), "Total Students", UITheme.ACCENT));
        stats.add(UITheme.statCard("✔", "0", "Active", UITheme.ACCENT_GREEN));
        stats.add(UITheme.statCard("✘", "0", "Inactive", UITheme.ACCENT_RED));

        JPanel form = buildFormCard("📝  Student Details",
            new String[]{"👤", "🚪", "📊"},
            new String[]{"Name", "Room No", "Status"},
            new JComponent[]{txtSName, txtSRoom, cboSStatus},
            new JButton[]{btnSave, btnUpdate, btnDelete});

        return wrapContent("👥  Students", stats, form, studentTable);
    }

    private JPanel buildRoomsPanel() {
        JButton btnSave   = UITheme.button("➕  Create", UITheme.ACCENT_GREEN);
        JButton btnUpdate = UITheme.button("✏️  Update", UITheme.ACCENT_ORANGE);
        JButton btnDelete = UITheme.button("🗑️  Delete", UITheme.ACCENT_RED);

        btnSave.addActionListener(e   -> { controller.saveRoom();   clearRoomFields(); });
        btnUpdate.addActionListener(e -> { controller.updateRoom(); clearRoomFields(); });
        btnDelete.addActionListener(e -> {
            if (confirm("Delete selected room?")) { controller.deleteRoom(); clearRoomFields(); }
        });

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(UITheme.statCard("🚪", String.valueOf(roomTable.getRowCount()), "Total Rooms", UITheme.ACCENT));
        stats.add(UITheme.statCard("✔", "0", "Available", UITheme.ACCENT_GREEN));
        stats.add(UITheme.statCard("✘", "0", "Occupied", UITheme.ACCENT_RED));

        JPanel form = buildFormCard("📝  Room Details",
            new String[]{"🚪", "👥", "📊"},
            new String[]{"Room No", "Capacity", "Status"},
            new JComponent[]{txtRNo, txtRCap, cboRStatus},
            new JButton[]{btnSave, btnUpdate, btnDelete});

        return wrapContent("🚪  Rooms", stats, form, roomTable);
    }

    private JPanel buildUsersPanel() {
        JButton btnSave   = UITheme.button("➕  Add User", UITheme.ACCENT);
        JButton btnUpdate = UITheme.button("✏️  Update",   UITheme.ACCENT_ORANGE);
        JButton btnDelete = UITheme.button("🗑️  Delete",   UITheme.ACCENT_RED);

        btnSave.addActionListener(e   -> { controller.saveUser();   clearUserFields(); });
        btnUpdate.addActionListener(e -> { controller.updateUser(); clearUserFields(); });
        btnDelete.addActionListener(e -> {
            if (confirm("Delete selected user?")) { controller.deleteUser(); clearUserFields(); }
        });

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(UITheme.statCard("👤", String.valueOf(userTable.getRowCount()), "Total Users", UITheme.ACCENT));
        stats.add(UITheme.statCard("👨‍🎓", "0", "Students", UITheme.ACCENT_TEAL));
        stats.add(UITheme.statCard("👨‍💼", "0", "Admins", UITheme.ACCENT_ORANGE));

        JPanel form = buildFormCard("📝  User Details",
            new String[]{"👤", "🔒", "🎭"},
            new String[]{"Username", "Password", "Role"},
            new JComponent[]{txtUUser, txtUPass, cboURole},
            new JButton[]{btnSave, btnUpdate, btnDelete});

        return wrapContent("👤  Users", stats, form, userTable);
    }

    private JPanel buildBookingsPanel() {
        JButton btnApprove = UITheme.button("✔  Approve", UITheme.ACCENT_GREEN);
        JButton btnReject  = UITheme.button("✘  Reject",  UITheme.ACCENT_RED);
        JButton btnRefresh = UITheme.button("↻  Refresh", UITheme.ACCENT);

        btnApprove.addActionListener(e -> controller.approveBooking());
        btnReject.addActionListener(e  -> controller.rejectBooking());
        btnRefresh.addActionListener(e -> controller.loadBookings());

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(UITheme.statCard("📋", String.valueOf(bookingTable.getRowCount()), "Total Bookings", UITheme.ACCENT));
        stats.add(UITheme.statCard("⏳", "0", "Pending", UITheme.ACCENT_ORANGE));
        stats.add(UITheme.statCard("✔", "0", "Approved", UITheme.ACCENT_GREEN));

        JPanel card = UITheme.card();
        card.setLayout(new BorderLayout(0, 12));

        JLabel title = UITheme.sectionTitle("📋", "Booking Requests");
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnRow.add(UITheme.label("Select a booking then:"));
        btnRow.add(btnApprove); btnRow.add(btnReject); btnRow.add(btnRefresh);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(title,  BorderLayout.NORTH);
        top.add(btnRow, BorderLayout.CENTER);

        card.add(top, BorderLayout.NORTH);
        card.add(UITheme.scrollPane(bookingTable), BorderLayout.CENTER);

        JPanel outer = new JPanel(new BorderLayout(0, 16));
        outer.setBackground(UITheme.BG_PAGE);
        outer.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JPanel stack = new JPanel(new BorderLayout(0, 16));
        stack.setOpaque(false);
        stack.add(stats, BorderLayout.NORTH);
        stack.add(card,  BorderLayout.CENTER);

        outer.add(UITheme.pageTitle("📋", "Bookings"), BorderLayout.NORTH);
        outer.add(stack, BorderLayout.CENTER);
        return outer;
    }

    // ── Layout Helpers ────────────────────────────────────────────────────────

    private JPanel buildFormCard(String heading, String[] icons, String[] labels, JComponent[] fields, JButton[] buttons) {
        JPanel card = UITheme.card();
        card.setLayout(new BorderLayout(0, 12));

        JLabel title = UITheme.sectionTitle(heading.split("  ")[0], heading.split("  ")[1]);

        JPanel rows = new JPanel(new GridLayout(labels.length, 1, 0, 10));
        rows.setOpaque(false);
        for (int i = 0; i < labels.length; i++)
            rows.add(UITheme.formRow(icons[i], labels[i], fields[i]));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        for (JButton b : buttons) btnRow.add(b);

        card.add(title,  BorderLayout.NORTH);
        card.add(rows,   BorderLayout.CENTER);
        card.add(btnRow, BorderLayout.SOUTH);
        return card;
    }

    private JPanel wrapContent(String pageName, JPanel stats, JPanel formCard, JTable table) {
        JPanel outer = new JPanel(new BorderLayout(0, 16));
        outer.setBackground(UITheme.BG_PAGE);
        outer.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JPanel tableCard = UITheme.card();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.add(UITheme.sectionTitle("📊", "Records"), BorderLayout.NORTH);
        tableCard.add(UITheme.scrollPane(table), BorderLayout.CENTER);

        JPanel stack = new JPanel(new BorderLayout(0, 16));
        stack.setOpaque(false);
        stack.add(stats,     BorderLayout.NORTH);
        stack.add(formCard,  BorderLayout.CENTER);
        stack.add(tableCard, BorderLayout.SOUTH);

        outer.add(UITheme.pageTitle(pageName.split("  ")[0], pageName.split("  ")[1]), BorderLayout.NORTH);
        outer.add(stack, BorderLayout.CENTER);
        return outer;
    }

    // ── Auto-fill on row select ────────────────────────────────────────────────

    private void fillStudentFields() {
        int r = studentTable.getSelectedRow(); if (r < 0) return;
        txtSName.setText(str(studentTable.getValueAt(r, 1)));
        txtSRoom.setText(str(studentTable.getValueAt(r, 2)));
        cboSStatus.setSelectedItem(str(studentTable.getValueAt(r, 3)).replace("✔  ", "").replace("✘  ", ""));
    }

    private void fillRoomFields() {
        int r = roomTable.getSelectedRow(); if (r < 0) return;
        txtRNo.setText(str(roomTable.getValueAt(r, 1)));
        txtRCap.setText(str(roomTable.getValueAt(r, 2)));
        cboRStatus.setSelectedItem(str(roomTable.getValueAt(r, 3)).replace("✔  ", "").replace("✘  ", ""));
    }

    private void fillUserFields() {
        int r = userTable.getSelectedRow(); if (r < 0) return;
        txtUUser.setText(str(userTable.getValueAt(r, 1)));
        txtUPass.setText("");
        cboURole.setSelectedItem(userTable.getValueAt(r, 2));
    }

    private String str(Object o) { return o == null ? "" : o.toString(); }

    private void clearStudentFields() { txtSName.setText(""); txtSRoom.setText(""); cboSStatus.setSelectedIndex(0); }
    private void clearRoomFields()    { txtRNo.setText(""); txtRCap.setText(""); cboRStatus.setSelectedIndex(0); }
    private void clearUserFields()    { txtUUser.setText(""); txtUPass.setText(""); cboURole.setSelectedIndex(0); }

    private boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // ── Getters for Controller ────────────────────────────────────────────────

    public String getStudentName()   { return txtSName.getText(); }
    public String getStudentRoom()   { return txtSRoom.getText(); }
    public String getStudentStatus() { return cboSStatus.getSelectedItem().toString(); }
    public JTable getStudentTable()  { return studentTable; }

    public String getRoomNo()        { return txtRNo.getText(); }
    public String getRoomCapacity()  { return txtRCap.getText(); }
    public String getRoomStatus()    { return cboRStatus.getSelectedItem().toString(); }
    public JTable getRoomTable()     { return roomTable; }

    public String getUserUsername()  { return txtUUser.getText(); }
    public String getUserPassword()  { return txtUPass.getText(); }
    public String getUserRole()      { return cboURole.getSelectedItem().toString(); }
    public JTable getUserTable()     { return userTable; }

    public JTable getBookingTable()  { return bookingTable; }
}
