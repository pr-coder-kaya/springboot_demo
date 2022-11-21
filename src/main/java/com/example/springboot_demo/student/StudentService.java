package com.example.springboot_demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentService {

    public List<Student> getStudents() {
        return new ArrayList<>(Arrays.asList(
                new Student(1,
                        "John",
                        "Doe",
                        "johndoe@gmail.com",
                        LocalDate.of(2022, Month.NOVEMBER,
                                21))
        )
        );
    }

}
