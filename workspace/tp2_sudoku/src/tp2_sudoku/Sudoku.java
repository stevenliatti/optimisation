package tp2_sudoku;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {

	public final static int SUDOKU_SIZE = 9;
	public final static int SQUARE_LATIN_SIZE = (int) Math.sqrt(SUDOKU_SIZE);

	public static void main(String[] args) throws IOException {
		Square[][] board = boardFromFile("data/sudoku.txt");
		printBoard(board);
		System.out.println("-------------------------------------------------");
		Point branch = initConstraint(board);
		printBoard(board);
		System.out.println("-------------------------------------------------");
		System.out.println(branch);
	}

	public static void printBoard(Square[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static Square[][] boardFromFile(String fileName) throws FileNotFoundException, IOException {
		Square[][] board = new Square[SUDOKU_SIZE][SUDOKU_SIZE];
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		String[] split;
		int i = 0;
		while ((line = br.readLine()) != null) {
			split = line.split(" ");
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				board[i][j] = new Square(split[j]);
			}
			i++;
		}

		br.close();
		return board;
	}

	public static Square[][] copyOfBoard(Square[][] board) {
		final int size = board.length;
		Square[][] newBoard = new Square[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newBoard[i][j] = new Square(board[i][j]);
			}
		}

		return newBoard;
	}

	public static Point initConstraint(Square[][] board) {
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				if (board[i][j].isTaken()) {
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
		return findBestSquare(board);
	}

	private static boolean checkOrUpdate(Square[][] board, Point square, String value, boolean check) {
		int modRow = (square.x / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
		int modColumn = (square.y / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
		
		if (check) {
			for (int i = 0; i < board.length; i++) {
				if (board[i][square.y].getValue().equals(value))
					return false;
				if (board[square.x][i].getValue().equals(value))
					return false;
			}

			for (int i = modRow; i < modRow + SQUARE_LATIN_SIZE; i++) {
				for (int j = modColumn; j < modColumn + SQUARE_LATIN_SIZE; j++) {
					if (board[i][j].getValue().equals(value))
						return false;
				}
			}
		}
		else {
			for (int i = 0; i < board.length; i++) {
				board[i][square.y].updatePossibleValues(value);
				board[square.x][i].updatePossibleValues(value);
			}

			for (int i = modRow; i < modRow + SQUARE_LATIN_SIZE; i++) {
				for (int j = modColumn; j < modColumn + SQUARE_LATIN_SIZE; j++) {
					board[i][j].updatePossibleValues(value);
				}
			}
			
			board[square.x][square.y].setValue(value);
		}
		return true;
	}

	private static Point findBestSquare(Square[][] board) {
		Point bestSquare = new Point();
		int min = SUDOKU_SIZE;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j].isFree()) {
					if (min > board[i][j].getLegalValues().size()) {
						min = board[i][j].getLegalValues().size();
						bestSquare.setLocation(i, j);
					}
				}
			}
		}
		
		return bestSquare;
	}
	
	public static void updateBoard(Square[][] board, Point square) {
		Square updateSquare = board[square.x][square.y];

		for (String value : updateSquare.getLegalValues()) {
			if (checkOrUpdate(board, square, value, true)) {
				Square[][] newBoard = copyOfBoard(board);
				checkOrUpdate(newBoard, square, value, false);
				
				
				Point newSquare = findBestSquare(newBoard);
				updateBoard(newBoard, newSquare);
			}
		}
	}
}
