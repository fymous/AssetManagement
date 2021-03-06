package com.springboot.rest.geocodes;

import org.apache.log4j.Logger;

/**
 * Calculates distance between customer and shop coordinates
 *
 */
public class DistanceBetweenCoordinates {
	private static final Logger logger = Logger.getLogger(DistanceBetweenCoordinates.class);
	
	public double distanceBetween(double lat1, double lng1, double lat2, double lng2) {
		logger.info("Entering distanceBetween method in methDistanceBetweenCoordinates");
	    double earthRadius = 6371000; //in meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = (double) (earthRadius * c);

	    return Math.abs(dist);
	    }
}
