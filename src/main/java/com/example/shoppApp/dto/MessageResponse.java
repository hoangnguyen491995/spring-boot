package com.example.shoppApp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    Long chatId;
    Long userId;
    String content;
    LocalDateTime createdAt;
}
