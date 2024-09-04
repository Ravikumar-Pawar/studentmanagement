package com.msrit.studentmanagement.service;

import com.msrit.studentmanagement.model.Student;
import com.msrit.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StudentServiceTests {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    public void testAddStudent() {
        MockitoAnnotations.openMocks(this);
        Student student = new Student("USN017", "Sanya Patel", "sanya.patel@example.com", "2233445566", List.of("Math", "Biology"));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.addStudent(student);
        assertNotNull(result);
        assertEquals("Sanya Patel", result.getName());
    }
}
