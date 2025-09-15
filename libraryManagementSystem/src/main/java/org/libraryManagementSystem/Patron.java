package org.libraryManagementSystem;

/**
 * Brent Echols
 * CEN-3024C Software Development
 * 09/15/2025
 * Patron.java
 * Patron class represents a library user in the LMS.
 * Each Patron has:
 * A unique 7-digit ID
 * A name
 * An address
 * An overdue fine (0–250)
 */
public class Patron {
    private String id;      // Unique 7-digit identifier
    private String name;    // Patron's name
    private String address; // Patron's address
    private double fine;    // Patron's overdue fine amount

    public Patron(String id, String name, String address, double fine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fine = fine;
    }

    public String getID() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getFine() { return fine; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setFine(double fine) { this.fine = fine; }

    //Returns a readable string of the patron's information.
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name +
                " | Address: " + address + " | Fine: $" + String.format("%.2f", fine);
    }
}
