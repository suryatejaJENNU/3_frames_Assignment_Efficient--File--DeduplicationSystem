package main.java.deduplication;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("📁 Enter the absolute folder path to scan for duplicates: ");
        String folderPath = scanner.nextLine().trim();

        System.out.print("Do you want to (D)elete duplicates or (B)ackup before deleting? (D/B): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        boolean backupMode = choice.equals("b");

        FileDeduplicator deduplicator = new FileDeduplicator(folderPath, backupMode);
        deduplicator.scanAndRemoveDuplicates();
    }
}
