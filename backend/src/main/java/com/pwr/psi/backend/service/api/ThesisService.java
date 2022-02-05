package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisForm;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;

import java.util.List;

public interface ThesisService {

    List<ThesisDto> findAllAvailableTheses();

    List<ThesisDto> findFilteredAvailableTheses(ThesisSearchDto thesisSearchDto);

    List<ThesisDto> findMyFilteredThesesBasedOnUserRole(ThesisSearchDto thesisSearchDto, String username) throws UserNotFoundException;

    FilterOptionsDto getFilterOptions();

    ThesisDto assignThesisToStudent(String username, int id, String reporter) throws UserNotFoundException, ThesisNotFoundException, StudentAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException;

    ThesisDto markThesisAsDone(int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    ThesisDto markThesisAsAssigned(int id) throws ThesisNotFoundException, ThesisNotAvailableException, ThesisWorkloadLimitReachedException;

    ThesisDto markThesisAsRegistered(int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    ThesisDto createThesis(ThesisForm thesisForm, String username) throws UserNotFoundException, FieldNotFoundException, ThesisNotAvailableException;

    ThesisDto findAvailableThesisById(int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException;
}
