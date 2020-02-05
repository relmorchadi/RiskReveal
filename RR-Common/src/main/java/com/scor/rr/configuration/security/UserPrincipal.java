package com.scor.rr.configuration.security;


import com.scor.rr.domain.UserRrEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;


    private UserRrEntity user;
    private List<? extends GrantedAuthority> authorities;

    public UserPrincipal(UserRrEntity user, List<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserPrincipal() {
    }

    public static UserPrincipal create(UserRrEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new UserPrincipal(user, authorities);
    }


    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUserCode();
    }

    public UserRrEntity getUser() {
        return this.user;
    }

    public void setUser(UserRrEntity user) {
        this.user = user;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return this.user.getUserCode();
    }
}

