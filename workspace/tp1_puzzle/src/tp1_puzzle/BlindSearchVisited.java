package tp1_puzzle;

import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BlindSearchVisited {
	
	public static void printSituation(State startState, State currentState, int iterations) {
		System.out.println("Nombre d'itérations : " + iterations);
		System.out.println("État de départ : ");
		System.out.println(startState);
		System.out.println("État final : ");
		System.out.println(currentState);
	}

	public static void main(String[] args) {
//		final int[][] canonical = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] canonical = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
		final int[][] example1 = {{5, 2, 4}, {7, 3, 1}, {0, 8, 6}};
		final int[][] example2 = {{1, 2, 3, 4}, {5, 6, 7, 0}, {9, 10, 11, 8}, {13, 14, 15, 12}};
		final int[][] example3 = {{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};
		State finalState = new State(canonical);
//		State startState = State.randomState(canonical);
		State startState = new State(example2);
		State currentState = new State(startState);
		int iterations = 0;
		ConcurrentLinkedQueue<State> queue = new ConcurrentLinkedQueue<State>();
		HashSet<State> visitedStates = new HashSet<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			printSituation(startState, currentState, iterations);
			return;
		}

		// 2. INSERER(noeud-initial,FILE)
		queue.add(currentState);
		visitedStates.add(currentState);

		// 3. Répéter:
		while (true) {
			// a. Si vide(FILE) alors retourner échec
			if (queue.isEmpty()) {
				System.out.println("Échec");
				printSituation(startState, currentState, iterations);
				return;
			}

			// b. n <- RETIRER(FILE)
			// c. s <- ETAT(n)
			currentState = queue.poll();
			iterations++;
			System.out.println("--- " + iterations + " ---");
			System.out.println(currentState);
			// i.  Créer un nouveau noeud n' comme "enfant" de n
			// ii. Si SOLUTION?(s') alors retourner chemin ou état-solution
			if (currentState.equals(finalState)) {
				printSituation(startState, currentState, iterations);
				return;
			}
			currentState.generateSuccessors();
			for (State state : currentState.getSuccessors()) {
				// iii. INSERER(n',FILE)
				if (!visitedStates.contains(state)) {
					queue.add(state);
					visitedStates.add(state);
				}
			}
		}
	}
}
