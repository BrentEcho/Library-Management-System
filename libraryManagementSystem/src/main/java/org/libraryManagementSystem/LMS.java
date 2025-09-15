package org.libraryManagementSystem;

import java.io.*;
import java.util.*;

/**
 * Brent Echols
 * CEN-3024C Software Development
 * 09/15/2025
 * LMS.java
 * LMS (Library Management System) manages all patron records.
 * Adding patrons manually or from a file, removing patrons,
 * displaying patrons, and navigating through a menu.
 */
public class LMS {
    private Map<String, Patron> patrons; // Stores patrons with ID as key


    public LMS() {
        patrons = new HashMap<>();
    }

    /**
     * Load patrons from a text file with format: ID-Name-Address-Fine
     * invalid lines are skipped and messages are logged for each line.
     */
    public List<String> addPatronFile(String filename) {
        List<String> messages = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            messages.add("ERROR: File not found: " + filename);
            return messages;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNo = 0;

            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("-", 4);
                if (parts.length != 4) {
                    messages.add("Line " + lineNo + ": INVALID FORMAT -> " + line);
                    continue;
                }

                String id = parts[0].trim();
                String name = parts[1].trim();
                String address = parts[2].trim();
                String fineStr = parts[3].trim();

                boolean validLine = true;

                // Validate ID
                if (!validateID(id)) {
                    messages.add("Line " + lineNo + ": INVALID ID -> " + id);
                    validLine = false;
                } else if (patrons.containsKey(id)) {
                    messages.add("Line " + lineNo + ": DUPLICATE ID -> " + id);
                    validLine = false;
                }

                // Validate name and address
                if (!validateName(name)) {
                    messages.add("Line " + lineNo + ": INVALID NAME -> " + name);
                    validLine = false;
                }
                if (!validateAddress(address)) {
                    messages.add("Line " + lineNo + ": INVALID ADDRESS -> " + address);
                    validLine = false;
                }

                // Validate fine
                double fine = 0;
                try {
                    fine = Double.parseDouble(fineStr);
                    if (fine < 0 || fine > 250) {
                        messages.add("Line " + lineNo + ": FINE OUT OF RANGE -> " + fineStr);
                        validLine = false;
                    }
                } catch (NumberFormatException e) {
                    messages.add("Line " + lineNo + ": INVALID FINE -> " + fineStr);
                    validLine = false;
                }

                // Add patron if valid
                if (validLine) {
                    patrons.put(id, new Patron(id, name, address, fine));
                    messages.add("Line " + lineNo + ": ADDED " + id + " - " + name);
                }
            }
        } catch (IOException e) {
            messages.add("ERROR: " + e.getMessage());
        }

        return messages;
    }
    // Add a patron manually using interactive validation

    public void addPatronManually(Scanner scanner) {
        String id;
        while (true) {
            System.out.print("Enter 7-digit ID: ");
            id = scanner.nextLine().trim();
            if (!validateID(id)) {
                System.out.println("Invalid ID. Must be exactly 7 digits.");
            } else if (patrons.containsKey(id)) {
                System.out.println("Duplicate ID. A patron with this ID already exists.");
            } else {
                break;
            }
        }

        String name;
        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine().trim();
            if (!validateName(name)) {
                System.out.println("Invalid name. Name cannot be empty.");
            } else {
                break;
            }
        }

        String address;
        while (true) {
            System.out.print("Enter Address: ");
            address = scanner.nextLine().trim();
            if (!validateAddress(address)) {
                System.out.println("Invalid address. Address cannot be empty.");
            } else {
                break;
            }
        }

        double fine;
        while (true) {
            System.out.print("Enter Fine (0-250): ");
            String fineStr = scanner.nextLine().trim();
            try {
                fine = Double.parseDouble(fineStr);
                if (fine < 0 || fine > 250) {
                    System.out.println("Fine must be between 0 and 250.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid fine. Please enter a numeric value.");
            }
        }

        patrons.put(id, new Patron(id, name, address, fine));
        System.out.println("Patron added successfully.");
    }

    public void removePatron(String id) {
        if (patrons.remove(id) != null) {
            System.out.println("Patron " + id + " removed successfully.");
        } else {
            System.out.println("No patron found with ID " + id);
        }
    }

    public void displayAllPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("No patrons in the system.");
        } else {
            for (Patron p : patrons.values()) {
                System.out.println(p);
            }
        }
    }

    // Menu-driven navigation for the LMS.

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Load patrons from file");
            System.out.println("2. Add patron manually");
            System.out.println("3. Remove patron by ID");
            System.out.println("4. Display all patrons");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number between 1 and 5.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter file name (e.g., patrons.txt): ");
                    String filename = scanner.nextLine();
                    List<String> messages = addPatronFile(filename);
                    messages.forEach(System.out::println);
                    break;
                case 2:
                    addPatronManually(scanner);
                    break;
                case 3:
                    System.out.print("Enter Patron ID to remove: ");
                    String id = scanner.nextLine();
                    removePatron(id);
                    break;
                case 4:
                    displayAllPatrons();
                    break;
                case 5:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Select 1-5.");
            }
        } while (choice != 5);
    }

    private boolean validateID(String id) { return id != null && id.matches("\\d{7}"); }
    private boolean validateName(String name) { return name != null && !name.trim().isEmpty(); }
    private boolean validateAddress(String address) { return address != null && !address.trim().isEmpty(); }
}
