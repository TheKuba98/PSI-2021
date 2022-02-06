package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.model.entity.Field;
import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.repository.FieldRepository;
import com.pwr.psi.backend.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FieldServiceTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Test
    public void shouldReturnCorrectValuesFromStudentRepository(){
        //given

        //when
        Student student = studentRepository.findByUsername("233331").orElse(null);

        //then
        assertThat(student).isNotNull();
        assertThat(student.getField())
                .hasSize(2)
                .extracting(Field::getName)
                .contains("Informatyka", "Inżynieria Systemów");

    }


}
