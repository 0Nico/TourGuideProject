package com.tourguide.users.domain.dto;

public class LocationDto {

	public double longitude;
	public double latitude;

   public LocationDto(double latitude, double longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
   }
}
