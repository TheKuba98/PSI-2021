package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.exception.ThesisNotAvailableException;
import com.pwr.psi.backend.exception.ThesisNotFoundException;
import com.pwr.psi.backend.exception.UserAlreadyAssignedException;
import com.pwr.psi.backend.exception.UserNotFoundException;
import com.pwr.psi.backend.model.dto.MessageExternalDto;

public interface ReviewService {

    MessageExternalDto addReviewer(String username, int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException, UserAlreadyAssignedException;
}
