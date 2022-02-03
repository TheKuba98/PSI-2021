package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.model.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudentsFromExternalSystem();
}
