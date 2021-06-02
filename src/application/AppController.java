package application;

import java.util.Optional;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputDialog;
import javafx.event.ActionEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.animation.FillTransition;
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
    
    @FXML
    private Label stepLabel;
    
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
    	stepLabel.setVisible(false);
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
    	clearAllPaths();
    	startButton.setDisable(true);
    	if (stepCheck.isSelected()) {
    		nextButton.setVisible(true);
    		stepLabel.setVisible(true);
    	}
    	if (comboBox.getValue().equals(s1)) {
    		onMSTStart();
    	}
    	else if (comboBox.getValue().equals(s2)) {
    		onDynamicStart();
    	}
    }
    
    public void onMSTStart() {
    	List<Path> mst = ((MSTSolve)s1).mst(graph.getCityList());
		List<City> oddCities = ((MSTSolve)s1).oddVertices(graph.getCityList(), mst);
		List<Path> matching = ((MSTSolve)s1).perfectMatching(oddCities);
		List<City> euler = (((MSTSolve)s1).eulerTour(graph.getCityList(), mst, matching));
		List<Path> result = (((MSTSolve)s1).makeHamiltonian(euler));
		
		if (stepCheck.isSelected()) {
			stepLabel.setText("Step 1: Find the minimum spanning tree");
			graphPane.getChildren().addAll(mst);
			nextButton.setOnAction(e2 -> {
				stepLabel.setText("Step 2: Find the odd vertices: " + oddCities);
				for (City i: oddCities) {
					i.setFill(Color.BLUE);
				}
				nextButton.setOnAction(e3 -> {
					stepLabel.setText("Step 3: Create minimum matching\nA Eulerian path is formed:\n" + euler);
					graphPane.getChildren().addAll(matching);
					nextButton.setOnAction(e4 -> {
						stepLabel.setText("Step 4: Create a Hamiltonian path:\n" + result + "\nTotal distance: " + (float)getTotalWeight(result));
						clearAllPaths();
						graphPane.getChildren().addAll(result);
						nextButton.setVisible(false);
						startButton.setDisable(false);
						for (City i: oddCities) {
							i.setFill(Color.BLACK);
						}
					});
				});
			});
		}
		
    }
    
    public void onDynamicStart() {
    	graphPane.getChildren().removeIf((Node t) -> t.getClass().getSimpleName().equals("Path"));
		System.out.println(s2.solve(graph));
    }
    
    //---------------------------------------------------
  	// When click clear -> remove all cities
  	//---------------------------------------------------
    public void onClear(ActionEvent event) {
    	graphPane.getChildren().clear();
    	graph.getCityList().clear();
    	City.resetCityNo();
    }
    
    public void clearAllPaths() {
    	graphPane.getChildren().removeIf((Node t) -> t.getClass().getSimpleName().equals("Path"));
    }
    public double getTotalWeight(List<Path> pathList) {
    	double result = 0;
    	for (Path p: pathList) {
    		result += p.getWeight();
    	}
    	return result;
    }
 
}
