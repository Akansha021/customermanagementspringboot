package com.example.customermanagement.repository;

import com.example.customermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //@Query("select u from User u where u.username = ?1")
    User findByUsername(String name);
}
