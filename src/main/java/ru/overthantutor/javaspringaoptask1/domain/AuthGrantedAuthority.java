package ru.overthantutor.javaspringaoptask1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity(name = "auth_granted_authority_t")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthGrantedAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "auth_user_detail_id")
    private AuthUserDetails authUserDetails;

    public AuthGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().isAssignableFrom(AuthGrantedAuthority.class)) {
            return false;
        } else {
            AuthGrantedAuthority authGrantedAuthority = (AuthGrantedAuthority) obj;
            return authGrantedAuthority.getAuthority().equals(role.toString());
        }
    }
}
