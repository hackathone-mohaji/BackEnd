package com.mohaji.hackathon.domain.auth.repository;


import com.mohaji.hackathon.domain.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    @Modifying
    @Query("UPDATE Account m SET m.refreshToken = null WHERE m.id = :memberId")
    void invalidateRefreshToken(@Param("memberId") Long memberId);


    @Modifying
    @Query("UPDATE Account m SET m.refreshToken = :refreshToken WHERE m.id = :id")
    void updateRefreshToken(@Param("id") Long id, @Param("refreshToken") String refreshToken);
}
