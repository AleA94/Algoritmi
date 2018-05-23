package Grafo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Directory {

	private static Scanner sc;

	public static void main(String[] args) {

		List<List<Integer>> directory = new ArrayList<List<Integer>>();
		List<String> list = new ArrayList<String>();
		int numDirectory = 0;
		int numMaxDirectory = 0;
		int numMaxPersone = 0;
		int cont = 0;
		try {
			if (args.length > 0) {
				sc = new Scanner(new File(args[0]));
				while (sc.hasNext()) {
					String news = sc.nextLine();
					if (cont == 0) {
						String[] sub = news.split(" ");
						numDirectory = Integer.parseInt(sub[0]);
						numMaxDirectory = Integer.parseInt(sub[1]);
						numMaxPersone = Integer.parseInt(sub[2]);
					}

					if (cont > 0) {
						String[] sub = news.split(" ");
						List<Integer> s = new ArrayList<Integer>();
						for (int i = 2; i < sub.length; i++) {
							if (!s.contains(Integer.parseInt(sub[i])))
								s.add(Integer.parseInt(sub[i]));
						}
						
						directory.add(s);
						list.add(sub[0]);
					}
					cont++;
				}
			} else {

				System.out.println("No file name given");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<String> sol = new ArrayList<String>();
		if (backtracking(list, directory, numMaxPersone, numDirectory, numMaxDirectory, sol)) {
			for (int i = 0; i < sol.size(); i++) {
				System.out.print(sol.get(i) + " ");
			}
		}
	}

	public static boolean backtracking(List<String> map, List<List<Integer>> list, int numPersone, int numDirectory,
			int numMaxDirectory, ArrayList<String> sol) {
		int x = 0;
		while (x < numDirectory) {
			if (canAdd(map.get(x), sol, numDirectory)) {
				sol.add(map.get(x));
				if (isComplete(sol, map, list, numMaxDirectory, numPersone)) {
					return true;
				} else if (backtracking(map, list, numPersone, numDirectory, numMaxDirectory, sol))
					return true;
				sol.remove(map.get(x));
				x++;
			} else {
				x++;

			}
		}
		return false;
	}

	public static boolean canAdd(String s, ArrayList<String> sol, int numDirectory) {
		if (sol.size() < numDirectory) {

			if (!sol.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isComplete(ArrayList<String> sol, List<String> map, List<List<Integer>> list,
			int numMaxDirectory, int numPersone) {

		ArrayList<Integer> persone = new ArrayList<Integer>();
		List<Integer> daControllare = new ArrayList<Integer>();
		
		for (String i : sol) {
			int cont = 0;
		
			for (String s : map) {
				if (s==i) {
					daControllare.add(cont);
					break;
				}
				cont++;
			}

		}
		for (Integer c : daControllare) {
			for (int k = 0; k < list.get(c).size(); k++) {
				if (!persone.contains(list.get(c).get(k))) {
					persone.add(list.get(c).get(k));
				}

			}
			c++;
		}
		
		if (persone.size() >= numPersone && sol.size() <= numMaxDirectory) {
			return true;
		} else
			return false;
	}
}
