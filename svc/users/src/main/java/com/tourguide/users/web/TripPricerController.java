package com.tourguide.users.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import com.tourguide.users.domain.Provider;
import com.tourguide.users.service.TripPricerService;
import com.tourguide.users.service.UserService;

@RestController
@RequestMapping("/tripPricer")
public class TripPricerController {

	@Autowired
	UserService userService;
	
	@Autowired
	TripPricerService tripPricerService;
	
	
	@GetMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tripPricerService.getTripDeals(userService.getUser(userName));
    	return JsonStream.serialize(providers);
    }
}
