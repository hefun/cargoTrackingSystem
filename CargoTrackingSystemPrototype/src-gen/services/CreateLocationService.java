package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface CreateLocationService {

	/* all system operations of the use case*/
	boolean inputLocationInfo(String locationCode, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
