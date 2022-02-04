package com.pwr.psi.mockservice.service.impl;

import com.pwr.psi.mockservice.dto.StudentDto;
import com.pwr.psi.mockservice.service.api.StudentService;
import com.pwr.psi.mockservice.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private InMemoryStorage inMemoryStorage;


    @Override
    public String saveStudent(StudentDto studentDto) {
        LOG.info("Saving student {} to temporal memory storage", studentDto.toString());
        return inMemoryStorage.save(studentDto);
    }

    @Override
    public Collection<StudentDto> getStudents() {
        LOG.info("Responding with student list and clearing storage");
        Collection<StudentDto> list = new ArrayList<>(inMemoryStorage.findAll());
        clearStudents();
        return list;
    }

    public void clearStudents() {
        inMemoryStorage.clear();
    }
}
