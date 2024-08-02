package com.TransferApp.MoneyTransfer.service.sercurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
//this is the class that holds the email and password from database
@Data
@AllArgsConstructor
@Builder
public class CustomerDetailsImpl implements UserDetails {
      private Long id;
    private String email;
    private String password;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public long getId() {
        return this.id;
    }
}
