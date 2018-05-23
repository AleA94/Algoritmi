package Grafo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Esame {

	private static Scanner sc;

	public static void main(String[] args) {
		SparseGraph<String, String> grafo = new SparseGraph<String, String>();
		String matrix[][] = {};
		int n = 0;
		int m = 0;
		int cont = 0;

		try {
			if (args.length > 0) {
				sc = new Scanner(new File(args[0]));
				while (sc.hasNext()) {

					String news = sc.nextLine();
					if (cont == 0) {
						String[] sub = news.split(" ");
						n = Integer.parseInt(sub[0]);
						m = Integer.parseInt(sub[1]);
						matrix = new String[n][m];
					}

					if (cont > 0 && cont <= m) {
						String[] sub = news.split("");
						for (int i = 0; i < sub.length; i++) {
							matrix[cont - 1][i] = sub[i];
						}
					}

					cont++;

				}
			} else {

				System.out.println("No file name given");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j].equals("."))
					grafo.addVertex(i + " " + j + " " + matrix[i][j]);
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j].equals(".")) {
					if (i + 1 < n && matrix[i + 1][j].equals("."))
						grafo.addEdge(i + " " + j + " " + matrix[i][j], (i + 1) + " " + j + " " + matrix[i + 1][j],
								matrix[i][j] + " " + matrix[i + 1][j]);

					if (j + 1 < m && matrix[i][j + 1].equals("."))
						grafo.addEdge(i + " " + j + " " + matrix[i][j], i + " " + (j + 1) + " " + matrix[i][j + 1],
								matrix[i][j] + " " + matrix[i][j + 1]);

					if (i - 1 >= 0 && matrix[i - 1][j].equals("."))
						grafo.addEdge(i + " " + j + " " + matrix[i][j], (i - 1) + " " + j + " " + matrix[i - 1][j],
								matrix[i][j] + " " + matrix[i - 1][j]);

					if (j - 1 >= 0 && matrix[i][j - 1].equals("."))
						grafo.addEdge(i + " " + j + " " + matrix[i][j], i + " " + (j - 1) + " " + matrix[i][j - 1],
								matrix[i][j] + " " + matrix[i][j - 1]);

				}
			}
		}

		List<String> s = intersezioni(grafo);
		HashMap<Integer, String> puntoVicino = new HashMap<Integer, String>();
		puntoVicino(s, grafo, puntoVicino);
		System.out.println(puntoVicino.size());

		for (String h : puntoVicino.values()) {
			System.out.println(h);

		}
	}

	public static void puntoVicino(List<String> s, SparseGraph<String, String> grafo,
			HashMap<Integer, String> puntoVicino) {
		MinPathDijkstra<String, String> cammino = new MinPathDijkstra<String, String>();
		MinPathDijkstra<String, String> cammino2 = new MinPathDijkstra<String, String>();
		int cont = 0;
		int size = Integer.MAX_VALUE;
		String j = " ";
		boolean b = false;
		for (String g : s) {
			String c = " ";
			String[] t = {};
			String[] d = {};
			int size2 = Integer.MAX_VALUE;
			for (String h : s) {
				cammino.minPath(grafo, g, h);
				size = cammino.path.size();
				
				if (g != h) {
					for (String r : s) {
						if ( h != r && g != r) {

							
							t = h.split(" ");
							d = r.split(" ");

							if (cammino.path.size() != 0) {
								if ((d[0].equals(t[0])) || d[1].equals(t[1])) {
									
									cammino2.minPath(grafo, g, r);
									if (cammino2.path.size() != 0)
										size2 = cammino2.path.size();
									
									if (size >= size2) {
										b = true;
										j = " { (" + d[0] + ", " + d[1] + ") } ";
										
									}
									
								}

							}
							
						}
						
					}
					t = h.split(" ");
					if (b)
						c = c + j;
					else if(size!=0 && !b)
						c = c+ " { (" + t[0] + ", " + t[1] + ") } ";
					b = false;
					//counter = 0;
					
				}
			}
			String[] p = g.split(" ");

			if (c.equals(" ")) {
				String value = "(" + p[0] + ", " + p[1] + ") -> { }";
				puntoVicino.put(cont, value + c);

			} else {
				String value = "(" + p[0] + ", " + p[1] + ") -> ";
				puntoVicino.put(cont, value + c);
			}
			size = Integer.MAX_VALUE;
			cont++;
		}
	}

	public static List<String> intersezioni(SparseGraph<String, String> grafo) {
		List<String> l = new ArrayList<String>();
		for (String i : grafo.nodi) {
			if(grafo.neighbors(i).size()>=3) {
				l.add(i);
			}
		}

		return l;
	}
}
