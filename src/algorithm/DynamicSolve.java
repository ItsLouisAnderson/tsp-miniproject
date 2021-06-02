package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

import model.City;
import model.Graph;

public class DynamicSolve extends SolveStrategy{
	private String name = "Dynamic Programming";
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public double cost(List<City> l, City i, City start) {
		if (l.size() == 1) {
			return i.getDistance(start);
		}
		else {
			l.remove(i);
			Map<City, Double> tmp = new HashMap<City, Double>();
			for (City j: l) {
				tmp.put(j, Double.valueOf(cost(l, j, start)));
			}
			Double min = Collections.min(tmp.values());
			return min;
		}
	}
	
	public double solve(Graph g) {
		List<City> cityList = new ArrayList<City>();
		cityList.addAll(g.getCityList());
		if (cityList.size() < 2) {
			return 0.0;
		}
		City start = cityList.get(0);
		cityList.remove(start);
		City end = cityList.get(cityList.size()-1);
		return cost(cityList, end, start);
	}
}
