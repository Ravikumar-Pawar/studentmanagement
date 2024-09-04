package com.msrit.studentmanagement.repository;

import com.msrit.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUsn(String usn);

    void deleteByUsn(String usn);

    void deleteAllByUsn(String usn);
}
