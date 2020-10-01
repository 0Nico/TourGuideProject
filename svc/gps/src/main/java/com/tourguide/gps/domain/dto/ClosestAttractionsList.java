package com.tourguide.gps.domain.dto;

import java.util.List;

public class ClosestAttractionsList {

	private String userName;
	private double userLongitude;
	private double userLatitude;
	private List<AttractionDto> closestAttractionsList;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getUserLongitude() {
		return userLongitude;
	}
	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}
	public double getUserLatitude() {
		return userLatitude;
	}
	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}
	public List<AttractionDto> getClosestAttractionsList() {
		return closestAttractionsList;
	}
	public void setClosestAttractionsList(List<AttractionDto> closestAttractionsList) {
		this.closestAttractionsList = closestAttractionsList;
	}
	
	

}
