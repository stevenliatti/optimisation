package tp1_puzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	public List<State> getSuccessors() {
		return successors;
	}
}
