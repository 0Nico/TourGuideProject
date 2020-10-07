package com.tourguide.gps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.tourguide.gps.client.UserClient;
import com.tourguide.gps.service.GpsService;
import com.tourguide.gps.tracker.Tracker;


@EnableFeignClients
@SpringBootApplication
public class GpsApplication {

	@Autowired
	UserClient userClient;
	
	@Autowired
	GpsService gpsService;
	
	public static void main(String[] args) {
		SpringApplication.run(GpsApplication.class, args);
	}

	@Bean
	Tracker tracker(UserClient userClient, GpsService gpsService ) {
		return new Tracker(userClient, gpsService);
	}
}
