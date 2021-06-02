package model;

import java.lang.Math;
import java.util.List;

import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class City extends Circle{
	public static final int MAX_NUMBER_OF_CITIES = 10;
	private static int cityNo = 1;
	private StringProperty name = new SimpleStringProperty();
	private Label cityLabel = new Label();
	
	public City(double x, double y) {
		this.name.setValue("City" + String.valueOf(cityNo));
		this.setCenterX(x);
		this.setCenterY(y);
		this.setRadius(5);
		this.cityLabel.textProperty().bind(name);
		this.cityLabel.setLayoutX(x + 5);
		this.cityLabel.setLayoutY(y);
		cityNo++;
	}
	public City(String name, double x, double y) {
		this.name.setValue(name);
		this.setCenterX(x);
		this.setCenterY(y);
		this.setRadius(5);
		this.cityLabel.setText(this.name + " ");
		this.cityLabel.setLayoutX(x + 5);
		this.cityLabel.setLayoutY(y);
	}

	public String getName() {
		return name.getValue();
	}

	public void setName(String name) {
		this.name.setValue(name);
	}
	
	public double getDistance(City c) {
		return Math.sqrt(Math.pow(this.getCenterX() - c.getCenterX(), 2)
						+ Math.pow(this.getCenterY() - c.getCenterY(), 2));
	}
	public Label getCityLabel() {
		return cityLabel;
	}
	
	public static void resetCityNo() {
		cityNo = 1;
	}
	
	public int degree(List<Path> pathList) {
		int degree = 0;
		for (Path p: pathList) {
			if (p.contains(this)) {
				degree++;
			}
		}
		return degree;
	}
	
	@Override
	public String toString() {
		return this.name.getValue();
	}
	
}
