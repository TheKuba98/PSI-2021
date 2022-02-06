package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.model.dto.FieldExternalDto;
import com.pwr.psi.backend.model.dto.StudentExternalSystemDto;
import com.pwr.psi.backend.model.entity.Field;
import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.model.repository.FieldRepository;
import com.pwr.psi.backend.model.repository.StudentRepository;
import com.pwr.psi.backend.service.api.StudentService;
import com.pwr.psi.backend.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final RestTemplate restTemplate;
    private String url = "http://localhost:8081/api/student/list";

    private final StudentRepository studentRepository;
    private final FieldRepository fieldRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<Student> getStudentsFromExternalSystem() {
        LOG.info("Getting students from external system");
        ResponseEntity<StudentExternalSystemDto[]> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(createHeaders("technicaluser", "password")),
                StudentExternalSystemDto[].class);
        StudentExternalSystemDto[] list = response.getBody();
        LOG.info("Received {} students", list.length);
        List<Student> savedStudents = saveReceivedStudents(list);
        updateFields(savedStudents);
        return savedStudents;
    }

    private void updateFields(List<Student> savedStudents) {
        savedStudents.forEach(s -> {
            Set<Field> f = s.getField().stream().filter(Objects::nonNull).collect(Collectors.toSet());
            f.forEach(fi -> fi.getStudents().add(s));
            fieldRepository.saveAll(f);

        });
    }

    private List<Student> saveReceivedStudents(StudentExternalSystemDto[] list) {
        List<Student> collect = Arrays.stream(list)
                .map(student -> studentMapper.studentExternalSystemDtoToStudent(student, collectFields(student.getFields())))
                .collect(Collectors.toList());
        return studentRepository.saveAll(collect);
    }

    private Set<Field> collectFields(List<FieldExternalDto> fields) {
        return fields.stream().map(field -> fieldRepository.findByNameAndDegreeAndEducationCycleAndFaculty(
                field.getName(),
                field.getDegree(),
                field.getEducationCycle(),
                field.getFaculty()
        ).orElse(null)).collect(Collectors.toSet());
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
