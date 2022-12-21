package com.example.springboot_demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "{studentId}")
    public Optional<Student> getStudent(@PathVariable("studentId") long id) {
        return studentService.getStudent(id);
    }

    @GetMapping(path = "getAll")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return student;
    }

    @DeleteMapping(path = "{studentId}")
    public String deleteStudent(@PathVariable("studentId") long id) {
        studentService.deleteStudent(id);
        return "Student with id " + id + " is deleted.";
    }

    @DeleteMapping(path = "deleteAll")
    public String deleteAllStudents() {
        studentService.deleteAllStudents();
        if (studentService.getAllStudents().size() == 0) return "All students are deleted.";
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An issue occurred when deleting all student.");
    }

    @PatchMapping(path = "{studentId}")
    public Optional<Student> updateStudentPatch(@RequestBody Student student, @PathVariable("studentId") long id) {
        studentService.updateStudentPatch(student, id);
        return studentService.getStudent(id);
    }

    @PutMapping(path = "{studentId}")
    public Optional<Student> updateStudentPut(@RequestBody Student student, @PathVariable("studentId") long id) {
        studentService.updateStudentPut(student, id);
        return studentService.getStudent(id);
    }
}
