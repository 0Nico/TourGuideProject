package com.tourguide.rewards.unit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tourguide.rewards.domain.Reward;
import com.tourguide.rewards.domain.dto.RewardDto;
import com.tourguide.rewards.service.RewardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRewardService {

	@Autowired
	RewardService rewardService;
	
	
	@Test
	public void testGetUserReward() {
	
		rewardService.cleanInternalRewardsMap();
		
		List<Reward> rewardList = new ArrayList<Reward>();
		rewardList.add(new Reward(UUID.randomUUID(), UUID.randomUUID(), 100, "gabriel"));
		rewardList.add(new Reward(UUID.randomUUID(), UUID.randomUUID(), 50, "gabriel"));
		
		rewardService.internalRewardsMap.put("gabriel", rewardList);
		
		List<RewardDto> rewardList2 = rewardService.getUserRewards("gabriel");
		assertTrue(rewardList2.size() > 0 );
		assertTrue(rewardList2.size() == 2 );
		
	}
	
	@Test
	public void testGetCumulativeRewardPoint() {
		
		rewardService.cleanInternalRewardsMap();
		
		List<Reward> rewardList = new ArrayList<Reward>();
		rewardList.add(new Reward(UUID.randomUUID(), UUID.randomUUID(), 100, "gabriel"));
		rewardList.add(new Reward(UUID.randomUUID(), UUID.randomUUID(), 50, "gabriel"));
		
		rewardService.internalRewardsMap.put("gabriel", rewardList);
		
		assertTrue(rewardService.getCumulativeRewardPoint("gabriel") == 150 );
	}
	
	@Test
	public void testCreateReward() {
		
		rewardService.cleanInternalRewardsMap();
		
		RewardDto rewardDto = new RewardDto();
		rewardDto.setAttractionId(UUID.randomUUID());
		rewardDto.setVisitedLocationId(UUID.randomUUID());
		rewardDto.setRewardPoints(20);
		rewardDto.setUserName("jon");
		
		rewardService.createReward(rewardDto);
		
		List<Reward> jonRewards = rewardService.internalRewardsMap.get("jon");
		assertTrue(jonRewards.size() > 0);
		assertTrue(jonRewards.get(0).getRewardPoints() == 20);
		
	}
}
