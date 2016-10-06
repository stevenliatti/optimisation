package tp1_puzzle;

import java.util.List;

public class State {
	private String layout;
	private List<State> successors;
	
	public State(String layout) {
		this.layout = layout;
		this.generateSuccessors();
	}
	
	public State(State state) {
		this.layout = state.layout;
		this.successors = state.successors;
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
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
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
	public String toString() {
		int sqrt = (int) Math.sqrt(layout.length());
		String print = "";
		for (int i = 0; i < layout.length(); i = i + sqrt) {
			print += layout.substring(i, i + sqrt) + "\n";
		}
		return print;
	}
	
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public List<State> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<State> successors) {
		this.successors = successors;
	}
}
