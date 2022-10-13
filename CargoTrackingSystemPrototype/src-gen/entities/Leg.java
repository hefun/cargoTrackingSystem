package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Leg implements Serializable {
	
	/* all primary attributes */
	private LocalDate LoadTime;
	private LocalDate UnloadTime;
	
	/* all references */
	private Location LoadLocation; 
	private Location UnloadLocation; 
	
	/* all get and set functions */
	public LocalDate getLoadTime() {
		return LoadTime;
	}	
	
	public void setLoadTime(LocalDate loadtime) {
		this.LoadTime = loadtime;
	}
	public LocalDate getUnloadTime() {
		return UnloadTime;
	}	
	
	public void setUnloadTime(LocalDate unloadtime) {
		this.UnloadTime = unloadtime;
	}
	
	/* all functions for reference*/
	public Location getLoadLocation() {
		return LoadLocation;
	}	
	
	public void setLoadLocation(Location location) {
		this.LoadLocation = location;
	}			
	public Location getUnloadLocation() {
		return UnloadLocation;
	}	
	
	public void setUnloadLocation(Location location) {
		this.UnloadLocation = location;
	}			
	


}
