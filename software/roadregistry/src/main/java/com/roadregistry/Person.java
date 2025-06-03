// Logan MacGregor s4095198
package com.roadregistry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthday;
    private List<DemeritRecord> demeritRecords = new ArrayList<>();
    private boolean isSuspended;

    public Person(String personID, String firstName, String lastName, String address, String birthday) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
    }

// addPerson
public boolean addPerson() {
    // Condition 1: PersonID must be 10 characters long:
    // first 2 are digits from 2–9
    // characters 3–8 include at least two special characters
    // last 2 are uppercase letters
    if (personID.length() != 10 || 
        !personID.substring(0, 2).matches("[2-9]{2}") ||
        !personID.substring(2, 8).matches(".*[^a-zA-Z0-9].*.*[^a-zA-Z0-9].*") ||
        !personID.substring(8).matches("[A-Z]{2}")) {
        return false;
    }

    // Condition 2: Address must be in "Street Number | Street | City | State | Country" format, and State must be Victoria
    String[] addressParts = address.split("\\|");
    if (addressParts.length != 4 || !addressParts[1].contains("Street") || !addressParts[2].trim().equals("Victoria")) {
        return false;
    }

    // Condition 3: Birthday must be in DD-MM-YYYY format
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate.parse(birthday, formatter);
    } catch (DateTimeParseException e) {
        return false;
    }

    // If all conditions passed, write to text file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("persons.txt", true))) {
        writer.write(toString());
        writer.newLine();
        return true;
    } catch (IOException e) {
        return false;
    }
}

 // updatePersonlaDetails
 public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthday) {
    // Validate new birthday format
    LocalDate newDob;
    try {
        newDob = LocalDate.parse(newBirthday, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    } catch (DateTimeParseException e) {
        return false;
    }

    LocalDate currentDob;
    try {
        currentDob = LocalDate.parse(this.birthday, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    } catch (DateTimeParseException e) {
        return false;
    }

    // 1. Under 18 cannot change address
    if (!this.address.equals(newAddress)) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - currentDob.getYear();
        if (today.getDayOfYear() < currentDob.getDayOfYear()) {
            age--;
        }
        if (age < 18) return false;
    }

    // 2. If birthday is changed, no other fields can change
    if (!this.birthday.equals(newBirthday)) {
        if (!this.personID.equals(newID) || !this.firstName.equals(newFirstName) ||
            !this.lastName.equals(newLastName) || !this.address.equals(newAddress)) {
            return false;
        }
    }

    // 3. If first digit of personID is even, ID cannot be changed
    char firstChar = this.personID.charAt(0);
    if (Character.isDigit(firstChar) && (firstChar - '0') % 2 == 0) {
        if (!this.personID.equals(newID)) {
            return false;
        }
    }

    // PersonID format validation (same as addPerson method)
    if (newID.length() != 10 || 
        !newID.substring(0, 2).matches("[2-9]{2}") ||
        !newID.substring(2, 8).matches(".*[^a-zA-Z0-9].*.*[^a-zA-Z0-9].*") ||
        !newID.substring(8).matches("[A-Z]{2}")) {
        return false;
    }

    // Address format validation (same as addPerson method)
    String[] addressParts = newAddress.split("\\|");
    if (addressParts.length != 4 || !addressParts[1].contains("Street") || !addressParts[2].trim().equals("Victoria")) {
        return false;
    }

    // All validations passed — update
    this.personID = newID;
    this.firstName = newFirstName;
    this.lastName = newLastName;
    this.address = newAddress;
    this.birthday = newBirthday;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("persons.txt", true))) {
        writer.write("UPDATED: " + toString());
        writer.newLine();
        return true;
    } catch (IOException e) {
        return false;
    }
}

   // addDemeritPoints 
   public String addDemeritPoints(int points, String offenseDate) {
    // Condition 1: Points must be between 1-6
    if (points < 1 || points > 6) {
        return "Failed";
    }

    // Condition 2: Date format validation (DD-MM-YYYY)
    LocalDate date;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date = LocalDate.parse(offenseDate, formatter);
    } catch (DateTimeParseException e) {
        return "Failed";
    }

    LocalDate personDob;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        personDob = LocalDate.parse(this.birthday, formatter);
    } catch (DateTimeParseException e) {
        return "Failed";
    }

    // Calculate age at time of offense
    int age = date.getYear() - personDob.getYear();
    if (date.getDayOfYear() < personDob.getDayOfYear()) {
        age--;
    }

    // Add the new demerit record
    demeritRecords.add(new DemeritRecord(points, date));

    // Calculate total points within the last 2 years from offense date
    LocalDate twoYearsAgo = date.minusYears(2);
    int totalPointsWithinTwoYears = 0;
    
    for (DemeritRecord record : demeritRecords) {
        if (!record.getDate().isBefore(twoYearsAgo) && !record.getDate().isAfter(date)) {
            totalPointsWithinTwoYears += record.getPoints();
        }
    }

    // Condition 3: Set suspension based on age and total points
    if (age < 21) {
        // Under 21: suspend if total points within 2 years exceed 6
        if (totalPointsWithinTwoYears > 6) {
            isSuspended = true;
        }
    } else {
        // Over 21: suspend if total points within 2 years exceed 12
        if (totalPointsWithinTwoYears > 12) {
            isSuspended = true;
        }
    }

    // Write to file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("demerits.txt", true))) {
        writer.write(personID + "," + points + "," + offenseDate + "," + (isSuspended ? "Suspended" : "Active"));
        writer.newLine();
        return "Success";
    } catch (IOException e) {
        return "Failed";
    }
}
    @Override
    public String toString() {
        return personID + "," + firstName + "," + lastName + "," + address + "," + birthday + "," + (isSuspended ? "Suspended" : "Active");
    }
}
