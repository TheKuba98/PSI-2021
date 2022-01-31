package com.pwr.psi.backend.service;

import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import com.pwr.psi.backend.repository.ThesisRepository;
import com.pwr.psi.backend.service.mapper.ThesisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO: Musi być do serwisów interfejs???
@RequiredArgsConstructor
@Service
public class ThesisService {

    private final ThesisRepository thesisRepository;
    private final ThesisMapper thesisMapper;

    public List<ThesisDto> findAllAvailableTheses() {
        return thesisMapper.thesisListToThesisDtoList(thesisRepository.findAll());
    }

    public List<ThesisDto> findFilteredAvailableTheses(ThesisSearchDto thesisSearchDto) {
        List<ThesisDto> thesisDtoList = thesisMapper.thesisListToThesisDtoList(thesisRepository.findAllBySupervisorNotNull());
        return filterTheses(thesisDtoList, thesisSearchDto);
    }

    private List<ThesisDto> filterTheses(List<ThesisDto> thesisListDto, ThesisSearchDto thesisSearchDto) {
        return thesisListDto.stream()
                .filter(elem -> elem.getThema().toLowerCase().indexOf(thesisSearchDto.getThema().toLowerCase()) != -1)
                .filter(elem -> elem.getSupervisorUsername().toLowerCase().indexOf(thesisSearchDto.getSupervisor().toLowerCase()) != -1)
                .filter(elem -> elem.getType().toLowerCase().indexOf(thesisSearchDto.getThema().toLowerCase()) != -1)
                .filter(elem -> elem.getYear().toLowerCase().indexOf(thesisSearchDto.getYear().toLowerCase()) != -1)
                .filter(elem -> elem.getFieldName().toLowerCase().indexOf(thesisSearchDto.getFieldName().toLowerCase()) != -1)
                .filter(elem -> elem.getLanguage().toLowerCase().indexOf(thesisSearchDto.getLanguage().toLowerCase()) != -1)
                .collect(Collectors.toList());
    }
}
