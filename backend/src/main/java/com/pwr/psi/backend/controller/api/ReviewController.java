package com.pwr.psi.backend.controller.api;

import com.pwr.psi.backend.exception.ThesisNotAvailableException;
import com.pwr.psi.backend.exception.ThesisNotFoundException;
import com.pwr.psi.backend.exception.UserAlreadyAssignedException;
import com.pwr.psi.backend.exception.UserNotFoundException;
import com.pwr.psi.backend.model.dto.MessageExternalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api")
@CrossOrigin
public interface ReviewController {

    @PreAuthorize("hasRole('REPRESENTATIVE')")
    @PostMapping("/theses/{id}/add-review" )
    ResponseEntity<MessageExternalDto> addReviewer(@RequestParam String username, @PathVariable int id, Principal principal) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException, UserAlreadyAssignedException;

}
