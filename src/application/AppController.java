package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

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
    private ComboBox<SolveStrategy> comboBox = new ComboBox<SolveStrategy>();
    
    SolveStrategy s1 = new MSTSolve();
	SolveStrategy s2 = new DynamicSolve();
	SolveStrategy s3 = new NaiveSolve();
	
	ObservableList<SolveStrategy> strategies = FXCollections.observableArrayList(s1, s2, s3);

    public void initialize(){
    	comboBox.setItems(strategies);
    	comboBox.getSelectionModel().selectFirst();
    }
}
