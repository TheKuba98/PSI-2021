package com.pwr.psi.backend.service.mapper;

import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.entity.Thesis;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThesisMapper {

    public ThesisDto thesisToThesisDTO(Thesis thesis){
        String supervisorNames = thesis.getSupervisor().getFirstName() + " " + thesis.getSupervisor().getLastName();
        return new ThesisDto(thesis.getThesisDetails().getThema(),
                             thesis.getSupervisor().getUsername(),
                             supervisorNames,
                             thesis.getThesisDetails().getThesisType().toString(),
                             thesis.getThesisDetails().getField().getEducationCycle(),
                             thesis.getThesisDetails().getField().getName(),
                             thesis.getThesisDetails().getLanguage(),
                             thesis.getThesisStatus().toString());
    }

    public List<ThesisDto> thesisListToThesisDtoList(List<Thesis> thesisList){
        return thesisList.stream().map(this::thesisToThesisDTO).collect(Collectors.toList());
    };

}
