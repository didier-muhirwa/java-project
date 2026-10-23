package hostelmanagementsystem.controller;

import hostelmanagementsystem.model.AdminDAO;
import hostelmanagementsystem.model.Student;
import hostelmanagementsystem.model.StudentDAO;
import hostelmanagementsystem.view.AdminDashboard;
import hostelmanagementsystem.view.LoginForm;
import hostelmanagementsystem.view.StudentDashboard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * LAYER 5 – CONTROLLER (The Worker)
 * Single controller that handles both Admin and Student roles.
 *
 * Admin path  → takes info from AdminDashboard, executes SQL via AdminDAO / StudentDAO.
 * Student path → takes info from StudentDashboard, executes SQL via StudentDAO.
 *
 * Commands: INSERT (Save), UPDATE (Edit), DELETE (Remove), SELECT (Display).
 */
public class StudentController {

    // Admin role
    private AdminDashboard  adminView;
    private final AdminDAO  adminDAO   = new AdminDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    // Student role
    private StudentDashboard studentView;
    private String           currentStudent;

    // ── Admin Constructor ─────────────────────────────────────────────────────
    public StudentController(AdminDashboard view) {
        this.adminView = view;
    }

    // ── Student Constructor ───────────────────────────────────────────────────
    public StudentController(StudentDashboard view, String studentName) {
        this.studentView    = view;
        this.currentStudent = studentName;
        wireStudentButtons();
        loadMyBookings();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ADMIN – STUDENTS CRUD
    // ══════════════════════════════════════════════════════════════════════════

    public void saveStudent() {
        Student s = new Student(adminView.getStudentName(),
                                adminView.getStudentRoom(),
                                adminView.getStudentStatus());
        if (studentDAO.save(s)) { msg("Student saved."); loadStudents(); }
        else msg("Failed to save student.");
    }

    public void updateStudent() {
        int row = adminView.getStudentTable().getSelectedRow();
        if (row < 0) { msg("Select a student row first."); return; }
        int id = (int) adminView.getStudentTable().getValueAt(row, 0);
        Student s = new Student(id, adminView.getStudentName(),
                                    adminView.getStudentRoom(),
                                    adminView.getStudentStatus());
        if (studentDAO.update(s)) { msg("Student updated."); loadStudents(); }
        else msg("Update failed.");
    }

    public void deleteStudent() {
        int row = adminView.getStudentTable().getSelectedRow();
        if (row < 0) { msg("Select a student row first."); return; }
        int id = (int) adminView.getStudentTable().getValueAt(row, 0);
        if (studentDAO.delete(id)) { msg("Student deleted."); loadStudents(); }
        else msg("Delete failed.");
    }

    public void loadStudents() {
        DefaultTableModel m = (DefaultTableModel) adminView.getStudentTable().getModel();
        m.setRowCount(0);
        for (Student s : studentDAO.getAll())
            m.addRow(new Object[]{s.getId(), s.getName(), s.getRoomNo(), s.getStatus()});
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ADMIN – ROOMS CRUD
    // ══════════════════════════════════════════════════════════════════════════

    public void saveRoom() {
        String no = adminView.getRoomNo(), cap = adminView.getRoomCapacity();
        if (no.isEmpty() || cap.isEmpty()) { msg("Fill all room fields."); return; }
        try {
            if (adminDAO.saveRoom(no, Integer.parseInt(cap))) { msg("Room created."); loadRooms(); }
            else msg("Failed to create room.");
        } catch (NumberFormatException e) { msg("Capacity must be a number."); }
    }

    public void updateRoom() {
        int row = adminView.getRoomTable().getSelectedRow();
        if (row < 0) { msg("Select a room row first."); return; }
        int id = (int) adminView.getRoomTable().getValueAt(row, 0);
        try {
            if (adminDAO.updateRoom(id, adminView.getRoomNo(),
                    Integer.parseInt(adminView.getRoomCapacity()), adminView.getRoomStatus())) {
                msg("Room updated."); loadRooms();
            } else msg("Update failed.");
        } catch (NumberFormatException e) { msg("Capacity must be a number."); }
    }

    public void deleteRoom() {
        int row = adminView.getRoomTable().getSelectedRow();
        if (row < 0) { msg("Select a room row first."); return; }
        int id = (int) adminView.getRoomTable().getValueAt(row, 0);
        if (adminDAO.deleteRoom(id)) { msg("Room deleted."); loadRooms(); }
        else msg("Delete failed.");
    }

    public void loadRooms() {
        DefaultTableModel m = (DefaultTableModel) adminView.getRoomTable().getModel();
        m.setRowCount(0);
        for (Object[] r : adminDAO.getAllRooms()) m.addRow(r);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ADMIN – USERS CRUD
    // ══════════════════════════════════════════════════════════════════════════

    public void saveUser() {
        String u = adminView.getUserUsername(), p = adminView.getUserPassword();
        if (u.isEmpty() || p.isEmpty()) { msg("Fill all user fields."); return; }
        if (adminDAO.saveUser(u, p, adminView.getUserRole())) { msg("User added."); loadUsers(); }
        else msg("Failed (username may already exist).");
    }

    public void updateUser() {
        int row = adminView.getUserTable().getSelectedRow();
        if (row < 0) { msg("Select a user row first."); return; }
        int id = (int) adminView.getUserTable().getValueAt(row, 0);
        if (adminDAO.updateUser(id, adminView.getUserUsername(),
                adminView.getUserPassword(), adminView.getUserRole())) {
            msg("User updated."); loadUsers();
        } else msg("Update failed.");
    }

    public void deleteUser() {
        int row = adminView.getUserTable().getSelectedRow();
        if (row < 0) { msg("Select a user row first."); return; }
        int id = (int) adminView.getUserTable().getValueAt(row, 0);
        if (adminDAO.deleteUser(id)) { msg("User deleted."); loadUsers(); }
        else msg("Delete failed.");
    }

    public void loadUsers() {
        DefaultTableModel m = (DefaultTableModel) adminView.getUserTable().getModel();
        m.setRowCount(0);
        for (Object[] u : adminDAO.getAllUsers()) m.addRow(u);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ADMIN – BOOKINGS (Approve / Reject)
    // ══════════════════════════════════════════════════════════════════════════

    public void approveBooking() { changeBookingStatus("Approved"); }
    public void rejectBooking()  { changeBookingStatus("Rejected"); }

    private void changeBookingStatus(String status) {
        int row = adminView.getBookingTable().getSelectedRow();
        if (row < 0) { msg("Select a booking row first."); return; }
        int id = (int) adminView.getBookingTable().getValueAt(row, 0);
        if (adminDAO.updateBookingStatus(id, status)) { msg("Booking " + status + "."); loadBookings(); }
        else msg("Failed to update booking.");
    }

    public void loadBookings() {
        DefaultTableModel m = (DefaultTableModel) adminView.getBookingTable().getModel();
        m.setRowCount(0);
        for (Object[] b : adminDAO.getAllBookings()) m.addRow(b);
    }

    /** Called once on AdminDashboard open to populate all 4 tables. */
    public void loadAll() {
        loadStudents(); loadRooms(); loadUsers(); loadBookings();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  STUDENT – Check rooms / Apply / Logout
    // ══════════════════════════════════════════════════════════════════════════

    private void wireStudentButtons() {
        studentView.btnCheckRooms.addActionListener(e -> {
            List<String> rooms = studentDAO.getAvailableRooms();
            if (rooms.isEmpty()) JOptionPane.showMessageDialog(studentView, "No rooms available.");
            else JOptionPane.showMessageDialog(studentView, "Available: " + String.join(", ", rooms));
        });

        studentView.btnApply.addActionListener(e -> {
            List<String> rooms = studentDAO.getAvailableRooms();
            if (rooms.isEmpty()) { JOptionPane.showMessageDialog(studentView, "No rooms available."); return; }
            String selected = (String) JOptionPane.showInputDialog(studentView,
                    "Select a room to request:", "Apply for Room",
                    JOptionPane.QUESTION_MESSAGE, null, rooms.toArray(), rooms.get(0));
            if (selected != null) {
                if (studentDAO.requestBooking(currentStudent, selected)) {
                    JOptionPane.showMessageDialog(studentView,
                            "Booking request sent for " + selected + ".\nWaiting for admin approval.");
                    loadMyBookings();
                } else {
                    JOptionPane.showMessageDialog(studentView, "Booking failed. Try again.");
                }
            }
        });

        studentView.btnLogout.addActionListener(e -> {
            studentView.dispose();
            new LoginForm().setVisible(true);
        });
    }

    private void loadMyBookings() {
        DefaultTableModel m = (DefaultTableModel) studentView.bookingTable.getModel();
        m.setRowCount(0);
        for (Object[] b : studentDAO.getBookingsFor(currentStudent)) m.addRow(b);
    }

    private void msg(String text) {
        JOptionPane.showMessageDialog(adminView != null ? adminView : studentView, text);
    }
}
