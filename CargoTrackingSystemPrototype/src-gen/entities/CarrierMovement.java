package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class CarrierMovement implements Serializable {
	
	/* all primary attributes */
	private LocalDate DepartureTime;
	private LocalDate ArrivalTime;
	
	/* all references */
	private Location DepartureLocation; 
	private Location ArrivalLocation; 
	private Voyage Voyage; 
	
	/* all get and set functions */
	public LocalDate getDepartureTime() {
		return DepartureTime;
	}	
	
	public void setDepartureTime(LocalDate departuretime) {
		this.DepartureTime = departuretime;
	}
	public LocalDate getArrivalTime() {
		return ArrivalTime;
	}	
	
	public void setArrivalTime(LocalDate arrivaltime) {
		this.ArrivalTime = arrivaltime;
	}
	
	/* all functions for reference*/
	public Location getDepartureLocation() {
		return DepartureLocation;
	}	
	
	public void setDepartureLocation(Location location) {
		this.DepartureLocation = location;
	}			
	public Location getArrivalLocation() {
		return ArrivalLocation;
	}	
	
	public void setArrivalLocation(Location location) {
		this.ArrivalLocation = location;
	}			
	public Voyage getVoyage() {
		return Voyage;
	}	
	
	public void setVoyage(Voyage voyage) {
		this.Voyage = voyage;
	}			
	


}
