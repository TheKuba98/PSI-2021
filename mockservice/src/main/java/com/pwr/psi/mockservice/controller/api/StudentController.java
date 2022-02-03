package com.pwr.psi.mockservice.controller.api;

import com.pwr.psi.mockservice.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RequestMapping("/api")
public interface StudentController {


    @PostMapping("/student/add")
    ResponseEntity<String> addStudent(@RequestBody StudentDto studentDto);


    @GetMapping("/student/list")
    ResponseEntity<Collection<StudentDto>> getPendingStudents();
}
