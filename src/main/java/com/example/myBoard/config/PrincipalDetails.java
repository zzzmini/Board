package com.example.myBoard.config;

import com.example.myBoard.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {
    private UserAccount user;
=======
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private UserAccount user;
    private Map<String, Object> attributes;

    public PrincipalDetails(UserAccount user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
>>>>>>> f4545c3 (#.OAuth2)

    public PrincipalDetails(UserAccount user) {
        this.user = user;
    }

    public UserAccount getUser() {
        return user;
    }

    @Override
<<<<<<< HEAD
=======
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
>>>>>>> f4545c3 (#.OAuth2)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add( () -> {
            return user.getUserRole().getValue();});
        return collect;
    }

    @Override
    public String getPassword() {
<<<<<<< HEAD
        return user.getUserPassword();
=======
        return user.getPassword();
>>>>>>> f4545c3 (#.OAuth2)
    }

    @Override
    public String getUsername() {
<<<<<<< HEAD
        return user.getUserId();
=======
        return user.getUsername();
>>>>>>> f4545c3 (#.OAuth2)
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
<<<<<<< HEAD
=======

    @Override
    public String getName() {
        String sub = attributes.get("sub").toString();
        return sub;
    }
>>>>>>> f4545c3 (#.OAuth2)
}
