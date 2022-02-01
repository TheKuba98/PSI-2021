package com.pwr.psi.backend.controller.api;

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
    ResponseEntity<List<ThesisDto>> findMyFilteredTheses(@RequestBody ThesisSearchDto thesisSearchDto, Principal principal);

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/theses/{id}/assign")
    ResponseEntity<ThesisDto> assignThesis(@PathVariable int id);

//    @PreAuthorize("hasRole('EMPLOYEE')")
//    @PostMapping("/theses/{id}/accept")
//    ResponseEntity<ThesisDto> acceptStudent(@PathVariable int id);

//    @PreAuthorize("hasRole('EMPLOYEE')")
//    @PostMapping("/theses/{id}/decline")
//    ResponseEntity<ThesisDto> declineStudent(@PathVariable int id);

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
