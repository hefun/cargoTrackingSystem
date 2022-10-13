package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Cargo implements Serializable {
	
	/* all primary attributes */
	private String TrackingId;
	
	/* all references */
	private List<User> Users = new LinkedList<User>(); 
	private Delivery Delivery; 
	private List<HandlingEvent> Events = new LinkedList<HandlingEvent>(); 
	private Itinerary Itinerary; 
	private RouteSpecification RouteSpecification; 
	
	/* all get and set functions */
	public String getTrackingId() {
		return TrackingId;
	}	
	
	public void setTrackingId(String trackingid) {
		this.TrackingId = trackingid;
	}
	
	/* all functions for reference*/
	public List<User> getUsers() {
		return Users;
	}	
	
	public void addUsers(User user) {
		this.Users.add(user);
	}
	
	public void deleteUsers(User user) {
		this.Users.remove(user);
	}
	public Delivery getDelivery() {
		return Delivery;
	}	
	
	public void setDelivery(Delivery delivery) {
		this.Delivery = delivery;
	}			
	public List<HandlingEvent> getEvents() {
		return Events;
	}	
	
	public void addEvents(HandlingEvent handlingevent) {
		this.Events.add(handlingevent);
	}
	
	public void deleteEvents(HandlingEvent handlingevent) {
		this.Events.remove(handlingevent);
	}
	public Itinerary getItinerary() {
		return Itinerary;
	}	
	
	public void setItinerary(Itinerary itinerary) {
		this.Itinerary = itinerary;
	}			
	public RouteSpecification getRouteSpecification() {
		return RouteSpecification;
	}	
	
	public void setRouteSpecification(RouteSpecification routespecification) {
		this.RouteSpecification = routespecification;
	}			
	


}
