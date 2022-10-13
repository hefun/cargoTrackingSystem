package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Delivery implements Serializable {
	
	/* all primary attributes */
	private TransportStatus TransportStatus;
	private boolean Unloaded;
	private LocalDate EstimatedArrivalDate;
	private String RoutingStatus;
	
	/* all references */
	
	/* all get and set functions */
	public TransportStatus getTransportStatus() {
		return TransportStatus;
	}	
	
	public void setTransportStatus(TransportStatus transportstatus) {
		this.TransportStatus = transportstatus;
	}
	public boolean getUnloaded() {
		return Unloaded;
	}	
	
	public void setUnloaded(boolean unloaded) {
		this.Unloaded = unloaded;
	}
	public LocalDate getEstimatedArrivalDate() {
		return EstimatedArrivalDate;
	}	
	
	public void setEstimatedArrivalDate(LocalDate estimatedarrivaldate) {
		this.EstimatedArrivalDate = estimatedarrivaldate;
	}
	public String getRoutingStatus() {
		return RoutingStatus;
	}	
	
	public void setRoutingStatus(String routingstatus) {
		this.RoutingStatus = routingstatus;
	}
	
	/* all functions for reference*/
	


}
