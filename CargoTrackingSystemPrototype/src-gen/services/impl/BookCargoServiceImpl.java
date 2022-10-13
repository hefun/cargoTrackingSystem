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

public class BookCargoServiceImpl implements BookCargoService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public BookCargoServiceImpl() {
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
	public List<Location> getAllLocations() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get locations
		List<Location> locations = new LinkedList<>();
		(List<Location>)EntityManager.getAllInstancesOf("Location")
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(locations) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return locations;
		}
		else
		{
			throw new PreconditionException();
		}
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean createNewCargo(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
		if (StandardOPs.oclIsundefined(c) == true) 
		{ 
			/* Logic here */
			Cargo c = null;
			c = (Cargo) EntityManager.createObject("Cargo");
			CurrentCargo = c;
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			CurrentCargo == c
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
		//all relevant vars : c
		//all relevant entities : Cargo
	}  
	
	static {opINVRelatedEntity.put("createNewCargo", Arrays.asList("Cargo"));}
	 
	@SuppressWarnings("unchecked")
	public boolean createRouteSpecification(String originCode, String destinationCode, LocalDate arrivalDeadline) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get origin
		Location origin = null;
		//no nested iterator --  iterator: any previous:any
		for (Location l : (List<Location>)EntityManager.getAllInstancesOf("Location"))
		{
			if (l.getUnLocode().equals(originCode))
			{
				origin = l;
				break;
			}
				
			
		}
		//Get destination
		Location destination = null;
		//no nested iterator --  iterator: any previous:any
		for (Location l : (List<Location>)EntityManager.getAllInstancesOf("Location"))
		{
			if (l.getUnLocode().equals(destinationCode))
			{
				destination = l;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(CurrentCargo) == false && StandardOPs.oclIsundefined(origin) == false && StandardOPs.oclIsundefined(destination) == false) 
		{ 
			/* Logic here */
			RouteSpecification rs = null;
			rs = (RouteSpecification) EntityManager.createObject("RouteSpecification");
			rs.setOrigin(origin);
			rs.setDestination(destination);
			rs.setArrivalDeadline(arrivalDeadline);
			CurrentCargo.setRouteSpecification(rs);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			rs.getOrigin() == origin
			 && 
			rs.getDestination() == destination
			 && 
			rs.getArrivalDeadline() == arrivalDeadline
			 && 
			CurrentCargo.getRouteSpecification() == rs
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
		//string parameters: [originCode, destinationCode] 
		//all relevant vars : rs CurrentCargo
		//all relevant entities : RouteSpecification 
	}  
	
	static {opINVRelatedEntity.put("createRouteSpecification", Arrays.asList("RouteSpecification",""));}
	 
	
	
	
	/* temp property for controller */
	private Cargo CurrentCargo;
			
	/* all get and set functions for temp property*/
	public Cargo getCurrentCargo() {
		return CurrentCargo;
	}	
	
	public void setCurrentCargo(Cargo currentcargo) {
		this.CurrentCargo = currentcargo;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
