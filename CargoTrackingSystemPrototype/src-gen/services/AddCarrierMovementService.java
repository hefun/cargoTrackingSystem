package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface AddCarrierMovementService {

	/* all system operations of the use case*/
	boolean addCarrierMovement(String departure, String arrival, LocalDate departureTime, LocalDate arrivalTime, String voyageNum) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
