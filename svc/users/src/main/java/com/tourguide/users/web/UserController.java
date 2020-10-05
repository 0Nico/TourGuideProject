package com.tourguide.users.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import com.tourguide.users.domain.User;
import com.tourguide.users.domain.UserPreferences;
import com.tourguide.users.service.TripPricerService;
import com.tourguide.users.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	TripPricerService tripPricerService;
	
	
	@GetMapping 
    public User getUser(@RequestParam String userName) {
    	User user = userService.getUser(userName);
		return user;
    }
	
	@GetMapping("/list")
    public List<User> getUserList() {
    	List<User> users = userService.getAllUsers();
		return users;
    }
	
	@PostMapping
	public void createUser(@RequestBody User user) {
		userService.addUser(user);
	}
	
	@PutMapping("/preferences")
	public User updateUserPreferences(@RequestParam String userName, @RequestBody UserPreferences userPref) {
		User user = userService.updateUserPref(userName, userPref);
		return user;
	}
	
	
}
