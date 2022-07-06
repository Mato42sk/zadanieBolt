package com.example.userLogin.registration.token;

import com.example.userLogin.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
        Optional<ConfirmationToken> findByToken(String token);
        Optional<ConfirmationToken> findByAppUser(AppUser appUser);
        @Transactional
        @Modifying
        @Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
        void updateConfirmedAt(String token, LocalDateTime confirmedAt);

        @org.springframework.transaction.annotation.Transactional
        @Modifying
        @Query("delete FROM ConfirmationToken t WHERE t.token = ?1")
        void delete(String token);
}
