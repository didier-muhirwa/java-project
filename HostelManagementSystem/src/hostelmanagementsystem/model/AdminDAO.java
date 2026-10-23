package hostelmanagementsystem.model;

import hostelmanagementsystem.database.DBConnection;
import java.sql.*;
import java.util.*;

/**
 * LAYER 3 – MODEL (DAO)
 * Executes all SQL for rooms, users, and bookings tables.
 * Called exclusively by StudentController (admin role path).
 */
public class AdminDAO {

    // ── ROOMS: INSERT / UPDATE / DELETE / SELECT ──────────────────────────────

    public boolean saveRoom(String roomNo, int capacity) {
        String sql = "INSERT INTO rooms (room_no, capacity, status) VALUES (?, ?, 'Available')";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, roomNo); ps.setInt(2, capacity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateRoom(int id, String roomNo, int capacity, String status) {
        String sql = "UPDATE rooms SET room_no=?, capacity=?, status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, roomNo); ps.setInt(2, capacity);
            ps.setString(3, status); ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Object[]> getAllRooms() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id, room_no, capacity, status FROM rooms";
        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next())
                list.add(new Object[]{rs.getInt(1), rs.getString(2),
                                      rs.getInt(3),  rs.getString(4)});
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ── USERS: INSERT / UPDATE / DELETE / SELECT ──────────────────────────────

    public boolean saveUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username); ps.setString(2, password); ps.setString(3, role);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateUser(int id, String username, String password, String role) {
        String sql = "UPDATE users SET username=?, password=?, role=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username); ps.setString(2, password);
            ps.setString(3, role);     ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Object[]> getAllUsers() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users";
        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next())
                list.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3)});
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ── BOOKINGS: SELECT / APPROVE / REJECT ───────────────────────────────────

    public List<Object[]> getAllBookings() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id, student_name, room_no, booking_date, booking_status FROM bookings";
        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next())
                list.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3),
                                      rs.getString(4), rs.getString(5)});
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET booking_status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status); ps.setInt(2, bookingId);
            if (ps.executeUpdate() > 0) {
                if ("Approved".equals(status)) setRoomStatus(bookingId, "Occupied",  c);
                if ("Rejected".equals(status)) setRoomStatus(bookingId, "Available", c);
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private void setRoomStatus(int bookingId, String status, Connection c) throws SQLException {
        String sql = "UPDATE rooms SET status=? WHERE room_no=(SELECT room_no FROM bookings WHERE id=?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status); ps.setInt(2, bookingId); ps.executeUpdate();
        }
    }
}
