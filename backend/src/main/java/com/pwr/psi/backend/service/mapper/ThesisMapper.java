package com.pwr.psi.backend.service.mapper;

import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.entity.Review;
import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.model.entity.Thesis;
import com.pwr.psi.backend.model.entity.UniversityEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ThesisMapper {
    private final UserMapper userMapper;

    public ThesisDto thesisToThesisDTO(Thesis thesis) {
        String supervisorNames = thesis.getSupervisor().getFirstName() + " " + thesis.getSupervisor().getLastName();
        String reporter = Objects.isNull(thesis.getThesisDetails().getStudent())
                ? null
                : thesis.getThesisDetails().getStudent().getUsername();
        List<Student> thesisAuthors = Objects.isNull(thesis.getAuthors())
                ? List.of()
                : List.copyOf(thesis.getAuthors());
        List<UniversityEmployee> reviewers = Objects.isNull(thesis.getReviews())
                ? List.of()
                :thesis.getReviews().stream().map(Review::getAuthor).collect(Collectors.toList());

        return new ThesisDto(thesis.getThesisDetails().getThema(),
                thesis.getSupervisor().getUsername(),
                supervisorNames,
                thesis.getThesisDetails().getThesisType().toString(),
                thesis.getThesisDetails().getField().getEducationCycle(),
                thesis.getThesisDetails().getField().getName(),
                thesis.getThesisDetails().getLanguage(),
                thesis.getThesisStatus().toString(),
                userMapper.studentListToUserDtoList(thesisAuthors),
                thesis.getThesisId(),
                reporter,
                userMapper.employeeListToUserDtoList(reviewers));
    }

    public List<ThesisDto> thesisListToThesisDtoList(List<Thesis> thesisList) {
        return thesisList.stream().map(this::thesisToThesisDTO).collect(Collectors.toList());
    };
}
