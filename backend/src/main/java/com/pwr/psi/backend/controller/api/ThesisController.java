package com.pwr.psi.backend.controller.api;

import com.pwr.psi.backend.model.dto.FilterOptionsDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@CrossOrigin
public interface ThesisController {

    @PostMapping("/theses")
    ResponseEntity<List<ThesisDto>> findFilteredAvailableTheses(@RequestBody ThesisSearchDto thesisSearchDto);

    @GetMapping("/all-theses")
    ResponseEntity<List<ThesisDto>> findAllAvailableTheses();

    @GetMapping("/filter-options")
    ResponseEntity<FilterOptionsDto> getFilterOptions();
}
