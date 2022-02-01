package com.pwr.psi.backend.model.entity;

import com.pwr.psi.backend.model.enums.ThesisType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "thesis_details")
public class ThesisDetails {
    @Id
    @Column(name = "thesis_details_id")
    @NotNull
    private int thesisDetailsId;

    @Column
    private String language;

    @Column(name = "thesis_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ThesisType thesisType;

    @ManyToOne
    @JoinColumn(name="field_id", nullable=false)
    private Field field;

    @Column(name = "thema")
    @NotNull
    private String thema;

    //    TODO: To nie jest student? (ManyToOne?, może być null jak nie ma autora jeszcze?)
//    @Column(name = "username")
//    @NotNull ??
//    private String username;

    @ManyToOne
    @JoinColumn(name = "username")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thesis_id", referencedColumnName = "thesis_id")
    private Thesis thesis;

}
