package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface BookCargoService {

	/* all system operations of the use case*/
	List<Location> getAllLocations() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean createNewCargo(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean createRouteSpecification(String originCode, String destinationCode, LocalDate arrivalDeadline) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	Cargo getCurrentCargo();
	void setCurrentCargo(Cargo currentcargo);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
