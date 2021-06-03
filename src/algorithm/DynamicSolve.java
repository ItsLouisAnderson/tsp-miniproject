package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Collections;
import java.util.HashMap;

import model.*;

public class DynamicSolve extends SolveStrategy{
	public double [][] path_2d;
	private String name = "Dynamic Programming";
	
	private int n;
	private int VISITED_ALL;

	private Double [][] visted_path_length;
	private ArrayList [][] visted_path;
	
	@Override
	public String toString() {
		return this.name;
	}

	protected List<Path> tsp(int mask, int pos, Graph graph) {
		List<Path> p = new ArrayList<Path>();

		if (mask == VISITED_ALL) {
			p.add(new Path(graph.getCityList().get(pos), graph.getCityList().get(0)));
					//, path_2d[pos][0]);
			return p;
		}

		if (visted_path_length[mask][pos] != -1) {
			p.addAll(visted_path[mask][pos]);
			//p.setLength(visted_path_length[mask][pos]);
			return p;
		}

		List<Path> tmp;
		for (int city = 0; city < n; city++) {

			if ((mask & (1 << city)) == 0) {

				tmp = tsp(mask | (1 << city), city, graph);
				tmp.add(new Path(graph.getCityList().get(pos), graph.getCityList().get(city)));
						//, path_2d[pos][city]);

				if(getLength(p) == 0){
					p.clear();
					p.addAll(tmp);
					continue;
				}

				if (getLength(tmp) < getLength(p)){
					p.clear();
					p.addAll(tmp);
				}
			}

		}

		ArrayList<Path> tmp2 = new ArrayList<>();
		tmp2.addAll(p);
		visted_path[mask][pos] = tmp2;
		visted_path_length[mask][pos] = getLength(p);
		return p;
	}

	private void create_2d_dynamic(Graph graph){
		create_2d(graph);
		n = graph.getCityList().size();
		VISITED_ALL = (1<<n) -1;
		visted_path_length = new Double[1<<n][n];
		visted_path = new ArrayList[1<<n][n];

		for(int i=0; i<(1<<n); i++){
			for(int j=0; j<n; j++){
				visted_path_length[i][j] = -1.0;
			}
		}
	}

	public void solve(Graph graph){
		create_2d_dynamic(graph);
		result = tsp(1,0, graph);
		//return this;
	}
}
