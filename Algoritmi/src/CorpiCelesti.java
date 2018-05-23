package Grafo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CorpiCelesti {

	public static void main(String[] args) {
		SparseGraph<String, String> grafo = new SparseGraph<String, String>();
		String matrix[][] = {};
		int n = 0;
		int m = 0;
		int cont = 0;
		
		try {
			if (args.length > 0) {
				Scanner sc = new Scanner(new File(args[0]));
				while (sc.hasNext()) {

					String news = sc.nextLine();
					if (cont == 0) {
						String[] sub = news.split(" ");
						n = Integer.parseInt(sub[0]);
						m = Integer.parseInt(sub[1]);
						matrix = new String[n][m];
					}

					if (cont > 0 && cont <= m) {
						String[] sub = news.split(" ");
						for(int i = 0; i<sub.length; i++){
							matrix[cont-1][i] = sub[i];
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
		for(int i = 0; i<n ; i++)
		{
			for(int j = 0; j<m; j++){
				grafo.addVertex(i+" "+j+" "+matrix[i][j]);
			}
		}
		
		for(int i = 0; i<n; i++)
		{
			for(int j = 0; j<m; j++){
				if(i+1<n)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], (i+1)+" "+j+" "+matrix[i+1][j], matrix[i][j] + " " + matrix[i+1][j]);
				
				if(i+1<n && j+1<m)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], (i+1)+" "+(j+1)+" "+matrix[i+1][j+1], matrix[i][j] + " " + matrix[i+1][j+1]);
				
				if(i-1>=0)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], (i-1)+" "+j+" "+matrix[i-1][j], matrix[i][j] + " " + matrix[i-1][j]);
				
				if(j+1<m)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], i+" "+(j+1)+" "+matrix[i][j+1], matrix[i][j] + " " + matrix[i][j+1]);
				
				if(j-1>=0)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], i+" "+(j-1)+" "+matrix[i][j-1], matrix[i][j] + " " + matrix[i][j-1]);
				
				if(i-1>=0 && j-1>=0)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], (i-1)+" "+(j-1)+" "+matrix[i-1][j-1], matrix[i][j] + " " + matrix[i-1][j-1]);
				
				if(i-1>=0 && j+1<m)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], (i-1)+" "+(j+1)+" "+matrix[i-1][j+1], matrix[i][j] + " " + matrix[i-1][j+1]);
				
				if(i+1<n && j-1>=0)
					grafo.addEdge(i+" "+j+" "+matrix[i][j], (i+1)+" "+(j-1)+" "+matrix[i+1][j-1], matrix[i][j] + " " + matrix[i+1][j-1]);
			}
		}
		String s = min(grafo,matrix,n,m);
		System.out.println(s);
	}
	
	
	public static String min(SparseGraph<String, String> grafo, String matrix[][], int n, int m){
		MinPathDijkstra<String, String> cammino = new MinPathDijkstra<String, String>();
		int maxSomma = 0;
		String nodo = null;
		for(int i=0; i<n; i++){
			for(int j=0; j<m; j++){
				int somma = 0;
				if(matrix[i][j].equals("-")){
					for(int k=0; k<n; k++){
						for(int s=0; s<m; s++){
							if(matrix[k][s].equals("#")){
								cammino.minPath(grafo, i+" "+j+" -", k+" "+s+" #").size();
								for(int y=0; y<cammino.path.size(); y++){
									String c = cammino.path.get(y).toString();
									String [] cam =c.split(" ");
									if(cam[2].equals("-"))
										somma++;
								}
							}
						}
						
					}
				}
				if(somma>maxSomma){
					maxSomma = somma;
					nodo = "<"+i+","+j+">";
				}
			}
		}
		return nodo;
	}

}
