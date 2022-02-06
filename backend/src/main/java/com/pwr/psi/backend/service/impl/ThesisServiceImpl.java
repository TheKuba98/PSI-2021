package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.*;
import com.pwr.psi.backend.model.entity.*;
import com.pwr.psi.backend.model.enums.DocumentFormat;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import com.pwr.psi.backend.model.enums.ThesisType;
import com.pwr.psi.backend.model.repository.*;
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
    public static final String FIELD_NOT_FOUND_MESSAGE = "Field with following name, type, and year does not exist";
    public static final String ROLE_STUDENT = "ROLE_STUDENT";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    private static final int MAX_STUDENTS_NUMBER_ASSIGNED_TO_THESIS = 4;
    private static final int FIRST_DEGREE_THESIS_WORKLOAD = 5;
    private static final int SECOND_DEGREE_THESIS_WORKLOAD = 10;
    public static final int NO_AUTHORS = 0;
    public static final boolean NOT_RESERVED = false;
    public static final boolean NOT_REGISTERED_BY_STUDENT = false;
    public static final boolean NOT_SHARED_WORK = false;
    public static final boolean REGISTERED_BY_STUDENT = true;
    public static final String THESIS_WORKLOAD_LIMIT_REACHED_MESSAGE = "Thesis workload limit has been reached";
    public static final String STUDENT_AND_THESIS_FIELDS_NOT_EQUAL = "Student fields and thesis field are not equal";

    private final ThesisRepository thesisRepository;
    private final ThesisDetailsRepository thesisDetailsRepository;
    private final UniversityEmployeeRepository universityEmployeeRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ThesisMapper thesisMapper;
    private final UserMapper userMapper;
    private final FieldRepository fieldRepository;


    public List<ThesisDto> findAllAvailableTheses() {
        return thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllByRegisteredByStudent(NOT_REGISTERED_BY_STUDENT));
    }

    public List<ThesisDto> findFilteredAvailableTheses(ThesisSearchDto thesisSearchDto) {
        List<ThesisDto> thesisDtoList = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllBySupervisorNotNullAndRegisteredByStudent(NOT_REGISTERED_BY_STUDENT));
        return filterTheses(thesisDtoList, thesisSearchDto);
    }

    public List<ThesisDto> findMyFilteredThesesBasedOnUserRole(ThesisSearchDto thesisSearchDto, String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (user.getRoles().contains(ROLE_STUDENT)) {
            return findMyThesesAsAuthor(thesisSearchDto, user);
        }

        if (user.getRoles().contains(ROLE_EMPLOYEE)) {
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

    public ThesisDto assignThesisToStudent(String username, int id, String reporter) throws UserNotFoundException, ThesisNotFoundException, UserAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException, BadFieldException {
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
            throw new UserAlreadyAssignedException(STUDENT_ALREADY_ASSIGNED_MESSAGE);
        }

        if (!student.getField().stream().map(Field::getName).collect(Collectors.toList()).contains(thesis.getThesisDetails().getField().getName())) {
            throw new BadFieldException(STUDENT_AND_THESIS_FIELDS_NOT_EQUAL);
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

    public ThesisDto markThesisAsAssigned(int id) throws ThesisNotFoundException, ThesisNotAvailableException, ThesisWorkloadLimitReachedException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));

        if (Objects.isNull(thesis.getSupervisor())) {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }

        int currentWorkload = getCurrentWorkload(thesis.getSupervisor());
        int thesisWorkload = getThesisWorkload(thesis);

        if (thesis.getSupervisor().getThesisWorkloadLimit() < (currentWorkload + thesisWorkload)) {
            throw new ThesisWorkloadLimitReachedException(THESIS_WORKLOAD_LIMIT_REACHED_MESSAGE);
        }

        if (thesis.getThesisStatus() == ThesisStatus.TO_ACCEPT) {
            thesis.setThesisStatus(ThesisStatus.ASSIGNED);
            return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }
    }

    private int getThesisWorkload(Thesis thesis) {
        if (thesis.getThesisDetails().getField().getDegree().equals(ThesisType.BA.toString())
                || thesis.getThesisDetails().getField().getDegree().equals(ThesisType.BSC.toString())) {
            return FIRST_DEGREE_THESIS_WORKLOAD;
        } else {
            return SECOND_DEGREE_THESIS_WORKLOAD;
        }
    }

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
                thesis.setSupervisor(null);
            }
            return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }
    }

    public ThesisDto createThesis(ThesisForm thesisForm, String username) throws UserNotFoundException, FieldNotFoundException, ThesisNotAvailableException, BadFieldException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        Field field = fieldRepository.findByDegreeAndEducationCycleAndName(thesisForm.getType(), thesisForm.getYear(), thesisForm.getField())
                .orElseThrow(() -> new FieldNotFoundException(FIELD_NOT_FOUND_MESSAGE));

        Thesis thesis = new Thesis();
        ThesisDetails thesisDetails = new ThesisDetails();
        thesisDetails.setLanguage(thesisForm.getLanguage());
        thesisDetails.setThesisType(ThesisType.valueOf(thesisForm.getType()));
        thesisDetails.setThema(thesisForm.getTheme());
        thesisDetails.setField(field);

        if (user.getRoles().contains(ROLE_EMPLOYEE)) {
            UniversityEmployee universityEmployee = (UniversityEmployee) user;
            thesis.setSupervisor(universityEmployee);
            thesis.setRegisteredByStudent(NOT_REGISTERED_BY_STUDENT);
            thesis.setThesisStatus(ThesisStatus.REGISTERED);
        } else if (user.getRoles().contains(ROLE_STUDENT)) {
            UniversityEmployee universityEmployee = universityEmployeeRepository.findByUsername(thesisForm.getSupervisor())
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
            Student student = (Student) user;

            if (!student.getField().stream().map(Field::getName).collect(Collectors.toList()).contains(thesisForm.getField())) {
                throw new BadFieldException(STUDENT_AND_THESIS_FIELDS_NOT_EQUAL);
            }

            thesisDetails.setStudent(student);
            thesis.addAuthor(student);
            thesis.setRegisteredByStudent(REGISTERED_BY_STUDENT);
            thesis.setThesisStatus(ThesisStatus.TO_ACCEPT);
            thesis.setSupervisor(universityEmployee);
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }

        thesis.setDocumentFormat(DocumentFormat.PDF);
        thesis.setSharedWork(NOT_SHARED_WORK);
        thesis.setReserved(NOT_RESERVED);
        thesis.setThesisDetails(thesisDetails);
        thesisDetails.setThesis(thesis);

        return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
    }

    public ThesisDto findAvailableThesisById(int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        if (thesis.getAuthors().contains(user)) {
            return thesisMapper.thesisToThesisDTO(thesis);
        } else {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }
    }

    @Override
    public ThesisDto updateThesis(ThesisForm thesisForm, int thesisId) throws ThesisNotAvailableException, UserNotFoundException {
        Thesis thesis = thesisRepository.findById(thesisId)
                .orElseThrow(() -> new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE));
        UniversityEmployee supervisor = universityEmployeeRepository.findByUsername(thesisForm.getSupervisor())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        ThesisDetails thesisDetails = thesis.getThesisDetails();
        thesisDetails.setThema(thesisForm.getTheme());
        thesis.setSupervisor(supervisor);
        thesis.setThesisDetails(thesisDetails);
        thesis.setThesisStatus(ThesisStatus.TO_ACCEPT);

        return thesisMapper.thesisToThesisDTO(thesisRepository.save(thesis));
    }

    @Override
    public List<ThesisDto> findFilteredThesesWithReviewers(ThesisSearchDto thesisSearchDto) {
        List<ThesisDto> theses = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllByThesisStatus(ThesisStatus.ASSIGNED));
        return filterTheses(theses, thesisSearchDto);
    }

    private int getCurrentWorkload(UniversityEmployee universityEmployee) {
        List<String> thesesDegrees = universityEmployee.getThesis().stream()
                .map(Thesis::getThesisDetails)
                .filter(a -> Objects.nonNull(a.getStudent()))
                .filter(a -> a.getThesis().getThesisStatus() == ThesisStatus.ASSIGNED)
                .map(ThesisDetails::getField)
                .map(Field::getDegree)
                .collect(Collectors.toList());

        List<String> thesisDetailsFirstDegree = thesesDegrees.stream().
                filter(elem -> elem.equals(ThesisType.BA.toString()) || elem.equals(ThesisType.BSC.toString()))
                .collect(Collectors.toList());
        List<String> thesisDetailsSecondDegree = thesesDegrees.stream()
                .filter(elem -> elem.equals(ThesisType.MASTERS.toString()))
                .collect(Collectors.toList());

        return (FIRST_DEGREE_THESIS_WORKLOAD * thesisDetailsFirstDegree.size())
                + SECOND_DEGREE_THESIS_WORKLOAD * thesisDetailsSecondDegree.size();
    }
}
