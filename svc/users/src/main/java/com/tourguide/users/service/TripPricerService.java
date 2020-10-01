package com.tourguide.users.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourguide.users.client.RewardClient;
import com.tourguide.users.domain.Provider;
import com.tourguide.users.domain.User;



@Service
public class TripPricerService {

	private static final String tripPricerApiKey = "test-server-api-key";
	
	@Autowired
	RewardClient rewardClient;
	
	
	
	public List<Provider> getTripDeals(User user) {
		
		int cumulatativeRewardPoints = rewardClient.getCumulativePoints(user.getUserName());
		
		List<Provider> providers = getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	 public List<Provider> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) {
	      List<Provider> providers = new ArrayList();
	      HashSet providersUsed = new HashSet();
	
	      for(int i = 0; i < 5; ++i) {
	         int multiple = ThreadLocalRandom.current().nextInt(100, 700);
	         double childrenDiscount = (double)(children / 3);
	         double price = (double)(multiple * adults) + (double)multiple * childrenDiscount * (double)nightsStay + 0.99D - (double)rewardsPoints;
	         if (price < 0.0D) {
	            price = 0.0D;
	         }
	
	         String provider = "";
	
	         do {
	            provider = this.getProviderName(apiKey, adults);
	         } while(providersUsed.contains(provider));
	
	         providersUsed.add(provider);
	         providers.add(new Provider(attractionId, provider, price));
	      }
	
	      return providers;
	   }
	
	   public String getProviderName(String apiKey, int adults) {
	      int multiple = ThreadLocalRandom.current().nextInt(1, 10);
	      switch(multiple) {
	      case 1:
	         return "Holiday Travels";
		  case 2:
		     return "Enterprize Ventures Limited";
		  case 3:
		     return "Sunny Days";
		  case 4:
		     return "FlyAway Trips";
		  case 5:
		     return "United Partners Vacations";
		  case 6:
		     return "Dream Trips";
		  case 7:
		     return "Live Free";
		  case 8:
		     return "Dancing Waves Cruselines and Partners";
		  case 9:
		     return "AdventureCo";
		  default:
		     return "Cure-Your-Blues";
		      }
	   }
}
