package com.tourguide.gps.domain.dto;

public class AttractionDto {

	private String attractionName;
	private double attractionLongitude;
	private double attractionLatitude;
	private double actualDistanceWithUser;
	private int rewardPointsVisitingAttraction;
	
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	public double getAttractionLongitude() {
		return attractionLongitude;
	}
	public void setAttractionLongitude(double attractionLongitude) {
		this.attractionLongitude = attractionLongitude;
	}
	public double getAttractionLatitude() {
		return attractionLatitude;
	}
	public void setAttractionLatitude(double attractionLatitude) {
		this.attractionLatitude = attractionLatitude;
	}
	public double getActualDistanceWithUser() {
		return actualDistanceWithUser;
	}
	public void setActualDistanceWithUser(double actualDistanceWithUser) {
		this.actualDistanceWithUser = actualDistanceWithUser;
	}
	public int getRewardPointsVisitingAttraction() {
		return rewardPointsVisitingAttraction;
	}
	public void setRewardPointsVisitingAttraction(int rewardPointsVisitingAttraction) {
		this.rewardPointsVisitingAttraction = rewardPointsVisitingAttraction;
	}
	
	
	
	
}
