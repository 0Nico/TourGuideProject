package com.tourguide.gps.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tourguide.gps.domain.Location;
import com.tourguide.gps.domain.VisitedLocation;
import com.tourguide.gps.domain.dto.ClosestAttractionsList;
import com.tourguide.gps.domain.dto.LocationDto;
import com.tourguide.gps.domain.dto.VisitedLocationDto;
import com.tourguide.gps.service.GpsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGpsService {

	@Autowired
	GpsService gpsService;
	
	
	@Test
	public void testGetUserLocation() {
		
		gpsService.cleanVisitedLocationMap();
		
		Location location = new Location(ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D),
				ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));
		VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), "jon", location , new Date());
		List<VisitedLocation> list = new ArrayList<>();
		list.add(visitedLocation);
		
		gpsService.internalVisitedLocationMap.put("jon", list);
		assertEquals(gpsService.getUserLocation("jon").location, location);
		
	}
	
	@Test
	public void testGenerateUserHistoryLocation() {
		
		gpsService.cleanVisitedLocationMap();
		
		gpsService.generateUserLocationHistory("jonny");
		List<VisitedLocation> visitLocationsGenerated = gpsService.getUserLastLocations("jonny");
		assertTrue(visitLocationsGenerated.size() == 3);
		assertEquals(visitLocationsGenerated.get(0).userName, "jonny");
		
	}
	
	@Test
	public void testAddVisitedLocation() {
		
		gpsService.cleanVisitedLocationMap();
		
		LocationDto locationDto = new LocationDto(ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D),
				ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));
		
		VisitedLocationDto visitedLocationDto = new VisitedLocationDto(UUID.randomUUID(), "Bernard", locationDto, new Date());
		
		gpsService.addVisitedLocation(visitedLocationDto);
		assertTrue(gpsService.getUserLastLocations("Bernard").size() == 1);
		assertTrue(gpsService.getUserLocation("Bernard").location.latitude == locationDto.latitude );
	}
	
	@Test
	public void testGetClosestAttractions() {
		
		gpsService.cleanVisitedLocationMap();
		
		Location location = new Location(ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D),
				ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));
		VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), "jon", location , new Date());
		
		ClosestAttractionsList closestAttractionsList = gpsService.getClosestAttractions(visitedLocation);
		
		assertTrue(closestAttractionsList.getClosestAttractionsList().size() == 5);
		assertEquals(closestAttractionsList.getUserName(), "jon");
		assertTrue(closestAttractionsList.getClosestAttractionsList().get(0).getActualDistanceWithUser() <
				closestAttractionsList.getClosestAttractionsList().get(1).getActualDistanceWithUser());
		
		
	}
	
	@Test
	public void testGetUserLastLocations() {
		
		gpsService.cleanVisitedLocationMap();
		
		gpsService.generateUserLocationHistory("Bernard");
		List<VisitedLocation> visitLocationsGenerated = gpsService.getUserLastLocations("Bernard");
	
	    
		assertTrue(visitLocationsGenerated.get(0).timeVisited.after(Date.from(LocalDateTime.now().minusDays(31).toInstant(ZoneOffset.UTC))));
		assertEquals(visitLocationsGenerated.get(0).userName, "Bernard");
	}
	
	@Test
	public void testGetAllCurrentLocations() {
		
		gpsService.cleanVisitedLocationMap();
		
		LocationDto locationDto = new LocationDto(ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D),
				ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));
		
		VisitedLocationDto visitedLocationDto1 = new VisitedLocationDto(UUID.randomUUID(), "Bernard", locationDto, Date.from(LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC)));
		VisitedLocationDto visitedLocationDto2 = new VisitedLocationDto(UUID.fromString("a1e286f9-63f9-4cb0-b9e3-19b0f804931d"), "Bernard", locationDto, new Date());
		VisitedLocationDto visitedLocationDto3 = new VisitedLocationDto(UUID.randomUUID(), "Jon", locationDto, Date.from(LocalDateTime.now().minusDays(3).toInstant(ZoneOffset.UTC)));
		VisitedLocationDto visitedLocationDto4 = new VisitedLocationDto(UUID.fromString("06feaf7c-b97a-4a79-a403-e14bc234b58e"), "Jon", locationDto, new Date());
		
		gpsService.addVisitedLocation(visitedLocationDto1);
		gpsService.addVisitedLocation(visitedLocationDto2);
		gpsService.addVisitedLocation(visitedLocationDto3);
		gpsService.addVisitedLocation(visitedLocationDto4);
		
		List<VisitedLocation> allCurrentLocations = gpsService.getAllCurrentLocations();
	
	    
		assertTrue(allCurrentLocations.size() > 0 );
		assertTrue(allCurrentLocations.stream().anyMatch(visitLoc -> visitLoc.id.equals(UUID.fromString("06feaf7c-b97a-4a79-a403-e14bc234b58e")) ));
	}
	
}
