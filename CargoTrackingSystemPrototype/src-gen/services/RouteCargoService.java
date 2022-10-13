package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface RouteCargoService {

	/* all system operations of the use case*/
	boolean routeCargo(String trackingId, String locationCode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
