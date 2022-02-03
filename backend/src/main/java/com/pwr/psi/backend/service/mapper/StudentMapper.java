package com.pwr.psi.backend.service.mapper;

import com.pwr.psi.backend.model.dto.StudentExternalSystemDto;
import com.pwr.psi.backend.model.entity.Field;
import com.pwr.psi.backend.model.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class StudentMapper {

    @Autowired
    private PasswordEncoder encoder;

    public Student studentExternalSystemDtoToStudent(StudentExternalSystemDto studentExternalSystemDto, Set<Field> fields) {
        Student student = new Student();
        student.setUsername(studentExternalSystemDto.getIndexNumber());
        student.setIndexNumber(studentExternalSystemDto.getIndexNumber());
        student.setField(fields);
        student.setActive(true);
        student.setBirthDate(studentExternalSystemDto.getBirthDate());
        student.setFirstName(studentExternalSystemDto.getFirstName());
        student.setLastName(studentExternalSystemDto.getLastName());
        student.setRoles("ROLE_STUDENT");
        student.setPassword(encoder.encode("student"));

        return student;
    }

}
