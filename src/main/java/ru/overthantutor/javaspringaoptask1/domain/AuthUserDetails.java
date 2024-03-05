package ru.overthantutor.javaspringaoptask1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity(name = "auth_user_details_t")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AuthUserDetails implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    private String firstname;
    private String lastname;
    private Integer age;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @OneToMany(mappedBy = "authUserDetails", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<AuthGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean contains(String role) {
        return getAuthorities().stream().anyMatch(auth  -> auth.getAuthority().equals(role));
    }
}
