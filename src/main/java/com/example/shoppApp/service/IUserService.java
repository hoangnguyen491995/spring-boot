package com.example.shoppApp.service;

import com.example.shoppApp.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserService {

    User addUser(User user);

//     User updateUser(User user) ;

    String deleteUser(long User_Id);

    List<User> getAllUser();


    User findById(Long id);

    User updateUser(User user);

    List<User> getUserByBirthday();

    User login(User user);

//    void save(User user);

}
