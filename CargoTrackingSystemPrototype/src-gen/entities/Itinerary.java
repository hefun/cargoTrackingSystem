package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Itinerary implements Serializable {
	
	/* all primary attributes */
	private String IeneraryNumber;
	
	/* all references */
	private List<Leg> Step = new LinkedList<Leg>(); 
	private RouteSpecification Specification; 
	
	/* all get and set functions */
	public String getIeneraryNumber() {
		return IeneraryNumber;
	}	
	
	public void setIeneraryNumber(String ienerarynumber) {
		this.IeneraryNumber = ienerarynumber;
	}
	
	/* all functions for reference*/
	public List<Leg> getStep() {
		return Step;
	}	
	
	public void addStep(Leg leg) {
		this.Step.add(leg);
	}
	
	public void deleteStep(Leg leg) {
		this.Step.remove(leg);
	}
	public RouteSpecification getSpecification() {
		return Specification;
	}	
	
	public void setSpecification(RouteSpecification routespecification) {
		this.Specification = routespecification;
	}			
	


}
