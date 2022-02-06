package com.pwr.psi.backend.controller.impl;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.controller.api.ThesisController;
import com.pwr.psi.backend.model.dto.*;
import com.pwr.psi.backend.service.api.ThesisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ThesisControllerImpl implements ThesisController {

    private final ThesisService thesisService;

    @Override
    public ResponseEntity<List<ThesisDto>> findFilteredAvailableTheses(ThesisSearchDto thesisSearchDto) {
        return ResponseEntity.ok(thesisService.findFilteredAvailableTheses(thesisSearchDto));
    }

    @Override
    public ResponseEntity<List<ThesisDto>> findFilteredAvailableThesesWithReviewers(ThesisSearchDto thesisSearchDto) {
        return ResponseEntity.ok(thesisService.findFilteredThesesWithReviewers(thesisSearchDto));
    }

    @Override
    public ResponseEntity<ThesisDto> findAvailableThesisById(int id, Principal principal) throws UserNotFoundException, ThesisNotFoundException, ThesisNotAvailableException {
        return ResponseEntity.ok(thesisService.findAvailableThesisById(id, principal.getName()));
    }

    @Override
    public ResponseEntity<List<ThesisDto>> findMyFilteredTheses(ThesisSearchDto thesisSearchDto, Principal principal) throws UserNotFoundException {
        return ResponseEntity.ok(thesisService.findMyFilteredThesesBasedOnUserRole(thesisSearchDto, principal.getName()));
    }

    @Override
    public ResponseEntity<ThesisDto> assignThesis(String username, int id, Principal principal) throws ThesisNotFoundException, UserNotFoundException, UserAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException {
        return ResponseEntity.ok(thesisService.assignThesisToStudent(username, id, principal.getName()));
    }

    @Override
    public ResponseEntity<ThesisDto> markThesisAsDone(int id) throws ThesisNotFoundException, ThesisNotAvailableException {
        return ResponseEntity.ok(thesisService.markThesisAsDone(id));
    }

    @Override
    public ResponseEntity<ThesisDto> markThesisAsAssigned(int id) throws ThesisNotFoundException, ThesisNotAvailableException, ThesisWorkloadLimitReachedException {
        return ResponseEntity.ok(thesisService.markThesisAsAssigned(id));
    }

    @Override
    public ResponseEntity<ThesisDto> markThesisAsRegistered(int id) throws ThesisNotFoundException, ThesisNotAvailableException {
        return ResponseEntity.ok(thesisService.markThesisAsRegistered(id));
    }

    @Override
    public ResponseEntity<ThesisDto> createThesis(ThesisForm thesisForm, Principal principal) throws UserNotFoundException, FieldNotFoundException, ThesisNotAvailableException {
        return ResponseEntity.ok(thesisService.createThesis(thesisForm, principal.getName()));
    }

    @Override
    public ResponseEntity<ThesisDto> updateThesis(ThesisForm thesisForm, int thesisId) throws ThesisNotAvailableException, UserNotFoundException {
        return ResponseEntity.ok(thesisService.updateThesis(thesisForm, thesisId));
    }

    @Override
    public ResponseEntity<List<ThesisDto>> findAllAvailableTheses() {
        return ResponseEntity.ok(thesisService.findAllAvailableTheses());
    }

    @Override
    public ResponseEntity<FilterOptionsDto> getFilterOptions() {
        return ResponseEntity.ok(thesisService.getFilterOptions());
    }
}
