package ru.overthantutor.javaspringaoptask1.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.overthantutor.javaspringaoptask1.domain.AuthUserDetails;
import ru.overthantutor.javaspringaoptask1.service.JpaUserDetailsManager;

@RequiredArgsConstructor
@Setter
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final AuthUserDetails user = (AuthUserDetails) jpaUserDetailsManager.loadUserByUsername(name);
        if (user == null)
            return null;
        if (passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        else
            return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
