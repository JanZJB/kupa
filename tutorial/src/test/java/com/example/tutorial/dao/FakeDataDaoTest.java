package com.example.tutorial.dao;

import com.example.tutorial.model.User;
import org.assertj.core.api.ListAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @Before
    public void setUp() throws Exception {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    public void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);
        User user = users.get(0);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("joe.jones@gmail.com");

    }

    @Test
    public void shouldSelectUserByUserId() {
        UUID annaUserUid = UUID.randomUUID();
        User anna = new User(annaUserUid, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        fakeDataDao.insertUser(annaUserUid, anna);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);

        Optional<User> annaOptional = fakeDataDao.selectUserByUserId(annaUserUid);
        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
    }

    @Test
    public void shouldNotSelectUserByRandomUserId() {
        Optional<User> user = fakeDataDao.selectUserByUserId(UUID.randomUUID());
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void shouldUpdateUser() {
        UUID joeUserId = fakeDataDao.selectAllUsers().get(0).getUserId();

        User newJoe = new User(joeUserId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        fakeDataDao.updateUser(newJoe);
        Optional<User> user = fakeDataDao.selectUserByUserId(joeUserId);
        assertThat(user.isPresent()).isTrue();

        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(user.get()).isEqualToComparingFieldByField(newJoe);

    }

    @Test
    public void deleteUserByUserId() {
        UUID joeUserId = fakeDataDao.selectAllUsers().get(0).getUserId();

        fakeDataDao.deleteUserByUserId(joeUserId);

        assertThat(fakeDataDao.selectUserByUserId(joeUserId).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();

    }

    @Test
    public void insertUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        fakeDataDao.insertUser(userId, user);

        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserId(userId).get()).isEqualToComparingFieldByField(user);


    }
}