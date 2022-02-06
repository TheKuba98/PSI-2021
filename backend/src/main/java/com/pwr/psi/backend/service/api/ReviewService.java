package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.MessageExternalDto;

public interface ReviewService {

    MessageExternalDto addReviewer(String username, int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException, UserAlreadyAssignedException, CanNotBeReviewerException;
}
