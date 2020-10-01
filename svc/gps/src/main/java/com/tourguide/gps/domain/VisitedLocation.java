package com.tourguide.gps.domain;

import java.util.Date;
import java.util.UUID;

public class VisitedLocation {

	public final UUID id;
	public final String userName;
	public final Location location;
	public final Date timeVisited;
	
	public VisitedLocation(UUID id, String userName, Location location, Date timeVisited) {
		this.id = id;
		this.userName = userName;
		this.location = location;
		this.timeVisited = timeVisited;
	}


}
