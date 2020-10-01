package com.tourguide.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "reward", url = "http://localhost:8082/")
public interface RewardClient {

	@RequestMapping(method = RequestMethod.GET, value = "/points")
    int getCumulativePoints(@RequestParam String userName);
	
	
}
