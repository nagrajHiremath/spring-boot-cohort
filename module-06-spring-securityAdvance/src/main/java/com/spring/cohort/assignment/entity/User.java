package com.spring.cohort.assignment.entity;

import com.spring.cohort.assignment.entity.enums.Roles;
import com.spring.cohort.assignment.entity.enums.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;

    String password;

    String userName;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Roles> role;

    @Enumerated(EnumType.STRING)
    SubscriptionPlan subscriptionPlan = SubscriptionPlan.FREE;

    Integer sessionLimit = 1;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream()
                .map(roles -> new SimpleGrantedAuthority("ROLE_"+roles.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword(){
        return this.password;
    }
}
