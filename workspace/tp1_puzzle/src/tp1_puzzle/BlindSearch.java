package tp1_puzzle;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BlindSearch {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		final int[][] canonical = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] example1 = {{5, 2, 4}, {7, 3, 1}, {0, 8, 6}};
		final int[][] example2 = {{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};
		final int[][] example3 = {{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};
		State startState;
		State finalState;
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous entrer à la main les états initiaux et finaux ? (y/n)");
		int[][] finalTab = null;
		int[][] startTab = null;
		if (sc.next().equals("y")) {
			System.out.println("État final [1 2 3 4 5 6 7 8 0] : ");
			String str = sc.nextLine();
			String finalStr[] = str.split(" ");
			System.out.println(finalStr.length);
			System.out.println(Math.sqrt(finalStr.length));
			for (int i = 0; i < Math.sqrt(finalStr.length); i++) {
				for (int j = 0; j < Math.sqrt(finalStr.length); j++) {
					System.out.println((int) (Math.sqrt(finalStr.length) * i + j));
					finalTab[i][j] = Integer.parseInt(finalStr[(int) (Math.sqrt(finalStr.length) * i + j)]);
				}
			}
			finalState = new State(finalTab);
			
			System.out.println("État initial [1 2 3 4 5 6 7 8 0] : ");
			str = sc.nextLine();
			String startStr[] = str.split(",");
			for (int i = 0; i < Math.sqrt(finalStr.length); i++) {
				for (int j = 0; j < Math.sqrt(finalStr.length); j++) {
					startTab[i][j] = Integer.parseInt(finalStr[(int) (Math.sqrt(startStr.length) * i + j)]);
				}
			}
			startState = new State(startTab);
		}
		else {
			finalState = new State(canonical);
			startState = State.randomState(canonical);
//			startState = new State(example1);
		}
		State currentState = new State(startState);
		int iterations = 0;
		ConcurrentLinkedQueue<State> queue = new ConcurrentLinkedQueue<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			System.out.println("Solution trouvée en " + iterations + " itérations");
			System.out.println("État de départ : ");
			System.out.println(startState);
			System.out.println("État final : ");
			System.out.println(currentState);
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
			iterations++;
			System.out.println("--- " + iterations + " ---");
			System.out.println(currentState);
			// i.  Créer un nouveau noeud n' comme "enfant" de n
			// ii. Si SOLUTION?(s') alors retourner chemin ou état-solution
			if (currentState.equals(finalState)) {
				System.out.println("Solution trouvée en " + iterations + " itérations");
				System.out.println("État de départ : ");
				System.out.println(startState);
				System.out.println("État final : ");
				System.out.println(currentState);
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
