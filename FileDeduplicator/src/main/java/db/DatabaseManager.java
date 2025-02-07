package main.java.db;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/file_deduplication";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Rgukt@123";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                System.err.println("⚠️ Database Connection Error: " + e.getMessage());
                System.exit(1);
            }
        }
        return connection;
    }

    public static void createTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS file_hashes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    file_hash VARCHAR(64) UNIQUE NOT NULL,
                    file_path TEXT NOT NULL,
                    status ENUM('Unique', 'Duplicate - Deleted', 'Duplicate - Backed Up') NOT NULL
                )
                """;
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.err.println("⚠️ Error creating table: " + e.getMessage());
        }
    }

    public static String fileExistsInDB(String fileHash) {
        String query = "SELECT file_path FROM file_hashes WHERE file_hash = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setString(1, fileHash);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("file_path");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error querying database: " + e.getMessage());
        }
        return null;
    }

    public static void insertFileRecord(String fileHash, String filePath, String status) {
        String query = "INSERT IGNORE INTO file_hashes (file_hash, file_path, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setString(1, fileHash);
            pstmt.setString(2, filePath);
            pstmt.setString(3, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("⚠️ Error inserting record: " + e.getMessage());
        }
    }
}
