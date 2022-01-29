package com.pwr.psi.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "field")
@Setter
@Getter
@RequiredArgsConstructor
public class Field {

    @Id
    @Column(name = "field_id")
    @NotNull
    private int fieldId;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String degree;

    @Column(name = "thesis_workload")
    @NotNull
    private int thesisWorkload;

    @Column(name = "education_cycle")
    @NotNull
    private String educationCycle;

    @ManyToMany
    @JoinTable(
            name = "field_user",
            joinColumns = @JoinColumn(name = "field_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private Set<Student> students;

    @OneToMany(mappedBy="field")
    private Set<ThesisDetails> thesisDetails;
}
