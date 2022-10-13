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

public class CreateLocationServiceImpl implements CreateLocationService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public CreateLocationServiceImpl() {
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
	public boolean inputLocationInfo(String locationCode, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(location) == true) 
		{ 
			/* Logic here */
			Location location = null;
			location = (Location) EntityManager.createObject("Location");
			location.setUnLocode(locationCode);
			location.setName(name);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			location.getUnLocode() == locationCode
			 && 
			location.getName() == name
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
		//string parameters: [locationCode, name] 
		//all relevant vars : location
		//all relevant entities : Location
	}  
	
	static {opINVRelatedEntity.put("inputLocationInfo", Arrays.asList("Location"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
