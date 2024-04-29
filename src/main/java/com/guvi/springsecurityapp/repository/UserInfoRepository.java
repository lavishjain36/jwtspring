package com.guvi.springsecurityapp.repository;

import com.guvi.springsecurityapp.entity.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<Userinfo,Integer> {
    //provide custom method to find User by username
    Optional<Userinfo> findByName(String username);
}
