package hostelmanagementsystem.model;

/**
 * LAYER 3 – MODEL (POJO)
 * Holds student/resident data (name, room_no, status).
 * Passed between View → Controller → DAO.
 */
public class Student {
    private int    id;
    private String name;
    private String roomNo;
    private String status;

    public Student(String name, String roomNo, String status) {
        this.name   = name;
        this.roomNo = roomNo;
        this.status = status;
    }

    public Student(int id, String name, String roomNo, String status) {
        this.id     = id;
        this.name   = name;
        this.roomNo = roomNo;
        this.status = status;
    }

    public int    getId()     { return id; }
    public String getName()   { return name; }
    public String getRoomNo() { return roomNo; }
    public String getStatus() { return status; }
}
