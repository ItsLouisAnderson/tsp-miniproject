package model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private List<City> cityList = new ArrayList<City>();
	
	public List<City> getCityList(){
		return cityList;
	}
	public void addCity(City c) {
		this.cityList.add(c);
	}
	public void removeCity(City c) {
		this.cityList.remove(c);
	}
}
