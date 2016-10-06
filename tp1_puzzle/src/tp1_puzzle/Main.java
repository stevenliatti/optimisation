package tp1_puzzle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) {
		final String canonical = "123456780";
		State finalState = new State(canonical);
		System.out.println(finalState);
		State currentState = randomState(canonical);
		//		List<State> list = new ArrayList<State>();
		//		list.add(new State(canonical));
		//		currentState.setSuccessors(list);
		int iterations = 0;
		PriorityQueue<State> queue = new PriorityQueue<State>();

		// 1. Si SOLUTION?(état-initial) alors retourner état-initial
		if (currentState.equals(finalState)) {
			System.out.println("Solution trouvée en " + iterations + "itérations");
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
				queue.add(state);
			}
		}
	}

	public static State randomState(String string) {
		List<String> letters = Arrays.asList(string.split(""));
		Collections.shuffle(letters);
		String newString = "";

		for (String letter : letters) {
			newString += letter;
		}

		return new State(newString);
	}

}
