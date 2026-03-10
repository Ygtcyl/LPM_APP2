package com.sau.lpm.lpm_app2.repository;


import com.sau.lpm.lpm_app2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
