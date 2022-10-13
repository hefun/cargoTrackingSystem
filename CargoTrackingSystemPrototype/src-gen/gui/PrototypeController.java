package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.time.LocalDate;
import java.util.LinkedList;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import gui.supportclass.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import services.*;
import services.impl.*;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

import entities.*;

public class PrototypeController implements Initializable {


	DateTimeFormatter dateformatter;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		cargotrackingsystemsystem_service = ServiceManager.createCargoTrackingSystemSystem();
		thirdpartyservices_service = ServiceManager.createThirdPartyServices();
		viewtrackingservice_service = ServiceManager.createViewTrackingService();
		createlocationservice_service = ServiceManager.createCreateLocationService();
		createvoyageservice_service = ServiceManager.createCreateVoyageService();
		viewcargosservice_service = ServiceManager.createViewCargosService();
		bookcargoservice_service = ServiceManager.createBookCargoService();
		changecargodestinationservice_service = ServiceManager.createChangeCargoDestinationService();
		routecargoservice_service = ServiceManager.createRouteCargoService();
		addcarriermovementservice_service = ServiceManager.createAddCarrierMovementService();
		handlecargoeventservice_service = ServiceManager.createHandleCargoEventService();
				
		this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	   	 //prepare data for contract
	   	 prepareData();
	   	 
	   	 //generate invariant panel
	   	 genereateInvairantPanel();
	   	 
		 //Actor Threeview Binding
		 actorTreeViewBinding();
		 
		 //Generate
		 generatOperationPane();
		 genereateOpInvariantPanel();
		 
		 //prilimariry data
		 try {
			DataFitService.fit();
		 } catch (PreconditionException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
		 
		 //generate class statistic
		 classStatisicBingding();
		 
		 //generate object statistic
		 generateObjectTable();
		 
		 //genereate association statistic
		 associationStatisicBingding();

		 //set listener 
		 setListeners();
	}
	
	/**
	 * deepCopyforTreeItem (Actor Generation)
	 */
	TreeItem<String> deepCopyTree(TreeItem<String> item) {
		    TreeItem<String> copy = new TreeItem<String>(item.getValue());
		    for (TreeItem<String> child : item.getChildren()) {
		        copy.getChildren().add(deepCopyTree(child));
		    }
		    return copy;
	}
	
	/**
	 * check all invariant and update invariant panel
	 */
	public void invairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}				
			}
			
			for (Entry<String, Label> inv : service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * check op invariant and update op invariant panel
	 */		
	public void opInvairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : op_entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
			for (Entry<String, Label> inv : op_service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
	*	generate op invariant panel 
	*/
	public void genereateOpInvariantPanel() {
		
		opInvariantPanel = new HashMap<String, VBox>();
		op_entity_invariants_label_map = new LinkedHashMap<String, Label>();
		op_service_invariants_label_map = new LinkedHashMap<String, Label>();
		
		VBox v;
		List<String> entities;
		v = new VBox();
		
		//entities invariants
		entities = ViewTrackingServiceImpl.opINVRelatedEntity.get("getCargoInfo");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("getCargoInfo" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ViewTrackingService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("getCargoInfo", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ViewCargosServiceImpl.opINVRelatedEntity.get("getAllCargoRoute");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("getAllCargoRoute" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ViewCargosService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("getAllCargoRoute", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ViewCargosServiceImpl.opINVRelatedEntity.get("getCertainCargoRoute");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("getCertainCargoRoute" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ViewCargosService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("getCertainCargoRoute", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BookCargoServiceImpl.opINVRelatedEntity.get("getAllLocations");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("getAllLocations" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BookCargoService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("getAllLocations", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BookCargoServiceImpl.opINVRelatedEntity.get("createNewCargo");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createNewCargo" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BookCargoService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createNewCargo", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BookCargoServiceImpl.opINVRelatedEntity.get("createRouteSpecification");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createRouteSpecification" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BookCargoService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createRouteSpecification", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ChangeCargoDestinationServiceImpl.opINVRelatedEntity.get("selectLocation");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("selectLocation" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ChangeCargoDestinationService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("selectLocation", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ChangeCargoDestinationServiceImpl.opINVRelatedEntity.get("changeDestination");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("changeDestination" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ChangeCargoDestinationService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("changeDestination", v);
		
		v = new VBox();
		
		//entities invariants
		entities = RouteCargoServiceImpl.opINVRelatedEntity.get("routeCargo");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("routeCargo" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("RouteCargoService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("routeCargo", v);
		
		v = new VBox();
		
		//entities invariants
		entities = CreateLocationServiceImpl.opINVRelatedEntity.get("inputLocationInfo");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputLocationInfo" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("CreateLocationService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputLocationInfo", v);
		
		v = new VBox();
		
		//entities invariants
		entities = CreateVoyageServiceImpl.opINVRelatedEntity.get("createVoyage");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createVoyage" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("CreateVoyageService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createVoyage", v);
		
		v = new VBox();
		
		//entities invariants
		entities = AddCarrierMovementServiceImpl.opINVRelatedEntity.get("addCarrierMovement");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("addCarrierMovement" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("AddCarrierMovementService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("addCarrierMovement", v);
		
		v = new VBox();
		
		//entities invariants
		entities = HandleCargoEventServiceImpl.opINVRelatedEntity.get("createLoadEvent");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createLoadEvent" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("HandleCargoEventService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createLoadEvent", v);
		
		v = new VBox();
		
		//entities invariants
		entities = HandleCargoEventServiceImpl.opINVRelatedEntity.get("createUnloadEvent");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createUnloadEvent" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("HandleCargoEventService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createUnloadEvent", v);
		
		
	}
	
	
	/*
	*  generate invariant panel
	*/
	public void genereateInvairantPanel() {
		
		service_invariants_label_map = new LinkedHashMap<String, Label>();
		entity_invariants_label_map = new LinkedHashMap<String, Label>();
		
		//entity_invariants_map
		VBox v = new VBox();
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			Label l = new Label(inv.getKey());
			l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			service_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		//entity invariants
		for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
			
			String INVname = inv.getKey();
			Label l = new Label(INVname);
			if (INVname.contains("AssociationInvariants")) {
				l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #099b17 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			} else {
				l.setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
			}	
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			entity_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		ScrollPane scrollPane = new ScrollPane(v);
		scrollPane.setFitToWidth(true);
		all_invariant_pane.setMaxHeight(850);
		
		all_invariant_pane.setContent(scrollPane);
	}	
	
	
	
	/* 
	*	mainPane add listener
	*/
	public void setListeners() {
		 mainPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			 
			 	if (newTab.getText().equals("System State")) {
			 		System.out.println("refresh all");
			 		refreshAll();
			 	}
		    
		    });
	}
	
	
	//checking all invariants
	public void checkAllInvariants() {
		
		invairantPanelUpdate();
	
	}	
	
	//refresh all
	public void refreshAll() {
		
		invairantPanelUpdate();
		classStatisticUpdate();
		generateObjectTable();
	}
	
	
	//update association
	public void updateAssociation(String className) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber();
		}
		
	}
	
	public void updateAssociation(String className, int index) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber(index);
		}
		
	}	
	
	public void generateObjectTable() {
		
		allObjectTables = new LinkedHashMap<String, TableView>();
		
		TableView<Map<String, String>> tableCargo = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableCargo_TrackingId = new TableColumn<Map<String, String>, String>("TrackingId");
		tableCargo_TrackingId.setMinWidth("TrackingId".length()*10);
		tableCargo_TrackingId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TrackingId"));
		    }
		});	
		tableCargo.getColumns().add(tableCargo_TrackingId);
		
		//table data
		ObservableList<Map<String, String>> dataCargo = FXCollections.observableArrayList();
		List<Cargo> rsCargo = EntityManager.getAllInstancesOf("Cargo");
		for (Cargo r : rsCargo) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getTrackingId() != null)
				unit.put("TrackingId", String.valueOf(r.getTrackingId()));
			else
				unit.put("TrackingId", "");

			dataCargo.add(unit);
		}
		
		tableCargo.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableCargo.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Cargo", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableCargo.setItems(dataCargo);
		allObjectTables.put("Cargo", tableCargo);
		
		TableView<Map<String, String>> tableLocation = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableLocation_UnLocode = new TableColumn<Map<String, String>, String>("UnLocode");
		tableLocation_UnLocode.setMinWidth("UnLocode".length()*10);
		tableLocation_UnLocode.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UnLocode"));
		    }
		});	
		tableLocation.getColumns().add(tableLocation_UnLocode);
		TableColumn<Map<String, String>, String> tableLocation_Name = new TableColumn<Map<String, String>, String>("Name");
		tableLocation_Name.setMinWidth("Name".length()*10);
		tableLocation_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableLocation.getColumns().add(tableLocation_Name);
		
		//table data
		ObservableList<Map<String, String>> dataLocation = FXCollections.observableArrayList();
		List<Location> rsLocation = EntityManager.getAllInstancesOf("Location");
		for (Location r : rsLocation) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getUnLocode() != null)
				unit.put("UnLocode", String.valueOf(r.getUnLocode()));
			else
				unit.put("UnLocode", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");

			dataLocation.add(unit);
		}
		
		tableLocation.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableLocation.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Location", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableLocation.setItems(dataLocation);
		allObjectTables.put("Location", tableLocation);
		
		TableView<Map<String, String>> tableHandlingEvent = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableHandlingEvent_OperationType = new TableColumn<Map<String, String>, String>("OperationType");
		tableHandlingEvent_OperationType.setMinWidth("OperationType".length()*10);
		tableHandlingEvent_OperationType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OperationType"));
		    }
		});	
		tableHandlingEvent.getColumns().add(tableHandlingEvent_OperationType);
		TableColumn<Map<String, String>, String> tableHandlingEvent_CompletionTime = new TableColumn<Map<String, String>, String>("CompletionTime");
		tableHandlingEvent_CompletionTime.setMinWidth("CompletionTime".length()*10);
		tableHandlingEvent_CompletionTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CompletionTime"));
		    }
		});	
		tableHandlingEvent.getColumns().add(tableHandlingEvent_CompletionTime);
		TableColumn<Map<String, String>, String> tableHandlingEvent_RegistrationTime = new TableColumn<Map<String, String>, String>("RegistrationTime");
		tableHandlingEvent_RegistrationTime.setMinWidth("RegistrationTime".length()*10);
		tableHandlingEvent_RegistrationTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RegistrationTime"));
		    }
		});	
		tableHandlingEvent.getColumns().add(tableHandlingEvent_RegistrationTime);
		
		//table data
		ObservableList<Map<String, String>> dataHandlingEvent = FXCollections.observableArrayList();
		List<HandlingEvent> rsHandlingEvent = EntityManager.getAllInstancesOf("HandlingEvent");
		for (HandlingEvent r : rsHandlingEvent) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("OperationType", String.valueOf(r.getOperationType()));
			if (r.getCompletionTime() != null)
				unit.put("CompletionTime", r.getCompletionTime().format(dateformatter));
			else
				unit.put("CompletionTime", "");
			if (r.getRegistrationTime() != null)
				unit.put("RegistrationTime", r.getRegistrationTime().format(dateformatter));
			else
				unit.put("RegistrationTime", "");

			dataHandlingEvent.add(unit);
		}
		
		tableHandlingEvent.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableHandlingEvent.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("HandlingEvent", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableHandlingEvent.setItems(dataHandlingEvent);
		allObjectTables.put("HandlingEvent", tableHandlingEvent);
		
		TableView<Map<String, String>> tableCarrierMovement = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableCarrierMovement_DepartureTime = new TableColumn<Map<String, String>, String>("DepartureTime");
		tableCarrierMovement_DepartureTime.setMinWidth("DepartureTime".length()*10);
		tableCarrierMovement_DepartureTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("DepartureTime"));
		    }
		});	
		tableCarrierMovement.getColumns().add(tableCarrierMovement_DepartureTime);
		TableColumn<Map<String, String>, String> tableCarrierMovement_ArrivalTime = new TableColumn<Map<String, String>, String>("ArrivalTime");
		tableCarrierMovement_ArrivalTime.setMinWidth("ArrivalTime".length()*10);
		tableCarrierMovement_ArrivalTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ArrivalTime"));
		    }
		});	
		tableCarrierMovement.getColumns().add(tableCarrierMovement_ArrivalTime);
		
		//table data
		ObservableList<Map<String, String>> dataCarrierMovement = FXCollections.observableArrayList();
		List<CarrierMovement> rsCarrierMovement = EntityManager.getAllInstancesOf("CarrierMovement");
		for (CarrierMovement r : rsCarrierMovement) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getDepartureTime() != null)
				unit.put("DepartureTime", r.getDepartureTime().format(dateformatter));
			else
				unit.put("DepartureTime", "");
			if (r.getArrivalTime() != null)
				unit.put("ArrivalTime", r.getArrivalTime().format(dateformatter));
			else
				unit.put("ArrivalTime", "");

			dataCarrierMovement.add(unit);
		}
		
		tableCarrierMovement.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableCarrierMovement.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("CarrierMovement", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableCarrierMovement.setItems(dataCarrierMovement);
		allObjectTables.put("CarrierMovement", tableCarrierMovement);
		
		TableView<Map<String, String>> tableUser = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableUser_Name = new TableColumn<Map<String, String>, String>("Name");
		tableUser_Name.setMinWidth("Name".length()*10);
		tableUser_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Name);
		TableColumn<Map<String, String>, String> tableUser_UserID = new TableColumn<Map<String, String>, String>("UserID");
		tableUser_UserID.setMinWidth("UserID".length()*10);
		tableUser_UserID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserID"));
		    }
		});	
		tableUser.getColumns().add(tableUser_UserID);
		TableColumn<Map<String, String>, String> tableUser_Role = new TableColumn<Map<String, String>, String>("Role");
		tableUser_Role.setMinWidth("Role".length()*10);
		tableUser_Role.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Role"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Role);
		
		//table data
		ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
		List<User> rsUser = EntityManager.getAllInstancesOf("User");
		for (User r : rsUser) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			if (r.getUserID() != null)
				unit.put("UserID", String.valueOf(r.getUserID()));
			else
				unit.put("UserID", "");
			if (r.getRole() != null)
				unit.put("Role", String.valueOf(r.getRole()));
			else
				unit.put("Role", "");

			dataUser.add(unit);
		}
		
		tableUser.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableUser.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("User", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableUser.setItems(dataUser);
		allObjectTables.put("User", tableUser);
		
		TableView<Map<String, String>> tableRouteSpecification = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableRouteSpecification_ArrivalDeadline = new TableColumn<Map<String, String>, String>("ArrivalDeadline");
		tableRouteSpecification_ArrivalDeadline.setMinWidth("ArrivalDeadline".length()*10);
		tableRouteSpecification_ArrivalDeadline.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ArrivalDeadline"));
		    }
		});	
		tableRouteSpecification.getColumns().add(tableRouteSpecification_ArrivalDeadline);
		
		//table data
		ObservableList<Map<String, String>> dataRouteSpecification = FXCollections.observableArrayList();
		List<RouteSpecification> rsRouteSpecification = EntityManager.getAllInstancesOf("RouteSpecification");
		for (RouteSpecification r : rsRouteSpecification) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getArrivalDeadline() != null)
				unit.put("ArrivalDeadline", r.getArrivalDeadline().format(dateformatter));
			else
				unit.put("ArrivalDeadline", "");

			dataRouteSpecification.add(unit);
		}
		
		tableRouteSpecification.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableRouteSpecification.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("RouteSpecification", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableRouteSpecification.setItems(dataRouteSpecification);
		allObjectTables.put("RouteSpecification", tableRouteSpecification);
		
		TableView<Map<String, String>> tableItinerary = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableItinerary_IeneraryNumber = new TableColumn<Map<String, String>, String>("IeneraryNumber");
		tableItinerary_IeneraryNumber.setMinWidth("IeneraryNumber".length()*10);
		tableItinerary_IeneraryNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IeneraryNumber"));
		    }
		});	
		tableItinerary.getColumns().add(tableItinerary_IeneraryNumber);
		
		//table data
		ObservableList<Map<String, String>> dataItinerary = FXCollections.observableArrayList();
		List<Itinerary> rsItinerary = EntityManager.getAllInstancesOf("Itinerary");
		for (Itinerary r : rsItinerary) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getIeneraryNumber() != null)
				unit.put("IeneraryNumber", String.valueOf(r.getIeneraryNumber()));
			else
				unit.put("IeneraryNumber", "");

			dataItinerary.add(unit);
		}
		
		tableItinerary.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableItinerary.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Itinerary", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableItinerary.setItems(dataItinerary);
		allObjectTables.put("Itinerary", tableItinerary);
		
		TableView<Map<String, String>> tableVoyage = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableVoyage_VoyageNumber = new TableColumn<Map<String, String>, String>("VoyageNumber");
		tableVoyage_VoyageNumber.setMinWidth("VoyageNumber".length()*10);
		tableVoyage_VoyageNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("VoyageNumber"));
		    }
		});	
		tableVoyage.getColumns().add(tableVoyage_VoyageNumber);
		
		//table data
		ObservableList<Map<String, String>> dataVoyage = FXCollections.observableArrayList();
		List<Voyage> rsVoyage = EntityManager.getAllInstancesOf("Voyage");
		for (Voyage r : rsVoyage) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getVoyageNumber() != null)
				unit.put("VoyageNumber", String.valueOf(r.getVoyageNumber()));
			else
				unit.put("VoyageNumber", "");

			dataVoyage.add(unit);
		}
		
		tableVoyage.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableVoyage.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Voyage", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableVoyage.setItems(dataVoyage);
		allObjectTables.put("Voyage", tableVoyage);
		
		TableView<Map<String, String>> tableLeg = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableLeg_LoadTime = new TableColumn<Map<String, String>, String>("LoadTime");
		tableLeg_LoadTime.setMinWidth("LoadTime".length()*10);
		tableLeg_LoadTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LoadTime"));
		    }
		});	
		tableLeg.getColumns().add(tableLeg_LoadTime);
		TableColumn<Map<String, String>, String> tableLeg_UnloadTime = new TableColumn<Map<String, String>, String>("UnloadTime");
		tableLeg_UnloadTime.setMinWidth("UnloadTime".length()*10);
		tableLeg_UnloadTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UnloadTime"));
		    }
		});	
		tableLeg.getColumns().add(tableLeg_UnloadTime);
		
		//table data
		ObservableList<Map<String, String>> dataLeg = FXCollections.observableArrayList();
		List<Leg> rsLeg = EntityManager.getAllInstancesOf("Leg");
		for (Leg r : rsLeg) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getLoadTime() != null)
				unit.put("LoadTime", r.getLoadTime().format(dateformatter));
			else
				unit.put("LoadTime", "");
			if (r.getUnloadTime() != null)
				unit.put("UnloadTime", r.getUnloadTime().format(dateformatter));
			else
				unit.put("UnloadTime", "");

			dataLeg.add(unit);
		}
		
		tableLeg.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableLeg.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Leg", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableLeg.setItems(dataLeg);
		allObjectTables.put("Leg", tableLeg);
		
		TableView<Map<String, String>> tableDelivery = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableDelivery_TransportStatus = new TableColumn<Map<String, String>, String>("TransportStatus");
		tableDelivery_TransportStatus.setMinWidth("TransportStatus".length()*10);
		tableDelivery_TransportStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TransportStatus"));
		    }
		});	
		tableDelivery.getColumns().add(tableDelivery_TransportStatus);
		TableColumn<Map<String, String>, String> tableDelivery_Unloaded = new TableColumn<Map<String, String>, String>("Unloaded");
		tableDelivery_Unloaded.setMinWidth("Unloaded".length()*10);
		tableDelivery_Unloaded.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Unloaded"));
		    }
		});	
		tableDelivery.getColumns().add(tableDelivery_Unloaded);
		TableColumn<Map<String, String>, String> tableDelivery_EstimatedArrivalDate = new TableColumn<Map<String, String>, String>("EstimatedArrivalDate");
		tableDelivery_EstimatedArrivalDate.setMinWidth("EstimatedArrivalDate".length()*10);
		tableDelivery_EstimatedArrivalDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EstimatedArrivalDate"));
		    }
		});	
		tableDelivery.getColumns().add(tableDelivery_EstimatedArrivalDate);
		TableColumn<Map<String, String>, String> tableDelivery_RoutingStatus = new TableColumn<Map<String, String>, String>("RoutingStatus");
		tableDelivery_RoutingStatus.setMinWidth("RoutingStatus".length()*10);
		tableDelivery_RoutingStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RoutingStatus"));
		    }
		});	
		tableDelivery.getColumns().add(tableDelivery_RoutingStatus);
		
		//table data
		ObservableList<Map<String, String>> dataDelivery = FXCollections.observableArrayList();
		List<Delivery> rsDelivery = EntityManager.getAllInstancesOf("Delivery");
		for (Delivery r : rsDelivery) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("TransportStatus", String.valueOf(r.getTransportStatus()));
			unit.put("Unloaded", String.valueOf(r.getUnloaded()));
			if (r.getEstimatedArrivalDate() != null)
				unit.put("EstimatedArrivalDate", r.getEstimatedArrivalDate().format(dateformatter));
			else
				unit.put("EstimatedArrivalDate", "");
			if (r.getRoutingStatus() != null)
				unit.put("RoutingStatus", String.valueOf(r.getRoutingStatus()));
			else
				unit.put("RoutingStatus", "");

			dataDelivery.add(unit);
		}
		
		tableDelivery.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableDelivery.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Delivery", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableDelivery.setItems(dataDelivery);
		allObjectTables.put("Delivery", tableDelivery);
		
		TableView<Map<String, String>> tableGetCargoInfoResult = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		
		//table data
		ObservableList<Map<String, String>> dataGetCargoInfoResult = FXCollections.observableArrayList();
		List<GetCargoInfoResult> rsGetCargoInfoResult = EntityManager.getAllInstancesOf("GetCargoInfoResult");
		for (GetCargoInfoResult r : rsGetCargoInfoResult) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			

			dataGetCargoInfoResult.add(unit);
		}
		
		tableGetCargoInfoResult.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableGetCargoInfoResult.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("GetCargoInfoResult", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableGetCargoInfoResult.setItems(dataGetCargoInfoResult);
		allObjectTables.put("GetCargoInfoResult", tableGetCargoInfoResult);
		
		TableView<Map<String, String>> tableGetCertainCargoRouteResult = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		
		//table data
		ObservableList<Map<String, String>> dataGetCertainCargoRouteResult = FXCollections.observableArrayList();
		List<GetCertainCargoRouteResult> rsGetCertainCargoRouteResult = EntityManager.getAllInstancesOf("GetCertainCargoRouteResult");
		for (GetCertainCargoRouteResult r : rsGetCertainCargoRouteResult) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			

			dataGetCertainCargoRouteResult.add(unit);
		}
		
		tableGetCertainCargoRouteResult.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableGetCertainCargoRouteResult.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("GetCertainCargoRouteResult", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableGetCertainCargoRouteResult.setItems(dataGetCertainCargoRouteResult);
		allObjectTables.put("GetCertainCargoRouteResult", tableGetCertainCargoRouteResult);
		

		
	}
	
	/* 
	* update all object tables with sub dataset
	*/ 
	public void updateCargoTable(List<Cargo> rsCargo) {
			ObservableList<Map<String, String>> dataCargo = FXCollections.observableArrayList();
			for (Cargo r : rsCargo) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getTrackingId() != null)
					unit.put("TrackingId", String.valueOf(r.getTrackingId()));
				else
					unit.put("TrackingId", "");
				dataCargo.add(unit);
			}
			
			allObjectTables.get("Cargo").setItems(dataCargo);
	}
	public void updateLocationTable(List<Location> rsLocation) {
			ObservableList<Map<String, String>> dataLocation = FXCollections.observableArrayList();
			for (Location r : rsLocation) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getUnLocode() != null)
					unit.put("UnLocode", String.valueOf(r.getUnLocode()));
				else
					unit.put("UnLocode", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataLocation.add(unit);
			}
			
			allObjectTables.get("Location").setItems(dataLocation);
	}
	public void updateHandlingEventTable(List<HandlingEvent> rsHandlingEvent) {
			ObservableList<Map<String, String>> dataHandlingEvent = FXCollections.observableArrayList();
			for (HandlingEvent r : rsHandlingEvent) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("OperationType", String.valueOf(r.getOperationType()));
				if (r.getCompletionTime() != null)
					unit.put("CompletionTime", r.getCompletionTime().format(dateformatter));
				else
					unit.put("CompletionTime", "");
				if (r.getRegistrationTime() != null)
					unit.put("RegistrationTime", r.getRegistrationTime().format(dateformatter));
				else
					unit.put("RegistrationTime", "");
				dataHandlingEvent.add(unit);
			}
			
			allObjectTables.get("HandlingEvent").setItems(dataHandlingEvent);
	}
	public void updateCarrierMovementTable(List<CarrierMovement> rsCarrierMovement) {
			ObservableList<Map<String, String>> dataCarrierMovement = FXCollections.observableArrayList();
			for (CarrierMovement r : rsCarrierMovement) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getDepartureTime() != null)
					unit.put("DepartureTime", r.getDepartureTime().format(dateformatter));
				else
					unit.put("DepartureTime", "");
				if (r.getArrivalTime() != null)
					unit.put("ArrivalTime", r.getArrivalTime().format(dateformatter));
				else
					unit.put("ArrivalTime", "");
				dataCarrierMovement.add(unit);
			}
			
			allObjectTables.get("CarrierMovement").setItems(dataCarrierMovement);
	}
	public void updateUserTable(List<User> rsUser) {
			ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
			for (User r : rsUser) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getRole() != null)
					unit.put("Role", String.valueOf(r.getRole()));
				else
					unit.put("Role", "");
				dataUser.add(unit);
			}
			
			allObjectTables.get("User").setItems(dataUser);
	}
	public void updateRouteSpecificationTable(List<RouteSpecification> rsRouteSpecification) {
			ObservableList<Map<String, String>> dataRouteSpecification = FXCollections.observableArrayList();
			for (RouteSpecification r : rsRouteSpecification) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getArrivalDeadline() != null)
					unit.put("ArrivalDeadline", r.getArrivalDeadline().format(dateformatter));
				else
					unit.put("ArrivalDeadline", "");
				dataRouteSpecification.add(unit);
			}
			
			allObjectTables.get("RouteSpecification").setItems(dataRouteSpecification);
	}
	public void updateItineraryTable(List<Itinerary> rsItinerary) {
			ObservableList<Map<String, String>> dataItinerary = FXCollections.observableArrayList();
			for (Itinerary r : rsItinerary) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getIeneraryNumber() != null)
					unit.put("IeneraryNumber", String.valueOf(r.getIeneraryNumber()));
				else
					unit.put("IeneraryNumber", "");
				dataItinerary.add(unit);
			}
			
			allObjectTables.get("Itinerary").setItems(dataItinerary);
	}
	public void updateVoyageTable(List<Voyage> rsVoyage) {
			ObservableList<Map<String, String>> dataVoyage = FXCollections.observableArrayList();
			for (Voyage r : rsVoyage) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getVoyageNumber() != null)
					unit.put("VoyageNumber", String.valueOf(r.getVoyageNumber()));
				else
					unit.put("VoyageNumber", "");
				dataVoyage.add(unit);
			}
			
			allObjectTables.get("Voyage").setItems(dataVoyage);
	}
	public void updateLegTable(List<Leg> rsLeg) {
			ObservableList<Map<String, String>> dataLeg = FXCollections.observableArrayList();
			for (Leg r : rsLeg) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getLoadTime() != null)
					unit.put("LoadTime", r.getLoadTime().format(dateformatter));
				else
					unit.put("LoadTime", "");
				if (r.getUnloadTime() != null)
					unit.put("UnloadTime", r.getUnloadTime().format(dateformatter));
				else
					unit.put("UnloadTime", "");
				dataLeg.add(unit);
			}
			
			allObjectTables.get("Leg").setItems(dataLeg);
	}
	public void updateDeliveryTable(List<Delivery> rsDelivery) {
			ObservableList<Map<String, String>> dataDelivery = FXCollections.observableArrayList();
			for (Delivery r : rsDelivery) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("TransportStatus", String.valueOf(r.getTransportStatus()));
				unit.put("Unloaded", String.valueOf(r.getUnloaded()));
				if (r.getEstimatedArrivalDate() != null)
					unit.put("EstimatedArrivalDate", r.getEstimatedArrivalDate().format(dateformatter));
				else
					unit.put("EstimatedArrivalDate", "");
				if (r.getRoutingStatus() != null)
					unit.put("RoutingStatus", String.valueOf(r.getRoutingStatus()));
				else
					unit.put("RoutingStatus", "");
				dataDelivery.add(unit);
			}
			
			allObjectTables.get("Delivery").setItems(dataDelivery);
	}
	public void updateGetCargoInfoResultTable(List<GetCargoInfoResult> rsGetCargoInfoResult) {
			ObservableList<Map<String, String>> dataGetCargoInfoResult = FXCollections.observableArrayList();
			for (GetCargoInfoResult r : rsGetCargoInfoResult) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				dataGetCargoInfoResult.add(unit);
			}
			
			allObjectTables.get("GetCargoInfoResult").setItems(dataGetCargoInfoResult);
	}
	public void updateGetCertainCargoRouteResultTable(List<GetCertainCargoRouteResult> rsGetCertainCargoRouteResult) {
			ObservableList<Map<String, String>> dataGetCertainCargoRouteResult = FXCollections.observableArrayList();
			for (GetCertainCargoRouteResult r : rsGetCertainCargoRouteResult) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				dataGetCertainCargoRouteResult.add(unit);
			}
			
			allObjectTables.get("GetCertainCargoRouteResult").setItems(dataGetCertainCargoRouteResult);
	}
	
	/* 
	* update all object tables
	*/ 
	public void updateCargoTable() {
			ObservableList<Map<String, String>> dataCargo = FXCollections.observableArrayList();
			List<Cargo> rsCargo = EntityManager.getAllInstancesOf("Cargo");
			for (Cargo r : rsCargo) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getTrackingId() != null)
					unit.put("TrackingId", String.valueOf(r.getTrackingId()));
				else
					unit.put("TrackingId", "");
				dataCargo.add(unit);
			}
			
			allObjectTables.get("Cargo").setItems(dataCargo);
	}
	public void updateLocationTable() {
			ObservableList<Map<String, String>> dataLocation = FXCollections.observableArrayList();
			List<Location> rsLocation = EntityManager.getAllInstancesOf("Location");
			for (Location r : rsLocation) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getUnLocode() != null)
					unit.put("UnLocode", String.valueOf(r.getUnLocode()));
				else
					unit.put("UnLocode", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataLocation.add(unit);
			}
			
			allObjectTables.get("Location").setItems(dataLocation);
	}
	public void updateHandlingEventTable() {
			ObservableList<Map<String, String>> dataHandlingEvent = FXCollections.observableArrayList();
			List<HandlingEvent> rsHandlingEvent = EntityManager.getAllInstancesOf("HandlingEvent");
			for (HandlingEvent r : rsHandlingEvent) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("OperationType", String.valueOf(r.getOperationType()));
				if (r.getCompletionTime() != null)
					unit.put("CompletionTime", r.getCompletionTime().format(dateformatter));
				else
					unit.put("CompletionTime", "");
				if (r.getRegistrationTime() != null)
					unit.put("RegistrationTime", r.getRegistrationTime().format(dateformatter));
				else
					unit.put("RegistrationTime", "");
				dataHandlingEvent.add(unit);
			}
			
			allObjectTables.get("HandlingEvent").setItems(dataHandlingEvent);
	}
	public void updateCarrierMovementTable() {
			ObservableList<Map<String, String>> dataCarrierMovement = FXCollections.observableArrayList();
			List<CarrierMovement> rsCarrierMovement = EntityManager.getAllInstancesOf("CarrierMovement");
			for (CarrierMovement r : rsCarrierMovement) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getDepartureTime() != null)
					unit.put("DepartureTime", r.getDepartureTime().format(dateformatter));
				else
					unit.put("DepartureTime", "");
				if (r.getArrivalTime() != null)
					unit.put("ArrivalTime", r.getArrivalTime().format(dateformatter));
				else
					unit.put("ArrivalTime", "");
				dataCarrierMovement.add(unit);
			}
			
			allObjectTables.get("CarrierMovement").setItems(dataCarrierMovement);
	}
	public void updateUserTable() {
			ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
			List<User> rsUser = EntityManager.getAllInstancesOf("User");
			for (User r : rsUser) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getRole() != null)
					unit.put("Role", String.valueOf(r.getRole()));
				else
					unit.put("Role", "");
				dataUser.add(unit);
			}
			
			allObjectTables.get("User").setItems(dataUser);
	}
	public void updateRouteSpecificationTable() {
			ObservableList<Map<String, String>> dataRouteSpecification = FXCollections.observableArrayList();
			List<RouteSpecification> rsRouteSpecification = EntityManager.getAllInstancesOf("RouteSpecification");
			for (RouteSpecification r : rsRouteSpecification) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getArrivalDeadline() != null)
					unit.put("ArrivalDeadline", r.getArrivalDeadline().format(dateformatter));
				else
					unit.put("ArrivalDeadline", "");
				dataRouteSpecification.add(unit);
			}
			
			allObjectTables.get("RouteSpecification").setItems(dataRouteSpecification);
	}
	public void updateItineraryTable() {
			ObservableList<Map<String, String>> dataItinerary = FXCollections.observableArrayList();
			List<Itinerary> rsItinerary = EntityManager.getAllInstancesOf("Itinerary");
			for (Itinerary r : rsItinerary) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getIeneraryNumber() != null)
					unit.put("IeneraryNumber", String.valueOf(r.getIeneraryNumber()));
				else
					unit.put("IeneraryNumber", "");
				dataItinerary.add(unit);
			}
			
			allObjectTables.get("Itinerary").setItems(dataItinerary);
	}
	public void updateVoyageTable() {
			ObservableList<Map<String, String>> dataVoyage = FXCollections.observableArrayList();
			List<Voyage> rsVoyage = EntityManager.getAllInstancesOf("Voyage");
			for (Voyage r : rsVoyage) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getVoyageNumber() != null)
					unit.put("VoyageNumber", String.valueOf(r.getVoyageNumber()));
				else
					unit.put("VoyageNumber", "");
				dataVoyage.add(unit);
			}
			
			allObjectTables.get("Voyage").setItems(dataVoyage);
	}
	public void updateLegTable() {
			ObservableList<Map<String, String>> dataLeg = FXCollections.observableArrayList();
			List<Leg> rsLeg = EntityManager.getAllInstancesOf("Leg");
			for (Leg r : rsLeg) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getLoadTime() != null)
					unit.put("LoadTime", r.getLoadTime().format(dateformatter));
				else
					unit.put("LoadTime", "");
				if (r.getUnloadTime() != null)
					unit.put("UnloadTime", r.getUnloadTime().format(dateformatter));
				else
					unit.put("UnloadTime", "");
				dataLeg.add(unit);
			}
			
			allObjectTables.get("Leg").setItems(dataLeg);
	}
	public void updateDeliveryTable() {
			ObservableList<Map<String, String>> dataDelivery = FXCollections.observableArrayList();
			List<Delivery> rsDelivery = EntityManager.getAllInstancesOf("Delivery");
			for (Delivery r : rsDelivery) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("TransportStatus", String.valueOf(r.getTransportStatus()));
				unit.put("Unloaded", String.valueOf(r.getUnloaded()));
				if (r.getEstimatedArrivalDate() != null)
					unit.put("EstimatedArrivalDate", r.getEstimatedArrivalDate().format(dateformatter));
				else
					unit.put("EstimatedArrivalDate", "");
				if (r.getRoutingStatus() != null)
					unit.put("RoutingStatus", String.valueOf(r.getRoutingStatus()));
				else
					unit.put("RoutingStatus", "");
				dataDelivery.add(unit);
			}
			
			allObjectTables.get("Delivery").setItems(dataDelivery);
	}
	public void updateGetCargoInfoResultTable() {
			ObservableList<Map<String, String>> dataGetCargoInfoResult = FXCollections.observableArrayList();
			List<GetCargoInfoResult> rsGetCargoInfoResult = EntityManager.getAllInstancesOf("GetCargoInfoResult");
			for (GetCargoInfoResult r : rsGetCargoInfoResult) {
				Map<String, String> unit = new HashMap<String, String>();


				dataGetCargoInfoResult.add(unit);
			}
			
			allObjectTables.get("GetCargoInfoResult").setItems(dataGetCargoInfoResult);
	}
	public void updateGetCertainCargoRouteResultTable() {
			ObservableList<Map<String, String>> dataGetCertainCargoRouteResult = FXCollections.observableArrayList();
			List<GetCertainCargoRouteResult> rsGetCertainCargoRouteResult = EntityManager.getAllInstancesOf("GetCertainCargoRouteResult");
			for (GetCertainCargoRouteResult r : rsGetCertainCargoRouteResult) {
				Map<String, String> unit = new HashMap<String, String>();


				dataGetCertainCargoRouteResult.add(unit);
			}
			
			allObjectTables.get("GetCertainCargoRouteResult").setItems(dataGetCertainCargoRouteResult);
	}
	
	public void classStatisicBingding() {
	
		 classInfodata = FXCollections.observableArrayList();
	 	 cargo = new ClassInfo("Cargo", EntityManager.getAllInstancesOf("Cargo").size());
	 	 classInfodata.add(cargo);
	 	 location = new ClassInfo("Location", EntityManager.getAllInstancesOf("Location").size());
	 	 classInfodata.add(location);
	 	 handlingevent = new ClassInfo("HandlingEvent", EntityManager.getAllInstancesOf("HandlingEvent").size());
	 	 classInfodata.add(handlingevent);
	 	 carriermovement = new ClassInfo("CarrierMovement", EntityManager.getAllInstancesOf("CarrierMovement").size());
	 	 classInfodata.add(carriermovement);
	 	 user = new ClassInfo("User", EntityManager.getAllInstancesOf("User").size());
	 	 classInfodata.add(user);
	 	 routespecification = new ClassInfo("RouteSpecification", EntityManager.getAllInstancesOf("RouteSpecification").size());
	 	 classInfodata.add(routespecification);
	 	 itinerary = new ClassInfo("Itinerary", EntityManager.getAllInstancesOf("Itinerary").size());
	 	 classInfodata.add(itinerary);
	 	 voyage = new ClassInfo("Voyage", EntityManager.getAllInstancesOf("Voyage").size());
	 	 classInfodata.add(voyage);
	 	 leg = new ClassInfo("Leg", EntityManager.getAllInstancesOf("Leg").size());
	 	 classInfodata.add(leg);
	 	 delivery = new ClassInfo("Delivery", EntityManager.getAllInstancesOf("Delivery").size());
	 	 classInfodata.add(delivery);
	 	 getcargoinforesult = new ClassInfo("GetCargoInfoResult", EntityManager.getAllInstancesOf("GetCargoInfoResult").size());
	 	 classInfodata.add(getcargoinforesult);
	 	 getcertaincargorouteresult = new ClassInfo("GetCertainCargoRouteResult", EntityManager.getAllInstancesOf("GetCertainCargoRouteResult").size());
	 	 classInfodata.add(getcertaincargorouteresult);
	 	 
		 class_statisic.setItems(classInfodata);
		 
		 //Class Statisic Binding
		 class_statisic.getSelectionModel().selectedItemProperty().addListener(
				 (observable, oldValue, newValue) ->  { 
				 										 //no selected object in table
				 										 objectindex = -1;
				 										 
				 										 //get lastest data, reflect updateTableData method
				 										 try {
												 			 Method updateob = this.getClass().getMethod("update" + newValue.getName() + "Table", null);
												 			 updateob.invoke(this);			 
												 		 } catch (Exception e) {
												 		 	 e.printStackTrace();
												 		 }		 										 
				 	
				 										 //show object table
				 			 				 			 TableView obs = allObjectTables.get(newValue.getName());
				 			 				 			 if (obs != null) {
				 			 				 				object_statics.setContent(obs);
				 			 				 				object_statics.setText("All Objects " + newValue.getName() + ":");
				 			 				 			 }
				 			 				 			 
				 			 				 			 //update association information
							 			 				 updateAssociation(newValue.getName());
				 			 				 			 
				 			 				 			 //show association information
				 			 				 			 ObservableList<AssociationInfo> asso = allassociationData.get(newValue.getName());
				 			 				 			 if (asso != null) {
				 			 				 			 	association_statisic.setItems(asso);
				 			 				 			 }
				 			 				 		  });
	}
	
	public void classStatisticUpdate() {
	 	 cargo.setNumber(EntityManager.getAllInstancesOf("Cargo").size());
	 	 location.setNumber(EntityManager.getAllInstancesOf("Location").size());
	 	 handlingevent.setNumber(EntityManager.getAllInstancesOf("HandlingEvent").size());
	 	 carriermovement.setNumber(EntityManager.getAllInstancesOf("CarrierMovement").size());
	 	 user.setNumber(EntityManager.getAllInstancesOf("User").size());
	 	 routespecification.setNumber(EntityManager.getAllInstancesOf("RouteSpecification").size());
	 	 itinerary.setNumber(EntityManager.getAllInstancesOf("Itinerary").size());
	 	 voyage.setNumber(EntityManager.getAllInstancesOf("Voyage").size());
	 	 leg.setNumber(EntityManager.getAllInstancesOf("Leg").size());
	 	 delivery.setNumber(EntityManager.getAllInstancesOf("Delivery").size());
	 	 getcargoinforesult.setNumber(EntityManager.getAllInstancesOf("GetCargoInfoResult").size());
	 	 getcertaincargorouteresult.setNumber(EntityManager.getAllInstancesOf("GetCertainCargoRouteResult").size());
		
	}
	
	/**
	 * association binding
	 */
	public void associationStatisicBingding() {
		
		allassociationData = new HashMap<String, ObservableList<AssociationInfo>>();
		
		ObservableList<AssociationInfo> Cargo_association_data = FXCollections.observableArrayList();
		AssociationInfo Cargo_associatition_Users = new AssociationInfo("Cargo", "User", "Users", true);
		Cargo_association_data.add(Cargo_associatition_Users);
		AssociationInfo Cargo_associatition_Delivery = new AssociationInfo("Cargo", "Delivery", "Delivery", false);
		Cargo_association_data.add(Cargo_associatition_Delivery);
		AssociationInfo Cargo_associatition_Events = new AssociationInfo("Cargo", "HandlingEvent", "Events", true);
		Cargo_association_data.add(Cargo_associatition_Events);
		AssociationInfo Cargo_associatition_Itinerary = new AssociationInfo("Cargo", "Itinerary", "Itinerary", false);
		Cargo_association_data.add(Cargo_associatition_Itinerary);
		AssociationInfo Cargo_associatition_RouteSpecification = new AssociationInfo("Cargo", "RouteSpecification", "RouteSpecification", false);
		Cargo_association_data.add(Cargo_associatition_RouteSpecification);
		
		allassociationData.put("Cargo", Cargo_association_data);
		
		ObservableList<AssociationInfo> Location_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Location", Location_association_data);
		
		ObservableList<AssociationInfo> HandlingEvent_association_data = FXCollections.observableArrayList();
		AssociationInfo HandlingEvent_associatition_Handled = new AssociationInfo("HandlingEvent", "Cargo", "Handled", false);
		HandlingEvent_association_data.add(HandlingEvent_associatition_Handled);
		AssociationInfo HandlingEvent_associatition_Location = new AssociationInfo("HandlingEvent", "Location", "Location", false);
		HandlingEvent_association_data.add(HandlingEvent_associatition_Location);
		AssociationInfo HandlingEvent_associatition_Voyage = new AssociationInfo("HandlingEvent", "Voyage", "Voyage", false);
		HandlingEvent_association_data.add(HandlingEvent_associatition_Voyage);
		
		allassociationData.put("HandlingEvent", HandlingEvent_association_data);
		
		ObservableList<AssociationInfo> CarrierMovement_association_data = FXCollections.observableArrayList();
		AssociationInfo CarrierMovement_associatition_DepartureLocation = new AssociationInfo("CarrierMovement", "Location", "DepartureLocation", false);
		CarrierMovement_association_data.add(CarrierMovement_associatition_DepartureLocation);
		AssociationInfo CarrierMovement_associatition_ArrivalLocation = new AssociationInfo("CarrierMovement", "Location", "ArrivalLocation", false);
		CarrierMovement_association_data.add(CarrierMovement_associatition_ArrivalLocation);
		AssociationInfo CarrierMovement_associatition_Voyage = new AssociationInfo("CarrierMovement", "Voyage", "Voyage", false);
		CarrierMovement_association_data.add(CarrierMovement_associatition_Voyage);
		
		allassociationData.put("CarrierMovement", CarrierMovement_association_data);
		
		ObservableList<AssociationInfo> User_association_data = FXCollections.observableArrayList();
		AssociationInfo User_associatition_Cargos = new AssociationInfo("User", "Cargo", "Cargos", true);
		User_association_data.add(User_associatition_Cargos);
		
		allassociationData.put("User", User_association_data);
		
		ObservableList<AssociationInfo> RouteSpecification_association_data = FXCollections.observableArrayList();
		AssociationInfo RouteSpecification_associatition_Origin = new AssociationInfo("RouteSpecification", "Location", "Origin", false);
		RouteSpecification_association_data.add(RouteSpecification_associatition_Origin);
		AssociationInfo RouteSpecification_associatition_Destination = new AssociationInfo("RouteSpecification", "Location", "Destination", false);
		RouteSpecification_association_data.add(RouteSpecification_associatition_Destination);
		
		allassociationData.put("RouteSpecification", RouteSpecification_association_data);
		
		ObservableList<AssociationInfo> Itinerary_association_data = FXCollections.observableArrayList();
		AssociationInfo Itinerary_associatition_Step = new AssociationInfo("Itinerary", "Leg", "Step", true);
		Itinerary_association_data.add(Itinerary_associatition_Step);
		AssociationInfo Itinerary_associatition_Specification = new AssociationInfo("Itinerary", "RouteSpecification", "Specification", false);
		Itinerary_association_data.add(Itinerary_associatition_Specification);
		
		allassociationData.put("Itinerary", Itinerary_association_data);
		
		ObservableList<AssociationInfo> Voyage_association_data = FXCollections.observableArrayList();
		AssociationInfo Voyage_associatition_CarrierMovement = new AssociationInfo("Voyage", "CarrierMovement", "CarrierMovement", true);
		Voyage_association_data.add(Voyage_associatition_CarrierMovement);
		
		allassociationData.put("Voyage", Voyage_association_data);
		
		ObservableList<AssociationInfo> Leg_association_data = FXCollections.observableArrayList();
		AssociationInfo Leg_associatition_LoadLocation = new AssociationInfo("Leg", "Location", "LoadLocation", false);
		Leg_association_data.add(Leg_associatition_LoadLocation);
		AssociationInfo Leg_associatition_UnloadLocation = new AssociationInfo("Leg", "Location", "UnloadLocation", false);
		Leg_association_data.add(Leg_associatition_UnloadLocation);
		
		allassociationData.put("Leg", Leg_association_data);
		
		ObservableList<AssociationInfo> Delivery_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Delivery", Delivery_association_data);
		
		ObservableList<AssociationInfo> GetCargoInfoResult_association_data = FXCollections.observableArrayList();
		AssociationInfo GetCargoInfoResult_associatition_Delivery = new AssociationInfo("GetCargoInfoResult", "Delivery", "Delivery", false);
		GetCargoInfoResult_association_data.add(GetCargoInfoResult_associatition_Delivery);
		AssociationInfo GetCargoInfoResult_associatition_Events = new AssociationInfo("GetCargoInfoResult", "HandlingEvent", "Events", true);
		GetCargoInfoResult_association_data.add(GetCargoInfoResult_associatition_Events);
		AssociationInfo GetCargoInfoResult_associatition_Voyages = new AssociationInfo("GetCargoInfoResult", "Voyage", "Voyages", true);
		GetCargoInfoResult_association_data.add(GetCargoInfoResult_associatition_Voyages);
		
		allassociationData.put("GetCargoInfoResult", GetCargoInfoResult_association_data);
		
		ObservableList<AssociationInfo> GetCertainCargoRouteResult_association_data = FXCollections.observableArrayList();
		AssociationInfo GetCertainCargoRouteResult_associatition_Cargo = new AssociationInfo("GetCertainCargoRouteResult", "Cargo", "Cargo", false);
		GetCertainCargoRouteResult_association_data.add(GetCertainCargoRouteResult_associatition_Cargo);
		AssociationInfo GetCertainCargoRouteResult_associatition_Delivery = new AssociationInfo("GetCertainCargoRouteResult", "Delivery", "Delivery", false);
		GetCertainCargoRouteResult_association_data.add(GetCertainCargoRouteResult_associatition_Delivery);
		AssociationInfo GetCertainCargoRouteResult_associatition_RouteSpecification = new AssociationInfo("GetCertainCargoRouteResult", "RouteSpecification", "RouteSpecification", false);
		GetCertainCargoRouteResult_association_data.add(GetCertainCargoRouteResult_associatition_RouteSpecification);
		AssociationInfo GetCertainCargoRouteResult_associatition_Itinerary = new AssociationInfo("GetCertainCargoRouteResult", "Itinerary", "Itinerary", false);
		GetCertainCargoRouteResult_association_data.add(GetCertainCargoRouteResult_associatition_Itinerary);
		
		allassociationData.put("GetCertainCargoRouteResult", GetCertainCargoRouteResult_association_data);
		
		
		association_statisic.getSelectionModel().selectedItemProperty().addListener(
			    (observable, oldValue, newValue) ->  { 
	
							 		if (newValue != null) {
							 			 try {
							 			 	 if (newValue.getNumber() != 0) {
								 				 //choose object or not
								 				 if (objectindex != -1) {
									 				 Class[] cArg = new Class[1];
									 				 cArg[0] = List.class;
									 				 //reflect updateTableData method
										 			 Method updateob = this.getClass().getMethod("update" + newValue.getTargetClass() + "Table", cArg);
										 			 //find choosen object
										 			 Object selectedob = EntityManager.getAllInstancesOf(newValue.getSourceClass()).get(objectindex);
										 			 //reflect find association method
										 			 Method getAssociatedObject = selectedob.getClass().getMethod("get" + newValue.getAssociationName());
										 			 List r = new LinkedList();
										 			 //one or mulity?
										 			 if (newValue.getIsMultiple() == true) {
											 			 
											 			r = (List) getAssociatedObject.invoke(selectedob);
										 			 }
										 			 else {
										 				r.add(getAssociatedObject.invoke(selectedob));
										 			 }
										 			 //invoke update method
										 			 updateob.invoke(this, r);
										 			  
										 			 
								 				 }
												 //bind updated data to GUI
					 				 			 TableView obs = allObjectTables.get(newValue.getTargetClass());
					 				 			 if (obs != null) {
					 				 				object_statics.setContent(obs);
					 				 				object_statics.setText("Targets Objects " + newValue.getTargetClass() + ":");
					 				 			 }
					 				 		 }
							 			 }
							 			 catch (Exception e) {
							 				 e.printStackTrace();
							 			 }
							 		}
					 		  });
		
	}	
	
	

    //prepare data for contract
	public void prepareData() {
		
		//definition map
		definitions_map = new HashMap<String, String>();
		definitions_map.put("getCargoInfo", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\n\revents:Set(HandlingEvent) = HandlingEvent.allInstance()->select(handlingEvent:HandlingEvent | handlingEvent.Handled = c)\n\rvoyages:Set(Voyage) = events->collect(h:HandlingEvent | h.Voyage)\r\r\n");
		definitions_map.put("getAllCargoRoute", "routeSpecifications:Set(RouteSpecification) = Cargo.allInstance()->collect(c:Cargo | c.RouteSpecification)\r\r\n");
		definitions_map.put("getCertainCargoRoute", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\r\r\n");
		definitions_map.put("getAllLocations", "locations:Set(Location) = Location.allInstance()\r\r\n");
		definitions_map.put("createNewCargo", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\r\r\n");
		definitions_map.put("createRouteSpecification", "origin:Location = Location.allInstance()->any(l:Location | l.UnLocode = originCode)\n\rdestination:Location = Location.allInstance()->any(l:Location | l.UnLocode = destinationCode)\r\r\n");
		definitions_map.put("selectLocation", "location:Location = Location.allInstance()->any(l:Location | l.Unlocode = locationCode)\r\r\n");
		definitions_map.put("changeDestination", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\n\rroute:RouteSpecification = c.RouteSpecification\n\rdelivery:Delivery = c.Delivery\r\r\n");
		definitions_map.put("routeCargo", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\n\rlocation:Location = Location.allInstance()->any(l:Location | l.UnLocode = locationCode)\n\ritinerary:Itinerary = c.Itinerary\n\rlegs:Set(Leg) = itinerary.Step\n\rcm = CarrierMovement.allInstance()->any(cm:CarrierMovement | cm.ArrivalLocation = location)\n\rvoyage = cm.Voyage\r\r\n");
		definitions_map.put("inputLocationInfo", "location:Location = Location.allInstance()->any(l:Location | l.UnLocode = locationCode)\r\r\n");
		definitions_map.put("createVoyage", "voyage:Voyage = Voyage.allInstance()->any(v:Voyage | v.VoyageNumber = number)\r\r\n");
		definitions_map.put("addCarrierMovement", "dlocation:Location = Location.allInstance()->any(l:Location | l.UnLocode = departure)\n\ralocation:Location = Location.allInstance()->any(l:Location | l.UnLocode = arrival)\n\rvoyage:Voyage = Voyage.allInstance()->any(v:Voyage | v.VoyageNumber = voyageNum)\r\r\n");
		definitions_map.put("createLoadEvent", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\r\r\n");
		definitions_map.put("createUnloadEvent", "c:Cargo = Cargo.allInstance()->any(c:Cargo | c.TrackingId = trackingId)\r\r\n");
		
		//precondition map
		preconditions_map = new HashMap<String, String>();
		preconditions_map.put("getCargoInfo", "c.oclIsUndefined() = false");
		preconditions_map.put("getAllCargoRoute", "routeSpecifications.oclIsUndefined() = false");
		preconditions_map.put("getCertainCargoRoute", "c.oclIsUndefined() = false");
		preconditions_map.put("getAllLocations", "locations.oclIsUndefined() = false");
		preconditions_map.put("createNewCargo", "c.oclIsUndefined() = true");
		preconditions_map.put("createRouteSpecification", "CurrentCargo.oclIsUndefined() = false and\norigin.oclIsUndefined() = false and\ndestination.oclIsUndefined() = false\n");
		preconditions_map.put("selectLocation", "location.oclIsUndefined() = false");
		preconditions_map.put("changeDestination", "c.oclIsUndefined() = false and\nroute.oclIsUndefined() = false and\ndelivery.oclIsUndefined() = false and\nCurrentLocation.oclIsUndefined() = false\n");
		preconditions_map.put("routeCargo", "c.oclIsUndefined() = false and\nlocation.oclIsUndefined() = false and\ncm.oclIsUndefined() = false and\nvoyage.oclIsUndefined() = false\n");
		preconditions_map.put("inputLocationInfo", "location.oclIsUndefined() = true");
		preconditions_map.put("createVoyage", "voyage.oclIsUndefined() = true");
		preconditions_map.put("addCarrierMovement", "dlocation.oclIsUndefined() = false and\nalocation.oclIsUndefined() = false and\nvoyage.oclIsUndefined() = false\n");
		preconditions_map.put("createLoadEvent", "c.oclIsUndefined() = false");
		preconditions_map.put("createUnloadEvent", "c.oclIsUndefined() = false");
		
		//postcondition map
		postconditions_map = new HashMap<String, String>();
		postconditions_map.put("getCargoInfo", "let res:GetCargoInfoResult inres.oclIsNew() and\nres.Events->includesAll(events) and\nres.Voyages->includesAll(voyages) and\nres.Delivery = c.Delivery and\nresult = res\n");
		postconditions_map.put("getAllCargoRoute", "result = routeSpecifications");
		postconditions_map.put("getCertainCargoRoute", "let res:GetCertainCargoRouteResult inres.oclIsNew() and\nres.Cargo = c and\nres.Itinerary = c.Itinerary and\nres.RouteSpecification = c.RouteSpecification and\nres.Delivery = c.Delivery and\nresult = res\n");
		postconditions_map.put("getAllLocations", "result = locations");
		postconditions_map.put("createNewCargo", "let c:Cargo inc.oclIsNew() and\nCurrentCargo = c and\nresult = true\n");
		postconditions_map.put("createRouteSpecification", "let rs:RouteSpecification inrs.oclIsNew() and\nrs.Origin = origin and\nrs.Destination = destination and\nrs.ArrivalDeadline = arrivalDeadline and\nCurrentCargo.RouteSpecification = rs and\nresult = true\n");
		postconditions_map.put("selectLocation", "CurrentLocation = location and\nresult = true\n");
		postconditions_map.put("changeDestination", "route.Destination = CurrentLocation and\ndelivery.TransportStatus = MISROUTED and\nresult = true\n");
		postconditions_map.put("routeCargo", "let route:RouteSpecification inroute.oclIsNew() and\nroute.Destination = location and\nitinerary.Specification = route\n");
		postconditions_map.put("inputLocationInfo", "let location:Location inlocation.oclIsNew() and\nlocation.UnLocode = locationCode and\nlocation.Name = name and\nresult = true\n");
		postconditions_map.put("createVoyage", "let voyage:Voyage invoyage.oclIsNew() and\nvoyage.VoyageNumber = number and\nresult = true\n");
		postconditions_map.put("addCarrierMovement", "let cm:CarrierMovement incm.oclIsNew() and\ncm.DepartureLocation = dlocation and\ncm.ArrivalLocation = alocation and\ncm.DepartureTime = departureTime and\ncm.ArrivalTime = arrivalTime and\ncm.Voyage = voyage and\nresult = true\n");
		postconditions_map.put("createLoadEvent", "let he:HandlingEvent inhe.oclIsNew() and\nhe.OperationType = OperationType::LOAD and\nhe.CompletionTime = completionTime and\nhe.RegistrationTime = registrationTime and\nhe.Handled = c and\nresult = true\n");
		postconditions_map.put("createUnloadEvent", "let he:HandlingEvent inhe.oclIsNew() and\nhe.OperationType = OperationType::UNLOAD and\nhe.CompletionTime = completionTime and\nhe.RegistrationTime = registrationTime and\nhe.Handled = c and\nresult = true\n");
		
		//service invariants map
		service_invariants_map = new LinkedHashMap<String, String>();
		
		//entity invariants map
		entity_invariants_map = new LinkedHashMap<String, String>();
		
	}
	
	public void generatOperationPane() {
		
		 operationPanels = new LinkedHashMap<String, GridPane>();
		
		 // ==================== GridPane_getCargoInfo ====================
		 GridPane getCargoInfo = new GridPane();
		 getCargoInfo.setHgap(4);
		 getCargoInfo.setVgap(6);
		 getCargoInfo.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> getCargoInfo_content = getCargoInfo.getChildren();
		 Label getCargoInfo_trackingId_label = new Label("trackingId:");
		 getCargoInfo_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 getCargoInfo_content.add(getCargoInfo_trackingId_label);
		 GridPane.setConstraints(getCargoInfo_trackingId_label, 0, 0);
		 
		 getCargoInfo_trackingId_t = new TextField();
		 getCargoInfo_content.add(getCargoInfo_trackingId_t);
		 getCargoInfo_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(getCargoInfo_trackingId_t, 1, 0);
		 operationPanels.put("getCargoInfo", getCargoInfo);
		 
		 // ==================== GridPane_getAllCargoRoute ====================
		 GridPane getAllCargoRoute = new GridPane();
		 getAllCargoRoute.setHgap(4);
		 getAllCargoRoute.setVgap(6);
		 getAllCargoRoute.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> getAllCargoRoute_content = getAllCargoRoute.getChildren();
		 Label getAllCargoRoute_label = new Label("This operation is no intput parameters..");
		 getAllCargoRoute_label.setMinWidth(Region.USE_PREF_SIZE);
		 getAllCargoRoute_content.add(getAllCargoRoute_label);
		 GridPane.setConstraints(getAllCargoRoute_label, 0, 0);
		 operationPanels.put("getAllCargoRoute", getAllCargoRoute);
		 
		 // ==================== GridPane_getCertainCargoRoute ====================
		 GridPane getCertainCargoRoute = new GridPane();
		 getCertainCargoRoute.setHgap(4);
		 getCertainCargoRoute.setVgap(6);
		 getCertainCargoRoute.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> getCertainCargoRoute_content = getCertainCargoRoute.getChildren();
		 Label getCertainCargoRoute_trackingId_label = new Label("trackingId:");
		 getCertainCargoRoute_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 getCertainCargoRoute_content.add(getCertainCargoRoute_trackingId_label);
		 GridPane.setConstraints(getCertainCargoRoute_trackingId_label, 0, 0);
		 
		 getCertainCargoRoute_trackingId_t = new TextField();
		 getCertainCargoRoute_content.add(getCertainCargoRoute_trackingId_t);
		 getCertainCargoRoute_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(getCertainCargoRoute_trackingId_t, 1, 0);
		 operationPanels.put("getCertainCargoRoute", getCertainCargoRoute);
		 
		 // ==================== GridPane_getAllLocations ====================
		 GridPane getAllLocations = new GridPane();
		 getAllLocations.setHgap(4);
		 getAllLocations.setVgap(6);
		 getAllLocations.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> getAllLocations_content = getAllLocations.getChildren();
		 Label getAllLocations_label = new Label("This operation is no intput parameters..");
		 getAllLocations_label.setMinWidth(Region.USE_PREF_SIZE);
		 getAllLocations_content.add(getAllLocations_label);
		 GridPane.setConstraints(getAllLocations_label, 0, 0);
		 operationPanels.put("getAllLocations", getAllLocations);
		 
		 // ==================== GridPane_createNewCargo ====================
		 GridPane createNewCargo = new GridPane();
		 createNewCargo.setHgap(4);
		 createNewCargo.setVgap(6);
		 createNewCargo.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createNewCargo_content = createNewCargo.getChildren();
		 Label createNewCargo_trackingId_label = new Label("trackingId:");
		 createNewCargo_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 createNewCargo_content.add(createNewCargo_trackingId_label);
		 GridPane.setConstraints(createNewCargo_trackingId_label, 0, 0);
		 
		 createNewCargo_trackingId_t = new TextField();
		 createNewCargo_content.add(createNewCargo_trackingId_t);
		 createNewCargo_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createNewCargo_trackingId_t, 1, 0);
		 operationPanels.put("createNewCargo", createNewCargo);
		 
		 // ==================== GridPane_createRouteSpecification ====================
		 GridPane createRouteSpecification = new GridPane();
		 createRouteSpecification.setHgap(4);
		 createRouteSpecification.setVgap(6);
		 createRouteSpecification.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createRouteSpecification_content = createRouteSpecification.getChildren();
		 Label createRouteSpecification_originCode_label = new Label("originCode:");
		 createRouteSpecification_originCode_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRouteSpecification_content.add(createRouteSpecification_originCode_label);
		 GridPane.setConstraints(createRouteSpecification_originCode_label, 0, 0);
		 
		 createRouteSpecification_originCode_t = new TextField();
		 createRouteSpecification_content.add(createRouteSpecification_originCode_t);
		 createRouteSpecification_originCode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRouteSpecification_originCode_t, 1, 0);
		 Label createRouteSpecification_destinationCode_label = new Label("destinationCode:");
		 createRouteSpecification_destinationCode_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRouteSpecification_content.add(createRouteSpecification_destinationCode_label);
		 GridPane.setConstraints(createRouteSpecification_destinationCode_label, 0, 1);
		 
		 createRouteSpecification_destinationCode_t = new TextField();
		 createRouteSpecification_content.add(createRouteSpecification_destinationCode_t);
		 createRouteSpecification_destinationCode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRouteSpecification_destinationCode_t, 1, 1);
		 Label createRouteSpecification_arrivalDeadline_label = new Label("arrivalDeadline (yyyy-MM-dd):");
		 createRouteSpecification_arrivalDeadline_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRouteSpecification_content.add(createRouteSpecification_arrivalDeadline_label);
		 GridPane.setConstraints(createRouteSpecification_arrivalDeadline_label, 0, 2);
		 
		 createRouteSpecification_arrivalDeadline_t = new TextField();
		 createRouteSpecification_content.add(createRouteSpecification_arrivalDeadline_t);
		 createRouteSpecification_arrivalDeadline_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRouteSpecification_arrivalDeadline_t, 1, 2);
		 operationPanels.put("createRouteSpecification", createRouteSpecification);
		 
		 // ==================== GridPane_selectLocation ====================
		 GridPane selectLocation = new GridPane();
		 selectLocation.setHgap(4);
		 selectLocation.setVgap(6);
		 selectLocation.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> selectLocation_content = selectLocation.getChildren();
		 Label selectLocation_locationCode_label = new Label("locationCode:");
		 selectLocation_locationCode_label.setMinWidth(Region.USE_PREF_SIZE);
		 selectLocation_content.add(selectLocation_locationCode_label);
		 GridPane.setConstraints(selectLocation_locationCode_label, 0, 0);
		 
		 selectLocation_locationCode_t = new TextField();
		 selectLocation_content.add(selectLocation_locationCode_t);
		 selectLocation_locationCode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(selectLocation_locationCode_t, 1, 0);
		 operationPanels.put("selectLocation", selectLocation);
		 
		 // ==================== GridPane_changeDestination ====================
		 GridPane changeDestination = new GridPane();
		 changeDestination.setHgap(4);
		 changeDestination.setVgap(6);
		 changeDestination.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> changeDestination_content = changeDestination.getChildren();
		 Label changeDestination_trackingId_label = new Label("trackingId:");
		 changeDestination_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 changeDestination_content.add(changeDestination_trackingId_label);
		 GridPane.setConstraints(changeDestination_trackingId_label, 0, 0);
		 
		 changeDestination_trackingId_t = new TextField();
		 changeDestination_content.add(changeDestination_trackingId_t);
		 changeDestination_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(changeDestination_trackingId_t, 1, 0);
		 operationPanels.put("changeDestination", changeDestination);
		 
		 // ==================== GridPane_routeCargo ====================
		 GridPane routeCargo = new GridPane();
		 routeCargo.setHgap(4);
		 routeCargo.setVgap(6);
		 routeCargo.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> routeCargo_content = routeCargo.getChildren();
		 Label routeCargo_trackingId_label = new Label("trackingId:");
		 routeCargo_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 routeCargo_content.add(routeCargo_trackingId_label);
		 GridPane.setConstraints(routeCargo_trackingId_label, 0, 0);
		 
		 routeCargo_trackingId_t = new TextField();
		 routeCargo_content.add(routeCargo_trackingId_t);
		 routeCargo_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(routeCargo_trackingId_t, 1, 0);
		 Label routeCargo_locationCode_label = new Label("locationCode:");
		 routeCargo_locationCode_label.setMinWidth(Region.USE_PREF_SIZE);
		 routeCargo_content.add(routeCargo_locationCode_label);
		 GridPane.setConstraints(routeCargo_locationCode_label, 0, 1);
		 
		 routeCargo_locationCode_t = new TextField();
		 routeCargo_content.add(routeCargo_locationCode_t);
		 routeCargo_locationCode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(routeCargo_locationCode_t, 1, 1);
		 operationPanels.put("routeCargo", routeCargo);
		 
		 // ==================== GridPane_inputLocationInfo ====================
		 GridPane inputLocationInfo = new GridPane();
		 inputLocationInfo.setHgap(4);
		 inputLocationInfo.setVgap(6);
		 inputLocationInfo.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputLocationInfo_content = inputLocationInfo.getChildren();
		 Label inputLocationInfo_locationCode_label = new Label("locationCode:");
		 inputLocationInfo_locationCode_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputLocationInfo_content.add(inputLocationInfo_locationCode_label);
		 GridPane.setConstraints(inputLocationInfo_locationCode_label, 0, 0);
		 
		 inputLocationInfo_locationCode_t = new TextField();
		 inputLocationInfo_content.add(inputLocationInfo_locationCode_t);
		 inputLocationInfo_locationCode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputLocationInfo_locationCode_t, 1, 0);
		 Label inputLocationInfo_name_label = new Label("name:");
		 inputLocationInfo_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputLocationInfo_content.add(inputLocationInfo_name_label);
		 GridPane.setConstraints(inputLocationInfo_name_label, 0, 1);
		 
		 inputLocationInfo_name_t = new TextField();
		 inputLocationInfo_content.add(inputLocationInfo_name_t);
		 inputLocationInfo_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputLocationInfo_name_t, 1, 1);
		 operationPanels.put("inputLocationInfo", inputLocationInfo);
		 
		 // ==================== GridPane_createVoyage ====================
		 GridPane createVoyage = new GridPane();
		 createVoyage.setHgap(4);
		 createVoyage.setVgap(6);
		 createVoyage.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createVoyage_content = createVoyage.getChildren();
		 Label createVoyage_number_label = new Label("number:");
		 createVoyage_number_label.setMinWidth(Region.USE_PREF_SIZE);
		 createVoyage_content.add(createVoyage_number_label);
		 GridPane.setConstraints(createVoyage_number_label, 0, 0);
		 
		 createVoyage_number_t = new TextField();
		 createVoyage_content.add(createVoyage_number_t);
		 createVoyage_number_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createVoyage_number_t, 1, 0);
		 operationPanels.put("createVoyage", createVoyage);
		 
		 // ==================== GridPane_addCarrierMovement ====================
		 GridPane addCarrierMovement = new GridPane();
		 addCarrierMovement.setHgap(4);
		 addCarrierMovement.setVgap(6);
		 addCarrierMovement.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> addCarrierMovement_content = addCarrierMovement.getChildren();
		 Label addCarrierMovement_departure_label = new Label("departure:");
		 addCarrierMovement_departure_label.setMinWidth(Region.USE_PREF_SIZE);
		 addCarrierMovement_content.add(addCarrierMovement_departure_label);
		 GridPane.setConstraints(addCarrierMovement_departure_label, 0, 0);
		 
		 addCarrierMovement_departure_t = new TextField();
		 addCarrierMovement_content.add(addCarrierMovement_departure_t);
		 addCarrierMovement_departure_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addCarrierMovement_departure_t, 1, 0);
		 Label addCarrierMovement_arrival_label = new Label("arrival:");
		 addCarrierMovement_arrival_label.setMinWidth(Region.USE_PREF_SIZE);
		 addCarrierMovement_content.add(addCarrierMovement_arrival_label);
		 GridPane.setConstraints(addCarrierMovement_arrival_label, 0, 1);
		 
		 addCarrierMovement_arrival_t = new TextField();
		 addCarrierMovement_content.add(addCarrierMovement_arrival_t);
		 addCarrierMovement_arrival_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addCarrierMovement_arrival_t, 1, 1);
		 Label addCarrierMovement_departureTime_label = new Label("departureTime (yyyy-MM-dd):");
		 addCarrierMovement_departureTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 addCarrierMovement_content.add(addCarrierMovement_departureTime_label);
		 GridPane.setConstraints(addCarrierMovement_departureTime_label, 0, 2);
		 
		 addCarrierMovement_departureTime_t = new TextField();
		 addCarrierMovement_content.add(addCarrierMovement_departureTime_t);
		 addCarrierMovement_departureTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addCarrierMovement_departureTime_t, 1, 2);
		 Label addCarrierMovement_arrivalTime_label = new Label("arrivalTime (yyyy-MM-dd):");
		 addCarrierMovement_arrivalTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 addCarrierMovement_content.add(addCarrierMovement_arrivalTime_label);
		 GridPane.setConstraints(addCarrierMovement_arrivalTime_label, 0, 3);
		 
		 addCarrierMovement_arrivalTime_t = new TextField();
		 addCarrierMovement_content.add(addCarrierMovement_arrivalTime_t);
		 addCarrierMovement_arrivalTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addCarrierMovement_arrivalTime_t, 1, 3);
		 Label addCarrierMovement_voyageNum_label = new Label("voyageNum:");
		 addCarrierMovement_voyageNum_label.setMinWidth(Region.USE_PREF_SIZE);
		 addCarrierMovement_content.add(addCarrierMovement_voyageNum_label);
		 GridPane.setConstraints(addCarrierMovement_voyageNum_label, 0, 4);
		 
		 addCarrierMovement_voyageNum_t = new TextField();
		 addCarrierMovement_content.add(addCarrierMovement_voyageNum_t);
		 addCarrierMovement_voyageNum_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addCarrierMovement_voyageNum_t, 1, 4);
		 operationPanels.put("addCarrierMovement", addCarrierMovement);
		 
		 // ==================== GridPane_createLoadEvent ====================
		 GridPane createLoadEvent = new GridPane();
		 createLoadEvent.setHgap(4);
		 createLoadEvent.setVgap(6);
		 createLoadEvent.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createLoadEvent_content = createLoadEvent.getChildren();
		 Label createLoadEvent_completionTime_label = new Label("completionTime (yyyy-MM-dd):");
		 createLoadEvent_completionTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 createLoadEvent_content.add(createLoadEvent_completionTime_label);
		 GridPane.setConstraints(createLoadEvent_completionTime_label, 0, 0);
		 
		 createLoadEvent_completionTime_t = new TextField();
		 createLoadEvent_content.add(createLoadEvent_completionTime_t);
		 createLoadEvent_completionTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createLoadEvent_completionTime_t, 1, 0);
		 Label createLoadEvent_registrationTime_label = new Label("registrationTime (yyyy-MM-dd):");
		 createLoadEvent_registrationTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 createLoadEvent_content.add(createLoadEvent_registrationTime_label);
		 GridPane.setConstraints(createLoadEvent_registrationTime_label, 0, 1);
		 
		 createLoadEvent_registrationTime_t = new TextField();
		 createLoadEvent_content.add(createLoadEvent_registrationTime_t);
		 createLoadEvent_registrationTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createLoadEvent_registrationTime_t, 1, 1);
		 Label createLoadEvent_trackingId_label = new Label("trackingId:");
		 createLoadEvent_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 createLoadEvent_content.add(createLoadEvent_trackingId_label);
		 GridPane.setConstraints(createLoadEvent_trackingId_label, 0, 2);
		 
		 createLoadEvent_trackingId_t = new TextField();
		 createLoadEvent_content.add(createLoadEvent_trackingId_t);
		 createLoadEvent_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createLoadEvent_trackingId_t, 1, 2);
		 operationPanels.put("createLoadEvent", createLoadEvent);
		 
		 // ==================== GridPane_createUnloadEvent ====================
		 GridPane createUnloadEvent = new GridPane();
		 createUnloadEvent.setHgap(4);
		 createUnloadEvent.setVgap(6);
		 createUnloadEvent.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createUnloadEvent_content = createUnloadEvent.getChildren();
		 Label createUnloadEvent_completionTime_label = new Label("completionTime (yyyy-MM-dd):");
		 createUnloadEvent_completionTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUnloadEvent_content.add(createUnloadEvent_completionTime_label);
		 GridPane.setConstraints(createUnloadEvent_completionTime_label, 0, 0);
		 
		 createUnloadEvent_completionTime_t = new TextField();
		 createUnloadEvent_content.add(createUnloadEvent_completionTime_t);
		 createUnloadEvent_completionTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUnloadEvent_completionTime_t, 1, 0);
		 Label createUnloadEvent_registrationTime_label = new Label("registrationTime (yyyy-MM-dd):");
		 createUnloadEvent_registrationTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUnloadEvent_content.add(createUnloadEvent_registrationTime_label);
		 GridPane.setConstraints(createUnloadEvent_registrationTime_label, 0, 1);
		 
		 createUnloadEvent_registrationTime_t = new TextField();
		 createUnloadEvent_content.add(createUnloadEvent_registrationTime_t);
		 createUnloadEvent_registrationTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUnloadEvent_registrationTime_t, 1, 1);
		 Label createUnloadEvent_trackingId_label = new Label("trackingId:");
		 createUnloadEvent_trackingId_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUnloadEvent_content.add(createUnloadEvent_trackingId_label);
		 GridPane.setConstraints(createUnloadEvent_trackingId_label, 0, 2);
		 
		 createUnloadEvent_trackingId_t = new TextField();
		 createUnloadEvent_content.add(createUnloadEvent_trackingId_t);
		 createUnloadEvent_trackingId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUnloadEvent_trackingId_t, 1, 2);
		 operationPanels.put("createUnloadEvent", createUnloadEvent);
		 
	}	

	public void actorTreeViewBinding() {
		
		 

		TreeItem<String> treeRootadministrator = new TreeItem<String>("Root node");
		
		
					 			
						 		
		treeRootadministrator.getChildren().addAll(Arrays.asList(
				));	
				
	 			treeRootadministrator.setExpanded(true);

		actor_treeview_administrator.setShowRoot(false);
		actor_treeview_administrator.setRoot(treeRootadministrator);
	 		
		actor_treeview_administrator.getSelectionModel().selectedItemProperty().addListener(
		 				 (observable, oldValue, newValue) -> { 
		 				 								
		 				 							 //clear the previous return
		 											 operation_return_pane.setContent(new Label());
		 											 
		 				 							 clickedOp = newValue.getValue();
		 				 							 GridPane op = operationPanels.get(clickedOp);
		 				 							 VBox vb = opInvariantPanel.get(clickedOp);
		 				 							 
		 				 							 //op pannel
		 				 							 if (op != null) {
		 				 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
		 				 								 
		 				 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
		 				 								 choosenOperation = new LinkedList<TextField>();
		 				 								 for (Node n : l) {
		 				 								 	 if (n instanceof TextField) {
		 				 								 	 	choosenOperation.add((TextField)n);
		 				 								 	  }
		 				 								 }
		 				 								 
		 				 								 definition.setText(definitions_map.get(newValue.getValue()));
		 				 								 precondition.setText(preconditions_map.get(newValue.getValue()));
		 				 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
		 				 								 
		 				 						     }
		 				 							 else {
		 				 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
		 				 								 l.setPadding(new Insets(8, 8, 8, 8));
		 				 								 operation_paras.setContent(l);
		 				 							 }	
		 				 							 
		 				 							 //op invariants
		 				 							 if (vb != null) {
		 				 							 	ScrollPane scrollPane = new ScrollPane(vb);
		 				 							 	scrollPane.setFitToWidth(true);
		 				 							 	invariants_panes.setMaxHeight(200); 
		 				 							 	//all_invariant_pane.setContent(scrollPane);	
		 				 							 	
		 				 							 	invariants_panes.setContent(scrollPane);
		 				 							 } else {
		 				 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
		 				 							     l.setPadding(new Insets(8, 8, 8, 8));
		 				 							     invariants_panes.setContent(l);
		 				 							 }
		 				 							 
		 				 							 //reset pre- and post-conditions area color
		 				 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
		 				 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
		 				 							 //reset condition panel title
		 				 							 precondition_pane.setText("Precondition");
		 				 							 postcondition_pane.setText("Postcondition");
		 				 						} 
		 				 				);

		
		
		 
		TreeItem<String> treeRootcustomer = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_viewTracking = new TreeItem<String>("viewTracking");
			subTreeRoot_viewTracking.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("getCargoInfo")
				 		));	
		
		treeRootcustomer.getChildren().addAll(Arrays.asList(
			subTreeRoot_viewTracking
					));
		
		treeRootcustomer.setExpanded(true);

		actor_treeview_customer.setShowRoot(false);
		actor_treeview_customer.setRoot(treeRootcustomer);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_customer.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootadmin = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_createLocation = new TreeItem<String>("createLocation");
			subTreeRoot_createLocation.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("inputLocationInfo")
				 		));	
		
		treeRootadmin.getChildren().addAll(Arrays.asList(
			subTreeRoot_createLocation
					));
		
		treeRootadmin.setExpanded(true);

		actor_treeview_admin.setShowRoot(false);
		actor_treeview_admin.setRoot(treeRootadmin);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_admin.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootcargoplanner = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_viewCargos = new TreeItem<String>("viewCargos");
			subTreeRoot_viewCargos.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("getAllCargoRoute"),
					 	new TreeItem<String>("getCertainCargoRoute")
				 		));	
			TreeItem<String> subTreeRoot_bookCargo = new TreeItem<String>("bookCargo");
			subTreeRoot_bookCargo.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("getAllLocations"),
					 	new TreeItem<String>("createNewCargo"),
					 	new TreeItem<String>("createRouteSpecification")
				 		));	
			TreeItem<String> subTreeRoot_changeCargoDestination = new TreeItem<String>("changeCargoDestination");
			subTreeRoot_changeCargoDestination.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("selectLocation"),
					 	new TreeItem<String>("changeDestination")
				 		));	
			TreeItem<String> subTreeRoot_routeCargo = new TreeItem<String>("routeCargo");
			subTreeRoot_routeCargo.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("routeCargo")
				 		));	
		
		treeRootcargoplanner.getChildren().addAll(Arrays.asList(
			subTreeRoot_viewCargos,
			subTreeRoot_bookCargo,
			subTreeRoot_changeCargoDestination,
			subTreeRoot_routeCargo
					));
		
		treeRootcargoplanner.setExpanded(true);

		actor_treeview_cargoplanner.setShowRoot(false);
		actor_treeview_cargoplanner.setRoot(treeRootcargoplanner);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_cargoplanner.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootvoyagemanager = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_createVoyage = new TreeItem<String>("createVoyage");
			subTreeRoot_createVoyage.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("createVoyage")
				 		));	
			TreeItem<String> subTreeRoot_addCarrierMovement = new TreeItem<String>("addCarrierMovement");
			subTreeRoot_addCarrierMovement.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("addCarrierMovement")
				 		));	
		
		treeRootvoyagemanager.getChildren().addAll(Arrays.asList(
			subTreeRoot_createVoyage,
			subTreeRoot_addCarrierMovement
					));
		
		treeRootvoyagemanager.setExpanded(true);

		actor_treeview_voyagemanager.setShowRoot(false);
		actor_treeview_voyagemanager.setRoot(treeRootvoyagemanager);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_voyagemanager.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootcargotracker = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_handleCargoEvent = new TreeItem<String>("handleCargoEvent");
			subTreeRoot_handleCargoEvent.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("createLoadEvent"),
					 	new TreeItem<String>("createUnloadEvent")
				 		));	
		
		treeRootcargotracker.getChildren().addAll(Arrays.asList(
			subTreeRoot_handleCargoEvent
					));
		
		treeRootcargotracker.setExpanded(true);

		actor_treeview_cargotracker.setShowRoot(false);
		actor_treeview_cargotracker.setRoot(treeRootcargotracker);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_cargotracker.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
	}

	/**
	*    Execute Operation
	*/
	@FXML
	public void execute(ActionEvent event) {
		
		switch (clickedOp) {
		case "getCargoInfo" : getCargoInfo(); break;
		case "getAllCargoRoute" : getAllCargoRoute(); break;
		case "getCertainCargoRoute" : getCertainCargoRoute(); break;
		case "getAllLocations" : getAllLocations(); break;
		case "createNewCargo" : createNewCargo(); break;
		case "createRouteSpecification" : createRouteSpecification(); break;
		case "selectLocation" : selectLocation(); break;
		case "changeDestination" : changeDestination(); break;
		case "routeCargo" : routeCargo(); break;
		case "inputLocationInfo" : inputLocationInfo(); break;
		case "createVoyage" : createVoyage(); break;
		case "addCarrierMovement" : addCarrierMovement(); break;
		case "createLoadEvent" : createLoadEvent(); break;
		case "createUnloadEvent" : createUnloadEvent(); break;
		
		}
		
		System.out.println("execute buttion clicked");
		
		//checking relevant invariants
		opInvairantPanelUpdate();
	}

	/**
	*    Refresh All
	*/		
	@FXML
	public void refresh(ActionEvent event) {
		
		refreshAll();
		System.out.println("refresh all");
	}		
	
	/**
	*    Save All
	*/			
	@FXML
	public void save(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save State to File");
		fileChooser.setInitialFileName("*.state");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			System.out.println("save state to file " + file.getAbsolutePath());				
			EntityManager.save(file);
		}
	}
	
	/**
	*    Load All
	*/			
	@FXML
	public void load(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open State File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			System.out.println("choose file" + file.getAbsolutePath());
			EntityManager.load(file); 
		}
		
		//refresh GUI after load data
		refreshAll();
	}
	
	
	//precondition unsat dialog
	public void preconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Precondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}
	
	//postcondition unsat dialog
	public void postconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Postcondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}

	public void thirdpartyServiceUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("third party service is exception");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}		
	
	
	public void getCargoInfo() {
		
		System.out.println("execute getCargoInfo");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: getCargoInfo in service: ViewTrackingService ");
		
		try {
			//invoke op with parameters
				GetCargoInfoResult r = viewtrackingservice_service.getCargoInfo(
				getCargoInfo_trackingId_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableGetCargoInfoResult = new TableView<Map<String, String>>();
				
				ObservableList<Map<String, String>> dataGetCargoInfoResult = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					dataGetCargoInfoResult.add(unit);
				
				
				tableGetCargoInfoResult.setItems(dataGetCargoInfoResult);
				operation_return_pane.setContent(tableGetCargoInfoResult);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void getAllCargoRoute() {
		
		System.out.println("execute getAllCargoRoute");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: getAllCargoRoute in service: ViewCargosService ");
		
		try {
			//invoke op with parameters
					List<RouteSpecification> result = viewcargosservice_service.getAllCargoRoute(
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableRouteSpecification = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableRouteSpecification_ArrivalDeadline = new TableColumn<Map<String, String>, String>("ArrivalDeadline");
					tableRouteSpecification_ArrivalDeadline.setMinWidth("ArrivalDeadline".length()*10);
					tableRouteSpecification_ArrivalDeadline.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ArrivalDeadline"));
					    }
					});	
					tableRouteSpecification.getColumns().add(tableRouteSpecification_ArrivalDeadline);
					
					ObservableList<Map<String, String>> dataRouteSpecification = FXCollections.observableArrayList();
					for (RouteSpecification r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getArrivalDeadline() != null)
							unit.put("ArrivalDeadline", r.getArrivalDeadline().format(dateformatter));
						else
							unit.put("ArrivalDeadline", "");
						dataRouteSpecification.add(unit);
					}
					
					tableRouteSpecification.setItems(dataRouteSpecification);
					operation_return_pane.setContent(tableRouteSpecification);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void getCertainCargoRoute() {
		
		System.out.println("execute getCertainCargoRoute");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: getCertainCargoRoute in service: ViewCargosService ");
		
		try {
			//invoke op with parameters
				GetCertainCargoRouteResult r = viewcargosservice_service.getCertainCargoRoute(
				getCertainCargoRoute_trackingId_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableGetCertainCargoRouteResult = new TableView<Map<String, String>>();
				
				ObservableList<Map<String, String>> dataGetCertainCargoRouteResult = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					dataGetCertainCargoRouteResult.add(unit);
				
				
				tableGetCertainCargoRouteResult.setItems(dataGetCertainCargoRouteResult);
				operation_return_pane.setContent(tableGetCertainCargoRouteResult);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void getAllLocations() {
		
		System.out.println("execute getAllLocations");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: getAllLocations in service: BookCargoService ");
		
		try {
			//invoke op with parameters
					List<Location> result = bookcargoservice_service.getAllLocations(
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableLocation = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableLocation_UnLocode = new TableColumn<Map<String, String>, String>("UnLocode");
					tableLocation_UnLocode.setMinWidth("UnLocode".length()*10);
					tableLocation_UnLocode.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("UnLocode"));
					    }
					});	
					tableLocation.getColumns().add(tableLocation_UnLocode);
					TableColumn<Map<String, String>, String> tableLocation_Name = new TableColumn<Map<String, String>, String>("Name");
					tableLocation_Name.setMinWidth("Name".length()*10);
					tableLocation_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
					    }
					});	
					tableLocation.getColumns().add(tableLocation_Name);
					
					ObservableList<Map<String, String>> dataLocation = FXCollections.observableArrayList();
					for (Location r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getUnLocode() != null)
							unit.put("UnLocode", String.valueOf(r.getUnLocode()));
						else
							unit.put("UnLocode", "");
						if (r.getName() != null)
							unit.put("Name", String.valueOf(r.getName()));
						else
							unit.put("Name", "");
						dataLocation.add(unit);
					}
					
					tableLocation.setItems(dataLocation);
					operation_return_pane.setContent(tableLocation);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createNewCargo() {
		
		System.out.println("execute createNewCargo");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createNewCargo in service: BookCargoService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(bookcargoservice_service.createNewCargo(
			createNewCargo_trackingId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createRouteSpecification() {
		
		System.out.println("execute createRouteSpecification");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createRouteSpecification in service: BookCargoService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(bookcargoservice_service.createRouteSpecification(
			createRouteSpecification_originCode_t.getText(),
			createRouteSpecification_destinationCode_t.getText(),
			LocalDate.parse(createRouteSpecification_arrivalDeadline_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void selectLocation() {
		
		System.out.println("execute selectLocation");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: selectLocation in service: ChangeCargoDestinationService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(changecargodestinationservice_service.selectLocation(
			selectLocation_locationCode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void changeDestination() {
		
		System.out.println("execute changeDestination");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: changeDestination in service: ChangeCargoDestinationService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(changecargodestinationservice_service.changeDestination(
			changeDestination_trackingId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void routeCargo() {
		
		System.out.println("execute routeCargo");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: routeCargo in service: RouteCargoService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(routecargoservice_service.routeCargo(
			routeCargo_trackingId_t.getText(),
			routeCargo_locationCode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputLocationInfo() {
		
		System.out.println("execute inputLocationInfo");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputLocationInfo in service: CreateLocationService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(createlocationservice_service.inputLocationInfo(
			inputLocationInfo_locationCode_t.getText(),
			inputLocationInfo_name_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createVoyage() {
		
		System.out.println("execute createVoyage");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createVoyage in service: CreateVoyageService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(createvoyageservice_service.createVoyage(
			createVoyage_number_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void addCarrierMovement() {
		
		System.out.println("execute addCarrierMovement");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: addCarrierMovement in service: AddCarrierMovementService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(addcarriermovementservice_service.addCarrierMovement(
			addCarrierMovement_departure_t.getText(),
			addCarrierMovement_arrival_t.getText(),
			LocalDate.parse(addCarrierMovement_departureTime_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			LocalDate.parse(addCarrierMovement_arrivalTime_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			addCarrierMovement_voyageNum_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createLoadEvent() {
		
		System.out.println("execute createLoadEvent");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createLoadEvent in service: HandleCargoEventService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(handlecargoeventservice_service.createLoadEvent(
			LocalDate.parse(createLoadEvent_completionTime_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			LocalDate.parse(createLoadEvent_registrationTime_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			createLoadEvent_trackingId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createUnloadEvent() {
		
		System.out.println("execute createUnloadEvent");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createUnloadEvent in service: HandleCargoEventService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(handlecargoeventservice_service.createUnloadEvent(
			LocalDate.parse(createUnloadEvent_completionTime_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			LocalDate.parse(createUnloadEvent_registrationTime_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			createUnloadEvent_trackingId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}




	//select object index
	int objectindex;
	
	@FXML
	TabPane mainPane;

	@FXML
	TextArea log;
	
	@FXML
	TreeView<String> actor_treeview_customer;
	@FXML
	TreeView<String> actor_treeview_admin;
	@FXML
	TreeView<String> actor_treeview_cargoplanner;
	@FXML
	TreeView<String> actor_treeview_voyagemanager;
	@FXML
	TreeView<String> actor_treeview_cargotracker;
	
	@FXML
	TreeView<String> actor_treeview_administrator;


	@FXML
	TextArea definition;
	@FXML
	TextArea precondition;
	@FXML
	TextArea postcondition;
	@FXML
	TextArea invariants;
	
	@FXML
	TitledPane precondition_pane;
	@FXML
	TitledPane postcondition_pane;
	
	//chosen operation textfields
	List<TextField> choosenOperation;
	String clickedOp;
		
	@FXML
	TableView<ClassInfo> class_statisic;
	@FXML
	TableView<AssociationInfo> association_statisic;
	
	Map<String, ObservableList<AssociationInfo>> allassociationData;
	ObservableList<ClassInfo> classInfodata;
	
	CargoTrackingSystemSystem cargotrackingsystemsystem_service;
	ThirdPartyServices thirdpartyservices_service;
	ViewTrackingService viewtrackingservice_service;
	CreateLocationService createlocationservice_service;
	CreateVoyageService createvoyageservice_service;
	ViewCargosService viewcargosservice_service;
	BookCargoService bookcargoservice_service;
	ChangeCargoDestinationService changecargodestinationservice_service;
	RouteCargoService routecargoservice_service;
	AddCarrierMovementService addcarriermovementservice_service;
	HandleCargoEventService handlecargoeventservice_service;
	
	ClassInfo cargo;
	ClassInfo location;
	ClassInfo handlingevent;
	ClassInfo carriermovement;
	ClassInfo user;
	ClassInfo routespecification;
	ClassInfo itinerary;
	ClassInfo voyage;
	ClassInfo leg;
	ClassInfo delivery;
	ClassInfo getcargoinforesult;
	ClassInfo getcertaincargorouteresult;
		
	@FXML
	TitledPane object_statics;
	Map<String, TableView> allObjectTables;
	
	@FXML
	TitledPane operation_paras;
	
	@FXML
	TitledPane operation_return_pane;
	
	@FXML 
	TitledPane all_invariant_pane;
	
	@FXML
	TitledPane invariants_panes;
	
	Map<String, GridPane> operationPanels;
	Map<String, VBox> opInvariantPanel;
	
	//all textfiled or eumntity
	TextField getCargoInfo_trackingId_t;
	TextField getCertainCargoRoute_trackingId_t;
	TextField createNewCargo_trackingId_t;
	TextField createRouteSpecification_originCode_t;
	TextField createRouteSpecification_destinationCode_t;
	TextField createRouteSpecification_arrivalDeadline_t;
	TextField selectLocation_locationCode_t;
	TextField changeDestination_trackingId_t;
	TextField routeCargo_trackingId_t;
	TextField routeCargo_locationCode_t;
	TextField inputLocationInfo_locationCode_t;
	TextField inputLocationInfo_name_t;
	TextField createVoyage_number_t;
	TextField addCarrierMovement_departure_t;
	TextField addCarrierMovement_arrival_t;
	TextField addCarrierMovement_departureTime_t;
	TextField addCarrierMovement_arrivalTime_t;
	TextField addCarrierMovement_voyageNum_t;
	TextField createLoadEvent_completionTime_t;
	TextField createLoadEvent_registrationTime_t;
	TextField createLoadEvent_trackingId_t;
	TextField createUnloadEvent_completionTime_t;
	TextField createUnloadEvent_registrationTime_t;
	TextField createUnloadEvent_trackingId_t;
	
	HashMap<String, String> definitions_map;
	HashMap<String, String> preconditions_map;
	HashMap<String, String> postconditions_map;
	HashMap<String, String> invariants_map;
	LinkedHashMap<String, String> service_invariants_map;
	LinkedHashMap<String, String> entity_invariants_map;
	LinkedHashMap<String, Label> service_invariants_label_map;
	LinkedHashMap<String, Label> entity_invariants_label_map;
	LinkedHashMap<String, Label> op_entity_invariants_label_map;
	LinkedHashMap<String, Label> op_service_invariants_label_map;
	

	
}
