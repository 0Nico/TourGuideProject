package com.tourguide.users.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourguide.users.client.GpsClient;
import com.tourguide.users.domain.User;
import com.tourguide.users.domain.UserPreferences;


@Service
public class UserService {
	
	public final Map<String, User> internalUserMap = new HashMap<>();
	
	@Autowired
	GpsClient gpsClient;
	
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}
	
	public void addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	
	
	public void initializeInternalUsers(int userNumber) {
		IntStream.range(0, userNumber).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			gpsClient.generateUserLocationsHistory(userName);
			
			internalUserMap.put(userName, user);
		});
		
	}
	
	public void cleanInternalUserMap() {
		internalUserMap.clear();
	}

	public User updateUserPref(String userName, UserPreferences userPref) {
		User user = internalUserMap.get(userName);
		user.setUserPreferences(userPref);
		internalUserMap.put(userName, user);
		return user;
		
	}

}
