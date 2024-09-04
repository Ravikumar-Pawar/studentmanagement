package com.msrit.studentmanagement.repository;

import com.msrit.studentmanagement.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testFindStudentByUsn() {
        //Student student = new Student("USN018", "Vijay Kumar", "vijay.kumar@example.com", "3344556677", List.of("Physics", "Math"));
        //studentRepository.save(student);

        Student foundStudent = studentRepository.findById(Long.valueOf("USN018")).orElse(null);
        assertNotNull(foundStudent);
        assertEquals("Vijay Kumar", foundStudent.getName());
    }
}
