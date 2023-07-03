package com.example.shoppApp.repository;

import com.example.shoppApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String usename);

    List<User> findByBirthdayContaining(String birthday);

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
