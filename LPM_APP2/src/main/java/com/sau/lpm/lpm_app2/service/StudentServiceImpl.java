package com.sau.lpm.lpm_app2.service;

import com.sau.lpm.lpm_app2.dtos.StudentDTO;
import com.sau.lpm.lpm_app2.exception.ErrorMessages;
import com.sau.lpm.lpm_app2.exception.ResourceAlreadyExistsException;
import com.sau.lpm.lpm_app2.exception.ResourceNotFoundException;
import com.sau.lpm.lpm_app2.model.Student;
import com.sau.lpm.lpm_app2.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + id)).viewAsStudentDTO();
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(Student::viewAsStudentDTO).toList();
    }

    public StudentDTO createStudent(Student student) {
        if (studentRepository.findById(student.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_STUDENT_ALREADY_EXIST + ": " + student.getId());
        }
        return studentRepository.save(student).viewAsStudentDTO();
    }

    public StudentDTO updateStudent(Long id, Student student) {
        studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + id));
        student.setId(id);
        return studentRepository.save(student).viewAsStudentDTO();
    }

    public void deleteStudent(Long id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + id));
        studentRepository.deleteById(id);
    }

}

