package com.proba.felo.repository;

import com.proba.felo.model.entity.IsEmailValid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IsEmailValidRepository extends JpaRepository<IsEmailValid, Long> {

    Optional<IsEmailValid> findBy_user_id(Long id);
}
