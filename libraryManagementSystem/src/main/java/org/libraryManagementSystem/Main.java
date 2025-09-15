package org.libraryManagementSystem;

/**
 * Brent Echols
 * CEN-3024C Software Development
 * 09/15/2025
 * Main.java
 * Starts the application and shows the main menu.
 */
public class Main {
    public static void main(String[] args) {
        LMS lms = new LMS(); // Create LMS instance
        lms.showMenu();      // Start interactive menu
    }
}
