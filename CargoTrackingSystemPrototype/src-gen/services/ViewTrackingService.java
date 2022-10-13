package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ViewTrackingService {

	/* all system operations of the use case*/
	GetCargoInfoResult getCargoInfo(String trackingId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	String getCurrentCargoInfo();
	void setCurrentCargoInfo(String currentcargoinfo);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
