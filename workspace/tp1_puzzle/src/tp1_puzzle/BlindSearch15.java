package tp1_puzzle;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BlindSearch15 {

	public static void main(String[] args) {
		final int[][] canonical = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
		final int LIMIT = 200000;
		State startState;
		State finalState;

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous entrer à la main les états initiaux et finaux ? (y/n)");

		if (sc.nextLine().equals("y")) {
			System.out.println("État final [1 2 3 ...] : ");
			String str = sc.nextLine();
			finalState = State.stateFromString(str);

			System.out.println("État initial [1 2 3 ...] : ");
			str = sc.nextLine();
			startState = State.stateFromString(str);
		}
		else {
			finalState = new State(canonical);
			startState = State.randomState(canonical);
		}
		State currentState = new State(startState);
		int iterations = 0;
		ConcurrentLinkedQueue<State> queue = new ConcurrentLinkedQueue<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			State.printSituation(startState, currentState, iterations);
			return;
		}

		// 2. INSERER(noeud-initial,FILE)
		queue.add(currentState);

		// 3. Répéter:
		while (true) {
			// a. Si vide(FILE) alors retourner échec
			if (queue.isEmpty()) {
				System.out.println("Échec");
				State.printSituation(startState, currentState, iterations);
				return;
			}

			// Si la recherche dépasse un certain seuil
			if (queue.size() >= LIMIT) {
				System.out.println("Seuil fixé dépassé");
				State.printSituation(startState, currentState, finalState, iterations);
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
				State.printSituation(startState, currentState, iterations);
				return;
			}
			currentState.generateSuccessors();
			for (State state : currentState.getSuccessors()) {
				// iii. INSERER(n',FILE)
				queue.add(state);
			}
		}
	}
}
