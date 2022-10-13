package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class User implements Serializable {
	
	/* all primary attributes */
	private String Name;
	private String UserID;
	private String Role;
	
	/* all references */
	private List<Cargo> Cargos = new LinkedList<Cargo>(); 
	
	/* all get and set functions */
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public String getUserID() {
		return UserID;
	}	
	
	public void setUserID(String userid) {
		this.UserID = userid;
	}
	public String getRole() {
		return Role;
	}	
	
	public void setRole(String role) {
		this.Role = role;
	}
	
	/* all functions for reference*/
	public List<Cargo> getCargos() {
		return Cargos;
	}	
	
	public void addCargos(Cargo cargo) {
		this.Cargos.add(cargo);
	}
	
	public void deleteCargos(Cargo cargo) {
		this.Cargos.remove(cargo);
	}
	


}
