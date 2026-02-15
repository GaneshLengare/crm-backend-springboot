package com.ganesh.crm.security;

import com.ganesh.crm.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        SimpleGrantedAuthority roleAuthority =
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getUserType().getTypeName()
                );


        List<SimpleGrantedAuthority> permissionAuthorities =
                user.getUserType()
                        .getPermissions()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(permission -> {


                            String action =
                                    permission.getAction()
                                            .replace("CAN_", "");


                            String authority =
                                    permission.getModuleName() +
                                            "_" + action;

                            return new SimpleGrantedAuthority(authority);
                        })
                        .collect(Collectors.toList());


        return Stream.concat(
                Stream.of(roleAuthority),
                permissionAuthorities.stream()
        ).toList();
    }



    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber(); // login by phone
    }

    // 🛡 Account status

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
        return user.getStatus() == User.Status.ACTIVE;
    }
}
