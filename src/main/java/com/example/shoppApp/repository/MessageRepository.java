package com.example.shoppApp.repository;

import com.example.shoppApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatId(Long id);
}
