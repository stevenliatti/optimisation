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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		Point branch = initConstraint(board);
		System.out.println("-------------------------------------------------");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-------------------------------------------------");
		System.out.println(branch);
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

	//	private static void updateLiCoSq(Square[][] board, int li, int co, int fixedLi, int fixedCo) {
	//		if (board[li][co].isFree()) {
	//			board[li][co].getPossibleValues().remove(board[li][fixed].getValue());
	//			if (min > board[li][co].getPossibleValues().size()) {
	//				min = board[li][co].getPossibleValues().size();
	//				bestSquare.setLocation(li, co);
	//			}
	//		}
	//	}

	public static Point initConstraint(Square[][] board) {
		Point bestSquare = new Point();
		int min = SUDOKU_SIZE;
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				if (board[i][j].isTaken()) {
					for (int k = 0; k < board.length; k++) {
						if (board[i][k].isFree()) {
							board[i][k].getPossibleValues().remove(board[i][j].getValue());
							if (min > board[i][k].getPossibleValues().size()) {
								min = board[i][k].getPossibleValues().size();
								bestSquare.setLocation(i, k);
							}
						}
					}

					for (int l = 0; l < board.length; l++) {
						if (board[l][j].isFree()) {
							board[l][j].getPossibleValues().remove(board[i][j].getValue());
							if (min > board[l][j].getPossibleValues().size()) {
								min = board[l][j].getPossibleValues().size();
								bestSquare.setLocation(l, j);
							}
						}
					}

					int modLine = (i / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
					int modColumn = (j / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;

					for (int k = modLine; k < modLine + SQUARE_LATIN_SIZE; k++) {
						for (int l = modColumn; l < modColumn + SQUARE_LATIN_SIZE; l++) {
							if (board[k][l].isFree()) {
								board[k][l].getPossibleValues().remove(board[i][j].getValue());
								if (min > board[k][l].getPossibleValues().size()) {
									min = board[k][l].getPossibleValues().size();
									bestSquare.setLocation(k, l);
								}
							}
						}
					}
				}
			}
		}
		return bestSquare;
	}

	public static void updateConstraint(Square[][] board, Point coordSquare, boolean check) {
		Square updateSquare = board[coordSquare.x][coordSquare.y];
		if (updateSquare.getPossibleValues().size() == 1) {
			if (check) {
				for (int i = 0; i < board.length; i++) {
//					if (board[coordSquare.x][i].getValue().equals(updateSquare)) {
//						
//					}
				}
		
				for (int i = 0; i < board.length; i++) {
					
				}
		
				int modLine = (coordSquare.x / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
				int modColumn = (coordSquare.y / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
		
				for (int i = modLine; i < modLine + SQUARE_LATIN_SIZE; i++) {
					for (int j = modColumn; j < modColumn + SQUARE_LATIN_SIZE; j++) {
						
					}
				}
			}
			else {
				Point bestSquare = new Point();
				int min = SUDOKU_SIZE;
				
				for (int k = 0; k < board.length; k++) {
					if (board[coordSquare.x][k].isFree()) {
						board[coordSquare.x][k].getPossibleValues().remove(updateSquare.getValue());
						if (min > board[coordSquare.x][k].getPossibleValues().size()) {
							min = board[coordSquare.x][k].getPossibleValues().size();
							bestSquare.setLocation(coordSquare.x, k);
						}
					}
				}
		
				for (int l = 0; l < board.length; l++) {
					if (board[l][coordSquare.y].isFree()) {
						board[l][coordSquare.y].getPossibleValues().remove(board[coordSquare.x][coordSquare.y].getValue());
						if (min > board[l][coordSquare.y].getPossibleValues().size()) {
							min = board[l][coordSquare.y].getPossibleValues().size();
							bestSquare.setLocation(l, coordSquare.y);
						}
					}
				}
		
				int modLine = (coordSquare.x / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
				int modColumn = (coordSquare.y / SQUARE_LATIN_SIZE) * SQUARE_LATIN_SIZE;
		
				for (int k = modLine; k < modLine + SQUARE_LATIN_SIZE; k++) {
					for (int l = modColumn; l < modColumn + SQUARE_LATIN_SIZE; l++) {
						if (board[k][l].isFree()) {
							board[k][l].getPossibleValues().remove(board[coordSquare.x][coordSquare.y].getValue());
							if (min > board[k][l].getPossibleValues().size()) {
								min = board[k][l].getPossibleValues().size();
								bestSquare.setLocation(k, l);
							}
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < updateSquare.getPossibleValues().size(); i++) {
				
			}
		}
		
			

	}

	public static void updateBoard(Square[][] board, Point coordSquare) {
		Square updateSquare = board[coordSquare.x][coordSquare.y];

	}
}
