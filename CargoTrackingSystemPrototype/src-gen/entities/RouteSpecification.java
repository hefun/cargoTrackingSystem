package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class RouteSpecification implements Serializable {
	
	/* all primary attributes */
	private LocalDate ArrivalDeadline;
	
	/* all references */
	private Location Origin; 
	private Location Destination; 
	
	/* all get and set functions */
	public LocalDate getArrivalDeadline() {
		return ArrivalDeadline;
	}	
	
	public void setArrivalDeadline(LocalDate arrivaldeadline) {
		this.ArrivalDeadline = arrivaldeadline;
	}
	
	/* all functions for reference*/
	public Location getOrigin() {
		return Origin;
	}	
	
	public void setOrigin(Location location) {
		this.Origin = location;
	}			
	public Location getDestination() {
		return Destination;
	}	
	
	public void setDestination(Location location) {
		this.Destination = location;
	}			
	


}
