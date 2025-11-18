package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByOpenid(String openid);
}