package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class HandlingEvent implements Serializable {
	
	/* all primary attributes */
	private OperationType OperationType;
	private LocalDate CompletionTime;
	private LocalDate RegistrationTime;
	
	/* all references */
	private Cargo Handled; 
	private Location Location; 
	private Voyage Voyage; 
	
	/* all get and set functions */
	public OperationType getOperationType() {
		return OperationType;
	}	
	
	public void setOperationType(OperationType operationtype) {
		this.OperationType = operationtype;
	}
	public LocalDate getCompletionTime() {
		return CompletionTime;
	}	
	
	public void setCompletionTime(LocalDate completiontime) {
		this.CompletionTime = completiontime;
	}
	public LocalDate getRegistrationTime() {
		return RegistrationTime;
	}	
	
	public void setRegistrationTime(LocalDate registrationtime) {
		this.RegistrationTime = registrationtime;
	}
	
	/* all functions for reference*/
	public Cargo getHandled() {
		return Handled;
	}	
	
	public void setHandled(Cargo cargo) {
		this.Handled = cargo;
	}			
	public Location getLocation() {
		return Location;
	}	
	
	public void setLocation(Location location) {
		this.Location = location;
	}			
	public Voyage getVoyage() {
		return Voyage;
	}	
	
	public void setVoyage(Voyage voyage) {
		this.Voyage = voyage;
	}			
	


}
