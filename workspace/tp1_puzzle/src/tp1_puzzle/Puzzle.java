package tp1_puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Classe principale du Puzzle.
 * 
 * @author Steven Liatti
 */
public class Puzzle {

	/**
	 * Énumération pratique pour le menu utilisateur.
	 * 
	 * @author Steven Liatti
	 */
	enum Search {BLIND, SQUARE_FALSE, MANHATTAN;}

	/**
	 * Taille du petit puzzle.
	 */
	public final static int SMALL_PUZZLE = 8;
	/**
	 * Taille du grand puzzle.
	 */
	public final static int BIG_PUZZLE = 15;

	/**
	 * Point d'entrée du programme. Affiche sur la sortie standard un menu 
	 * demandant à l'utilisateur de choisir le mode du programme.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final int[][] canonical8 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] canonical15 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
		final int LIMIT = 300000;
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
			search(startState, finalState, sizePuzzle, LIMIT, false, Search.BLIND);
			break;
		case "2":
			if (sizePuzzle == SMALL_PUZZLE) {
				search(startState, finalState, sizePuzzle, LIMIT, true, Search.BLIND);
			}
			else {
				throw new IllegalArgumentException("Vous ne pouvez faire une recherche avec états visités avec une taille > 8");
			}
			break;
		case "3":
			search(startState, finalState, sizePuzzle, LIMIT, true, Search.SQUARE_FALSE);
			break;
		case "4":
			search(startState, finalState, sizePuzzle, LIMIT, true, Search.MANHATTAN);
			break;
		default:
			search(startState, finalState, sizePuzzle, LIMIT, false, Search.BLIND);
			break;
		}
	}

	/**
	 * Cette fonction implémente tous les types de recherche possibles (dans le
	 * cadre de ce TP) :
	 * 
	 * - Recherche aveugle avec et sans états visités
	 * - Recherche avec les deux heuristiques, Manhattan et SquareFalse
	 * 
	 * @param startState
	 * @param finalState
	 * @param sizePuzzle
	 * @param limit
	 * @param visited Indique si les états visités doivent être gardés en mémoire ou non
	 * @param search Indique le type de recherche : aveugle, SquareFalse ou Manhattan
	 */
	public static void search(State startState, State finalState, int sizePuzzle, int limit, boolean visited, Search search) {
		State currentState = new State(startState);
		int iterations = 0;
		Queue<State> queue;
		HashSet<State> visitedStates = new HashSet<State>();

		switch (search) {
		case BLIND:
			queue = new ConcurrentLinkedQueue<State>();
			break;
		case SQUARE_FALSE:
			queue = new PriorityQueue<State>(new SquareFalse(finalState));
			break;
		case MANHATTAN:
			queue = new PriorityQueue<State>(new Manhattan(finalState));
			break;
		default:
			queue = new ConcurrentLinkedQueue<State>();
			break;
		}

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
}