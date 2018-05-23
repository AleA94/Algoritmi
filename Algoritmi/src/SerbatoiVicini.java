package Grafo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SerbatoiVicini {

	public static void main(String[] args) {

		SparseGraph<String, String> grafo = new SparseGraph<String, String>();
		int cont = 0;
		int numNodi = 0;
		int numSerbatoi = 0;
		ArrayList<String> sol = new ArrayList<String>();
		try {
			if (args.length > 0) {
				Scanner sc = new Scanner(new File(args[0]));
				while (sc.hasNext()) {

					String newCity = sc.nextLine();
					if (cont == 0) {
						String[] sub = newCity.split(" ");
						numNodi = Integer.parseInt(sub[0]);
						numSerbatoi = Integer.parseInt(sub[1]);
					}

					if (cont > 0 && cont <= numNodi) {
						grafo.addVertex(newCity);
					}

					if (cont > numNodi) {
						String[] sub = newCity.split(" <-> ");
						grafo.addEdge(sub[0], sub[1], newCity);
						grafo.addEdge(sub[1], sub[0], newCity);
					}

					cont++;

				}
			} else {

				System.out.println("No file name given");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		grafo.printVertex();
		System.out.println(numNodi + " " + numSerbatoi);

		for (String i : grafo.nodi) {

			System.out.println("adiacenze di i " + i);


			System.out.println(grafo.neighbors(i).size());

		}
		
		if(backtracking(grafo,numSerbatoi, numNodi, sol)){
			System.out.println(sol.size());
			System.out.print("< ");
			for(int i=0; i<sol.size(); i++){
				System.out.print(sol.get(i)+" , ");
			}
			System.out.println(" >");
		}
		else
			System.out.println(sol.size());
	}

	public static boolean backtracking(SparseGraph<String, String> grafo, int numSerbatoi, int numNodi,
			ArrayList<String> sol) {
		int x = 1;
		while (x < numNodi) {
			if (canAdd(grafo.nodi.get(x), sol, numSerbatoi)) {
				sol.add(grafo.nodi.get(x));
				if (isComplete(sol, grafo, numNodi)) {
					return true;
				} else if (backtracking(grafo, numSerbatoi, numNodi, sol))
					return true;
				sol.remove(grafo.nodi.get(x));
				x++;
			} else
				x++;
		}
		return false;
	}
	
	public static boolean canAdd(String s, ArrayList<String> sol, int num){
		if(sol.size()<num){
			if(!sol.contains(s)){
			return true;
			}
		}
			return false;
	}
	
	public static boolean isComplete(ArrayList<String> sol, SparseGraph<String, String> grafo, int numNodi){
		
		ArrayList<String> adiacenze = new  ArrayList<String>();
		
		for (String i : sol) {
			
			adiacenze.add(i);

			for (String j : grafo.neighbors(i)) {
				
				if(!adiacenze.contains(j))
				       adiacenze.add(j);
				
			}
		}
		
		if(adiacenze.size() == numNodi){
			return true;
		}
		else
			return false;
	}

}
