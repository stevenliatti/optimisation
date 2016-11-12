package tp2_sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {
	
	public final static int SUDOKU_SIZE = 9;
	public final static int SQUARE_LATIN_SIZE = (int) Math.sqrt(SUDOKU_SIZE);

	public static void main(String[] args) throws IOException {
		Square[][] board = boardFromFile("data/sudoku.txt");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		initConstraint(board);
		System.out.println("-------------------------------------------------");
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
	
	public static void initConstraint(Square[][] board) {
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				if (board[i][j].isTaken()) {
					for (int k = 0; k < board.length; k++) {
						if (board[i][k].isFree()) {
							board[i][k].getPossibleValues().remove(board[i][j].getValue());
						}
					}
					
					for (int l = 0; l < board.length; l++) {
						if (board[l][j].isFree()) {
							board[l][j].getPossibleValues().remove(board[i][j].getValue());
						}
					}
					
					int modLine = (i / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
					int modColumn = (j / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
					
					for (int k = modLine; k < modLine + SQUARE_LATIN_SIZE; k++) {
						for (int l = modColumn; l < modColumn + SQUARE_LATIN_SIZE; l++) {
							if (board[k][l].isFree()) {
								board[k][l].getPossibleValues().remove(board[i][j].getValue());
							}
						}
					}
				}
			}
		}
	}

}
