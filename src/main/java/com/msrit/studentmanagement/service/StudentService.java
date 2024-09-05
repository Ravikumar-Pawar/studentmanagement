package com.msrit.studentmanagement.service;

import com.msrit.studentmanagement.model.Student;
import com.msrit.studentmanagement.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByUsn(String usn) {
        return studentRepository.findByUsn(usn);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudentByUsn(String usn) {
        studentRepository.deleteByUsn(usn);
    }
}
