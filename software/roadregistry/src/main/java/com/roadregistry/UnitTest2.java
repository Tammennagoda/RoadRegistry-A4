// Logan MacGregor s4095198
package com.roadregistry;

public class UnitTest2 {
    public static void main(String[] args) {
        System.out.println("Unit Test 2:"); 
        // Test case 2
        Person person = new Person("56a$bcDEF", "Tom", "Peterson", "20|Highland Street Melbourne |Victoria |Australia", "15-11-1990");

        if (person.addPerson()) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }

        // Test case 7
        boolean updated = person.updatePersonalDetails(
            "78#@abchEF", 
            "Tom", 
            "Peterson", 
            "20|Highland Street Melbourne |Victoria |Australia", 
            "15-11-1990"
        );
        System.out.println("Update result: " + (updated ? "Success" : "Failed"));

        // Test case 12
        String result = person.addDemeritPoints(7, "22-04-2025");
        System.out.println("Add demerit result: " + result);
    }
}
