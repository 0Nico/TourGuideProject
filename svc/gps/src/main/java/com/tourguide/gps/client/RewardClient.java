package com.tourguide.gps.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tourguide.gps.domain.dto.RewardDto;

@FeignClient(value = "reward", url = "http://localhost:8082/")
public interface RewardClient {

	@RequestMapping(method = RequestMethod.POST, value = "/")
    void createReward(@RequestBody RewardDto rewardDto);
	
}
