package com.foodwizard.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@Getter
public class User {

    private String id;
    private String name;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }
}
