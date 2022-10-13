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

public class ViewCargosServiceImpl implements ViewCargosService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ViewCargosServiceImpl() {
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
	public List<RouteSpecification> getAllCargoRoute() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get routeSpecifications
		List<RouteSpecification> routeSpecifications = new LinkedList<>();
		//no nested iterator --  iterator: collect previous:collect
		for (Cargo c : (List<Cargo>)EntityManager.getAllInstancesOf("Cargo"))
		{
			routeSpecifications.add(c.getRouteSpecification());
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(routeSpecifications) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return routeSpecifications;
		}
		else
		{
			throw new PreconditionException();
		}
	}  
	
	 
	@SuppressWarnings("unchecked")
	public GetCertainCargoRouteResult getCertainCargoRoute(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(c) == false) 
		{ 
			/* Logic here */
			GetCertainCargoRouteResult res = null;
			res = (GetCertainCargoRouteResult) EntityManager.createObject("GetCertainCargoRouteResult");
			res.setCargo(c);
			res.setItinerary(c.getItinerary());
			res.setRouteSpecification(c.getRouteSpecification());
			res.setDelivery(c.getDelivery());
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			res.getCargo() == c
			 && 
			res.getItinerary() == c.getItinerary()
			 && 
			res.getRouteSpecification() == c.getRouteSpecification()
			 && 
			res.getDelivery() == c.getDelivery()
			 && 
			true)) {
				throw new PostconditionException();
			}
			
			refresh(); return res;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [trackingId] 
		//all relevant vars : res
		//all relevant entities : GetCertainCargoRouteResult
	}  
	
	static {opINVRelatedEntity.put("getCertainCargoRoute", Arrays.asList("GetCertainCargoRouteResult"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
