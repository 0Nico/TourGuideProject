package com.tourguide.gps.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import com.tourguide.gps.domain.VisitedLocation;
import com.tourguide.gps.domain.dto.ClosestAttractionsList;
import com.tourguide.gps.service.GpsService;

@RestController
@RequestMapping("/gps")
public class GpsController {

	@Autowired
	GpsService gpsService;
	
	
	@GetMapping("/getUserLocation") 
	public String getUserLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = gpsService.getUserLocation(userName);
		return JsonStream.serialize(visitedLocation.location);
	}
	
	@GetMapping("/getClosestAttractions") 
    public ClosestAttractionsList getClosestAttractions(@RequestParam String userName) {
    	VisitedLocation visitedLocation = gpsService.getUserLocation(userName);
    	return gpsService.getClosestAttractions(visitedLocation);
    }
	
	@GetMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations(@RequestParam String userName) {
		List<VisitedLocation> visitedLocations = gpsService.getUserCurrentLocations(userName);
    	return JsonStream.serialize(visitedLocations);
    }
	
}
