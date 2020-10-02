package com.tourguide.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tourguide.users.domain.dto.VisitedLocationDto;

@FeignClient(value = "gps", url = "http://localhost:8081/gps")
public interface GpsClient {

	@RequestMapping(method = RequestMethod.POST, value = "/generateUserLocationsHistory")
    void generateUserLocationsHistory(@RequestParam String userName);
	
	@RequestMapping(method = RequestMethod.GET, value = "/getUserLocation") 
	public String getUserLocation(@RequestParam String userName);
	
	@RequestMapping(method = RequestMethod.POST, value = "/addVisitedLocations")
	public void addVisitedLocations(@RequestBody VisitedLocationDto visitedLocationDto);
}
