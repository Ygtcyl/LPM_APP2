package com.sau.lpm.lpm_app2.service;

import com.sau.lpm.lpm_app2.dtos.StudentDTO;
import com.sau.lpm.lpm_app2.model.Student;

import java.util.List;

public interface StudentService {
    public List<StudentDTO> getAllStudents();
    public StudentDTO getStudentById(Long id);
    public StudentDTO createStudent(Student student);
    public StudentDTO updateStudent(Long id, Student student);
    public void deleteStudent(Long id);
}
