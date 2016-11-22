package tp1_puzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Cette classe définit un état à un instant donné du puzzle.
 * 
 * @author Steven Liatti
 */
public class State {
	private String strLayout;
	private int[][] tabLayout;
	private List<State> successors;
	private int sizePuzzle;
	private State parentState;

	/**
	 * Construit un état à partir d'un tableau de int à 2 dimensions.
	 * 
	 * @param tabLayout
	 */
	public State(int[][] tabLayout) {
		this.tabLayout = tabLayout;
		this.generateStrLayout();
		this.successors = new ArrayList<>();
		this.sizePuzzle = strLayout.length() - 1;
	}

	/**
	 * Construit un état à partir d'un autre.
	 * 
	 * @param state
	 */
	public State(State state) {
		this.tabLayout = state.tabLayout;
		this.strLayout = state.strLayout;
		this.successors = state.successors;
		this.sizePuzzle = strLayout.length() - 1;
		this.parentState = state.parentState;
	}
	
	/**
	 * Retourne un état aléatoire à partir d'un tableau à 2 dimensions.
	 * 
	 * @param array
	 * @return un état aléatoire à partir d'un tableau à 2 dimensions.
	 */
	public static State randomState(int[][] array) {
		Stack<Integer> oneDim = new Stack<Integer>();
		int twoDim[][] = new int[array.length][array[0].length];

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				oneDim.add(array[i][j]);
			}
		}

		Collections.shuffle(oneDim);

		for (int i = 0; i < twoDim.length; i++) {
			for (int j = 0; j < twoDim[0].length; j++) {
				twoDim[i][j] = oneDim.pop();
			}
		}

	    return new State(twoDim);
	}
	
	/**
	 * Constuit un nouvel état à partir d'une String
	 * (exemple : "123456780").
	 * 
	 * @param str
	 * @return un nouvel état
	 */
	public static State stateFromString(String str) {
		String finalStr[] = str.split(" ");
		final int sqrt = (int) Math.sqrt(finalStr.length);
		int[][] finalTab = new int[sqrt][sqrt];

		for (int i = 0; i < sqrt; i++) {
			for (int j = 0; j < sqrt; j++) {
				finalTab[i][j] = Integer.parseInt(finalStr[sqrt * i + j]);
			}
		}
		return new State(finalTab);
	}
	
	/**
	 * Affiche sur la sortie standard la situation lors du parcours des états.
	 * 
	 * @param startState
	 * @param finalState
	 * @param iterations
	 */
	public static void printSituation(State startState, State finalState, int iterations) {
		System.out.println("Nombre d'états parcourus : " + iterations);
		System.out.println("État de départ : ");
		System.out.println(startState);
		System.out.println("État final : ");
		System.out.println(finalState);
	}
	
	/**
	 * Affiche sur la sortie standard la situation lors du parcours des états.
	 * 
	 * @param startState
	 * @param currentState
	 * @param finalState
	 * @param iterations
	 */
	public static void printSituation(State startState, State currentState, State finalState, int iterations) {
		System.out.println("Nombre d'états parcourus : " + iterations);
		System.out.println("État de départ : ");
		System.out.println(startState);
		System.out.println("État courant : ");
		System.out.println(currentState);
		System.out.println("État final : ");
		System.out.println(finalState);
	}
	
	/**
	 * Première heuristique. Calcule le nombre de plaquettes mal placées.
	 * 
	 * @param solution
	 * @return le nombre de plaquettes mal placées
	 */
	public int squareFalse(State solution) {
		int squareFalse = 0;
		
		for (int i = 0; i < tabLayout.length; i++) {
			for (int j = 0; j < tabLayout[i].length; j++) {
				if (tabLayout[i][j] != solution.tabLayout[i][j]) {
					squareFalse++;
				}
			}
		}
		
		// On ne prend pas en compte la case vide
		return squareFalse - 1;
	}
	
	private static Point[] arrayOfCoordinates(int[][] array) {
		final int size = array.length * array[0].length;
		Point[] arrayOfCoordinates = new Point[size];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				arrayOfCoordinates[array[i][j]] = new Point(i, j);
			}
		}
		return arrayOfCoordinates;
	}
	
	/**
	 * Deuxième heuristique. Calcule la somme des distances (Manhattan)
	 * de chaque plaquette numérotée à sa position finale.
	 * 
	 * @param solution
	 * @return
	 */
	public int manhattan(State solution) {
		int distance = 0;
		Point[] currCoor = arrayOfCoordinates(tabLayout);
		Point[] solCoor = arrayOfCoordinates(solution.tabLayout);
		
		for (int i = 1; i < solCoor.length; i++) {
			distance += Math.abs(currCoor[i].x - solCoor[i].x) + Math.abs(currCoor[i].y - solCoor[i].y);
		}
		
		return distance;
	}

	private void generateStrLayout() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tabLayout.length; i++) {
			for (int j = 0; j < tabLayout[i].length; j++) {
				builder.append(tabLayout[i][j]);
			}
		}
		strLayout = builder.toString();
	}
	
	// Fonction de "swap" entre deux nombres dans le puzzle
	private int[][] newTabLayout(Point oldPos, Point newPos) {
		int newTabLayout[][] = new int[tabLayout.length][tabLayout[0].length];
		for (int i = 0; i < newTabLayout.length; i++) {
			for (int j = 0; j < newTabLayout[i].length; j++) {
				newTabLayout[i][j] = tabLayout[i][j];
			}
		}
		int temp = newTabLayout[oldPos.x][oldPos.y];
		newTabLayout[oldPos.x][oldPos.y] = newTabLayout[newPos.x][newPos.y];
		newTabLayout[newPos.x][newPos.y] = temp;
		return newTabLayout;
	}
	
	@SuppressWarnings("unused")
	private void printTab(int[][] newTabLayout) {
		for (int i = 0; i < newTabLayout.length; i++) {
			for (int j = 0; j < newTabLayout.length; j++) {
				System.out.print(newTabLayout[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Génère les 2 à 4 successeurs à l'état courant (this).
	 */
	public void generateSuccessors() {
		Point emptySquare = new Point();
		// On cherche la case vide et on la stock dans emptySquare
		for (int i = 0; i < tabLayout.length; i++) {
			for (int j = 0; j < tabLayout[i].length; j++) {
				if (tabLayout[i][j] == 0) {
					emptySquare.setLocation(i, j);
					break;
				}
			}
		}
		
		int newTabLayout[][];

		// On ajoute les successeurs possibles à this.successors
		if (emptySquare.x - 1 >= 0) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x - 1, emptySquare.y));
			State successor = new State(newTabLayout);
			successor.setParentState(this);
			successors.add(successor);
		}
		if (emptySquare.x + 1 < tabLayout.length) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x + 1, emptySquare.y));
			State successor = new State(newTabLayout);
			successor.setParentState(this);
			successors.add(successor);
		}
		if (emptySquare.y - 1 >= 0) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x, emptySquare.y - 1));
			State successor = new State(newTabLayout);
			successor.setParentState(this);
			successors.add(successor);
		}
		if (emptySquare.y + 1 < tabLayout[0].length) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x, emptySquare.y + 1));
			State successor = new State(newTabLayout);
			successor.setParentState(this);
			successors.add(successor);
		}
	}

	@Override
	public int hashCode() {
		return strLayout.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (strLayout == null) {
			if (other.strLayout != null)
				return false;
		} else if (!strLayout.equals(other.strLayout))
			return false;
		return true;
	}

	/**
	 * Retourne l'état actuel sous la forme suivante :
	 * 123\n
	 * 456\n
	 * 780\n
	 * 
	 * @return l'état du puzzle formaté
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tabLayout.length; i++) {
			for (int j = 0; j < tabLayout[0].length; j++) {
				builder.append(tabLayout[i][j]);
				if (tabLayout[i][j] < 10) {
					builder.append("  ");
				}
				else {
					builder.append(" ");
				}
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Retourne les successeurs.
	 * 
	 * @return les successeurs
	 */
	public List<State> getSuccessors() {
		return successors;
	}

	/**
	 * Retourne la taille du puzzle.
	 * 
	 * @return la taille du puzzle
	 */
	public int getSizePuzzle() {
		return sizePuzzle;
	}
	
	/**
	 * Retourne le parent de l'état courant.
	 * 
	 * @return le parent de l'état courant
	 */
	public State getParentState() {
		return parentState;
	}

	/**
	 * Définit le parent de l'état courant.
	 * 
	 * @param parentState
	 */
	public void setParentState(State parentState) {
		this.parentState = parentState;
	}
}
