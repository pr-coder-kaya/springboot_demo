package com.example.springboot_demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/get/{studentId}")
    public Optional<Student> getStudent(@PathVariable("studentId") long id) {
        return studentService.getStudent(id);
    }

    @GetMapping(path = "/getAll")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping(path = "/create")
    public void createStudent(@RequestBody Student student){
        studentService.createStudent(student);
    }

    @DeleteMapping(path = "/delete/{studentId}")
    public void deleteStudent(@PathVariable("studentId") long id){
        studentService.deleteStudent(id);
    }

    @DeleteMapping(path = "/deleteAll")
    public void deleteAllStudents(){
        studentService.deleteAllStudents();
    }

    @PutMapping(path = "update/{studentId}")
    public void updateStudent(@PathVariable("studentId") long id,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false)LocalDate dateOfBirth){
        studentService.updateStudent(id, firstName, lastName, email, dateOfBirth);
    }
}
