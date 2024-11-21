package com.project.authtemp.model.credentials;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

    @Query("SELECT c FROM Credentials c WHERE c.user.id = :userId")
    List<Credentials> findAllCredentialsByUserId(@Param("userId") Long userId);

}
