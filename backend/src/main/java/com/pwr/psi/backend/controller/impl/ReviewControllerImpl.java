package com.pwr.psi.backend.controller.impl;


import com.pwr.psi.backend.controller.api.ReviewController;
import com.pwr.psi.backend.controller.api.StudentController;
import com.pwr.psi.backend.exception.ThesisNotAvailableException;
import com.pwr.psi.backend.exception.ThesisNotFoundException;
import com.pwr.psi.backend.exception.UserAlreadyAssignedException;
import com.pwr.psi.backend.exception.UserNotFoundException;
import com.pwr.psi.backend.model.dto.MessageExternalDto;
import com.pwr.psi.backend.model.dto.ThesisDto;
import com.pwr.psi.backend.service.api.MessageService;
import com.pwr.psi.backend.service.api.ReviewService;
import com.pwr.psi.backend.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;

    @Override
    public ResponseEntity<MessageExternalDto> addReviewer(String username, int id, Principal principal) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException, UserAlreadyAssignedException {
        return ResponseEntity.ok(reviewService.addReviewer(username, id, principal.getName()));
    }
}
