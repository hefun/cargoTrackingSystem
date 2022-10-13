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

public class RouteCargoServiceImpl implements RouteCargoService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public RouteCargoServiceImpl() {
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
	public boolean routeCargo(String trackingId, String locationCode) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
		//Get location
		Location location = null;
		//no nested iterator --  iterator: any previous:any
		for (Location l : (List<Location>)EntityManager.getAllInstancesOf("Location"))
		{
			if (l.getUnLocode().equals(locationCode))
			{
				location = l;
				break;
			}
				
			
		}
		//Get itinerary
		Itinerary itinerary = null;
		c.getItinerary()
		//Get legs
		List<Leg> legs = new LinkedList<>();
		legs = itinerary.getStep();
		//Get cm
		 cm = null;
		//no nested iterator --  iterator: any previous:any
		for (CarrierMovement cm : (List<CarrierMovement>)EntityManager.getAllInstancesOf("CarrierMovement"))
		{
			if (cm.getArrivalLocation() == location)
			{
				cm = cm;
				break;
			}
				
			
		}
		//Get voyage
		 voyage = null;
		cm.getVoyage()
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(c) == false && StandardOPs.oclIsundefined(location) == false && StandardOPs.oclIsundefined(cm) == false && StandardOPs.oclIsundefined(voyage) == false) 
		{ 
			/* Logic here */
			RouteSpecification route = null;
			route = (RouteSpecification) EntityManager.createObject("RouteSpecification");
			route.setDestination(location);
			itinerary.setSpecification(route);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			route.getDestination() == location
			 && 
			itinerary.getSpecification() == route)) {
				throw new PostconditionException();
			}
			
		
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [trackingId, locationCode] 
		//all relevant vars : route itinerary
		//all relevant entities : RouteSpecification Itinerary
	}  
	
	static {opINVRelatedEntity.put("routeCargo", Arrays.asList("RouteSpecification","Itinerary"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
