package com.pwr.psi.backend.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("ROLE_STUDENT")
public class Student extends User {

    @Column(name = "index_number")
    private String indexNumber;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "students")
    Set<Field> field;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "authors")
    Set<Thesis> theses;
}
