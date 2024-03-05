package ru.overthantutor.javaspringaoptask1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.overthantutor.javaspringaoptask1.domain.AuthGrantedAuthority;

import java.util.UUID;

public interface AuthGrantedAuthorityRepository extends JpaRepository<AuthGrantedAuthority, UUID> {
}
