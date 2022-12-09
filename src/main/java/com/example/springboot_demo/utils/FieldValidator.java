package com.example.springboot_demo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FieldValidator {
    public static boolean isNameValid(String name) {
        if (name == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid/missing field");
        else if (name.trim().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field value cannot be empty");
        else if (!name.matches("^[a-zA-Z0-9\\s]*$")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid character in the field");
        return true;
    }

    public static boolean isEmailValid(String email) {
        if (email == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid/missing field");
        else if (email.trim().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field value cannot be empty");
        else if (!email.matches("[\\w.-]{2,}@[\\w.-]{2,}\\.[\\w.-]{2,}"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format. The expected format is <2+chars>@<2+chars>.<2+chars> and only digits, letters, and @.-_ characters are allowed.");
        return true;
    }

    public static boolean isDateOfBirthValid(String date) {
        try{
            if(date == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid/missing field");
            else if(date.trim().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field value cannot be empty");
            else if(isDateFuture(date)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date. The date of birth cannot be a future date.");
            else if(isDate100YearsOrPast(date)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date. The date of birth cannot be older than 100 years.");
        }
        catch (DateTimeParseException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. The expected date format is yyyy-MM-dd");
        }
        return true;
    }

    public static boolean isDateFuture(String date){
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date, dateTimeFormatter).isAfter(localDate);
    }

    public static boolean isDate100YearsOrPast(String date){
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter).plusYears(100).getYear() < localDate.getYear();
    }
}
