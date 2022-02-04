package com.pwr.psi.backend.controller.impl;


import com.pwr.psi.backend.controller.api.StudentController;
import com.pwr.psi.backend.service.api.MessageService;
import com.pwr.psi.backend.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudentControllerImpl implements StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentControllerImpl.class);

    private final StudentService studentService;
    private final MessageService messageService;

    @Override
    @GetMapping("/student/list/update")
    public void getStudentsFromExternalSystem() {
        studentService.getStudentsFromExternalSystem();
        LOG.info(messageService.sendMessage("TEST", "TEST", "WIADOMOSC").toString());
    }
}
