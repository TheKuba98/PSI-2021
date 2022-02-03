package com.pwr.psi.mockservice.service.api;

import com.pwr.psi.mockservice.dto.StudentDto;

import java.util.Collection;

public interface StudentService {
    String saveStudent(StudentDto studentDto);
    Collection<StudentDto> getStudents();
}
