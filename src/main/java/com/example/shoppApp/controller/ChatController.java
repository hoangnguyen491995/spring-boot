package com.example.shoppApp.controller;

import com.example.shoppApp.dto.ChatRequest;
import com.example.shoppApp.dto.MessageRequest;
import com.example.shoppApp.dto.MessageResponse;
import com.example.shoppApp.entity.Chat;
import com.example.shoppApp.entity.Message;
import com.example.shoppApp.entity.User;
import com.example.shoppApp.exception.ResourceNotFoundException;
import com.example.shoppApp.repository.ChatRepository;
import com.example.shoppApp.repository.MessageRepository;
import com.example.shoppApp.repository.UserRepository;
import com.example.shoppApp.service.impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatController {

    final ChatRepository chatRepository;

    final UserRepository userRepository;

    final UserService userService;

    final MessageRepository messageRepository;


    @GetMapping("/{id}")
    public List<User> getChatHistory(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        var chatHistories = chatRepository.findByUsersId(id);
        var temp = new ArrayList<User>();
        for (var chat : chatHistories) {
            var userItem = chat.getUsers().stream().filter(i -> i.getId() != id).findFirst().orElseThrow(() -> new ResourceNotFoundException("USer", "id", id));
            chat.setUsers(null);
            var a = new HashSet<Chat>();
            a.add(chat);
            userItem.setChats(a);
            temp.add(userItem);
        }
        return temp;

    }

    @PostMapping
    public Long createChatWithFriend(@RequestBody ChatRequest chatRequest) {
        User user = userRepository.findById(chatRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", chatRequest.getUserId()));
        User friend = userRepository.findById(chatRequest.getFriendId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", chatRequest.getFriendId()));

        Long id1 = user.getId();
        Long id2 = friend.getId();
        Chat existsChat = chatRepository.findByNameIn(List.of(id1 + "_" + id2, id2 + "_" + id1));
        if (existsChat != null)
            return existsChat.getId();

        Chat chat = new Chat();
        chat.setName(user.getId() + "_" + friend.getId());
        chat.getUsers().add(user);
        chat.getUsers().add(friend);
        chatRepository.save(chat);
        Chat savedChat = chatRepository.save(chat);
        userRepository.saveAll(chat.getUsers());
        return savedChat.getId();
    }

    @PostMapping("/message")
    public String saveMessage(@RequestBody MessageRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
        Chat chat = chatRepository.findById(request.getChatId()).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", request.getChatId()));
        var message = Message.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user).chat(chat).build();
        messageRepository.save(message);

        return String.format("Message saved successfully with id %d, chat id %d, and user id %d",
                message.getId(), chat.getId(), user.getId());
    }

    @GetMapping("/message/{chatId}")
    public List<MessageResponse> getListMessageByChatId(@PathVariable("chatId") Long id) {
        return messageRepository.findByChatId(id).stream().map(i -> {
            return MessageResponse.builder()
                    .chatId(i.getChat().getId())
                    .content(i.getContent())
                    .userId(i.getUser().getId())
                    .createdAt(i.getCreatedAt())
                    .build();

        }).collect(Collectors.toList());

    }
}
