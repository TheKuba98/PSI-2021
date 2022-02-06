package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.MessageExternalDto;
import com.pwr.psi.backend.model.entity.DeanRepresentative;
import com.pwr.psi.backend.model.entity.Review;
import com.pwr.psi.backend.model.entity.Thesis;
import com.pwr.psi.backend.model.entity.UniversityEmployee;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import com.pwr.psi.backend.repository.DeanRepresentativeRepository;
import com.pwr.psi.backend.repository.ReviewRepository;
import com.pwr.psi.backend.repository.ThesisRepository;
import com.pwr.psi.backend.repository.UniversityEmployeeRepository;
import com.pwr.psi.backend.service.api.MessageService;
import com.pwr.psi.backend.service.api.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.pwr.psi.backend.model.enums.EPosition.RESEARCH_TEACHING_STAFF;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    public static final String THESIS_NOT_FOUND_MESSAGE = "Thesis with this id does not exist";
    public static final String USER_NOT_FOUND_MESSAGE = "User with this login does not exist";
    public static final String THESIS_NOT_AVAILABLE_MESSAGE = "Thesis is not available";
    public static final double DEFAULT_GRADE = 5.0;
    public static final int ALLOWED_REVIEWS_NUMBER = 2;
    public static final String DEFAULT_OPINION = "Super";
    public static final String MESSAGE = "Reviewer assigned";
    public static final String REVIEWER_ALREADY_ASSIGNED_MESSAGE = "This reviewer is already assigned to the following thesis";
    public static final String CANNOT_BE_REVIEWER_MESSAGE = "This employee can not be a reviewer";
    public static final String REVIEWS_LIMIT_REACHED = "This thesis has maximal number of reviews";
    public static final String SUPERVISOR_CAN_NOT_BE_REVIEWER_MESSAGE = "Supervisor can not be reviewer";

    private final MessageService messageService;
    private final ThesisRepository thesisRepository;
    private final DeanRepresentativeRepository deanRepresentativeRepository;
    private final UniversityEmployeeRepository universityEmployeeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public MessageExternalDto addReviewer(String username, int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException, UserAlreadyAssignedException, CanNotBeReviewerException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));

        if (thesis.getThesisStatus() != ThesisStatus.ASSIGNED) {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }

        UniversityEmployee universityEmployee = universityEmployeeRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (thesis.getReviews().stream().map(Review::getAuthor).collect(Collectors.toList()).contains(universityEmployee)) {
            throw new UserAlreadyAssignedException(REVIEWER_ALREADY_ASSIGNED_MESSAGE);
        }

        if (thesis.getReviews().size() >= ALLOWED_REVIEWS_NUMBER) {
            throw new UserAlreadyAssignedException(REVIEWS_LIMIT_REACHED);
        }

        if (universityEmployee.getPosition().equals(RESEARCH_TEACHING_STAFF)) {
            throw new CanNotBeReviewerException(CANNOT_BE_REVIEWER_MESSAGE);
        }

        if(universityEmployee.equals(thesis.getSupervisor())) {
            throw new CanNotBeReviewerException(SUPERVISOR_CAN_NOT_BE_REVIEWER_MESSAGE);
        }

        DeanRepresentative representative = deanRepresentativeRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        Review review = new Review();
        review.setActingDean(representative);
        review.setAuthor(universityEmployee);
        review.setThesis(thesis);
        review.setGrade(DEFAULT_GRADE);
        review.setOpinion(DEFAULT_OPINION);
        reviewRepository.save(review);


        return messageService.sendMessage(universityEmployee.getFirstName() + " " + universityEmployee.getLastName(),
                (representative.getFirstName() + " " + representative.getLastName()),
                MESSAGE);
    }
}
