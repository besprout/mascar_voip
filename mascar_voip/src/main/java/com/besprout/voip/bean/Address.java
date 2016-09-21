package com.besprout.voip.bean;

public class Address {
	
	private String shortAddress = null;
	private double lat = 0;
	private double lng = 0;
	private String subpremise = null;
	private String streetNumber = null;
	private String route = null;
	private String neighborhood = null;
	
	public String getShortAddress() {
		return shortAddress;
	}
	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}
	public String getLongAddress() {
		StringBuffer sb = new StringBuffer();
		sb.append(streetNumber).append(" ");
		sb.append(route).append(" ");
		if(subpremise != null){
			sb.append("room ").append(subpremise).append(" ");
		}
		if(neighborhood != null){
			sb.append(neighborhood);
		}
		
		return sb.toString();
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getSubpremise() {
		return subpremise;
	}
	public void setSubpremise(String subpremise) {
		this.subpremise = subpremise;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
}
