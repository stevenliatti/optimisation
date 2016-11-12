package tp2_sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {
	
	public final static int SUDOKU_SIZE = 9;

	public static void main(String[] args) throws IOException {
		Square[][] board = boardFromFile("data/sudoku.txt");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		initConstraint(board);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
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
				if (board[i][j].isFree()) {
					
				}
			}
		}
	}

}
