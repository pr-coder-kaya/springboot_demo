package com.example.springboot_demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if(studentRepository.existsById(id)) studentRepository.deleteById(id);
        else throw new IllegalStateException("Student with id " + id + " does not exists");
    }
}
