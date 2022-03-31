package com.proba.felo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class IsEmailValid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private boolean valid;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "_user_id", referencedColumnName = "id")
    private User _user;
}
