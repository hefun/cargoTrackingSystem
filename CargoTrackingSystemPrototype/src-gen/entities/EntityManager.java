package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Map;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class EntityManager {

	private static Map<String, List> AllInstance = new HashMap<String, List>();
	
	private static List<Cargo> CargoInstances = new LinkedList<Cargo>();
	private static List<Location> LocationInstances = new LinkedList<Location>();
	private static List<HandlingEvent> HandlingEventInstances = new LinkedList<HandlingEvent>();
	private static List<CarrierMovement> CarrierMovementInstances = new LinkedList<CarrierMovement>();
	private static List<User> UserInstances = new LinkedList<User>();
	private static List<RouteSpecification> RouteSpecificationInstances = new LinkedList<RouteSpecification>();
	private static List<Itinerary> ItineraryInstances = new LinkedList<Itinerary>();
	private static List<Voyage> VoyageInstances = new LinkedList<Voyage>();
	private static List<Leg> LegInstances = new LinkedList<Leg>();
	private static List<Delivery> DeliveryInstances = new LinkedList<Delivery>();
	private static List<GetCargoInfoResult> GetCargoInfoResultInstances = new LinkedList<GetCargoInfoResult>();
	private static List<GetCertainCargoRouteResult> GetCertainCargoRouteResultInstances = new LinkedList<GetCertainCargoRouteResult>();

	
	/* Put instances list into Map */
	static {
		AllInstance.put("Cargo", CargoInstances);
		AllInstance.put("Location", LocationInstances);
		AllInstance.put("HandlingEvent", HandlingEventInstances);
		AllInstance.put("CarrierMovement", CarrierMovementInstances);
		AllInstance.put("User", UserInstances);
		AllInstance.put("RouteSpecification", RouteSpecificationInstances);
		AllInstance.put("Itinerary", ItineraryInstances);
		AllInstance.put("Voyage", VoyageInstances);
		AllInstance.put("Leg", LegInstances);
		AllInstance.put("Delivery", DeliveryInstances);
		AllInstance.put("GetCargoInfoResult", GetCargoInfoResultInstances);
		AllInstance.put("GetCertainCargoRouteResult", GetCertainCargoRouteResultInstances);
	} 
		
	/* Save State */
	public static void save(File file) {
		
		try {
			
			ObjectOutputStream stateSave = new ObjectOutputStream(new FileOutputStream(file));
			
			stateSave.writeObject(CargoInstances);
			stateSave.writeObject(LocationInstances);
			stateSave.writeObject(HandlingEventInstances);
			stateSave.writeObject(CarrierMovementInstances);
			stateSave.writeObject(UserInstances);
			stateSave.writeObject(RouteSpecificationInstances);
			stateSave.writeObject(ItineraryInstances);
			stateSave.writeObject(VoyageInstances);
			stateSave.writeObject(LegInstances);
			stateSave.writeObject(DeliveryInstances);
			stateSave.writeObject(GetCargoInfoResultInstances);
			stateSave.writeObject(GetCertainCargoRouteResultInstances);
			
			stateSave.close();
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* Load State */
	public static void load(File file) {
		
		try {
			
			ObjectInputStream stateLoad = new ObjectInputStream(new FileInputStream(file));
			
			try {
				
				CargoInstances =  (List<Cargo>) stateLoad.readObject();
				AllInstance.put("Cargo", CargoInstances);
				LocationInstances =  (List<Location>) stateLoad.readObject();
				AllInstance.put("Location", LocationInstances);
				HandlingEventInstances =  (List<HandlingEvent>) stateLoad.readObject();
				AllInstance.put("HandlingEvent", HandlingEventInstances);
				CarrierMovementInstances =  (List<CarrierMovement>) stateLoad.readObject();
				AllInstance.put("CarrierMovement", CarrierMovementInstances);
				UserInstances =  (List<User>) stateLoad.readObject();
				AllInstance.put("User", UserInstances);
				RouteSpecificationInstances =  (List<RouteSpecification>) stateLoad.readObject();
				AllInstance.put("RouteSpecification", RouteSpecificationInstances);
				ItineraryInstances =  (List<Itinerary>) stateLoad.readObject();
				AllInstance.put("Itinerary", ItineraryInstances);
				VoyageInstances =  (List<Voyage>) stateLoad.readObject();
				AllInstance.put("Voyage", VoyageInstances);
				LegInstances =  (List<Leg>) stateLoad.readObject();
				AllInstance.put("Leg", LegInstances);
				DeliveryInstances =  (List<Delivery>) stateLoad.readObject();
				AllInstance.put("Delivery", DeliveryInstances);
				GetCargoInfoResultInstances =  (List<GetCargoInfoResult>) stateLoad.readObject();
				AllInstance.put("GetCargoInfoResult", GetCargoInfoResultInstances);
				GetCertainCargoRouteResultInstances =  (List<GetCertainCargoRouteResult>) stateLoad.readObject();
				AllInstance.put("GetCertainCargoRouteResult", GetCertainCargoRouteResultInstances);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/* create object */  
	public static Object createObject(String Classifer) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method createObjectMethod = c.getDeclaredMethod("create" + Classifer + "Object");
			return createObjectMethod.invoke(c);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* add object */  
	public static Object addObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectMethod = c.getDeclaredMethod("add" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) addObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	
	/* add objects */  
	public static Object addObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectsMethod = c.getDeclaredMethod("add" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) addObjectsMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* Release object */
	public static boolean deleteObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) deleteObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/* Release objects */
	public static boolean deleteObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) deleteObjectMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}		 	
	
	 /* Get all objects belongs to same class */
	public static List getAllInstancesOf(String ClassName) {
			 return AllInstance.get(ClassName);
	}	

   /* Sub-create object */
	public static Cargo createCargoObject() {
		Cargo o = new Cargo();
		return o;
	}
	
	public static boolean addCargoObject(Cargo o) {
		return CargoInstances.add(o);
	}
	
	public static boolean addCargoObjects(List<Cargo> os) {
		return CargoInstances.addAll(os);
	}
	
	public static boolean deleteCargoObject(Cargo o) {
		return CargoInstances.remove(o);
	}
	
	public static boolean deleteCargoObjects(List<Cargo> os) {
		return CargoInstances.removeAll(os);
	}
	public static Location createLocationObject() {
		Location o = new Location();
		return o;
	}
	
	public static boolean addLocationObject(Location o) {
		return LocationInstances.add(o);
	}
	
	public static boolean addLocationObjects(List<Location> os) {
		return LocationInstances.addAll(os);
	}
	
	public static boolean deleteLocationObject(Location o) {
		return LocationInstances.remove(o);
	}
	
	public static boolean deleteLocationObjects(List<Location> os) {
		return LocationInstances.removeAll(os);
	}
	public static HandlingEvent createHandlingEventObject() {
		HandlingEvent o = new HandlingEvent();
		return o;
	}
	
	public static boolean addHandlingEventObject(HandlingEvent o) {
		return HandlingEventInstances.add(o);
	}
	
	public static boolean addHandlingEventObjects(List<HandlingEvent> os) {
		return HandlingEventInstances.addAll(os);
	}
	
	public static boolean deleteHandlingEventObject(HandlingEvent o) {
		return HandlingEventInstances.remove(o);
	}
	
	public static boolean deleteHandlingEventObjects(List<HandlingEvent> os) {
		return HandlingEventInstances.removeAll(os);
	}
	public static CarrierMovement createCarrierMovementObject() {
		CarrierMovement o = new CarrierMovement();
		return o;
	}
	
	public static boolean addCarrierMovementObject(CarrierMovement o) {
		return CarrierMovementInstances.add(o);
	}
	
	public static boolean addCarrierMovementObjects(List<CarrierMovement> os) {
		return CarrierMovementInstances.addAll(os);
	}
	
	public static boolean deleteCarrierMovementObject(CarrierMovement o) {
		return CarrierMovementInstances.remove(o);
	}
	
	public static boolean deleteCarrierMovementObjects(List<CarrierMovement> os) {
		return CarrierMovementInstances.removeAll(os);
	}
	public static User createUserObject() {
		User o = new User();
		return o;
	}
	
	public static boolean addUserObject(User o) {
		return UserInstances.add(o);
	}
	
	public static boolean addUserObjects(List<User> os) {
		return UserInstances.addAll(os);
	}
	
	public static boolean deleteUserObject(User o) {
		return UserInstances.remove(o);
	}
	
	public static boolean deleteUserObjects(List<User> os) {
		return UserInstances.removeAll(os);
	}
	public static RouteSpecification createRouteSpecificationObject() {
		RouteSpecification o = new RouteSpecification();
		return o;
	}
	
	public static boolean addRouteSpecificationObject(RouteSpecification o) {
		return RouteSpecificationInstances.add(o);
	}
	
	public static boolean addRouteSpecificationObjects(List<RouteSpecification> os) {
		return RouteSpecificationInstances.addAll(os);
	}
	
	public static boolean deleteRouteSpecificationObject(RouteSpecification o) {
		return RouteSpecificationInstances.remove(o);
	}
	
	public static boolean deleteRouteSpecificationObjects(List<RouteSpecification> os) {
		return RouteSpecificationInstances.removeAll(os);
	}
	public static Itinerary createItineraryObject() {
		Itinerary o = new Itinerary();
		return o;
	}
	
	public static boolean addItineraryObject(Itinerary o) {
		return ItineraryInstances.add(o);
	}
	
	public static boolean addItineraryObjects(List<Itinerary> os) {
		return ItineraryInstances.addAll(os);
	}
	
	public static boolean deleteItineraryObject(Itinerary o) {
		return ItineraryInstances.remove(o);
	}
	
	public static boolean deleteItineraryObjects(List<Itinerary> os) {
		return ItineraryInstances.removeAll(os);
	}
	public static Voyage createVoyageObject() {
		Voyage o = new Voyage();
		return o;
	}
	
	public static boolean addVoyageObject(Voyage o) {
		return VoyageInstances.add(o);
	}
	
	public static boolean addVoyageObjects(List<Voyage> os) {
		return VoyageInstances.addAll(os);
	}
	
	public static boolean deleteVoyageObject(Voyage o) {
		return VoyageInstances.remove(o);
	}
	
	public static boolean deleteVoyageObjects(List<Voyage> os) {
		return VoyageInstances.removeAll(os);
	}
	public static Leg createLegObject() {
		Leg o = new Leg();
		return o;
	}
	
	public static boolean addLegObject(Leg o) {
		return LegInstances.add(o);
	}
	
	public static boolean addLegObjects(List<Leg> os) {
		return LegInstances.addAll(os);
	}
	
	public static boolean deleteLegObject(Leg o) {
		return LegInstances.remove(o);
	}
	
	public static boolean deleteLegObjects(List<Leg> os) {
		return LegInstances.removeAll(os);
	}
	public static Delivery createDeliveryObject() {
		Delivery o = new Delivery();
		return o;
	}
	
	public static boolean addDeliveryObject(Delivery o) {
		return DeliveryInstances.add(o);
	}
	
	public static boolean addDeliveryObjects(List<Delivery> os) {
		return DeliveryInstances.addAll(os);
	}
	
	public static boolean deleteDeliveryObject(Delivery o) {
		return DeliveryInstances.remove(o);
	}
	
	public static boolean deleteDeliveryObjects(List<Delivery> os) {
		return DeliveryInstances.removeAll(os);
	}
	public static GetCargoInfoResult createGetCargoInfoResultObject() {
		GetCargoInfoResult o = new GetCargoInfoResult();
		return o;
	}
	
	public static boolean addGetCargoInfoResultObject(GetCargoInfoResult o) {
		return GetCargoInfoResultInstances.add(o);
	}
	
	public static boolean addGetCargoInfoResultObjects(List<GetCargoInfoResult> os) {
		return GetCargoInfoResultInstances.addAll(os);
	}
	
	public static boolean deleteGetCargoInfoResultObject(GetCargoInfoResult o) {
		return GetCargoInfoResultInstances.remove(o);
	}
	
	public static boolean deleteGetCargoInfoResultObjects(List<GetCargoInfoResult> os) {
		return GetCargoInfoResultInstances.removeAll(os);
	}
	public static GetCertainCargoRouteResult createGetCertainCargoRouteResultObject() {
		GetCertainCargoRouteResult o = new GetCertainCargoRouteResult();
		return o;
	}
	
	public static boolean addGetCertainCargoRouteResultObject(GetCertainCargoRouteResult o) {
		return GetCertainCargoRouteResultInstances.add(o);
	}
	
	public static boolean addGetCertainCargoRouteResultObjects(List<GetCertainCargoRouteResult> os) {
		return GetCertainCargoRouteResultInstances.addAll(os);
	}
	
	public static boolean deleteGetCertainCargoRouteResultObject(GetCertainCargoRouteResult o) {
		return GetCertainCargoRouteResultInstances.remove(o);
	}
	
	public static boolean deleteGetCertainCargoRouteResultObjects(List<GetCertainCargoRouteResult> os) {
		return GetCertainCargoRouteResultInstances.removeAll(os);
	}
  
}

