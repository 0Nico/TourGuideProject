package com.tourguide.users.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tourguide.users.domain.dto.RewardDto;

@FeignClient(value = "reward", url = "http://localhost:8082/reward")
public interface RewardClient {

	@RequestMapping(method = RequestMethod.GET, value = "/points")
    int getCumulativePoints(@RequestParam String userName);
	
	@RequestMapping(method = RequestMethod.GET, value = "")
	List<RewardDto> getRewards(@RequestParam String userName);
	
}
