package com.guvi.springsecurityapp.config;

import com.guvi.springsecurityapp.entity.Userinfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//authentication and authorization
public class UserinfoUserDetails implements UserDetails {
    private  String name;
    private  String password;

    private List<GrantedAuthority> authorities;

    //constructor userinformation into UserDetails
    public  UserinfoUserDetails(Userinfo userinfo){
        //extract->username,password and role
        name=userinfo.getName();
        password=userinfo.getPassword();

        authorities= Arrays.stream(userinfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return list of roles assigned to user
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
}
