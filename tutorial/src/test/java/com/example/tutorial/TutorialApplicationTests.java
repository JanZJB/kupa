package com.example.tutorial;

import com.example.tutorial.clientproxy.UserResourceV1;
import com.example.tutorial.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class TutorialApplicationTests {

	@Autowired
	private UserResourceV1 userResourceV1;

	@Test
	public void contextLoads() {
		try {
			userResourceV1.fetchUsers(null);
		}
		catch (Exception e){
			System.out.println(e);
		}

		List<User> users = userResourceV1.fetchUsers(null);
		assertThat(users).hasSize(1);

		User joeUser = new User(null, "Joe", "Jones",
				User.Gender.MALE, 22, "joe.jones@gmail.com");

		assertThat(users.get(0)).isEqualToIgnoringGivenFields(joeUser, "userId");
		assertThat(users.get(0).getUserId()).isInstanceOf(UUID.class);
		assertThat(users.get(0).getUserId()).isNotNull();
	}

}
