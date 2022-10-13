package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class GetCertainCargoRouteResult implements Serializable {
	
	/* all primary attributes */
	
	/* all references */
	private Cargo Cargo; 
	private Delivery Delivery; 
	private RouteSpecification RouteSpecification; 
	private Itinerary Itinerary; 
	
	/* all get and set functions */
	
	/* all functions for reference*/
	public Cargo getCargo() {
		return Cargo;
	}	
	
	public void setCargo(Cargo cargo) {
		this.Cargo = cargo;
	}			
	public Delivery getDelivery() {
		return Delivery;
	}	
	
	public void setDelivery(Delivery delivery) {
		this.Delivery = delivery;
	}			
	public RouteSpecification getRouteSpecification() {
		return RouteSpecification;
	}	
	
	public void setRouteSpecification(RouteSpecification routespecification) {
		this.RouteSpecification = routespecification;
	}			
	public Itinerary getItinerary() {
		return Itinerary;
	}	
	
	public void setItinerary(Itinerary itinerary) {
		this.Itinerary = itinerary;
	}			
	


}
