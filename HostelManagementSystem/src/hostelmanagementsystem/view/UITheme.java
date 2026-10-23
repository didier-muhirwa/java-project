package hostelmanagementsystem.view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class UITheme {

    // ── Palette ───────────────────────────────────────────────────────────────
    public static final Color BG_DARK        = new Color(18,  18,  35);
    public static final Color BG_SIDEBAR     = new Color(26,  26,  50);
    public static final Color BG_CARD        = new Color(255, 255, 255);
    public static final Color BG_PAGE        = new Color(245, 246, 250);
    public static final Color BG_TABLE_HEAD  = new Color(63,  81, 181);
    public static final Color BG_TABLE_ALT   = new Color(240, 242, 255);
    public static final Color ACCENT         = new Color(63,  81, 181);
    public static final Color ACCENT_GREEN   = new Color(46, 125,  50);
    public static final Color ACCENT_RED     = new Color(183,  28,  28);
    public static final Color ACCENT_ORANGE  = new Color(230, 119,   0);
    public static final Color ACCENT_TEAL    = new Color(0,  137, 123);
    public static final Color NAV_ACTIVE_BG  = new Color(63,  81, 181);
    public static final Color TEXT_PRIMARY   = new Color(33,  33,  33);
    public static final Color TEXT_LIGHT     = new Color(255, 255, 255);
    public static final Color TEXT_MUTED     = new Color(117, 117, 117);
    public static final Color BORDER_COLOR   = new Color(220, 220, 235);

    // ── Fonts ─────────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD,  14);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BTN     = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_EMOJI   = new Font("Segoe UI Emoji", Font.PLAIN, 14);

    // ── Button ────────────────────────────────────────────────────────────────
    public static JButton button(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setBackground(bg);
        btn.setForeground(TEXT_LIGHT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 36));
        btn.addMouseListener(new MouseAdapter() {
            final Color orig = bg;
            public void mouseEntered(MouseEvent e) { btn.setBackground(orig.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(orig); }
        });
        return btn;
    }

    // ── TextField ─────────────────────────────────────────────────────────────
    public static JTextField field(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        f.setBackground(Color.WHITE);
        return f;
    }

    // ── PasswordField ─────────────────────────────────────────────────────────
    public static JPasswordField passField(int cols) {
        JPasswordField f = new JPasswordField(cols);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return f;
    }

    // ── ComboBox ──────────────────────────────────────────────────────────────
    public static JComboBox<String> combo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(FONT_BODY);
        c.setBackground(Color.WHITE);
        c.setPreferredSize(new Dimension(130, 34));
        return c;
    }

    // ── Label ─────────────────────────────────────────────────────────────────
    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    // ── Icon + Text label (emoji + text side by side) ─────────────────────────
    public static JLabel iconLabel(String emoji, String text, Font font, Color color) {
        JLabel l = new JLabel(emoji + "  " + text);
        l.setFont(font);
        l.setForeground(color);
        return l;
    }

    // ── Page Title with icon ──────────────────────────────────────────────────
    public static JLabel pageTitle(String emoji, String text) {
        JLabel l = new JLabel(emoji + "  " + text);
        l.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        l.setForeground(BG_DARK);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        return l;
    }

    // ── Section Title with icon ───────────────────────────────────────────────
    public static JLabel sectionTitle(String emoji, String text) {
        JLabel l = new JLabel(emoji + "  " + text);
        l.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        l.setForeground(ACCENT);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return l;
    }

    // ── Legacy sectionTitle (no icon) ────────────────────────────────────────
    public static JLabel sectionTitle(String text) {
        return sectionTitle("", text);
    }

    // ── Stat Card (icon + number + label) ────────────────────────────────────
    public static JPanel statCard(String emoji, String value, String label, Color accent) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));

        // Left accent bar
        JPanel bar = new JPanel();
        bar.setBackground(accent);
        bar.setPreferredSize(new Dimension(5, 0));

        JLabel ico = new JLabel(emoji, SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        ico.setPreferredSize(new Dimension(44, 44));

        JPanel text = new JPanel(new GridLayout(2, 1, 0, 2));
        text.setOpaque(false);
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        val.setForeground(accent);
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(TEXT_MUTED);
        text.add(val); text.add(lbl);

        card.add(bar,  BorderLayout.WEST);
        card.add(ico,  BorderLayout.CENTER);
        card.add(text, BorderLayout.EAST);
        return card;
    }

    // ── Card Panel ────────────────────────────────────────────────────────────
    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        return p;
    }

    // ── Table styling ─────────────────────────────────────────────────────────
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(197, 202, 233));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setBackground(BG_CARD);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        table.getTableHeader().setBackground(BG_TABLE_HEAD);
        table.getTableHeader().setForeground(TEXT_LIGHT);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                if (!sel) setBackground(row % 2 == 0 ? BG_CARD : BG_TABLE_ALT);
                return this;
            }
        });
    }

    // ── ScrollPane ────────────────────────────────────────────────────────────
    public static JScrollPane scrollPane(JTable table) {
        styleTable(table);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        sp.getViewport().setBackground(BG_CARD);
        return sp;
    }

    // ── Form Row ──────────────────────────────────────────────────────────────
    public static JPanel formRow(String emoji, String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        JLabel lbl = new JLabel(emoji + " " + labelText);
        lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setPreferredSize(new Dimension(110, 34));
        row.add(lbl,   BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    // ── Legacy formRow (no icon) ──────────────────────────────────────────────
    public static JPanel formRow(String labelText, JComponent field) {
        return formRow("", labelText, field);
    }

    // ── Status Badge Renderer ─────────────────────────────────────────────────
    public static TableCellRenderer statusBadgeRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                String val = v == null ? "" : v.toString();
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setFont(FONT_BTN);
                if (!sel) {
                    setBackground(row % 2 == 0 ? BG_CARD : BG_TABLE_ALT);
                    switch (val) {
                        case "Approved", "Active", "Available" -> { setForeground(ACCENT_GREEN); setText("✔  " + val); }
                        case "Rejected", "Inactive", "Occupied" -> { setForeground(ACCENT_RED);  setText("✘  " + val); }
                        case "Pending"  -> { setForeground(ACCENT_ORANGE); setText("⏳  " + val); }
                        default         -> setForeground(TEXT_PRIMARY);
                    }
                }
                return this;
            }
        };
    }
}
