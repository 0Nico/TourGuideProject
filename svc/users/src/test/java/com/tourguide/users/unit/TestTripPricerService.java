package com.tourguide.users.unit;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tourguide.users.domain.Provider;
import com.tourguide.users.service.TripPricerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTripPricerService {

	@Autowired
	TripPricerService tripPricerService;
	
	
	@Test
	public void testGetPrice() {
		
		List<Provider> providers = tripPricerService.getPrice("api-key", UUID.randomUUID(), 2, 1, 5, 250);
		assertTrue(providers.size() > 0);
	}
}
