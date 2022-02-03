package com.pwr.psi.backend.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface StudentController {
    void getStudentsFromExternalSystem();
}
