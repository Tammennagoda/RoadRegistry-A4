// Logan MacGregor s4095198
package com.roadregistry;

public class UnitTest3 {
    public static void main(String[] args) {
        System.out.println("Unit Test 3:"); 
        // Test case 3
        Person person = new Person("95$a#cimDH", "John", "Doe", "19|Highland Melbourne |Victoria |Australia", "15-03-2007");

        if (person.addPerson()) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }

        // Test case 8
        boolean updated = person.updatePersonalDetails(
            "78#@abchEF", 
            "John", 
            "Doe", 
            "19|Highland Street Melbourne |Victoria |Australia", 
            "15-03-2008"
        );
        System.out.println("Update result: " + (updated ? "Success" : "Failed"));

        // Test case 13
        String result = person.addDemeritPoints(4, "2024/01/01");
        System.out.println("Add demerit result: " + result);
    }
}