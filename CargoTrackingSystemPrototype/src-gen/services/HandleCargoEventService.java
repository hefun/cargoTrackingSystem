package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface HandleCargoEventService {

	/* all system operations of the use case*/
	boolean createLoadEvent(LocalDate completionTime, LocalDate registrationTime, String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean createUnloadEvent(LocalDate completionTime, LocalDate registrationTime, String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
