package com.example.shoppApp.repository;

import com.example.shoppApp.entity.Chat;
import com.example.shoppApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByNameIn(List<String> names);

    List<Chat> findByUsersId(Long id);
}