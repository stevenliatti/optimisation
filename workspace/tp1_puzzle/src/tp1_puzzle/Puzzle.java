package tp1_puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Puzzle {

	final static int SMALL_PUZZLE = 8;
	final static int BIG_PUZZLE = 15;

	public static void main(String[] args) {
		final int[][] canonical8 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] canonical15 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
		final int LIMIT = 200000;
		final int[][] canonical;

		State startState;
		State finalState;
		int sizePuzzle = SMALL_PUZZLE;

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
			
			if (finalState.getSizePuzzle() != startState.getSizePuzzle()) {
				throw new IllegalArgumentException("La taille de l'état initial diffère ce celle de l'état final.");
			}

			sizePuzzle = startState.getSizePuzzle();
		}
		else {
			System.out.println("Entrez la taille du puzzle [8/15] : ");
			sizePuzzle = Integer.parseInt(sc.nextLine());

			switch (sizePuzzle) {
			case SMALL_PUZZLE:
				canonical = canonical8;
				break;
			case BIG_PUZZLE:
				canonical = canonical15;
				break;
			default:
				canonical = canonical8;
				break;
			}

			finalState = new State(canonical);
			startState = State.randomState(canonical);
		}

		System.out.println("Quelle recherche voulez-vous faire ? : ");
		System.out.println("1 : Aveugle");
		System.out.println("2 : Aveugle avec états visités (taille 8 seulement)");
		System.out.println("3 : Heuristique 1 (nombre de plaquettes mal placées)");
		System.out.println("4 : Heuristique 2 (Manhattan)");
		String str = sc.nextLine();

		switch (str) {
		case "1":
			blindSearch(startState, finalState, sizePuzzle, LIMIT, false);
			break;
		case "2":
			if (sizePuzzle == SMALL_PUZZLE) {
				blindSearch(startState, finalState, sizePuzzle, LIMIT, true);
			}
			else {
				throw new IllegalArgumentException("Vous ne pouvez faire une recherche avec états visités avec une taille > 8");
			}
			break;
		case "3":
			heuristicSearch(startState, finalState, sizePuzzle, LIMIT, false);
			break;
		case "4":
			heuristicSearch(startState, finalState, sizePuzzle, LIMIT, true);
			break;
		default:
			blindSearch(startState, finalState, sizePuzzle, LIMIT, false);
			break;
		}
	}

	public static void blindSearch(State startState, State finalState, int sizePuzzle, int limit, boolean visited) {
		State currentState = new State(startState);
		int iterations = 0;
		ConcurrentLinkedQueue<State> queue = new ConcurrentLinkedQueue<State>();
		HashSet<State> visitedStates = new HashSet<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			System.out.println("Trouvé !");
			State.printSituation(startState, currentState, iterations);
			return;
		}

		// 2. INSERER(noeud-initial,FILE)
		queue.add(currentState);
		if (visited) {
			visitedStates.add(currentState);
		}

		// 3. Répéter:
		while (true) {
			// a. Si vide(FILE) alors retourner échec
			if (queue.isEmpty()) {
				System.out.println("Échec");
				State.printSituation(startState, currentState, iterations);
				return;
			}

			if (sizePuzzle == BIG_PUZZLE) {
				// Si la recherche dépasse un certain seuil
				if (queue.size() >= limit) {
					System.out.println("Seuil fixé (" + limit + ") dépassé");
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
				System.out.println("Trouvé !");
				State.printSituation(startState, currentState, iterations);
				return;
			}
			currentState.generateSuccessors();
			for (State state : currentState.getSuccessors()) {
				// iii. INSERER(n',FILE)
				if (!visited) {
					queue.add(state);
				}
				else {
					if (!visitedStates.contains(state)) {
						queue.add(state);
						visitedStates.add(state);
					}
				}
			}
		}
	}

	public static void heuristicSearch(State startState, State finalState, int sizePuzzle, int limit, boolean manhattan) {
		State currentState = new State(startState);
		int iterations = 0;
		Queue<State> queue;
		if (manhattan) {
			queue = new PriorityQueue<State>(new Manhattan(finalState));
		}
		else {
			queue = new PriorityQueue<State>(new SquareFalse(finalState));
		}
		HashSet<State> visitedStates = new HashSet<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			System.out.println("Trouvé !");
			State.printSituation(startState, currentState, iterations);
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
				State.printSituation(startState, currentState, iterations);
				return;
			}

			if (sizePuzzle == BIG_PUZZLE) {
				// Si la recherche dépasse un certain seuil
				if (queue.size() >= limit) {
					System.out.println("Seuil fixé (" + limit + ") dépassé");
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
				System.out.println("Trouvé !");
				State.printSituation(startState, currentState, iterations);
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
