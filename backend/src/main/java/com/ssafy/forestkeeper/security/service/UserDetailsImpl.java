package com.ssafy.forestkeeper.security.service;

import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.enums.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    @Autowired
    private User user;

    List<GrantedAuthority> roles = new ArrayList<>();

    public UserDetailsImpl(User user) {
        super();
        this.user = user;

        if (user.getUserCode().equals(UserCode.USER)) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (user.getUserCode().equals(UserCode.ADMIN)) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.user.isDelete();
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
        return !this.user.isDelete();
    }

}
