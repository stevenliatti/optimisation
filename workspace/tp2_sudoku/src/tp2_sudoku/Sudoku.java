package tp2_sudoku;

import java.io.IOException;
import java.util.List;

/**
 * Classe principale du programme. Construit un sudoku à partir
 * d'un fichier et le résout.
 * 
 * @author Steven Liatti
 */
public class Sudoku {

	static boolean FOUND = false;

	/**
	 * Point d'entrée du programme.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Board board = new Board("data/sudoku.txt");
		System.out.println(board);
		System.out.println("-----------------------");
		Board solution = resolve(board);
		System.out.println(solution);
	}

	/**
	 * Fonction implémentant l'algorithme de backtracking.
	 * 
	 * @param board
	 * @return
	 */
	public static Board resolve(Board board)
	{
		// 1. Si assignement A est complet alors retourner A
		if(board.isFull()) {
			return board;
		}

		//2. X <- sélectionner une variable absente de A
		//3. D <- sélectionner un ordre sur le domaine de X
		Board newBoard = new Board(board);
		Square bestSquare = newBoard.getBestSquare();
		List<String> values = bestSquare.getLegalValues();

		// 4. Pour chaque valeur v dans D faire
		for (int i = 0; i < values.size(); i++) {
			// a. Add (X <- v) à A
			// b. var-domaines <- forward checking(var-domaines, X, v, A)
			// c. Si aucune variable a une domaine vide alors
			// i. résultat <- PSC-BACKTRACKING(A, var-domaines)
			newBoard.update(bestSquare, values.get(i));
			Board solution = resolve(newBoard);

			// ii. Si résultat != échec alors retourner résultat
			if(solution != null) {
				return solution;
			}
		}

		// 5. Retourner échec
		return null;
	}
}
