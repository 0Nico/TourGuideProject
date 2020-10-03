package com.tourguide.gps.domain.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tourguide.gps.domain.Location;

@JsonInclude(Include.NON_NULL)
public class VisitedLocationDto {

	public UUID id;
	public String userName;
	public LocationDto location;
	public Date timeVisited;
	
	public VisitedLocationDto(UUID id, String userName, LocationDto location, Date timeVisited) {
		this.id = id;
		this.userName = userName;
		this.location = location;
		this.timeVisited = timeVisited;
	}
}
