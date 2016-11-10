package tp1_puzzle;

import java.util.Comparator;

public class SquareFalse implements Comparator<State> {

	private State solution;
	
	public SquareFalse(State solution) {
		this.solution = new State(solution);
	}
	
	@Override
	public int compare(State s1, State s2) {
		if (s1.squareFalse(solution) < s2.squareFalse(solution)) {
			return -1;
		}
		else if (s1.squareFalse(solution) > s2.squareFalse(solution)) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
