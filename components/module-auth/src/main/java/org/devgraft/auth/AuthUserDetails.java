package org.devgraft.auth;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AuthUserDetails implements UserDetails {
    private final String userName;
    private final ArrayList authorities;

    public AuthUserDetails(String signKey, List<@Pattern(regexp = "^ROLE_\\w{1,20}$")String> roles) {
        this.userName = signKey;
        this.authorities = new ArrayList<>();
        roles.forEach(s -> this.authorities.add(new SimpleGrantedAuthority(s)));
    }

    public AuthUserDetails(String signKey, @Pattern(regexp = "^ROLE_\\w{1,20}$") String role) {
        this.userName = signKey;
        this.authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
