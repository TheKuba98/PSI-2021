package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.*;

import java.util.List;

public interface ThesisService {

    List<ThesisDto> findAllAvailableTheses();

    List<ThesisDto> findFilteredAvailableTheses(ThesisSearchDto thesisSearchDto);

    List<ThesisDto> findMyFilteredThesesBasedOnUserRole(ThesisSearchDto thesisSearchDto, String username) throws UserNotFoundException;

    FilterOptionsDto getFilterOptions();

    ThesisDto assignThesisToStudent(String username, int id, String reporter) throws UserNotFoundException, ThesisNotFoundException, UserAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException;

    ThesisDto markThesisAsDone(int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    ThesisDto markThesisAsAssigned(int id) throws ThesisNotFoundException, ThesisNotAvailableException, ThesisWorkloadLimitReachedException;

    ThesisDto markThesisAsRegistered(int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    ThesisDto createThesis(ThesisForm thesisForm, String username) throws UserNotFoundException, FieldNotFoundException, ThesisNotAvailableException;

    ThesisDto findAvailableThesisById(int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException;

    ThesisDto updateThesis(ThesisForm thesisForm, int thesisId) throws ThesisNotAvailableException, UserNotFoundException;

    List<ThesisDto> findFilteredThesesWithReviewers(ThesisSearchDto thesisSearchDto);
}
