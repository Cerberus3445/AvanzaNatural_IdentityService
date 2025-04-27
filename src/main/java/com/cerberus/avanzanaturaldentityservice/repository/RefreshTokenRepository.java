package com.cerberus.avanzanaturaldentityservice.repository;

import com.cerberus.avanzanaturaldentityservice.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findFirstByToken(String token);

    void deleteByToken(String token);

    Integer countByUserCredential_Id(Long userId);

    void deleteAllByUserCredential_Id(Long userId);
}