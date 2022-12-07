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
    public Student createStudent(@RequestBody Student student){
        studentService.createStudent(student);
        return student;
    }

    @DeleteMapping(path = "/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") long id){
        studentService.deleteStudent(id);
        return "Student with id " + id + " is deleted.";
    }

    @DeleteMapping(path = "/deleteAll")
    public String deleteAllStudents(){
        studentService.deleteAllStudents();
        if(studentService.getAllStudents().size() == 0) return "All students are deleted.";
        throw new IllegalStateException("An issue occurred when deleting all student.");
    }

    @PatchMapping(path = "update/{studentId}")
    public Optional<Student> updateStudent(@PathVariable("studentId") long id,
                                           @RequestParam(required = false) String firstName,
                                           @RequestParam(required = false) String lastName,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String dateOfBirth){
        studentService.updateStudent(id, firstName, lastName, email, dateOfBirth);
        return studentService.getStudent(id);
    }

    @PutMapping(path = "update/{studentId}")
    public Optional<Student> updateStudent(@RequestBody Student student, @PathVariable("studentId") long id){
        studentService.updateStudent(student, id);
        return studentService.getStudent(id);
    }
}
