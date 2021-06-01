package algorithm;

import java.util.List;

import javafx.scene.paint.Color;

import java.util.ArrayList;

import model.*;

public class MSTSolve extends SolveStrategy{
	private String name = "Minumum Spanning Tree";
	
	@Override
	public String toString() {
		return this.name;
	}
	
	int minKey(double key[], Boolean mstSet[])
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
	
	public List<Path> mst(List<City> cityList){
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
        List <Path> mstPath = new ArrayList<Path>();
        for (int i = 1; i < V; i++) {
        	mstPath.add(new Path(cityList.get(parent[i]), cityList.get(i)));
        }
        return mstPath;
	}
	
	public List<City> oddVertices (List<City> cityList, List<Path> mst){
		List<City> result = new ArrayList<City>();
		for (City c: cityList) {
			if (c.degree(mst) % 2 != 0) {
				result.add(c);
			}
		}
		return result;
	}
	
	public List<Path> perfectMatching (List<City> oddVertices){
		/************************************************************************************
		  find a perfect matching M in the subgraph O using greedy algorithm but not minimum
		*************************************************************************************/
		List<Path> result = new ArrayList<Path>();
		
		City first, closest;
		double length;

		// for each odd node
		while (!oddVertices.isEmpty()) {
			first = oddVertices.get(0);
		    length = Integer.MAX_VALUE;
		    closest = null;
		    for (City it : oddVertices.subList(1, oddVertices.size())) {
		      // if this node is closer than the current closest, update closest and length
		      if (first.getDistance(it) < length) {
		        length = first.getDistance(it);
		        closest = it;
		      }
		    } // two nodes are matched, end of list reached
		    Path newMatching = new Path(first, closest);
		    newMatching.setStroke(Color.RED);
		    result.add(newMatching);
		    oddVertices.remove(first);
		    oddVertices.remove(closest);
		}
		
		return result;
	}
	public double solve(Graph graph) {
		return 0;
	}
}
