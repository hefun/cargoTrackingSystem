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

public class ViewTrackingServiceImpl implements ViewTrackingService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ViewTrackingServiceImpl() {
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
	public GetCargoInfoResult getCargoInfo(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
		//Get events
		List<HandlingEvent> events = new LinkedList<>();
		//no nested iterator --  iterator: select previous:select
		for (HandlingEvent handlingEvent : (List<HandlingEvent>)EntityManager.getAllInstancesOf("HandlingEvent"))
		{
			if (handlingEvent.getHandled() == c)
			{
				events.add(handlingEvent);
			}
				
			
		}
		//Get voyages
		List<Voyage> voyages = new LinkedList<>();
		//no nested iterator --  iterator: collect previous:collect
		for (HandlingEvent h : events)
		{
			voyages.add(h.getVoyage());
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(c) == false) 
		{ 
			/* Logic here */
			GetCargoInfoResult res = null;
			res = (GetCargoInfoResult) EntityManager.createObject("GetCargoInfoResult");
			EntityManager.addObjects(events);
			EntityManager.addObjects(voyages);
			res.setDelivery(c.getDelivery());
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			StandardOPs.includesAll(res.getEvents(), events)
			 && 
			StandardOPs.includesAll(res.getVoyages(), voyages)
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
		//all relevant vars : res voyages events
		//all relevant entities : GetCargoInfoResult List<Voyage> List<HandlingEvent>
	}  
	
	static {opINVRelatedEntity.put("getCargoInfo", Arrays.asList("GetCargoInfoResult","List<Voyage>","List<HandlingEvent>"));}
	 
	
	
	
	/* temp property for controller */
	private String CurrentCargoInfo;
			
	/* all get and set functions for temp property*/
	public String getCurrentCargoInfo() {
		return CurrentCargoInfo;
	}	
	
	public void setCurrentCargoInfo(String currentcargoinfo) {
		this.CurrentCargoInfo = currentcargoinfo;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
