package tp2_sudoku;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Cette classe définit une grille de sudoku, composée de
 * cases (Square). Elle contient la taille (du carré, le côté),
 * le nombre de cases vides et un tableau associatif entre un nombre
 * de possibilités par case et la liste des positions (dans la grille)
 * associées.
 * 
 * @author Steven Liatti
 */
public class Board {
	public final static int SUDOKU_SIZE = 9;
	public final static int SQUARE_LATIN_SIZE = (int) Math.sqrt(SUDOKU_SIZE);

	private Square[][] board;
	private int size;
	private int squaresFree;
	private TreeMap<Integer, List<Point>> bestSquares;

	/**
	 * Construit une nouvelle grille à partir d'un fichier (son nom). 
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public Board(String fileName) throws IOException {
		this.size = SUDOKU_SIZE;
		this.squaresFree = size * size;
		board = new Square[SUDOKU_SIZE][SUDOKU_SIZE];
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		String[] split;
		int i = 0;
		while ((line = br.readLine()) != null) {
			split = line.split(" ");
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				board[i][j] = new Square(split[j], i, j);
			}
			i++;
		}
		br.close();
		initConstraint();
	}
	
	/**
	 * Construit une grille à partir d'une autre.
	 * 
	 * @param other
	 */
	public Board(Board other) {
		this.size = other.size;
		this.squaresFree = other.squaresFree;
		this.board = new Square[size][size];
		this.bestSquares = new TreeMap<Integer, List<Point>>(other.bestSquares);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = new Square(other.board[i][j]);
			}
		}
	}

	/**
	 * Retourne un String mentionnant les cases libres et une représentation
	 * classique du sudoku.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Cases libres : " + squaresFree + "\n");
		for (int j = 0; j < size + SQUARE_LATIN_SIZE; j++) {
			sb.append("_ ");
		}
		sb.append("\n\n");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sb.append(board[i][j] + " ");
				if (j % SQUARE_LATIN_SIZE == SQUARE_LATIN_SIZE - 1) {
					sb.append("| ");
				}
			}
			sb.append("\n");
			if (i % SQUARE_LATIN_SIZE == SQUARE_LATIN_SIZE - 1) {
				for (int j = 0; j < size + SQUARE_LATIN_SIZE; j++) {
					sb.append("_ ");
				}
				sb.append("\n\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Retourne la case la plus contrainte (qui possède le moins de possibilités).
	 * 
	 * @return
	 */
	public Square getBestSquare() {
		List<Point> points = bestSquares.get(bestSquares.firstKey());
		Point point = points.get(0);
		return board[point.x][point.y];
	}

	/**
	 * Met à jour la grille lorsqu'une nouvelle valeur légale est
	 * insérée dans la case la plus contrainte.
	 * 
	 * @param inUpdate
	 * @param value
	 */
	public void update(Square inUpdate, String value) {
		Point point = inUpdate.getPosition();

		inUpdate.setTaken(true);
		inUpdate.setValue(value);

		for (int i = 0; i < size; i++) {
			if (board[i][point.y].isFree()) {
				board[i][point.y].updateLegalValues(value);
			}
			if (board[point.x][i].isFree()) {
				board[point.x][i].updateLegalValues(value);
			}
		}

		int modRow = (point.x / Board.SQUARE_LATIN_SIZE) * Board.SQUARE_LATIN_SIZE;
		int modColumn = (point.y / Board.SQUARE_LATIN_SIZE) * Board.SQUARE_LATIN_SIZE;

		for (int i = modRow; i < modRow + Board.SQUARE_LATIN_SIZE; i++) {
			for (int j = modColumn; j < modColumn + Board.SQUARE_LATIN_SIZE; j++) {
				if (board[i][j].isFree()) {
					board[i][j].updateLegalValues(value);
				}
			}
		}

		this.reduceSquareFree();
		this.findBestsSquare();
	}

	/**
	 * Recalcule la table associative des meilleures cases.
	 */
	public void findBestsSquare() {
		bestSquares = new TreeMap<Integer, List<Point>>();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].isFree()) {
					List<Point> points = bestSquares.get(board[i][j].getLegalValues().size());
					if (points == null) {
						points = new ArrayList<Point>();
					}
					points.add(new Point(i, j));
					bestSquares.put(board[i][j].getLegalValues().size(), points);
				}
			}
		}
	}

	/**
	 * Initialisation des contraintes. Est appelée à la construction de l'objet.
	 * Elle actualise les cases légales pour chaque case.
	 */
	private void initConstraint() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (board[i][j].isTaken()) {
					this.squaresFree--;

					for (int k = 0; k < board.length; k++) {
						if (board[i][k].isFree()) {
							board[i][k].getLegalValues().remove(board[i][j].getValue());
						}
					}

					for (int l = 0; l < board.length; l++) {
						if (board[l][j].isFree()) {
							board[l][j].getLegalValues().remove(board[i][j].getValue());
						}
					}

					int modRow = (i / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
					int modColumn = (j / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;

					for (int k = modRow; k < modRow + SQUARE_LATIN_SIZE; k++) {
						for (int l = modColumn; l < modColumn + SQUARE_LATIN_SIZE; l++) {
							if (board[k][l].isFree()) {
								board[k][l].getLegalValues().remove(board[i][j].getValue());
							}
						}
					}
				}
			}
		}
		findBestsSquare();
	}

	/**
	 * Réduit le nombre de cases vides de 1.
	 */
	public void reduceSquareFree() {
		squaresFree--;
	}

	/**
	 * Indique si le sudoku est rempli et donc (en théorie)
	 * une solution a été trouvée.
	 * 
	 * @return
	 */
	public boolean isFull() {
		return squaresFree == 0;
	}

	/**
	 * Retourne le tableau à deux dimensions contenant les cases.
	 * 
	 * @return le tableau à deux dimensions contenant les cases
	 */
	public Square[][] getBoard() {
		return board;
	}

	/**
	 * Retourne la taille de la grille.
	 * 
	 * @return la taille de la grille
	 */
	public int getSize() {
		return size;
	}
}
