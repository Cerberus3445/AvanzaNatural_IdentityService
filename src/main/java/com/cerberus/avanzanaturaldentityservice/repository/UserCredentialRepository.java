package com.cerberus.avanzanaturaldentityservice.repository;

import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

    Optional<UserCredential> findByEmail(String email);
}
