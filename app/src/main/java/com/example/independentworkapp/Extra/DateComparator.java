package com.example.independentworkapp.Extra;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateComparator {
    public int date1(String Date1){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Parse the date strings into LocalDate objects
        LocalDate localDate1 = LocalDate.parse(Date1, formatter);
        // Check if date1 is today's date or later
        if (localDate1.isAfter(today) || localDate1.isEqual(today)) {
           return 1;
        } else {
            return 0;
        }
    }
    public int date2(String Date1,String Date2){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Parse the date strings into LocalDate objects
        LocalDate localDate1 = LocalDate.parse(Date1, formatter);
        LocalDate localDate2 = LocalDate.parse(Date2, formatter);
        // Check if date1 is today's date or later
        if (localDate1.isAfter(localDate1) || localDate1.isEqual(localDate1)) {
            return 1;
        } else {
            return 0;
        }
    }
}
