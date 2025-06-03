// Logan MacGregor s4095198
package com.roadregistry;

public class UnitTest1 {
    public static void main(String[] args) {
        System.out.println("Unit Test 1:"); 
        // Test case 1
        Person person = new Person("56#@abcDEF", "Jackie", "Tran", "32|Highland Street Melbourne |Victoria |Australia", "23-04-2006");

        if (person.addPerson()) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }

        // Test case 6
        boolean updated = person.updatePersonalDetails(
            "56#$accKBL", 
            "Jackie", 
            "MacGregor", 
            "32|Highland Street Melbourne |Victoria |Australia", 
            "23-04-2006"
        );
        System.out.println("Update result: " + (updated ? "Success" : "Failed"));

         // Test case 11
        String result = person.addDemeritPoints(5, "01-12-2024");
        System.out.println("Add demerit result: " + result);
    }
}
