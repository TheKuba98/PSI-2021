package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.*;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import com.pwr.psi.backend.model.enums.ThesisType;
import com.pwr.psi.backend.repository.*;
import com.pwr.psi.backend.service.api.ThesisService;
import com.pwr.psi.backend.service.mapper.ThesisMapper;
import com.pwr.psi.backend.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ThesisServiceImpl implements ThesisService {

    public static final String STUDENT_ALREADY_ASSIGNED_MESSAGE = "This student is already assigned to the following thesis";
    public static final String THESIS_NOT_AVAILABLE_MESSAGE = "Thesis is not available";
    public static final String AUTHORS_LIMIT_REACHED_MESSAGE = "Maximum limit of authors in thesis is already reached";
    public static final String THESIS_NOT_FOUND_MESSAGE = "Thesis with this id does not exist";
    public static final String USER_NOT_FOUND_MESSAGE = "User with this login does not exist";
    private static final int MAX_STUDENTS_NUMBER_ASSIGNED_TO_THESIS = 4;
    public static final int NO_AUTHORS = 0;

    private final ThesisRepository thesisRepository;
    private final ThesisDetailsRepository thesisDetailsRepository;
    private final UniversityEmployeeRepository universityEmployeeRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ThesisMapper thesisMapper;
    private final UserMapper userMapper;
    private final FieldRepository fieldRepository;


    public List<ThesisDto> findAllAvailableTheses() {
        return thesisMapper.thesisListToThesisDtoList(thesisRepository.findAll());
    }

    public List<ThesisDto> findFilteredAvailableTheses(ThesisSearchDto thesisSearchDto) {
        List<ThesisDto> thesisDtoList = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllBySupervisorNotNull());
        return filterTheses(thesisDtoList, thesisSearchDto);
    }

    public List<ThesisDto> findMyFilteredThesesBasedOnUserRole(ThesisSearchDto thesisSearchDto, String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (user.getRoles().contains("ROLE_STUDENT")) {
            return findMyThesesAsAuthor(thesisSearchDto, user);
        }

        if (user.getRoles().contains("ROLE_EMPLOYEE")) {
            return findMyThesesAsSupervisor(thesisSearchDto, user);
        }

        return List.of();
    }

    private List<ThesisDto> findMyThesesAsAuthor(ThesisSearchDto thesisSearchDto, User user) {
        List<ThesisDto> list = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllByAuthorsContains((Student) user));
        return filterTheses(list, thesisSearchDto);
    }

    private List<ThesisDto> findMyThesesAsSupervisor(ThesisSearchDto thesisSearchDto, User user) {
        List<ThesisDto> list = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllBySupervisorEquals((UniversityEmployee) user));
        return filterTheses(list, thesisSearchDto);
    }

    private List<ThesisDto> filterTheses(List<ThesisDto> thesisListDto, ThesisSearchDto thesisSearchDto) {
        return thesisListDto.stream()
                .filter(elem -> elem.getTheme().toLowerCase()
                        .contains(thesisSearchDto.getTheme().toLowerCase()))
                .filter(elem -> elem.getSupervisorUsername().toLowerCase()
                        .contains(thesisSearchDto.getSupervisor().toLowerCase()))
                .filter(elem -> elem.getType().toLowerCase()
                        .contains(thesisSearchDto.getType().toLowerCase()))
                .filter(elem -> elem.getYear().toLowerCase()
                        .contains(thesisSearchDto.getYear().toLowerCase()))
                .filter(elem -> elem.getFieldName().toLowerCase()
                        .contains(thesisSearchDto.getFieldName().toLowerCase()))
                .filter(elem -> elem.getLanguage().toLowerCase()
                        .contains(thesisSearchDto.getLanguage().toLowerCase()))
                .collect(Collectors.toList());
    }

    public FilterOptionsDto getFilterOptions() {
        List<UniversityEmployee> universityEmployees = universityEmployeeRepository.findAll();
        List<Field> fields = fieldRepository.findAll();

        List<UserDto> supervisors = universityEmployees.stream()
                .map(userMapper::userToUserDTO)
                .sorted(Comparator.comparing(UserDto::getLastName))
                .collect(Collectors.toList());

        List<String> thesisTypes = ThesisType.getAllThesisTypes().stream()
                .map(Enum::toString)
                .sorted()
                .collect(Collectors.toList());

        List<String> years = fields.stream()
                .map(Field::getEducationCycle)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> fieldNames = fields.stream()
                .map(Field::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> languages = List.of("polish", "english");

        return new FilterOptionsDto(supervisors, thesisTypes, years, fieldNames, languages);
    }

    public ThesisDto assignThesisToStudent(String username, int id, String reporter) throws UserNotFoundException, ThesisNotFoundException, StudentAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException {
        String usernameToAssign;
        if (Objects.nonNull(username)) {
            usernameToAssign = username;
        } else {
            usernameToAssign = reporter;
        }

        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));

        if (thesis.getThesisStatus() == ThesisStatus.DONE) {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }

        if (isAuthorsNumberLimitReached(thesis)) {
            throw new AuthorsLimitReachedException(AUTHORS_LIMIT_REACHED_MESSAGE);
        }

        Student student = studentRepository.findByUsername(usernameToAssign).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (isStudentAlreadyAssignedToThesis(student, thesis)) {
            throw new StudentAlreadyAssignedException(STUDENT_ALREADY_ASSIGNED_MESSAGE);
        }

        if (hasThesisNoAuthors(thesis)) {
            ThesisDetails thesisDetails = thesis.getThesisDetails();
            thesisDetails.setStudent(student);
            thesisDetailsRepository.save(thesisDetails);
        } else {
            thesis.setSharedWork(true);
        }

        thesis.addAuthor(student);
        thesis.setReserved(true);
        thesis.setThesisStatus(ThesisStatus.TO_ACCEPT);
        return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
//        throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
    }

    private boolean isStudentAlreadyAssignedToThesis(Student student, Thesis thesis) {
        return thesis.getAuthors().contains(student);
    }

    private boolean isAuthorsNumberLimitReached(Thesis thesis) {
        return thesis.getAuthors().size() >= MAX_STUDENTS_NUMBER_ASSIGNED_TO_THESIS;
    }

    private boolean hasThesisNoAuthors(Thesis thesis) {
        return thesis.getAuthors().size() == NO_AUTHORS;
    }

    public ThesisDto markThesisAsDone(int id) throws ThesisNotFoundException, ThesisNotAvailableException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));
        if (thesis.getThesisStatus() == ThesisStatus.ASSIGNED) {
            thesis.setThesisStatus(ThesisStatus.DONE);
            return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }
    }

    public ThesisDto markThesisAsAssigned(int id) throws ThesisNotFoundException, ThesisNotAvailableException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));
        if (thesis.getThesisStatus() == ThesisStatus.TO_ACCEPT) {
            thesis.setThesisStatus(ThesisStatus.ASSIGNED);
            return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }
    }

    @Override
    public ThesisDto markThesisAsRegistered(int id) throws ThesisNotFoundException, ThesisNotAvailableException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));
        if (thesis.getThesisStatus() == ThesisStatus.TO_ACCEPT) {
            thesis.setThesisStatus(ThesisStatus.REGISTERED);

            if (!thesis.isRegisteredByStudent()) {
                thesis.getThesisDetails().setStudent(null);
                thesis.getAuthors().forEach(thesis::removeAuthor);
            } else {
                thesis.getAuthors().stream()
                        .filter(a -> !a.getUsername().equals(thesis.getThesisDetails().getStudent().getUsername()))
                        .forEach(thesis::removeAuthor);
            }
            return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }
    }
}
