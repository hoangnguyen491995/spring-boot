package com.example.shoppApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendshipRequest {
    private Long user1Id;
    private Long user2Id;
}
