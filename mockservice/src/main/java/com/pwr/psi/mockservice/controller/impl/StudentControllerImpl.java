package com.pwr.psi.mockservice.controller.impl;

import com.pwr.psi.mockservice.controller.api.StudentController;
import com.pwr.psi.mockservice.dto.StudentDto;
import com.pwr.psi.mockservice.service.api.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class StudentControllerImpl implements StudentController {

    @Autowired
    private StudentService studentService;

    @Override
    public ResponseEntity<String> addStudent(StudentDto studentDto) {
        return ResponseEntity.ok(studentService.saveStudent(studentDto));
    }

    @Override
    public ResponseEntity<Collection<StudentDto>> getPendingStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }
}
