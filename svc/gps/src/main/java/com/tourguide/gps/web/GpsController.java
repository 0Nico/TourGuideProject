package com.tourguide.gps.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import com.tourguide.gps.domain.Location;
import com.tourguide.gps.domain.VisitedLocation;
import com.tourguide.gps.domain.dto.ClosestAttractionsList;
import com.tourguide.gps.domain.dto.VisitedLocationDto;
import com.tourguide.gps.service.GpsService;

@RestController
@RequestMapping("/gps")
public class GpsController {

	@Autowired
	GpsService gpsService;
	
	
	@GetMapping("/getUserLocation") 
	public Location getUserLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = gpsService.getUserLocation(userName);
		return visitedLocation.location;
	}
	
	@GetMapping("/getClosestAttractions") 
    public ClosestAttractionsList getClosestAttractions(@RequestParam String userName) {
    	VisitedLocation visitedLocation = gpsService.getUserLocation(userName);
    	return gpsService.getClosestAttractions(visitedLocation);
    }
	
	@GetMapping("/getLastMonthLocations")
    public List<VisitedLocation> getLastMonthLocations(@RequestParam String userName) {
		List<VisitedLocation> visitedLocations = gpsService.getUserLastLocations(userName);
    	return visitedLocations;
    }
	
	@GetMapping("/getAllCurrentLocations")
    public List<VisitedLocation> getAllCurrentLocations() {
		List<VisitedLocation> visitedLocations = gpsService.getAllCurrentLocations();
    	return visitedLocations;
    }
	
	@PostMapping("/generateUserLocationsHistory")
	public void generateUserLocationsHistory(@RequestParam String userName) {
		gpsService.generateUserLocationHistory(userName);
	}
	
	@PostMapping("/addVisitedLocations")
	public void addVisitedLocations(@RequestBody VisitedLocationDto visitedLocationDto) {
		gpsService.addVisitedLocation(visitedLocationDto);
	}
	
	
	
}
