package main.java.deduplication;

import main.java.db.DatabaseManager;
import main.java.util.HashGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.util.Scanner;

public class FileDeduplicator {
    private final String targetDir;
    private final boolean backupMode;
    private final String backupDir;
    private Connection dbConnection;
    private long totalBytesSaved = 0;
    private int countCleaned = 0;

    public FileDeduplicator(String folderPath, boolean backupMode) {
        this.targetDir = folderPath;
        this.backupMode = backupMode;
        this.backupDir = backupMode ? Paths.get(targetDir, "duplicate_backup").toString() : null;

        dbConnection = DatabaseManager.getConnection();
        DatabaseManager.createTable();
    }

    public void scanAndRemoveDuplicates() {
        System.out.println("\nScanning directory: " + targetDir + "\n");

        File folder = new File(targetDir);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Error: Folder does not exist or is not a directory.");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) {
            System.err.println("Error: Unable to list files in directory.");
            return;
        }

        for (File file : files) {
            if (!file.isFile()) continue;

            String fileHash = HashGenerator.calculateHash(file);
            if (fileHash == null) continue;

            String existingFilePath = DatabaseManager.fileExistsInDB(fileHash);

            if (existingFilePath != null) {
                totalBytesSaved += file.length();
                countCleaned++;

                if (backupMode) {
                    moveToBackup(file);
                    DatabaseManager.insertFileRecord(fileHash, file.getAbsolutePath(), "Duplicate - Backed Up");
                } else {
                    file.delete();
                    DatabaseManager.insertFileRecord(fileHash, file.getAbsolutePath(), "Duplicate - Deleted");
                    System.out.println("🗑️ Removed duplicate: " + file.getName());
                }
            } else {
                DatabaseManager.insertFileRecord(fileHash, file.getAbsolutePath(), "Unique");
            }
        }

        printSummary();
    }

    private void moveToBackup(File file) {
        try {
            File backupFolder = new File(backupDir);
            if (!backupFolder.exists()) {
                backupFolder.mkdirs();
            }

            Path backupPath = Paths.get(backupDir, file.getName());
            Files.move(file.toPath(), backupPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved duplicate to backup: " + backupPath);
        } catch (IOException e) {
            System.err.println("⚠️ Error moving file to backup: " + e.getMessage());
        }
    }

    private void printSummary() {
        double mbSaved = totalBytesSaved / (1024.0 * 1024.0);
        System.out.println("\nDeduplication Completed");
        System.out.println("Unique files retained: Check database");
        System.out.println(" Duplicates " + (backupMode ? "moved to backup" : "removed") + ": " + countCleaned);
        System.out.printf("Total space saved: %.2f MB\n", mbSaved);
    }
}
