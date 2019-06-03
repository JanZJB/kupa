package com.example.tutorial.service;

import com.example.tutorial.dao.FakeDataDao;
import com.example.tutorial.model.User;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    public void shouldGetAllUsers() {
        UUID annaUserId = UUID.randomUUID();

        User annaUser = new User(annaUserId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>()
                .add(annaUser)
                .build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);

        User user = allUsers.get(0);
        assertAnnaFields(user);

    }

    @Test
    public void shouldGetAllUsersByGender(){
        UUID annaUserId = UUID.randomUUID();

        User annaUser = new User(annaUserId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        UUID joeUserId = UUID.randomUUID();

        User joeUser = new User(joeUserId, "joe", "jones",
                User.Gender.MALE, 30, "joe.jones@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>()
                .add(annaUser)
                .add(joeUser)
                .build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("FEMALE"));
        assertThat(filteredUsers).hasSize(1);
        assertAnnaFields(filteredUsers.get(0));
    }

    @Test
    public void shouldThrowExceptionWhenGenderIsInvalid() {
        assertThatThrownBy(() -> userService.getAllUsers(Optional.of("asdsadsa")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid gender");
    }

    @Test
    public void shouldGetUser() {
        UUID annaId = UUID.randomUUID();

        User annaUser = new User(annaId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserId(annaId)).willReturn(Optional.of(annaUser));

        Optional<User> userOptional = userService.getUser(annaId);

        assertThat(userOptional.isPresent()).isTrue();

        User user  = userOptional.get();

        assertAnnaFields(user);
    }

    @Test
    public void shouldUpdateUser() {
        UUID annaId = UUID.randomUUID();

        User annaUser = new User(annaId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserId(annaId)).willReturn(Optional.of(annaUser));
        given(fakeDataDao.updateUser(annaUser)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(annaUser);

        verify(fakeDataDao).selectUserByUserId(annaId);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertAnnaFields(user);

        assertThat(updateResult).isEqualTo(1);
    }

    @Test
    public void shouldRemoveUser() {
        UUID annaId = UUID.randomUUID();

        User annaUser = new User(annaId, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserId(annaId)).willReturn(Optional.of(annaUser));
        given(fakeDataDao.deleteUserByUserId(annaId)).willReturn(1);

        int deleteResult = userService.removeUser(annaId);

        verify(fakeDataDao).selectUserByUserId(annaId);
        verify(fakeDataDao).deleteUserByUserId(annaId);

        assertThat(deleteResult).isEqualTo(1);

    }

    @Test
    public void shouldInsertUser() {
        User annaUser = new User(null, "anna", "montana",
                User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.insertUser(any(UUID.class), eq(annaUser))).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(annaUser);

        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();

        assertAnnaFields(user);

        assertThat(insertResult).isEqualTo(1);

    }

    private void assertAnnaFields(User user) {
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
        assertThat(user.getUserId()).isInstanceOf(UUID.class);
        assertThat(user.getUserId()).isNotNull();

    }
}