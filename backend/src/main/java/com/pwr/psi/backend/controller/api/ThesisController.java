package com.pwr.psi.backend.controller.api;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin
public interface ThesisController {

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @PostMapping("/theses")
    ResponseEntity<List<ThesisDto>> findFilteredAvailableTheses(@RequestBody ThesisSearchDto thesisSearchDto);

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @PostMapping("/my-theses")
    ResponseEntity<List<ThesisDto>> findMyFilteredTheses(@RequestBody ThesisSearchDto thesisSearchDto, Principal principal) throws UserNotFoundException;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/theses/{id}/assign" )
    ResponseEntity<ThesisDto> assignThesis(@RequestParam(required = false) String username,
                                           @PathVariable int id,
                                           Principal principal) throws ThesisNotFoundException, UserNotFoundException, StudentAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/theses/{id}/complete")
    ResponseEntity<ThesisDto> markThesisAsDone(@PathVariable int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/theses/{id}/accept")
    ResponseEntity<ThesisDto> markThesisAsAssigned(@PathVariable int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/theses/{id}/reject")
    ResponseEntity<ThesisDto> markThesisAsRegistered(@PathVariable int id) throws ThesisNotFoundException, ThesisNotAvailableException;

//    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE')")
//    @PostMapping("/theses")
//    ResponseEntity<ThesisDto> createThesis();

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @GetMapping("/filter-options")
    ResponseEntity<FilterOptionsDto> getFilterOptions();

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @GetMapping("/all-theses")
    ResponseEntity<List<ThesisDto>> findAllAvailableTheses();
}
