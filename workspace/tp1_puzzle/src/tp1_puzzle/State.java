package tp1_puzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class State {
	private String strLayout;
	private int[][] tabLayout;
	List<State> successors;

	public State(int[][] tabLayout) {
		this.tabLayout = tabLayout;
		this.generateStrLayout();
		this.successors = new ArrayList<>();
	}

	public State(State state) {
		this.tabLayout = state.tabLayout;
		this.strLayout = state.strLayout;
		this.successors = state.successors;
	}
	
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
	
	public static void printSituation(State startState, State finalState, int iterations) {
		System.out.println("Nombre d'itérations : " + iterations);
		System.out.println("État de départ : ");
		System.out.println(startState);
		System.out.println("État final : ");
		System.out.println(finalState);
	}
	
	public static void printSituation(State startState, State currentState, State finalState, int iterations) {
		System.out.println("Nombre d'itérations : " + iterations);
		System.out.println("État de départ : ");
		System.out.println(startState);
		System.out.println("État courant : ");
		System.out.println(currentState);
		System.out.println("État final : ");
		System.out.println(finalState);
	}
	
	public static int squareFalse(State current, State solution) {
		int squareFalse = 0;
		
		for (int i = 0; i < current.tabLayout.length; i++) {
			for (int j = 0; j < current.tabLayout[i].length; j++) {
				if (current.tabLayout[i][j] != solution.tabLayout[i][j]) {
					squareFalse++;
				}
			}
		}
		
		// On ne prend pas en compte la case vide
		return squareFalse - 1;
	}
	
	private static int[] arrayOfIndex(int[][] array) {
		final int size = array.length * array[0].length;
		int[] arrayOfIndex = new int[size];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				arrayOfIndex[array[i][j]] = i * size + j;
			}
		}
		return arrayOfIndex;
	}
	
	public static int manhattan(State current, State solution) {
		int distance = 0;
		int[] arrayCurrent = arrayOfIndex(current.tabLayout);
		int[] arraySolution = arrayOfIndex(solution.tabLayout);
		
		
		
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

	public void generateSuccessors() {
		Point emptySquare = new Point();
		for (int i = 0; i < tabLayout.length; i++) {
			for (int j = 0; j < tabLayout[i].length; j++) {
				if (tabLayout[i][j] == 0) {
					emptySquare.setLocation(i, j);
					break;
				}
			}
		}
		
		int newTabLayout[][];

		if (emptySquare.x - 1 >= 0) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x - 1, emptySquare.y));
			successors.add(new State(newTabLayout));
		}
		if (emptySquare.x + 1 < tabLayout.length) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x + 1, emptySquare.y));
			successors.add(new State(newTabLayout));
		}
		if (emptySquare.y - 1 >= 0) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x, emptySquare.y - 1));
			successors.add(new State(newTabLayout));
		}
		if (emptySquare.y + 1 < tabLayout[0].length) {
			newTabLayout = newTabLayout(emptySquare, new Point(emptySquare.x, emptySquare.y + 1));
			successors.add(new State(newTabLayout));
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

	public List<State> getSuccessors() {
		return successors;
	}
}
