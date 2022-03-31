package com.proba.felo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private float geoLatitude;
    private float geoLongitude;
    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private User _user;
}
