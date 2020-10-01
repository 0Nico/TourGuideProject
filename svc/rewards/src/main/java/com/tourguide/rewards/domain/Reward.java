package com.tourguide.rewards.domain;

import java.util.UUID;



public class Reward {

	private final UUID visitedLocationId;
	private final UUID attractionId;
	private int rewardPoints;
	private final String userName;
	
	public Reward(UUID visitedLocationId, UUID attractionId, int rewardPoints, String userName) {
		this.visitedLocationId = visitedLocationId;
		this.attractionId = attractionId;
		this.rewardPoints = rewardPoints;
		this.userName = userName;
	}
	
	public int getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public UUID getVisitedLocationId() {
		return visitedLocationId;
	}
	public UUID getAttractionId() {
		return attractionId;
	}
	public String getUserName() {
		return userName;
	}
	
	
}
