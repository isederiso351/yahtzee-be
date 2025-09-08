package org.example.yahtzee_be.repository.jpa;

import jakarta.persistence.LockModeType;
import org.example.yahtzee_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {


    boolean existsByKeycloackID(String id);

    Optional<User> getByKeycloackID(String id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.keycloackID = :keycloackId")
    Optional<User> getByKeycloackIDLock(@Param("keycloackId")String id);

    Optional<User> getUserById(long id);

    @Modifying
    @Query(value = """
    INSERT INTO "user" (keycloackId, email, name, credit, last_bonus_credit)
    VALUES (:sub, :email, :username, :credit, NOW())
    ON CONFLICT (keycloackId) DO NOTHING
""", nativeQuery = true)
    void insertUserIfNotExists(@Param("sub") String sub,
                               @Param("email") String email,
                               @Param("username") String username,
                               @Param("credit") long credit);
}
