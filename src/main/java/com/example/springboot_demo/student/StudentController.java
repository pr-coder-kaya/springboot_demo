package com.example.springboot_demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    @GetMapping
    public List<Student> getStudents(){
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
