package tp1_puzzle;

import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BlindSearchVisited {

	public static void main(String[] args) {
		final int[][] canonical = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		State finalState = new State(canonical);
		System.out.println(finalState);
		State currentState = randomState(canonical);
		System.out.println(currentState);
		int iterations = 0;
		ConcurrentLinkedQueue<State> queue = new ConcurrentLinkedQueue<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			System.out.println("Solution trouvée en " + iterations + " itérations");
			return;
		}

		// 2. INSERER(noeud-initial,FILE)
		queue.add(currentState);

		// 3. Répéter:
		while (true) {
			// a. Si vide(FILE) alors retourner échec
			if (queue.isEmpty()) {
				System.out.println("Échec");
				return;
			}

			// b. n <- RETIRER(FILE)
			// c. s <- ETAT(n)
			currentState = queue.poll();
			currentState.generateSuccessors();
			for (State state : currentState.getSuccessors()) {
				iterations++;
				// i.  Créer un nouveau noeud n' comme "enfant" de n
				// ii. Si SOLUTION?(s') alors retourner chemin ou état-solution
				if (state.equals(finalState)) {
					System.out.println(state);
					System.out.println("Solution trouvée en " + iterations + " itérations");
					return;
				}
				// iii. INSERER(n',FILE)
				if (!queue.contains(state)) {
					queue.add(state);
				}
			}
		}
	}

	public static State randomState(int[][] tab) {
		Stack<Integer> oneDim = new Stack<Integer>();
		int twoDim[][] = new int[tab.length][tab[0].length];

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				oneDim.add(tab[i][j]);
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

}
