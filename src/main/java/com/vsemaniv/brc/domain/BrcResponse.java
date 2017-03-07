package com.vsemaniv.brc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents response of the application, Will be converted to JSON format before sending to client
 */
public class BrcResponse {

	@JsonProperty("dep_sid")
	private int departureStationId;

	@JsonProperty("arr_sid")
	private int arrivalStationId;

	@JsonProperty("direct_bus_route")
	private boolean directBusRoute;

	public BrcResponse(int departureStationId, int arrivalStationId, boolean directBusRoute) {
		this.departureStationId = departureStationId;
		this.arrivalStationId = arrivalStationId;
		this.directBusRoute = directBusRoute;
	}

	public int getDepartureStationId() {
		return departureStationId;
	}

	public void setDepartureStationId(int departureStationId) {
		this.departureStationId = departureStationId;
	}

	public int getArrivalStationId() {
		return arrivalStationId;
	}

	public void setArrivalStationId(int arrivalStationId) {
		this.arrivalStationId = arrivalStationId;
	}

	public boolean isDirectBusRoute() {
		return directBusRoute;
	}

	public void setDirectBusRoute(boolean directBusRoute) {
		this.directBusRoute = directBusRoute;
	}
}
