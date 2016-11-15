package tp1_puzzle;

import java.util.Comparator;

/**
 * Cette classe implémente un comparateur entre deux états
 * avec l'heuristique Manhattan.
 * 
 * @author Steven Liatti
 *
 */
public class Manhattan implements Comparator<State> {

	private State solution;

	/**
	 * Construit un objet Manhattan
	 * 
	 * @param solution
	 */
	public Manhattan(State solution) {
		this.solution = new State(solution);
	}

	@Override
	public int compare(State s1, State s2) {
		if (s1.manhattan(solution) < s2.manhattan(solution)) {
			return -1;
		}
		else if (s1.manhattan(solution) > s2.manhattan(solution)) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
