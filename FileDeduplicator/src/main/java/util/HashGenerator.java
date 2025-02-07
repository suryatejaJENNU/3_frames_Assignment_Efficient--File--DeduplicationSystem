package main.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class HashGenerator {
    public static String calculateHash(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[65536];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            StringBuilder hash = new StringBuilder();
            for (byte b : digest.digest()) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            System.err.println("⚠️ Error processing " + file.getName() + ": " + e.getMessage());
            return null;
        }
    }
}
