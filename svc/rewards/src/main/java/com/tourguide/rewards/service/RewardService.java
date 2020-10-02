package com.tourguide.rewards.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tourguide.rewards.domain.Reward;
import com.tourguide.rewards.domain.dto.RewardDto;

@Service
public class RewardService {
	
	public final Map<String, List<Reward>> internalRewardsMap = new HashMap<>();

	public List<RewardDto> getUserRewards(String userName) {
		List<Reward> rewardList = internalRewardsMap.getOrDefault(userName, new ArrayList<Reward>());
		List<RewardDto> dtoList = new ArrayList<>();
		rewardList.stream().forEach(r -> {
			RewardDto rewardDto = new RewardDto();
			rewardDto.setAttractionId(r.getAttractionId());
			rewardDto.setRewardPoints(r.getRewardPoints());
			rewardDto.setUserName(userName);
			rewardDto.setVisitedLocationId(r.getVisitedLocationId());
			dtoList.add(rewardDto);
		});
		return dtoList;
	}
	
	public int getCumulativeRewardPoint(String userName) {
		return internalRewardsMap.get(userName).stream().mapToInt(i -> i.getRewardPoints()).sum();
	}
	
	public void createReward(RewardDto rewardDto) {
		
		List<Reward> rewardsList = internalRewardsMap.getOrDefault(rewardDto.getUserName(), new ArrayList<Reward>());
		
		Reward reward = new Reward(rewardDto.getVisitedLocationId(), rewardDto.getAttractionId(), rewardDto.getRewardPoints(), rewardDto.getUserName());
		rewardsList.add(reward);
		internalRewardsMap.put(rewardDto.getUserName(), rewardsList);
	}
	
	public void cleanInternalRewardsMap() {
		internalRewardsMap.clear();
	}
}
