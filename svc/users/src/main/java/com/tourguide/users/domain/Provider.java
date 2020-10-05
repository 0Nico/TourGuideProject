package com.tourguide.users.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Provider {

	 public final String name;
	 public final double price;
	 public final UUID tripId;
	 
	 public Provider(UUID tripId, String name, double price) {
		 this.name = name;
		 this.tripId = tripId;
		 this.price = price;
	 }
}
