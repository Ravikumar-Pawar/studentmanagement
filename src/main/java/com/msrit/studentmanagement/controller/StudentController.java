package com.msrit.studentmanagement.controller;

import com.msrit.studentmanagement.model.Student;
import com.msrit.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllStudents() {


        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<>("No students found", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/usn/{usn}")
    public ResponseEntity<?> getStudentByUsn(@PathVariable String usn) {
        Student student = studentService.getStudentByUsn(usn);
            if (student==null){
                return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student,HttpStatus.OK);



    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (studentService.getStudentByUsn(student.getUsn())!=null) {
            return new ResponseEntity<>("Student with this USN already exists", HttpStatus.CONFLICT);
        }
        Student createdStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/usn/{usn}")
    public ResponseEntity<?> updateStudent(@PathVariable String usn, @RequestBody Student student) {
        Student existingdata = studentService.getStudentByUsn(usn);
        if (existingdata==null){
            return new ResponseEntity<>("Student with this USN not found", HttpStatus.NOT_FOUND);

        }
        existingdata.setCourses(student.getCourses());
        existingdata.setName(student.getName());
        existingdata.setEmailId(student.getEmailId());
        existingdata.setPhone(student.getPhone());
        Student updatedStudent = studentService.saveStudent(existingdata);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/usn/{usn}")
    public ResponseEntity<?> deleteStudent(@PathVariable String usn) {
        if (studentService.getStudentByUsn(usn)==null) {
            return new ResponseEntity<>("Student with this USN not found", HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudentByUsn(usn);
        return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
    }
}
