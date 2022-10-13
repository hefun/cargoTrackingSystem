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

public class CargoTrackingSystemSystemImpl implements CargoTrackingSystemSystem, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public CargoTrackingSystemSystemImpl() {
		services = new ThirdPartyServicesImpl();
	}

	public void refresh() {
		ViewTrackingService viewtrackingservice_service = (ViewTrackingService) ServiceManager
				.getAllInstancesOf("ViewTrackingService").get(0);
		CreateLocationService createlocationservice_service = (CreateLocationService) ServiceManager
				.getAllInstancesOf("CreateLocationService").get(0);
		CreateVoyageService createvoyageservice_service = (CreateVoyageService) ServiceManager
				.getAllInstancesOf("CreateVoyageService").get(0);
		ViewCargosService viewcargosservice_service = (ViewCargosService) ServiceManager
				.getAllInstancesOf("ViewCargosService").get(0);
		BookCargoService bookcargoservice_service = (BookCargoService) ServiceManager
				.getAllInstancesOf("BookCargoService").get(0);
		ChangeCargoDestinationService changecargodestinationservice_service = (ChangeCargoDestinationService) ServiceManager
				.getAllInstancesOf("ChangeCargoDestinationService").get(0);
		RouteCargoService routecargoservice_service = (RouteCargoService) ServiceManager
				.getAllInstancesOf("RouteCargoService").get(0);
		AddCarrierMovementService addcarriermovementservice_service = (AddCarrierMovementService) ServiceManager
				.getAllInstancesOf("AddCarrierMovementService").get(0);
		HandleCargoEventService handlecargoeventservice_service = (HandleCargoEventService) ServiceManager
				.getAllInstancesOf("HandleCargoEventService").get(0);
	}			
	
	/* Generate buiness logic according to functional requirement */
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
