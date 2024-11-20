package com.project.authtemp.model.savedpass;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface savedPassRepository extends JpaRepository<savedPass, Integer> {

    Optional<savedPass> findByWebsite(String website);

    Optional<savedPass> findByUsername(String username);

    @Query(value = """
            select s from savedPass s inner join User u
            on s.user.id = u.id
            where u.id = :id and s.revoked = false
            """)
    Optional<savedPass> findByUser(Integer id);

}
