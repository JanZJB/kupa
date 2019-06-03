package com.example.tutorial.service;

import com.example.tutorial.dao.UserDao;
import com.example.tutorial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();
        if(!gender.isPresent()){
            return users;
        }
        try {
            User.Gender filteredGender = User.Gender.valueOf(gender.get().toUpperCase());
            return users.stream()
                    .filter(user -> user.getGender().equals(filteredGender))
                    .collect(Collectors.toList());

        } catch (Exception e){
            throw new IllegalStateException("Invalid gender", e);
        }
    }

    public Optional<User> getUser(UUID userId) {
        return userDao.selectUserByUserId(userId);
    }


    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserId());
        if(optionalUser.isPresent()){
            return userDao.updateUser(user);
        }
        return -1;
    }


    public int removeUser(UUID userId) {
        Optional<User> optionalUser = getUser(userId);
        if(optionalUser.isPresent()){
            return userDao.deleteUserByUserId(userId);
        }
        return -1;
    }


    public int insertUser(User user) {
        UUID userId = UUID.randomUUID();
        return userDao.insertUser(userId, User.newUser(userId, user));
    }
}
