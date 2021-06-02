package model;

import javafx.scene.shape.Line;

public class Path extends Line{
	private City start;
	private City end;
	private double weight;
	
	public Path(City s, City e) {
		this.start = s;
		this.end = e;
		this.setStartX(s.getCenterX());
		this.setStartY(s.getCenterY());
		this.setEndX(e.getCenterX());
		this.setEndY(e.getCenterY());
		this.weight = s.getDistance(e);
	}
	
	public City getStartCity() {
		return this.start;
	}
	public City getEndCity() {
		return this.end;
	}
	public double getWeight() {
		return this.weight;
	}
	
	public boolean contains(City c) {
		if (c.equals(this.start) || c.equals(this.end)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.start + "-" + this.end;
	}

}
