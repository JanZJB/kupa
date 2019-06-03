package com.example.tutorial.dao;

import com.example.tutorial.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDataDao implements UserDao {

    private static Map<UUID, User> database;

    public FakeDataDao(){
        database = new HashMap<>();
        UUID joeUserId = UUID.randomUUID();
        database.put(joeUserId, new User(joeUserId, "Joe", "Jones",
                User.Gender.MALE, 22, "joe.jones@gmail.com"));

    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserId(UUID userId) {
        return Optional.ofNullable(database.get(userId));
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserId(), user);
        return 1;
    }

    @Override
    public int deleteUserByUserId(UUID userId) {
        database.remove(userId);
        return 1;
    }

    @Override
    public int insertUser(UUID userId, User user) {
        database.put(userId, user);
        return 1;
    }
}
