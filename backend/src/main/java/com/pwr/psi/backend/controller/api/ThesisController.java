package com.pwr.psi.backend.controller.api;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.*;
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
    @PostMapping("/theses-review")
    ResponseEntity<List<ThesisDto>> findFilteredAvailableThesesWithReviewers(@RequestBody ThesisSearchDto thesisSearchDto);

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @GetMapping("/theses/{id}")
    ResponseEntity<ThesisDto> findAvailableThesisById(@PathVariable int id, Principal principal) throws UserNotFoundException, ThesisNotFoundException, ThesisNotAvailableException;

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @PostMapping("/my-theses")
    ResponseEntity<List<ThesisDto>> findMyFilteredTheses(@RequestBody ThesisSearchDto thesisSearchDto, Principal principal) throws UserNotFoundException;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/theses/{id}/assign" )
    ResponseEntity<ThesisDto> assignThesis(@RequestParam(required = false) String username,
                                           @PathVariable int id,
                                           Principal principal) throws ThesisNotFoundException, UserNotFoundException, UserAlreadyAssignedException, ThesisNotAvailableException, AuthorsLimitReachedException;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/theses/{id}/complete")
    ResponseEntity<ThesisDto> markThesisAsDone(@PathVariable int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/theses/{id}/accept")
    ResponseEntity<ThesisDto> markThesisAsAssigned(@PathVariable int id) throws ThesisNotFoundException, ThesisNotAvailableException, ThesisWorkloadLimitReachedException;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/theses/{id}/reject")
    ResponseEntity<ThesisDto> markThesisAsRegistered(@PathVariable int id) throws ThesisNotFoundException, ThesisNotAvailableException;

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE')")
    @PostMapping("/add-thesis")
    ResponseEntity<ThesisDto> createThesis(@RequestBody ThesisForm thesisForm, Principal principal) throws UserNotFoundException, FieldNotFoundException, ThesisNotAvailableException;

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE')")
    @PutMapping("/update-thesis")
    ResponseEntity<ThesisDto> updateThesis(@RequestBody ThesisForm thesisForm, @RequestParam int thesisId) throws ThesisNotAvailableException, UserNotFoundException;

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @GetMapping("/filter-options")
    ResponseEntity<FilterOptionsDto> getFilterOptions();

    @PreAuthorize("hasRole('STUDENT') or hasRole('EMPLOYEE') or hasRole('REPRESENTATIVE')")
    @GetMapping("/all-theses")
    ResponseEntity<List<ThesisDto>> findAllAvailableTheses();
}
