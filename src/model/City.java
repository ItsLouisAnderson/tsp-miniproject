package model;

import java.lang.Math;

public class City {
	private String name;
	private float x;
	private float y;
	
	public City(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public City(String name, float x, float y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public float getDistance(City c) {
		return (float)Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
	}
}
