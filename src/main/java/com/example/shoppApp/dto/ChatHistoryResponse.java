package com.example.shoppApp.dto;


import com.example.shoppApp.entity.Chat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatHistoryResponse {
    long id;

    String username;

    String displayName;

    Long chatId;

}
