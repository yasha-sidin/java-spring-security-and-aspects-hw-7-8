package ru.overthantutor.javaspringaoptask1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.overthantutor.javaspringaoptask1.domain.AuthGrantedAuthority;
import ru.overthantutor.javaspringaoptask1.domain.AuthUserDetails;
import ru.overthantutor.javaspringaoptask1.repository.AuthGrantedAuthorityRepository;
import ru.overthantutor.javaspringaoptask1.repository.AuthUserDetailsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsManager {
    private final AuthUserDetailsRepository authUserDetailsRepository;
    private final AuthGrantedAuthorityRepository authGrantedAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserDetailsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with username = " + username));
    }

    @Override
    @Transactional
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            return;
        }
        authUserDetailsRepository.save((AuthUserDetails) user);
        for (GrantedAuthority authGrantedAuthority : user.getAuthorities()) {
            authGrantedAuthorityRepository.save((AuthGrantedAuthority) authGrantedAuthority);
        }
    }

    @Override
    @Transactional
    public void updateUser(UserDetails user) {
        if (!checkExistingById(((AuthUserDetails) user).getId())) {
            return;
        }
        authUserDetailsRepository.save((AuthUserDetails) user);
        for (GrantedAuthority authGrantedAuthority : user.getAuthorities()) {
            authGrantedAuthorityRepository.save((AuthGrantedAuthority) authGrantedAuthority);
        }
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        AuthUserDetails userDetails = authUserDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + username));
        authUserDetailsRepository.delete(userDetails);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        AuthUserDetails userDetails = authUserDetailsRepository.findByPassword(oldPassword)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid password "));
        userDetails.setPassword(newPassword);
        authUserDetailsRepository.save(userDetails);
    }

    @Override
    public boolean userExists(String username) {
        return authUserDetailsRepository.findByUsername(username).isPresent();
    }

    public boolean checkExistingById(UUID id) {
        return authUserDetailsRepository.findById(id).isPresent();
    }

    public List<AuthUserDetails> getAllUser() {
        return authUserDetailsRepository.findAll();
    }

    public Optional<AuthUserDetails> findById(UUID id) {
        return authUserDetailsRepository.findById(id);
    }
}
