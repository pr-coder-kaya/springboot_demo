package com.example.springboot_demo.student;

import com.example.springboot_demo.utils.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudent(long id){
        if(studentRepository.existsById(id)) return studentRepository.findStudentById(id);
        else throw new IllegalStateException("Student with id " + id + " does not exists");
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void createStudent(Student student) {
        if(FieldValidator.isNameValid(student.getFirstName()) &&
                FieldValidator.isNameValid(student.getLastName()) &&
                FieldValidator.isDateOfBirthValid(student.getDateOfBirth()) &&
                FieldValidator.isEmailValid(student.getEmail())
        ){
            if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
                throw new IllegalStateException("Email is already taken");
            }
            studentRepository.save(student);
        }
    }

    public void deleteStudent(long id) {
        if(studentRepository.existsById(id)) studentRepository.deleteById(id);
        else throw new IllegalStateException("Student with id " + id + " does not exists");
    }

    public void deleteAllStudents(){
        studentRepository.deleteAll();
    }

    @Transactional
    public void updateStudent(long id, String firstName, String lastName, String email, String dateOfBirth){
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exists"));

        if(firstName != null && !firstName.trim().isEmpty() && !student.getFirstName().equals(firstName)) student.setFirstName(firstName);

        if(lastName != null && !lastName.trim().isEmpty() && !student.getLastName().equals(lastName)) student.setLastName(lastName);

        if(email != null && !email.trim().isEmpty() && !student.getEmail().equals(email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()) throw new IllegalStateException("Email is already taken");
            student.setEmail(email);
        }

        if(dateOfBirth != null && !student.getDateOfBirth().equals(dateOfBirth)) student.setDateOfBirth(dateOfBirth);
    }

    @Transactional
    public Student updateStudent(Student student, long id){
        Student s = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exists"));
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        s.setEmail(student.getEmail());
        s.setDateOfBirth(student.getDateOfBirth());
        return s;
    }
}
