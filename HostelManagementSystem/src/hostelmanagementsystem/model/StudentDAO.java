package hostelmanagementsystem.model;

import hostelmanagementsystem.database.DBConnection;
import java.sql.*;
import java.util.*;

public class StudentDAO {

    public boolean save(Student s) {
        String sql = "INSERT INTO students (name, room_no, status) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRoomNo());
            ps.setString(3, s.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Student s) {
        String sql = "UPDATE students SET name=?, room_no=?, status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRoomNo());
            ps.setString(3, s.getStatus());
            ps.setInt(4, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, name, room_no, status FROM students";
        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next())
                list.add(new Student(rs.getInt(1), rs.getString(2),
                                     rs.getString(3), rs.getString(4)));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getAvailableRooms() {
        List<String> rooms = new ArrayList<>();
        String sql = "SELECT room_no FROM rooms WHERE status='Available'";
        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next()) rooms.add(rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return rooms;
    }

    public boolean requestBooking(String studentName, String roomNo) {
        try (Connection c = DBConnection.getConnection()) {
            // Ensure student record exists
            int studentId = getOrCreateStudentId(studentName, c);
            if (studentId == -1) return false;

            // Insert booking
            String sql = "INSERT INTO bookings (student_id, student_name, room_no, booking_status) VALUES (?, ?, ?, 'Pending')";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                ps.setString(2, studentName);
                ps.setString(3, roomNo);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private int getOrCreateStudentId(String name, Connection c) throws SQLException {
        // Check if student exists
        String check = "SELECT id FROM students WHERE name=?";
        try (PreparedStatement ps = c.prepareStatement(check)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        // Create student record if not exists
        String insert = "INSERT INTO students (name, status) VALUES (?, 'Active')";
        try (PreparedStatement ps = c.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public List<Object[]> getBookingsFor(String studentName) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id, room_no, booking_date, booking_status FROM bookings WHERE student_name=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, studentName);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new Object[]{rs.getInt(1), rs.getString(2),
                                      rs.getString(3), rs.getString(4)});
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
