package ru.overthantutor.javaspringaoptask1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.overthantutor.javaspringaoptask1.domain.AuthGrantedAuthority;
import ru.overthantutor.javaspringaoptask1.domain.AuthUserDetails;
import ru.overthantutor.javaspringaoptask1.domain.Role;


import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserCreationService {
    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserDetails createUser(String username, String password, String firstName, String lastName, int age, Role... roles) {
        AuthUserDetails user = new AuthUserDetails();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setAge(age);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);

        Set<AuthGrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for (Role role : roles) {
            AuthGrantedAuthority grantedAuthority = new AuthGrantedAuthority();
            grantedAuthority.setRole(role);
            grantedAuthority.setAuthUserDetails(user);
            grantedAuthoritySet.add(grantedAuthority);
        }
        user.setAuthorities(grantedAuthoritySet);
        jpaUserDetailsManager.createUser(user);
        return user;
    }
}
