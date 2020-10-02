package com.tourguide.users.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tourguide.users.domain.User;
import com.tourguide.users.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserService {
	
	@Autowired
	UserService userService;
	
	@Test
	public void testGetUser() {
		
		userService.cleanInternalUserMap();
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		
		userService.addUser(user);
		User user2 = userService.getUser("jon");
		
		assertEquals(user.getEmailAddress(), "jon@tourGuide.com" );
	}

	
	@Test
	public void testAddUser() {
		
		User user = new User(UUID.randomUUID(), "jon", "030", "jonny@tourGuide.com");
		userService.addUser(user);
		
		User addedUser = userService.getUser("jon");
		assertEquals(addedUser.getEmailAddress(), "jonny@tourGuide.com" );
		assertEquals(addedUser, user);
		
	}
	
	
	@Test
	public void testGetAllUsers() {

		userService.cleanInternalUserMap();
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		
		userService.addUser(user);
		userService.addUser(user2);
		
		List<User> userList = userService.getAllUsers();
		
		assertTrue(userList.contains(user));
		assertTrue(userList.contains(user2));
		assertTrue(userList.size() == 2);
	}
}
