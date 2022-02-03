package com.pwr.psi.mockservice.service.impl;

import com.pwr.psi.mockservice.dto.StudentDto;
import com.pwr.psi.mockservice.service.api.StudentService;
import com.pwr.psi.mockservice.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private InMemoryStorage inMemoryStorage;


    @Override
    public String saveStudent(StudentDto studentDto) {
        return inMemoryStorage.save(studentDto);
    }

    @Override
    public Collection<StudentDto> getStudents() {
        Collection<StudentDto> list = new ArrayList<>(inMemoryStorage.findAll());
        clearStudents();
        return list;
    }

    public void clearStudents() {
        inMemoryStorage.clear();
    }
}
