package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class GetCargoInfoResult implements Serializable {
	
	/* all primary attributes */
	
	/* all references */
	private Delivery Delivery; 
	private List<HandlingEvent> Events = new LinkedList<HandlingEvent>(); 
	private List<Voyage> Voyages = new LinkedList<Voyage>(); 
	
	/* all get and set functions */
	
	/* all functions for reference*/
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
	public List<Voyage> getVoyages() {
		return Voyages;
	}	
	
	public void addVoyages(Voyage voyage) {
		this.Voyages.add(voyage);
	}
	
	public void deleteVoyages(Voyage voyage) {
		this.Voyages.remove(voyage);
	}
	


}
