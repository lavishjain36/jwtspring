package com.guvi.springsecurityapp.config;


import com.guvi.springsecurityapp.entity.Userinfo;
import com.guvi.springsecurityapp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    //inject the repo layer here
    @Autowired
    private UserInfoRepository repository;
    //use this method to load the user details
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //extract the user information from repo by findByName
       Optional<Userinfo> userinfo=repository.findByName(username);
        return userinfo.map(UserinfoUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("user not available "+username));
    }
}
