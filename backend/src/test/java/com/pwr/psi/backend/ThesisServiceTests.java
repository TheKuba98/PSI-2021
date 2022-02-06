package com.pwr.psi.backend;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.model.entity.Thesis;
import com.pwr.psi.backend.model.entity.ThesisDetails;
import com.pwr.psi.backend.model.enums.DocumentFormat;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import com.pwr.psi.backend.model.enums.ThesisType;
import com.pwr.psi.backend.repository.FieldRepository;
import com.pwr.psi.backend.repository.StudentRepository;
import com.pwr.psi.backend.repository.ThesisRepository;
import com.pwr.psi.backend.repository.UniversityEmployeeRepository;
import com.pwr.psi.backend.service.api.ThesisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
public class ThesisServiceTests {

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private ThesisRepository thesisRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityEmployeeRepository universityEmployeeRepository;

    @Test
    public void shouldReturnAllThesis() {
        //given
        int expectedThesis = 4;

        //when
        List<ThesisDto> allAvailableTheses = thesisService.findAllAvailableTheses();

        //then
        assertThat(allAvailableTheses).hasSize(expectedThesis);
    }

    @Test
    public void shouldAddNewThesis() {
        //given
        int acutalThesisNumber = thesisService.findAllAvailableTheses().size();
        Thesis thesis = new Thesis();
        thesis.setDocumentFormat(DocumentFormat.DOC);
        thesis.setSharedWork(false);
        thesis.setThesisStatus(ThesisStatus.REGISTERED);
        thesis.setSupervisor(universityEmployeeRepository.getById("piotr70"));
        ThesisDetails td = new ThesisDetails();
        td.setThesisType(ThesisType.BSC);
        td.setThema("Temat_test");
        td.setThesis(thesis);
        td.setStudent(studentRepository.findByUsername("233333").orElse(null));
        td.setField(fieldRepository.findAll().get(0));
        thesis.setThesisDetails(td);
        thesisRepository.save(thesis);

        //when
        List<ThesisDto> allAvailableTheses = thesisService.findAllAvailableTheses();

        //then
        assertThat(allAvailableTheses).hasSize(acutalThesisNumber + 1);
        assertThat(allAvailableTheses)
                .extracting(ThesisDto::getThesisId, ThesisDto::getTheme)
                .contains(tuple(thesis.getThesisId(),"Temat_test"));
    }

    @Test
    public void shouldFilterThesis() {
        //given
        String theme = "Aplikacja webowa dla sportowców";
        ThesisSearchDto thesisSearchDto = new ThesisSearchDto(theme,"","","","",""
        );

        //when
        List<ThesisDto> allAvailableTheses = thesisService.findFilteredAvailableTheses(thesisSearchDto);

        //then
        assertThat(allAvailableTheses).hasSize(1)
                .extracting(ThesisDto::getTheme)
                .contains(theme);
    }

    @Test
    public void shouldFindThesesAssignedToUser() throws UserNotFoundException {
        //given
        String username = "233331";
        String theme = "Aplikacja webowa dla sportowców";
        ThesisSearchDto thesisSearchDto = new ThesisSearchDto(theme,"","","","",""
        );

        //when
        List<ThesisDto> thesesAssignedToUser = thesisService.findMyFilteredThesesBasedOnUserRole(thesisSearchDto,username);

        //then

        assertThat(thesesAssignedToUser).hasSize(1).extracting(ThesisDto::getTheme).contains(theme);
    }

    @Test
    public void shouldNotFindThesesAssignedToUser() throws UserNotFoundException {
        //given
        String username = "233331";
        String theme = "Aplikacja testowa";
        ThesisSearchDto thesisSearchDto = new ThesisSearchDto(theme,"","","","",""
        );

        //when
        List<ThesisDto> thesesAssignedToUser = thesisService.findMyFilteredThesesBasedOnUserRole(thesisSearchDto,username);

        //then

        assertThat(thesesAssignedToUser).hasSize(0);
    }

    @Test
    public void shouldReturnFilterOptions(){
        //given

        //when
        FilterOptionsDto filterOptions = thesisService.getFilterOptions();

        //then
        assertThat(filterOptions).isNotNull().extracting(fo -> fo.getSupervisors().size(),
                fo-> fo.getThesisTypes().size(),
                fo -> fo.getYears().size(),
                fo -> fo.getFieldNames().size(),
                fo -> fo.getLanguages().size()).containsExactly(2,3,3,3,2);
    }

    @Test
    public void shouldAssignThesisToStudent() throws UserNotFoundException, ThesisNotAvailableException, AuthorsLimitReachedException, ThesisNotFoundException, UserAlreadyAssignedException {
        //given
        Student student = studentRepository.findByUsername("233331").get();
        Thesis thesis = thesisRepository.findById(4).get();
        //when
        ThesisDto thesisDto = thesisService.assignThesisToStudent(student.getUsername(), thesis.getThesisId(), null);
        //then
        assertThat(thesisDto.getAuthors()).extracting(UserDto::getUsername,UserDto::getFirstName,UserDto::getLastName)
                .containsExactly(tuple(student.getUsername(), student.getFirstName(),student.getLastName()));
    }

    @Test
    public void shouldMarkThesisAsDone() throws ThesisNotFoundException, ThesisNotAvailableException {
        //given
        Thesis thesis = thesisRepository.findById(1).get();

        //when
        ThesisDto thesisDto = thesisService.markThesisAsDone(thesis.getThesisId());

        //then
        assertThat(thesisDto.getStatus()).isEqualTo(ThesisStatus.DONE.toString());

    }

    @Test
    public void shouldMarkThesisAsAssigned() throws ThesisNotFoundException, ThesisNotAvailableException, ThesisWorkloadLimitReachedException {
        //given
        Thesis thesis = thesisRepository.findById(3).get();
        thesis.setThesisStatus(ThesisStatus.TO_ACCEPT);
        thesisRepository.save(thesis);

        //when
        ThesisDto thesisDto = thesisService.markThesisAsAssigned(thesis.getThesisId());

        //then
        assertThat(thesisDto.getStatus()).isEqualTo(ThesisStatus.ASSIGNED.toString());
    }

    @Test
    public void shouldMarkThesisAsRegistered() throws ThesisNotFoundException, ThesisNotAvailableException {
        //given
        Thesis thesis = thesisRepository.findById(2).get();
        thesis.setThesisStatus(ThesisStatus.TO_ACCEPT);
        thesisRepository.save(thesis);

        //when
        ThesisDto thesisDto = thesisService.markThesisAsRegistered(thesis.getThesisId());

        //then
        assertThat(thesisDto.getStatus()).isEqualTo(ThesisStatus.REGISTERED.toString());
    }
}
