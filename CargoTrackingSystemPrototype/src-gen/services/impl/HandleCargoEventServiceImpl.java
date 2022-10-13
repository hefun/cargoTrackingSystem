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

public class HandleCargoEventServiceImpl implements HandleCargoEventService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public HandleCargoEventServiceImpl() {
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
	public boolean createLoadEvent(LocalDate completionTime, LocalDate registrationTime, String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
			HandlingEvent he = null;
			he = (HandlingEvent) EntityManager.createObject("HandlingEvent");
			he.setOperationType(OperationType.LOAD);
			he.setCompletionTime(completionTime);
			he.setRegistrationTime(registrationTime);
			he.setHandled(c);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			he.getOperationType() == OperationType.LOAD
			 && 
			he.getCompletionTime() == completionTime
			 && 
			he.getRegistrationTime() == registrationTime
			 && 
			he.getHandled() == c
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
		//all relevant vars : he
		//all relevant entities : HandlingEvent
	}  
	
	static {opINVRelatedEntity.put("createLoadEvent", Arrays.asList("HandlingEvent"));}
	 
	@SuppressWarnings("unchecked")
	public boolean createUnloadEvent(LocalDate completionTime, LocalDate registrationTime, String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
			HandlingEvent he = null;
			he = (HandlingEvent) EntityManager.createObject("HandlingEvent");
			he.setOperationType(OperationType.UNLOAD);
			he.setCompletionTime(completionTime);
			he.setRegistrationTime(registrationTime);
			he.setHandled(c);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			he.getOperationType() == OperationType.UNLOAD
			 && 
			he.getCompletionTime() == completionTime
			 && 
			he.getRegistrationTime() == registrationTime
			 && 
			he.getHandled() == c
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
		//all relevant vars : he
		//all relevant entities : HandlingEvent
	}  
	
	static {opINVRelatedEntity.put("createUnloadEvent", Arrays.asList("HandlingEvent"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
