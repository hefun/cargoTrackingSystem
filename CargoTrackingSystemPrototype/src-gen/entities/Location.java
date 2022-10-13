package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Location implements Serializable {
	
	/* all primary attributes */
	private String UnLocode;
	private String Name;
	
	/* all references */
	
	/* all get and set functions */
	public String getUnLocode() {
		return UnLocode;
	}	
	
	public void setUnLocode(String unlocode) {
		this.UnLocode = unlocode;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	
	/* all functions for reference*/
	


}
