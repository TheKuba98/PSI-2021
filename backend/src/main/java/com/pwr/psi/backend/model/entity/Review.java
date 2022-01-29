package com.pwr.psi.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Review {
    @Id
    @Column(name = "review_id")
    @NotNull
    private int reviewId;

    @Column
    @NotNull
    private double grade;

    @Column
    @NotNull
    private String opinion;

    @ManyToOne
    @JoinColumn(name="acting_dean", nullable=false)
    private DeanRepresentative actingDean;

    @ManyToOne
    @JoinColumn(name="thesis_id", nullable=false)
    private Thesis thesis;

    @ManyToOne
    @JoinColumn(name="username", nullable=false)
    private UniversityEmployee author;
}
