package com.tourguide.users.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import com.tourguide.users.domain.Provider;
import com.tourguide.users.domain.User;
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
    public String getUser(@RequestParam String userName) {
    	User user = userService.getUser(userName);
		return JsonStream.serialize(user);
    }
	
	@GetMapping("/list")
    public String getUserList() {
    	List<User> users = userService.getAllUsers();
		return JsonStream.serialize(users);
    }
	
	@PostMapping
	public void createUser(User user) {
		userService.addUser(user);
	}
	
	@GetMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tripPricerService.getTripDeals(userService.getUser(userName));
    	return JsonStream.serialize(providers);
    }
	
}
