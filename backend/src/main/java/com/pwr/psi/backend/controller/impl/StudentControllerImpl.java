package com.pwr.psi.backend.controller.impl;


import com.pwr.psi.backend.controller.api.StudentController;
import com.pwr.psi.backend.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudentControllerImpl implements StudentController {

    private final StudentService studentService;

    @Override
    @GetMapping("/student/list/update")
    public void getStudentsFromExternalSystem() {
        studentService.getStudentsFromExternalSystem();
    }
}
