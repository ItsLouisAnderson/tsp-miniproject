package application;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputDialog;
import javafx.event.ActionEvent;
import javafx.scene.paint.*;

import model.*;
import algorithm.*;

public class AppController{
	
	@FXML
    private Pane controlPane;
	
	@FXML
	private Pane graphPane;

    @FXML
    private Button clearButton;

    @FXML
    private Button startButton;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private CheckBox stepCheck;

    @FXML
    private ComboBox<SolveStrategy> comboBox = new ComboBox<SolveStrategy>();
    
    SolveStrategy s1 = new MSTSolve();
	SolveStrategy s2 = new DynamicSolve();
	SolveStrategy s3 = new NaiveSolve();	
	ObservableList<SolveStrategy> strategies = FXCollections.observableArrayList(s1, s2, s3);
	
	Graph graph = new Graph();

	//---------------------------------------------------
	// Scene Initialization
	//---------------------------------------------------
    public void initialize(){
    	comboBox.setItems(strategies);
    	comboBox.getSelectionModel().selectFirst();
    	nextButton.setVisible(false);
    	startButton.setDisable(true);
    }
    
    //---------------------------------------------------
  	// When left click on pane -> create and add city
  	//---------------------------------------------------
    public void onGraphPressed(MouseEvent mouseEvent) {
    	if (mouseEvent.isPrimaryButtonDown()) {
    		graphPane.getChildren().add(createAndAddCity(mouseEvent));
    		startButton.setDisable(false);
    	}
    }
    private Node createAndAddCity(MouseEvent mouseEvent) {
    	City city = new City(mouseEvent.getX(), mouseEvent.getY());
    	graph.addCity(city);
    	graphPane.getChildren().add(city.getCityLabel());
        	
        city.setOnMousePressed(e -> onCityPressed(e, city));
        
        // If right click on city label -> prompt to rename
        city.getCityLabel().setOnMousePressed(e -> {
        	if (e.isSecondaryButtonDown()) {
        		TextInputDialog dialog = new TextInputDialog();
        		dialog.setTitle("Renaming city");
        		dialog.setHeaderText("Renaming city");
        		dialog.setContentText("Please enter new city name:");
        	
        		Optional<String> result = dialog.showAndWait();
        		result.ifPresent(name -> city.setName(name));
        	}
        });
        
        return city;
    }
    
    //---------------------------------------------------
  	// When right click on city -> remove city
  	//---------------------------------------------------
    private void onCityPressed(MouseEvent e, City c) {
    	if (e.isSecondaryButtonDown()) {
    		graphPane.getChildren().remove(c);
    		graphPane.getChildren().remove(c.getCityLabel());
    		graph.removeCity(c);
    	}
    }
    
    //---------------------------------------------------
  	// When click start
  	//---------------------------------------------------
    public void onStart(ActionEvent event) {
    	startButton.setDisable(true);
    	if (stepCheck.isSelected()) {
    		nextButton.setVisible(true);
    	}
    	if (comboBox.getValue().equals(s1)) {
    		graphPane.getChildren().addAll(((MSTSolve)s1).mst(graph.getCityList()));
    	}
    	else if (comboBox.getValue().equals(s2)) {
    		System.out.println(s2.solve(graph));
    	}
    	
    	startButton.setDisable(false);
    }
    
    //---------------------------------------------------
  	// When click clear -> remove all cities
  	//---------------------------------------------------
    public void onClear(ActionEvent event) {
    	graphPane.getChildren().clear();
    	graph.getCityList().clear();
    	City.resetCityNo();
    }
    
}
