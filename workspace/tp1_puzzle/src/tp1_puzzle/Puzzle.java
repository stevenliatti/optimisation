package tp1_puzzle;

import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Puzzle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int[][] canonical8 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] canonical15 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] canonical;
		final int LIMIT = 200000;
		State startState;
		State finalState;
		
		switch (sizePuzzle) {
			case 8:
				canonical = canonical8;
				break;
			case 15:
				canonical = canonical15;
				break;
			default:
				canonical = canonical8;
				break;
		}
		
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

	}
	
	public static void BlindSearch(int sizePuzzle, int limit, State startState, State finalState) {
		State currentState = new State(startState);
		int iterations = 0;
		ConcurrentLinkedQueue<State> queue = new ConcurrentLinkedQueue<State>();
		HashSet<State> visitedStates = new HashSet<State>();
		
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

			if (sizePuzzle == 15) {
				// Si la recherche dépasse un certain seuil
				if (queue.size() >= limit) {
					System.out.println("Seuil fixé dépassé");
					State.printSituation(startState, currentState, finalState, iterations);
					return;
				}
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
