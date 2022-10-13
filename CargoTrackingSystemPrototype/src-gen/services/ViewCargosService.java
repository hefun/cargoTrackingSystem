package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ViewCargosService {

	/* all system operations of the use case*/
	List<RouteSpecification> getAllCargoRoute() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	GetCertainCargoRouteResult getCertainCargoRoute(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
