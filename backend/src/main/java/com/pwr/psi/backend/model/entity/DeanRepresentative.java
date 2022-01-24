package com.pwr.psi.backend.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("ROLE_REPRESENTATIVE")
public class DeanRepresentative extends User {

}
