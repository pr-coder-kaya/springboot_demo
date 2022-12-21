package com.example.springboot_demo.student;

import com.example.springboot_demo.utils.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudent(long id) {
        if (studentRepository.existsById(id)) return studentRepository.findStudentById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " does not exists");
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void createStudent(Student student) {
        if (FieldValidator.allStudentFieldsValid(student)) {
            if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sorry, the email you have entered is already in use");
            }
            studentRepository.save(student);
        }
    }

    public void deleteStudent(long id) {
        if (studentRepository.existsById(id)) studentRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " does not exists");
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    @Transactional
    public void updateStudentPatch(Student student, long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " does not exists"));

        if(student.getEmail() != null && FieldValidator.isEmailValid(student.getEmail())){
            if (!student.getEmail().equals(existingStudent.getEmail()) && studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sorry, the email you have entered is already in use");
            }
            else{
                existingStudent.setEmail(student.getEmail());
            }
        }

        if(student.getFirstName() != null && FieldValidator.isNameValid(student.getFirstName())) existingStudent.setFirstName(student.getFirstName());
        if(student.getLastName() != null && FieldValidator.isNameValid(student.getLastName())) existingStudent.setLastName(student.getLastName());
        if(student.getDateOfBirth() != null && FieldValidator.isDateOfBirthValid(student.getDateOfBirth())) existingStudent.setDateOfBirth(student.getDateOfBirth());
    }

    @Transactional
    public Student updateStudentPut(Student student, long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " does not exists"));

        if (FieldValidator.allStudentFieldsValid(student)) {
            if (!student.getEmail().equals(existingStudent.getEmail()) && studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sorry, the email you have entered is already in use");
            }
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setDateOfBirth(student.getDateOfBirth());
        }
        return existingStudent;
    }
}
