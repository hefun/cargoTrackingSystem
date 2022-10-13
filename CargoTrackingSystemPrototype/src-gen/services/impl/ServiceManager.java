package services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import services.*;
	
public class ServiceManager {
	
	private static Map<String, List> AllServiceInstance = new HashMap<String, List>();
	
	private static List<CargoTrackingSystemSystem> CargoTrackingSystemSystemInstances = new LinkedList<CargoTrackingSystemSystem>();
	private static List<ThirdPartyServices> ThirdPartyServicesInstances = new LinkedList<ThirdPartyServices>();
	private static List<ViewTrackingService> ViewTrackingServiceInstances = new LinkedList<ViewTrackingService>();
	private static List<CreateLocationService> CreateLocationServiceInstances = new LinkedList<CreateLocationService>();
	private static List<CreateVoyageService> CreateVoyageServiceInstances = new LinkedList<CreateVoyageService>();
	private static List<ViewCargosService> ViewCargosServiceInstances = new LinkedList<ViewCargosService>();
	private static List<BookCargoService> BookCargoServiceInstances = new LinkedList<BookCargoService>();
	private static List<ChangeCargoDestinationService> ChangeCargoDestinationServiceInstances = new LinkedList<ChangeCargoDestinationService>();
	private static List<RouteCargoService> RouteCargoServiceInstances = new LinkedList<RouteCargoService>();
	private static List<AddCarrierMovementService> AddCarrierMovementServiceInstances = new LinkedList<AddCarrierMovementService>();
	private static List<HandleCargoEventService> HandleCargoEventServiceInstances = new LinkedList<HandleCargoEventService>();
	
	static {
		AllServiceInstance.put("CargoTrackingSystemSystem", CargoTrackingSystemSystemInstances);
		AllServiceInstance.put("ThirdPartyServices", ThirdPartyServicesInstances);
		AllServiceInstance.put("ViewTrackingService", ViewTrackingServiceInstances);
		AllServiceInstance.put("CreateLocationService", CreateLocationServiceInstances);
		AllServiceInstance.put("CreateVoyageService", CreateVoyageServiceInstances);
		AllServiceInstance.put("ViewCargosService", ViewCargosServiceInstances);
		AllServiceInstance.put("BookCargoService", BookCargoServiceInstances);
		AllServiceInstance.put("ChangeCargoDestinationService", ChangeCargoDestinationServiceInstances);
		AllServiceInstance.put("RouteCargoService", RouteCargoServiceInstances);
		AllServiceInstance.put("AddCarrierMovementService", AddCarrierMovementServiceInstances);
		AllServiceInstance.put("HandleCargoEventService", HandleCargoEventServiceInstances);
	} 
	
	public static List getAllInstancesOf(String ClassName) {
			 return AllServiceInstance.get(ClassName);
	}	
	
	public static CargoTrackingSystemSystem createCargoTrackingSystemSystem() {
		CargoTrackingSystemSystem s = new CargoTrackingSystemSystemImpl();
		CargoTrackingSystemSystemInstances.add(s);
		return s;
	}
	public static ThirdPartyServices createThirdPartyServices() {
		ThirdPartyServices s = new ThirdPartyServicesImpl();
		ThirdPartyServicesInstances.add(s);
		return s;
	}
	public static ViewTrackingService createViewTrackingService() {
		ViewTrackingService s = new ViewTrackingServiceImpl();
		ViewTrackingServiceInstances.add(s);
		return s;
	}
	public static CreateLocationService createCreateLocationService() {
		CreateLocationService s = new CreateLocationServiceImpl();
		CreateLocationServiceInstances.add(s);
		return s;
	}
	public static CreateVoyageService createCreateVoyageService() {
		CreateVoyageService s = new CreateVoyageServiceImpl();
		CreateVoyageServiceInstances.add(s);
		return s;
	}
	public static ViewCargosService createViewCargosService() {
		ViewCargosService s = new ViewCargosServiceImpl();
		ViewCargosServiceInstances.add(s);
		return s;
	}
	public static BookCargoService createBookCargoService() {
		BookCargoService s = new BookCargoServiceImpl();
		BookCargoServiceInstances.add(s);
		return s;
	}
	public static ChangeCargoDestinationService createChangeCargoDestinationService() {
		ChangeCargoDestinationService s = new ChangeCargoDestinationServiceImpl();
		ChangeCargoDestinationServiceInstances.add(s);
		return s;
	}
	public static RouteCargoService createRouteCargoService() {
		RouteCargoService s = new RouteCargoServiceImpl();
		RouteCargoServiceInstances.add(s);
		return s;
	}
	public static AddCarrierMovementService createAddCarrierMovementService() {
		AddCarrierMovementService s = new AddCarrierMovementServiceImpl();
		AddCarrierMovementServiceInstances.add(s);
		return s;
	}
	public static HandleCargoEventService createHandleCargoEventService() {
		HandleCargoEventService s = new HandleCargoEventServiceImpl();
		HandleCargoEventServiceInstances.add(s);
		return s;
	}
}	
