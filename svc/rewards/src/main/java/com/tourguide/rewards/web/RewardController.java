package com.tourguide.rewards.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import com.tourguide.rewards.domain.Reward;
import com.tourguide.rewards.domain.dto.RewardDto;
import com.tourguide.rewards.service.RewardService;

@RestController
@RequestMapping("/reward")
public class RewardController {

	@Autowired
	RewardService rewardService;
	
	
	@GetMapping
	public String getRewards(@RequestParam String userName) {
		return JsonStream.serialize(rewardService.getUserRewards(userName));
	}
	
	@GetMapping("/points")
	public int getCumulativeRewardPoints(@RequestParam String userName) {
		return rewardService.getCumulativeRewardPoint(userName);
	}
	
	@PostMapping
	public void createReward(@RequestBody RewardDto rewardDto) {
		rewardService.createReward(rewardDto);
	}
}
