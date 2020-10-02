package com.tourguide.users.unit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tourguide.users.client.GpsClient;
import com.tourguide.users.client.RewardClient;
import com.tourguide.users.domain.User;
import com.tourguide.users.domain.dto.LocationDto;
import com.tourguide.users.domain.dto.VisitedLocationDto;
import com.tourguide.users.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPerformance {

	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GpsClient gpsClient;
	
	@Autowired
	private RewardClient rewardClient;
	

	@Test
	public void highVolumeTrackLocation() {

		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		
		userService.initializeInternalUsers(100000);

		List<User> allUsers = new ArrayList<>();
		allUsers = userService.getAllUsers();
		
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for(User user : allUsers) {
			gpsClient.getUserLocation(user.getUserName());
		}
		stopWatch.stop();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
	@Test
	public void highVolumeGetRewards() {


		// Users should be incremented up to 100,000, and test finishes within 20 minutes
		userService.initializeInternalUsers(100000);
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

	    
	    List<User> allUsers = new ArrayList<>();
		allUsers = userService.getAllUsers();
		
		UUID uuid = UUID.randomUUID();
		allUsers.forEach(u -> {
			LocationDto locationDto = new LocationDto(33.817595D, -117.922008D);
			VisitedLocationDto visitedLocationDto = new VisitedLocationDto(uuid, u.getUserName(), locationDto, new Date());
			gpsClient.addVisitedLocations(visitedLocationDto);
		});
	     
	    
		for(User user : allUsers) {
			assertTrue(rewardClient.getRewards(user.getUserName()).size() > 0 );
		}
		stopWatch.stop();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
}
