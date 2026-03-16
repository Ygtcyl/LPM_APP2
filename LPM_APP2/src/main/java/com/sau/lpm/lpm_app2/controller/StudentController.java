package com.sau.lpm.lpm_app2.controller;

import com.sau.lpm.lpm_app2.dtos.StudentDTO;
import com.sau.lpm.lpm_app2.model.Place;
import com.sau.lpm.lpm_app2.model.Student;
import com.sau.lpm.lpm_app2.service.StudentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private StudentServiceImpl studentService;
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        logger.info("Get all student");
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("Get student by id {}", id);
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody Student student) {
        logger.info("Creating student {}", student.getId());
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (id == null || id == 0 || student == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("Updating student {}", id);
        return new ResponseEntity<>(studentService.updateStudent(id, student), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Place> deleteStudent(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        studentService.deleteStudent(id);
        logger.info("Deleting student {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

