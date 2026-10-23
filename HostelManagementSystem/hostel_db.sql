-- ============================================================
--  HOSTEL MANAGEMENT SYSTEM - Database Schema
--  Run this entire script in MySQL Workbench before launching
-- ============================================================
CREATE DATABASE IF NOT EXISTS hostel_db;
USE hostel_db;

-- 1. users  (login accounts for Admin and Student)
CREATE TABLE IF NOT EXISTS users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  UNIQUE NOT NULL,
    password VARCHAR(50)  NOT NULL,
    role     ENUM('Admin','Student') NOT NULL DEFAULT 'Student'
);

-- 2. students  (hostel resident records)
CREATE TABLE IF NOT EXISTS students (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    room_no VARCHAR(20),
    status  ENUM('Active','Inactive') DEFAULT 'Active'
);

-- 3. rooms  (physical rooms in the hostel)
CREATE TABLE IF NOT EXISTS rooms (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    room_no  VARCHAR(20) UNIQUE NOT NULL,
    capacity INT  DEFAULT 1,
    status   ENUM('Available','Occupied') DEFAULT 'Available'
);

-- 4. bookings  (room booking requests with approval workflow)
CREATE TABLE IF NOT EXISTS bookings (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    student_id     INT,
    student_name   VARCHAR(100),
    room_no        VARCHAR(20),
    booking_date   DATE DEFAULT (CURDATE()),
    booking_status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE SET NULL
);

-- ── Seed Data ────────────────────────────────────────────────
INSERT IGNORE INTO users (username, password, role) VALUES
    ('admin',    'admin123', 'Admin'),
    ('student1', 'pass123',  'Student');

INSERT IGNORE INTO rooms (room_no, capacity, status) VALUES
    ('R101', 2, 'Available'),
    ('R102', 2, 'Available'),
    ('R103', 1, 'Available');
