package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.*;

public abstract class SolveStrategy {
	public List<Path> result = new ArrayList<Path>();
	public Double [][] path_2d;
	
    public void create_2d(Graph graph){
        path_2d = new Double[graph.getCityList().size()][graph.getCityList().size()];
        for(int i = 0; i < graph.getCityList().size(); i++){
            for(int j = 0; j < graph.getCityList().size(); j++){

                if (i == j){
                    continue;
                }

                if (i>j){
                    path_2d[i][j] = path_2d[j][i];
                    continue;
                }

                path_2d[i][j] = graph.getCityList().get(i).getDistance(graph.getCityList().get(j));
            }
        }
    }
    
    protected double getLength(List<Path> pathList) {
		double length = 0;
		for (Path p: pathList) {
			length += p.getWeight();
		}
		return length;
	}
	
	public abstract void solve(Graph g);
}
