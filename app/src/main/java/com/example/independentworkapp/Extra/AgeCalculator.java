package com.example.independentworkapp.Extra;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeCalculator {
    public int calculate(String date){
        String dobStr = "06/10/2002";
        // Parse the input string to LocalDate object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dob = LocalDate.parse(date, formatter);

        // Current date
        LocalDate today = LocalDate.now();

        // Calculate the difference between the two dates
        Period age = Period.between(dob, today);

        // Get the age in years
        int years = age.getYears();
        return years;
    }
}
