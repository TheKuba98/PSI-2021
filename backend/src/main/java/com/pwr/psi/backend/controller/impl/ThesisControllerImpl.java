package com.pwr.psi.backend.controller.impl;

import com.pwr.psi.backend.controller.api.ThesisController;
import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import com.pwr.psi.backend.service.ThesisService;
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
    public ResponseEntity<List<ThesisDto>> findMyFilteredTheses(ThesisSearchDto thesisSearchDto, Principal principal) {
        return ResponseEntity.ok(thesisService.findMyFilteredThesesBasedOnUserRole(thesisSearchDto, principal.getName()));
    }

    @Override
    public ResponseEntity<ThesisDto> assignThesis(int id) {
        return null;
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
