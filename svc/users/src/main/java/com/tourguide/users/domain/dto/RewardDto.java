package com.tourguide.users.domain.dto;

import java.util.UUID;

public class RewardDto {

	private UUID visitedLocationId;
	private UUID attractionId;
	private int rewardPoints;
	private String userName;
	
	public UUID getVisitedLocationId() {
		return visitedLocationId;
	}
	public void setVisitedLocationId(UUID visitedLocationId) {
		this.visitedLocationId = visitedLocationId;
	}
	public UUID getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}
	public int getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
