package com.example.shoppApp.service.impl;

import com.example.shoppApp.entity.User;
import com.example.shoppApp.exception.ResourceNotFoundException;
import com.example.shoppApp.repository.UserRepository;
import com.example.shoppApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User addUser(User user) {
        if (user.getEmail() == null)
            return null;
        User existUser = userRepository.findByEmail(user.getEmail());
        if (existUser != null)
            return null;
        user.setRole("USER");
        return userRepository.save(user);

    }

    //    @Override
//    public User updateUser( User user){
//         if(user != null){
//             User user1 = userRepository.getReferenceById(user.getUser_Id()) ;
//             if(user1 != null){
//                 user1.setEmail(user.getEmail());
//             }
//         }
//        return null ;
//    }
    public String deleteUser(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.getChats().clear(); // remove all chats of the user
            userRepository.save(user);
            userRepository.delete(user);
            return "Delete user successfully with id = " + id;
        }
        return "User with id = " + id + " does not exist in the database";
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll().stream().peek(i -> i.setChats(null)).collect(Collectors.toList());
    }

    // ==========login========
    @Override
    public User findById(Long id) {
        var result = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        result.setChats(null);
        return result;
    }

    @Override
    public User updateUser(User user) {
        User userInDb = userRepository.findById(user.getId()).orElse(null);
        if (userInDb == null)
            return null;
        user.setRole(userInDb.getRole());
        var result = userRepository.save(user);
        result.setChats(null);
        return result;
    }

    @Override
    public List<User> getUserByBirthday() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String formattedDate = currentDate.format(formatter);
        var result = userRepository.findByBirthdayContaining(formattedDate);
        return result.stream().peek(i -> i.setChats(null)).collect(Collectors.toList());
    }

    @Override
    public User login(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        var result = userRepository.findByEmailAndPassword(email, password);
        result.setChats(null);
        return result;
    }

//    @Override
//    public void save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        Role role = roleRepository.findByName("USER");
//        roles.add(role);
//        user.setRoles(roles);
//        User endUser = userRepository.save(user);
//        cartService.createOrder(endUser);
//    }

}
