package algorithm;

import java.util.List;
import java.util.ArrayDeque;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import model.*;

public class MSTSolve extends SolveStrategy{
	private String name = "Minumum Spanning Tree";
	public List<Path> mst = new ArrayList<Path>();
	public List<City> oddVertices = new ArrayList<City>();
	public List<Path> perfectMatching = new ArrayList<Path>();
	public List<City> eulerTour = new ArrayList<City>();
	
	@Override
	public String toString() {
		return this.name;
	}
	
	private int minKey(double key[], Boolean mstSet[])
    {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index = -1;
  
        for (int v = 0; v < key.length; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
  
        return min_index;
    }
	
	private void mst(List<City> cityList){
		final int V = cityList.size();
		// Array to store constructed MST
        int parent[] = new int[V];
  
        // Key values used to pick minimum weight edge in cut
        double key[] = new double[V];
  
        // To represent set of vertices included in MST
        Boolean mstSet[] = new Boolean[V];
  
        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
  
        // Always include first 1st vertex in MST.
        key[0] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
  
        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);
  
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
  
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++) {  
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (cityList.get(u).getDistance(cityList.get(v)) != 0 && mstSet[v] == false && cityList.get(u).getDistance(cityList.get(v)) < key[v]) {
                    parent[v] = u;
                    key[v] = cityList.get(u).getDistance(cityList.get(v));
                }
            }
        }
        for (int i = 1; i < V; i++) {
        	this.mst.add(new Path(cityList.get(parent[i]), cityList.get(i)));
        }
	}
	
	private void oddVertices (List<City> cityList){
		for (City c: cityList) {
			if (c.degree(this.mst) % 2 != 0) {
				this.oddVertices.add(c);
			}
		}
	}
	
	private void perfectMatching (){
		/************************************************************************************
		  find a perfect matching M in the subgraph O using greedy algorithm but not minimum
		*************************************************************************************/
		List<City> oddVerticesCopy = new ArrayList<City>();
		oddVerticesCopy.addAll(this.oddVertices);
		
		City first, closest;
		double length;

		// for each odd node
		while (!oddVerticesCopy.isEmpty()) {
			first = oddVerticesCopy.get(0);
		    length = Integer.MAX_VALUE;
		    closest = null;
		    for (City it : oddVerticesCopy.subList(1, oddVerticesCopy.size())) {
		      // if this node is closer than the current closest, update closest and length
		      if (first.getDistance(it) < length) {
		        length = first.getDistance(it);
		        closest = it;
		      }
		    } // two nodes are matched, end of list reached
		    Path newMatching = new Path(first, closest);
		    newMatching.setStroke(Color.RED);
		    this.perfectMatching.add(newMatching);
		    oddVerticesCopy.remove(first);
		    oddVerticesCopy.remove(closest);
		}
	}
	
	private void eulerTour(List<City> cityList){
		List<Path> tempPathList = new ArrayList<Path>();
		tempPathList.addAll(this.mst);
		tempPathList.addAll(this.perfectMatching);
		
		ArrayDeque<City> stack = new ArrayDeque<City>();
		City start = cityList.get(0);
		stack.push(start);
		
		while(!stack.isEmpty()) {
			City v = stack.peek();
			if (v.degree(tempPathList) == 0) {
				this.eulerTour.add(v);
				stack.pop();
			}
			else {
				for (int i = 0; i < tempPathList.size(); i++) {
					Path p = tempPathList.get(i);
					if (p.contains(v)) {
						if (p.getStartCity().equals(v)) {
							stack.push(p.getEndCity());
						}
						else{
							stack.push(p.getStartCity());
						}
						tempPathList.remove(p);
						break;
					}
				}
			}
		}
	}
	
	private void makeHamiltonian(){
		ArrayDeque<City> visited = new ArrayDeque<City>();
		List<City> hamiltonian = new ArrayList<City>();
		
		for (City c: eulerTour) {
			if (!visited.contains(c)) {
				visited.push(c);
				hamiltonian.add(c);
			}
		}
		
		for (int i = 0; i < hamiltonian.size()-1; i++) {
			this.result.add(new Path(hamiltonian.get(i), hamiltonian.get(i+1)));
		}
		this.result.add(new Path(hamiltonian.get(hamiltonian.size()-1), hamiltonian.get(0)));

	}
	private void reset() {
		this.mst.clear();
		this.oddVertices.clear();
		this.perfectMatching.clear();
		this.eulerTour.clear();
		this.result.clear();
	}
	
	public void solve(Graph graph) {
		this.reset(); //clear previous results
		
		List<City> cityList = graph.getCityList();
		this.mst(cityList);
		this.oddVertices(cityList);
		this.perfectMatching();
		this.eulerTour(cityList);
		this.makeHamiltonian();
	}
	
}
