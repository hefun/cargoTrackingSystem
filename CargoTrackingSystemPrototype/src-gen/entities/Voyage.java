package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Voyage implements Serializable {
	
	/* all primary attributes */
	private String VoyageNumber;
	
	/* all references */
	private List<CarrierMovement> CarrierMovement = new LinkedList<CarrierMovement>(); 
	
	/* all get and set functions */
	public String getVoyageNumber() {
		return VoyageNumber;
	}	
	
	public void setVoyageNumber(String voyagenumber) {
		this.VoyageNumber = voyagenumber;
	}
	
	/* all functions for reference*/
	public List<CarrierMovement> getCarrierMovement() {
		return CarrierMovement;
	}	
	
	public void addCarrierMovement(CarrierMovement carriermovement) {
		this.CarrierMovement.add(carriermovement);
	}
	
	public void deleteCarrierMovement(CarrierMovement carriermovement) {
		this.CarrierMovement.remove(carriermovement);
	}
	


}
