// Logan MacGregor s4095198
package com.roadregistry;

public class UnitTest4 {
        public static void main(String[] args) {
        System.out.println("Unit Test 4:"); 
        // Test case 4
        Person person = new Person("67$a#cimDH", "Mary", "Smith", "22|Albert Street Brisbane |Queensland |Australia", "17-10-2005");

        if (person.addPerson()) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }

        // Test case 9
        boolean updated = person.updatePersonalDetails(
            "78#@abchEF", 
            "Mary", 
            "Smith", 
            "22|Highland Street Melbourne |Victoria |Australia", 
            "17-10-2005"
        );
        System.out.println("Update result: " + (updated ? "Success" : "Failed"));

        // Test case 14
        String result = person.addDemeritPoints(8, "22/05/2024");
        System.out.println("Add demerit result: " + result);
    }
}
