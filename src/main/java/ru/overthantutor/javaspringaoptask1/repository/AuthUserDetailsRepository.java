package ru.overthantutor.javaspringaoptask1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.overthantutor.javaspringaoptask1.domain.AuthUserDetails;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserDetailsRepository extends JpaRepository<AuthUserDetails, UUID> {
    Optional<AuthUserDetails> findByUsername(String username);
    Optional<AuthUserDetails> findByPassword(String password);
}
