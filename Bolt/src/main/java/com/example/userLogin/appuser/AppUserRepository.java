package com.example.userLogin.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying  //enhance the @Query annotation, so after that can be executed not only the SELECT queries
    @Query("UPDATE AppUser a SET a.enabled = TRUE WHERE a.email = ?1")
    void enableAppUser(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppUser a WHERE a.email = ?1")
    void deleteAppUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.car = ?1, a.IBAN = ?2, a.description =?3, a.appUserRole=?4 where a.email = ?5")
    void updateCustomerToProvider(String car, String IBAN,String description, AppUserRole appUserRole, String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.cardNumber = ?1, a.cardValidity =?2, a.cv = ?3, a.phoneNumber =?4, a.appUserRole=?5 where a.email = ?6")
    void updateProviderToCustomer(Long cardNumber, String cardValidity, String cv, String phonenumber, AppUserRole appUserRole, String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.availability = ?1 WHERE a.email = ?2")
    void availability(Boolean availability, String email);

}
