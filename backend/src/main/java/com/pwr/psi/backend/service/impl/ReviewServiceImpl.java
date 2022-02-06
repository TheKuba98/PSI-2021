package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.exception.*;
import com.pwr.psi.backend.model.dto.*;
import com.pwr.psi.backend.model.entity.*;
import com.pwr.psi.backend.model.enums.DocumentFormat;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import com.pwr.psi.backend.model.enums.ThesisType;
import com.pwr.psi.backend.repository.*;
import com.pwr.psi.backend.service.api.MessageService;
import com.pwr.psi.backend.service.api.ReviewService;
import com.pwr.psi.backend.service.api.ThesisService;
import com.pwr.psi.backend.service.mapper.ThesisMapper;
import com.pwr.psi.backend.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    public static final String THESIS_NOT_FOUND_MESSAGE = "Thesis with this id does not exist";
    public static final String USER_NOT_FOUND_MESSAGE = "User with this login does not exist";
    public static final String THESIS_NOT_AVAILABLE_MESSAGE = "Thesis is not available";
    public static final double DEFAULT_GRADE = 5.0;
    public static final String DEFAULT_OPINION = "Super";
    public static final String MESSAGE = "Reviewer assigned";
    public static final String REVIEWER_ALREADY_ASSIGNED_MESSAGE = "This reviewer is already assigned to the following thesis";

    private final MessageService messageService;
    private final ThesisRepository thesisRepository;
    private final DeanRepresentativeRepository deanRepresentativeRepository;
    private final UniversityEmployeeRepository universityEmployeeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public MessageExternalDto addReviewer(String username, int id, String name) throws ThesisNotFoundException, UserNotFoundException, ThesisNotAvailableException, UserAlreadyAssignedException {
        Thesis thesis = thesisRepository.findById(id).orElseThrow(() -> new ThesisNotFoundException(THESIS_NOT_FOUND_MESSAGE));

        if (thesis.getThesisStatus() != ThesisStatus.ASSIGNED) {
            throw new ThesisNotAvailableException(THESIS_NOT_AVAILABLE_MESSAGE);
        }

        UniversityEmployee universityEmployee = universityEmployeeRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (thesis.getReviews().stream().map(Review::getAuthor).collect(Collectors.toList()).contains(universityEmployee)) {
            throw new UserAlreadyAssignedException(REVIEWER_ALREADY_ASSIGNED_MESSAGE);
        }

//        if(thesis.getReviews().stream().map(Review::getAuthor).collect(Collectors.toList()).contains(thesis.getSupervisor())) {
//            throw new ThesisNotAvailableException("Supervisor can not be reviewer");
//        }

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
