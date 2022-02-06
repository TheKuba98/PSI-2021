package com.pwr.psi.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwr.psi.backend.model.dto.FieldExternalDto;
import com.pwr.psi.backend.model.dto.StudentExternalSystemDto;
import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.model.repository.StudentRepository;
import com.pwr.psi.backend.service.api.StudentService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@Transactional
public class StudentServiceTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    EntityManager entityManager;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mapper.findAndRegisterModules();
    }

    @Test
    public void shouldAddStudentFromExternalSystem() throws URISyntaxException, JsonProcessingException{
        //given
        int acutalNumberOfStudents = studentRepository.findAll().size();
        StudentExternalSystemDto student = new StudentExternalSystemDto();
        student.setLastName("Test");
        student.setFirstName("Test");
        FieldExternalDto field = new FieldExternalDto();
        field.setDegree("Magister Inżynier");
        field.setName("Informatyka");
        field.setEducationCycle("2021");
        field.setFaculty("W8");
        student.setFields(Lists.newArrayList(field));
        student.setIndexNumber("1");
        student.setBirthDate(LocalDate.now());

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8081/api/student/list")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Lists.newArrayList(student)))
                );
        //when
        List<Student> studentsFromExternalSystem = studentService.getStudentsFromExternalSystem();

        //then
        mockServer.verify();
        assertThat(studentsFromExternalSystem).hasSize(1);
        assertThat(studentRepository.findAll().size()).isGreaterThan(acutalNumberOfStudents);
    }


}
