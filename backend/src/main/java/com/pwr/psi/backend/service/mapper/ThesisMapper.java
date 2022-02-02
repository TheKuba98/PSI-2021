package com.pwr.psi.backend.service.mapper;

import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.Thesis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ThesisMapper {
    private final UserMapper userMapper;

    public ThesisDto thesisToThesisDTO(Thesis thesis){
        String supervisorNames = thesis.getSupervisor().getFirstName() + " " + thesis.getSupervisor().getLastName();
        String reporter = Objects.isNull(thesis.getThesisDetails().getStudent())
                ? null
                : thesis.getThesisDetails().getStudent().getUsername();

        return new ThesisDto(thesis.getThesisDetails().getThema(),
                             thesis.getSupervisor().getUsername(),
                             supervisorNames,
                             thesis.getThesisDetails().getThesisType().toString(),
                             thesis.getThesisDetails().getField().getEducationCycle(),
                             thesis.getThesisDetails().getField().getName(),
                             thesis.getThesisDetails().getLanguage(),
                             thesis.getThesisStatus().toString(),
                             userMapper.studentListToUserDtoList(List.copyOf(thesis.getAuthors())),
                             thesis.getThesisId(),
                             reporter);
    }

    public List<ThesisDto> thesisListToThesisDtoList(List<Thesis> thesisList){
        return thesisList.stream().map(this::thesisToThesisDTO).collect(Collectors.toList());
    };

}
