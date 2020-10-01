package com.tourguide.rewards.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tourguide.rewards.domain.Reward;
import com.tourguide.rewards.domain.dto.RewardDto;

@Service
public class RewardService {
	
	public final Map<String, List<Reward>> internalRewardsMap = new HashMap<>();

	public List<Reward> getUserRewards(String userName) {
		return internalRewardsMap.get(userName);
	}
	
	public int getCumulativeRewardPoint(String userName) {
		return internalRewardsMap.get(userName).stream().mapToInt(i -> i.getRewardPoints()).sum();
	}
	
	public void createReward(RewardDto rewardDto) {
		
		List<Reward> rewardsList = internalRewardsMap.get(rewardDto.getUserName());
		
		Reward reward = new Reward(rewardDto.getVisitedLocationId(), rewardDto.getAttractionId(), rewardDto.getRewardPoints(), rewardDto.getUserName());
		rewardsList.add(reward);
		internalRewardsMap.put(rewardDto.getUserName(), rewardsList);
	}
}
