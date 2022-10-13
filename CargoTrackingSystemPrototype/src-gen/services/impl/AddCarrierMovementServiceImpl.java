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

public class AddCarrierMovementServiceImpl implements AddCarrierMovementService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public AddCarrierMovementServiceImpl() {
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
	public boolean addCarrierMovement(String departure, String arrival, LocalDate departureTime, LocalDate arrivalTime, String voyageNum) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get dlocation
		Location dlocation = null;
		//no nested iterator --  iterator: any previous:any
		for (Location l : (List<Location>)EntityManager.getAllInstancesOf("Location"))
		{
			if (l.getUnLocode().equals(departure))
			{
				dlocation = l;
				break;
			}
				
			
		}
		//Get alocation
		Location alocation = null;
		//no nested iterator --  iterator: any previous:any
		for (Location l : (List<Location>)EntityManager.getAllInstancesOf("Location"))
		{
			if (l.getUnLocode().equals(arrival))
			{
				alocation = l;
				break;
			}
				
			
		}
		//Get voyage
		Voyage voyage = null;
		//no nested iterator --  iterator: any previous:any
		for (Voyage v : (List<Voyage>)EntityManager.getAllInstancesOf("Voyage"))
		{
			if (v.getVoyageNumber().equals(voyageNum))
			{
				voyage = v;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(dlocation) == false && StandardOPs.oclIsundefined(alocation) == false && StandardOPs.oclIsundefined(voyage) == false) 
		{ 
			/* Logic here */
			CarrierMovement cm = null;
			cm = (CarrierMovement) EntityManager.createObject("CarrierMovement");
			cm.setDepartureLocation(dlocation);
			cm.setArrivalLocation(alocation);
			cm.setDepartureTime(departureTime);
			cm.setArrivalTime(arrivalTime);
			cm.setVoyage(voyage);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			cm.getDepartureLocation() == dlocation
			 && 
			cm.getArrivalLocation() == alocation
			 && 
			cm.getDepartureTime() == departureTime
			 && 
			cm.getArrivalTime() == arrivalTime
			 && 
			cm.getVoyage() == voyage
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
		//string parameters: [departure, arrival, voyageNum] 
		//all relevant vars : cm
		//all relevant entities : CarrierMovement
	}  
	
	static {opINVRelatedEntity.put("addCarrierMovement", Arrays.asList("CarrierMovement"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
