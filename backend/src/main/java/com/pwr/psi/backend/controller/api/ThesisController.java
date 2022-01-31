package com.pwr.psi.backend.controller.api;

import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.model.dto.ThesisSearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@CrossOrigin
public interface ThesisController {

    @GetMapping("/thesis")
    ResponseEntity<List<ThesisDto>> findFilteredAvailableTheses(@RequestBody ThesisSearchDto thesisSearchDto);

    @GetMapping("/all-thesis")
    ResponseEntity<List<ThesisDto>> findAllAvailableTheses();
}
