package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ChangeCargoDestinationService {

	/* all system operations of the use case*/
	boolean selectLocation(String locationCode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean changeDestination(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	Location getCurrentLocation();
	void setCurrentLocation(Location currentlocation);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
