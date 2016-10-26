package tp1_puzzle;

import java.util.List;

public class State {
	private String strLayout;
	private int[][] tabLayout;
	List<State> successors;
	
	public State(int[][] tabLayout) {
		this.tabLayout = tabLayout;
		this.generateStrLayout(tabLayout);
		this.generateSuccessors();
	}
	
	public State(State state) {
		this.tabLayout = state.tabLayout;
		this.generateStrLayout(state.tabLayout);
		this.successors = state.successors;
	}
	
	private void generateStrLayout(int[][] tabLayout) {
		
	}
	
	public void generateSuccessors() {
		
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

	public String getStrLayout() {
		return strLayout;
	}

	public void setStrLayout(String strLayout) {
		this.strLayout = strLayout;
	}

	public int[][] getTabLayout() {
		return tabLayout;
	}

	public void setTabLayout(int[][] tabLayout) {
		this.tabLayout = tabLayout;
	}

	public List<State> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<State> successors) {
		this.successors = successors;
	}
}
