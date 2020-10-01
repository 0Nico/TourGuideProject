package com.tourguide.gps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.tourguide.gps.service.GpsService;

@EnableFeignClients
@SpringBootApplication
public class GpsApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(GpsApplication.class, args);
	}

}
