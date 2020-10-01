package com.tourguide.gps.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourguide.gps.client.RewardClient;
import com.tourguide.gps.domain.Attraction;
import com.tourguide.gps.domain.Location;
import com.tourguide.gps.domain.VisitedLocation;
import com.tourguide.gps.domain.dto.AttractionDto;
import com.tourguide.gps.domain.dto.ClosestAttractionsList;
import com.tourguide.gps.domain.dto.RewardDto;




@Service
public class GpsService {

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    
	public final Map<String, List<VisitedLocation>> internalVisitedLocationMap = new HashMap<>();
	
	public final List<Attraction> internalAttractionMap = new ArrayList<>();
	
	
	@Autowired
	RewardClient rewardClient;
	
	
	public VisitedLocation getUserLocation(String userName) {
		
		List<VisitedLocation> visitedLocations = internalVisitedLocationMap.getOrDefault(userName, new ArrayList<VisitedLocation>());
		if (visitedLocations.isEmpty()) {
			visitedLocations.add(generateRandomLocation(userName));
		} 
		
		VisitedLocation actualVisitedLocation = visitedLocations.get(visitedLocations.size() - 1);
		for(Attraction attraction : internalAttractionMap) {
			if (getDistance(attraction, actualVisitedLocation.location) > 10 ? false : true) {
				RewardDto rewardDto = new RewardDto();
				rewardDto.setAttractionId(attraction.attractionId);
				rewardDto.setVisitedLocationId(actualVisitedLocation.id);
				rewardDto.setUserName(userName);
				rewardDto.setRewardPoints(getAttractionRewardPoints());
				rewardClient.createReward(rewardDto);
			}
		}
	    return actualVisitedLocation;
	}
	
	
	public VisitedLocation generateRandomLocation(String userName) {
		
		double longitude = ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D);
		longitude = Double.parseDouble(String.format("%.6f", longitude));
		double latitude = ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D);
		latitude = Double.parseDouble(String.format("%.6f", latitude));
		VisitedLocation visitedLocation = new VisitedLocation(
				UUID.randomUUID() ,userName, new Location(latitude, longitude), new Date());
		return visitedLocation;
	}


	public ClosestAttractionsList getClosestAttractions(VisitedLocation visitedLocation) {
		
		ClosestAttractionsList closestAttractionsList = new ClosestAttractionsList();
		closestAttractionsList.setUserName(visitedLocation.userName);
		closestAttractionsList.setUserLatitude(visitedLocation.location.latitude);
		closestAttractionsList.setUserLongitude(visitedLocation.location.longitude);
		List<AttractionDto> nearbyAttractions = new ArrayList<>();
		internalAttractionMap.stream().sorted((attra1,attra2) -> 
			Double.compare(getDistance(visitedLocation.location, attra1), getDistance(visitedLocation.location, attra2)))
			.limit(5).forEach(attra ->{
				AttractionDto attractionDto = new AttractionDto();
				attractionDto.setAttractionName(attra.attractionName);
				attractionDto.setAttractionLatitude(attra.latitude);
				attractionDto.setAttractionLongitude(attra.longitude);
				attractionDto.setActualDistanceWithUser(getDistance(visitedLocation.location, attra));
				attractionDto.setRewardPointsVisitingAttraction(getAttractionRewardPoints());
				nearbyAttractions.add(attractionDto);
			});;
		closestAttractionsList.setClosestAttractionsList(nearbyAttractions);
		return closestAttractionsList;
	}
	
	
	

	
	
	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

	
	  public int getAttractionRewardPoints() {
	      int randomInt = ThreadLocalRandom.current().nextInt(1, 1000);
	      return randomInt;
	   }


	public List<VisitedLocation> getUserCurrentLocations(String userName) {
		return internalVisitedLocationMap.get(userName).stream().filter(loc ->
			System.currentTimeMillis() - loc.timeVisited.getTime() < 1000 * 60 * 60 * 24 * 31) // Visited date must be less than a month
				.sorted((loc1,loc2) -> loc1.timeVisited.compareTo(loc2.timeVisited)).collect(Collectors.toList());
		
	}
	
	public void initializeAttractions() {
	
		internalAttractionMap.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
		internalAttractionMap.add(new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
		internalAttractionMap.add(new Attraction("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
		internalAttractionMap.add(new Attraction("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
		internalAttractionMap.add(new Attraction("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
		internalAttractionMap.add(new Attraction("Hot Springs National Park", "Hot Springs", "AR", 34.52153D, -93.042267D));
		internalAttractionMap.add(new Attraction("Kartchner Caverns State Park", "Benson", "AZ", 31.837551D, -110.347382D));
		internalAttractionMap.add(new Attraction("Legend Valley", "Thornville", "OH", 39.937778D, -82.40667D));
		internalAttractionMap.add(new Attraction("Flowers Bakery of London", "Flowers Bakery of London", "KY", 37.131527D, -84.07486D));
		internalAttractionMap.add(new Attraction("McKinley Tower", "Anchorage", "AK", 61.218887D, -149.877502D));
		internalAttractionMap.add(new Attraction("Flatiron Building", "New York City", "NY", 40.741112D, -73.989723D));
		internalAttractionMap.add(new Attraction("Fallingwater", "Mill Run", "PA", 39.906113D, -79.468056D));
		internalAttractionMap.add(new Attraction("Union Station", "Washington D.C.", "CA", 38.897095D, -77.006332D));
		internalAttractionMap.add(new Attraction("Roger Dean Stadium", "Jupiter", "FL", 26.890959D, -80.116577D));
		internalAttractionMap.add(new Attraction("Texas Memorial Stadium", "Austin", "TX", 30.283682D, -97.732536D));
		  internalAttractionMap.add(new Attraction("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.208973D, -87.550438D));
		  internalAttractionMap.add(new Attraction("Tiger Stadium", "Baton Rouge", "LA", 30.412035D, -91.183815D));
		  internalAttractionMap.add(new Attraction("Neyland Stadium", "Knoxville", "TN", 35.955013D, -83.925011D));
		  internalAttractionMap.add(new Attraction("Kyle Field", "College Station", "TX", 30.61025D, -96.339844D));
		  internalAttractionMap.add(new Attraction("San Diego Zoo", "San Diego", "CA", 32.735317D, -117.149048D));
		  internalAttractionMap.add(new Attraction("Zoo Tampa at Lowry Park", "Tampa", "FL", 28.012804D, -82.469269D));
		  internalAttractionMap.add(new Attraction("Franklin Park Zoo", "Boston", "MA", 42.302601D, -71.086731D));
		  internalAttractionMap.add(new Attraction("El Paso Zoo", "El Paso", "TX", 31.769125D, -106.44487D));
		  internalAttractionMap.add(new Attraction("Kansas City Zoo", "Kansas City", "MO", 39.007504D, -94.529625D));
		  internalAttractionMap.add(new Attraction("Bronx Zoo", "Bronx", "NY", 40.852905D, -73.872971D));
		  internalAttractionMap.add(new Attraction("Cinderella Castle", "Orlando", "FL", 28.419411D, -81.5812D));
	   }


}
