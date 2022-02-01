package com.pwr.psi.backend.service;

import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.*;
import com.pwr.psi.backend.repository.*;
import com.pwr.psi.backend.service.mapper.ThesisMapper;
import com.pwr.psi.backend.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//TODO: Musi być do serwisów interfejs???
@RequiredArgsConstructor
@Service
public class ThesisService {

    private final ThesisRepository thesisRepository;
    private final ThesisDetailsRepository thesisDetailsRepository;
    private final UniversityEmployeeRepository universityEmployeeRepository;
    private final UserRepository userRepository;
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

    public List<ThesisDto> findMyFilteredThesesBasedOnUserRole(ThesisSearchDto thesisSearchDto, String name) {
        User user = userRepository.findByUsername(name).orElse(null);

        if (user.getRoles().contains("ROLE_STUDENT")) {
            return findMyThesesAsAuthor(thesisSearchDto, user);
        }

        if (user.getRoles().contains("ROLE_EMPLOYEE")) {
            return findMyThesesAsSupervisor(thesisSearchDto, user);
        }

        return null;
    }

    private List<ThesisDto> findMyThesesAsAuthor(ThesisSearchDto thesisSearchDto, User user) {
        List<ThesisDto> list = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllByAuthorsContains((Student) user));
        return filterTheses(list, thesisSearchDto);
    }

    private List<ThesisDto> findMyThesesAsSupervisor(ThesisSearchDto thesisSearchDto, User user) {
        List<ThesisDto> list = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllBySupervisorEquals((UniversityEmployee) user));
        list.forEach(System.out::println);
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
        List<ThesisDetails> thesisDetails = thesisDetailsRepository.findAll();
        List<Field> fields = fieldRepository.findAll();

        List<UserDto> supervisors = universityEmployees.stream()
                .map(userMapper::userToUserDTO)
                .sorted(Comparator.comparing(UserDto::getLastName))
                .collect(Collectors.toList());

        List<String> thesisTypes = thesisDetails.stream()
                .map(elem -> elem.getThesisType().toString())
                .distinct()
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

        List<String> languages = thesisDetails.stream()
                .map(ThesisDetails::getLanguage)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return new FilterOptionsDto(supervisors, thesisTypes, years, fieldNames, languages);
    }
}
