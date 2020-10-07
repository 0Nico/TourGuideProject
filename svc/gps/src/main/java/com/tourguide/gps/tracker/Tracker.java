package com.tourguide.gps.tracker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tourguide.gps.client.UserClient;
import com.tourguide.gps.service.GpsService;



public class Tracker extends Thread{

	private Logger logger = LoggerFactory.getLogger(Tracker.class);
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	private UserClient userClient;
	private GpsService gpsService;
	
	public Tracker(UserClient userClient, GpsService gpsService) {
		 this.userClient = userClient;
		 this.gpsService = gpsService;
		 executorService.submit(this);
		}

	
	@Override
	public void run() {
		
		StopWatch stopWatch = new StopWatch();
		while(true) {
			
			List<String> users = userClient.getUserList();
			logger.info("Begin Tracker. Tracking " + users.size() + " users.");
			stopWatch.start();
			users.forEach(u -> gpsService.getUserLocation(u));
			stopWatch.stop();
			logger.info("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
			stopWatch.reset();
			try {
				logger.info("Tracker sleeping");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				break;
			}
		}
		
	}
}
