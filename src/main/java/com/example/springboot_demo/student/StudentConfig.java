package com.example.springboot_demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student s1 = new Student(
                    "John",
                    "Doe",
                    "johndoe@gmail.com",
                    LocalDate.of(2022, Month.NOVEMBER, 22)
            );

            Student s2 = new Student(
                    "Alex",
                    "Smith",
                    "alexsmith@gmail.com",
                    LocalDate.of(2021, Month.NOVEMBER, 22)
            );

            studentRepository.saveAll(new ArrayList<>(Arrays.asList(s1, s2)));
        };
    }
}
