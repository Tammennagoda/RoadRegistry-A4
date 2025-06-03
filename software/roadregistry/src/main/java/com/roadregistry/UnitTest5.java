// Logan MacGregor s4095198
package com.roadregistry;

public class UnitTest5 {
        public static void main(String[] args) {
        System.out.println("Unit Test 5:"); 
        // Test case 5
        Person person = new Person("99$@#gllGJ", "Luke", "Skywalker", "78|Highland Street Melbourne |Victoria |Australia", "1998-11-21");

        if (person.addPerson()) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }

        // Test case 10
        boolean updated = person.updatePersonalDetails(
            "88$@bgllMM", 
            "Luke", 
            "Skywalker", 
            "89|Highland Street Melbourne |Victoria |Australia", 
            "29-08-1999"
        );
        System.out.println("Update result: " + (updated ? "Success" : "Failed"));

        // Test case 15
        String result = person.addDemeritPoints(15, "17/08/2024");
        System.out.println("Add demerit result: " + result);
    }
}
