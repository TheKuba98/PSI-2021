package com.pwr.psi.backend.model.entity;

import com.pwr.psi.backend.model.enums.DocumentFormat;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Thesis {

    @Id
    @Column(name = "thesis_id")
    @NotNull
    private int thesisId;

    @Column(name = "asap_date")
    private LocalDate asapDate;

    @Column(name = "document_format")
    @NotNull
    @Enumerated(EnumType.STRING)
    private DocumentFormat documentFormat;

    @Column(name = "entering_date")
    private LocalDate enteringDate;

    @Column(name = "shared_work")
    @NotNull
    private boolean sharedWork;

    @Column()
    private boolean reserved;

    @Column(name = "thesis_status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ThesisStatus thesisStatus;

    @ManyToOne
    @JoinColumn(name="username", nullable=false)
    private UniversityEmployee supervisor;

    @ManyToMany
    @JoinTable(
            name = "share",
            joinColumns = @JoinColumn(name = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private Set<Student> authors;

    @OneToMany(mappedBy="thesis")
    private Set<Review> reviews;

    @OneToOne(mappedBy = "thesis")
    private ThesisDetails thesisDetails;
}

