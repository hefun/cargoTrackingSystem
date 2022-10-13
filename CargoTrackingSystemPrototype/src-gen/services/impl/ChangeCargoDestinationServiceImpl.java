package services.impl;

import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;

public class ChangeCargoDestinationServiceImpl implements ChangeCargoDestinationService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ChangeCargoDestinationServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
			
	/* all get and set functions for temp property*/
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	public void refresh() {
		CargoTrackingSystemSystem cargotrackingsystemsystem_service = (CargoTrackingSystemSystem) ServiceManager.getAllInstancesOf("CargoTrackingSystemSystem").get(0);
	}
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public boolean selectLocation(String locationCode) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get location
		Location location = null;
		//no nested iterator --  iterator: any previous:any
		for (Location l : (List<Location>)EntityManager.getAllInstancesOf("Location"))
		{
			if (l.getUnlocode().equals(locationCode))
			{
				location = l;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(location) == false) 
		{ 
			/* Logic here */
			CurrentLocation = location;
			
			
			refresh();
			// post-condition checking
			if (!(CurrentLocation == location
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [locationCode] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean changeDestination(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get c
		Cargo c = null;
		//no nested iterator --  iterator: any previous:any
		for (Cargo c : (List<Cargo>)EntityManager.getAllInstancesOf("Cargo"))
		{
			if (c.getTrackingId().equals(trackingId))
			{
				c = c;
				break;
			}
				
			
		}
		//Get route
		RouteSpecification route = null;
		c.getRouteSpecification()
		//Get delivery
		Delivery delivery = null;
		c.getDelivery()
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(c) == false && StandardOPs.oclIsundefined(route) == false && StandardOPs.oclIsundefined(delivery) == false && StandardOPs.oclIsundefined(CurrentLocation) == false) 
		{ 
			/* Logic here */
			route.setDestination(CurrentLocation);
			delivery.setTransportStatus(MISROUTED);
			
			
			refresh();
			// post-condition checking
			if (!(route.getDestination() == CurrentLocation
			 && 
			delivery.getTransportStatus() == MISROUTED
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [trackingId] 
		//all relevant vars : delivery route
		//all relevant entities : Delivery RouteSpecification
	}  
	
	static {opINVRelatedEntity.put("changeDestination", Arrays.asList("Delivery","RouteSpecification"));}
	 
	
	
	
	/* temp property for controller */
	private Location CurrentLocation;
			
	/* all get and set functions for temp property*/
	public Location getCurrentLocation() {
		return CurrentLocation;
	}	
	
	public void setCurrentLocation(Location currentlocation) {
		this.CurrentLocation = currentlocation;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
