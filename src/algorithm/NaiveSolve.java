package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class NaiveSolve extends SolveStrategy{
	private String name = "Naive Programming";
	
	@Override
	public String toString() {
		return this.name;
	}
	
	private void create_2d_Naive(Graph graph){
        create_2d(graph);
    }

    private List<Path> exe(Graph graph){
        int number_of_city = graph.getCityList().size();
        List<List<Integer>> permutList = getPermut(number_of_city, false);
        List<Path> tmp  = new ArrayList<Path>();
        List<Path> main = new ArrayList<Path>();

        for (List<Integer> permut : permutList) {

            for(int i = 0; i < number_of_city-1; i++){
                if(i==0){
                    int i1 = permut.get(0);

                    City y0 = graph.getCityList().get(0);
                    City y1 = graph.getCityList().get(i1);

                    Path e1 = new Path(y0, y1);

                    tmp.add(e1);
                    //, path_2d[0][y1.getId()-1]);
                }

                if(i==number_of_city-2){
                    int iz = permut.get(i);

                    City yz = graph.getCityList().get(iz);
                    City y0 = graph.getCityList().get(0);

                    Path e = new Path(yz, y0);

                    tmp.add(e);
                    //, path_2d[yz.getId()-1][0]);
                    continue;
                }

                int m = permut.get(i);
                int n = permut.get(i+1);
                City ym = graph.getCityList().get(m);
                City yn = graph.getCityList().get(n);

                Path e = new Path(ym, yn);

                tmp.add(e);
                //, path_2d[ym.getId()-1][yn.getId()-1]);
            }

            if(getLength(main) == 0){
            	main.clear();
            	main.addAll(tmp);
            	tmp.clear();
                continue;
            }

            if(getLength(tmp) < getLength(main)){
                main.clear();
                main.addAll(tmp);
                tmp.clear();
            }

            tmp.clear();
        }

        return main;
    }
    private List<List<Integer>> getPermut(int n, boolean enable_print){
        int[] arr = new int[n-1];
        for(int i = 0; i < n-1; i++){
            arr[i] = i+1;
        }

        List<List<Integer>> list = new ArrayList<>();
        permutation(list, new ArrayList<>(),arr);



        if (enable_print){
            System.out.println("Array : ");
            for(int i = 0; i < n-1; i++){
                System.out.println(arr[i]);
            }

            System.out.println("Permuations of array : ");
            System.out.println("=========================================");
            for(List<Integer> perm : list) {
                System.out.println(perm);
            }
        }

        return list;

    }
    private void permutation (List<List<Integer>> list, List<Integer> resultList, int [] arr){
        if(resultList.size() == arr.length){
            list.add(new ArrayList<>(resultList));
        }
        else{
            for (int j : arr) {
                if (resultList.contains(j)) {
                    continue;
                }
                resultList.add(j);
                permutation(list, resultList, arr);
                resultList.remove(resultList.size() - 1);
            }
        }
    }
    
    public void solve(Graph graph){
        create_2d_Naive(graph);
        result = exe(graph);
        //return this;
    }
}
