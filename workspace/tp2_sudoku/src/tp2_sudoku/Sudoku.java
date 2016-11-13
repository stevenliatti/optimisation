package tp2_sudoku;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

public class Sudoku {

	public static void main(String[] args) throws IOException {
		Board board = new Board("data/sudoku.txt");
		System.out.println(board);
		System.out.println("-------------------------------------------------");
		resolve(board, false);
	}

	public static void resolve(Board board, boolean found) {
		if (!found) {
			for (Entry<Integer, List<Point>> entry : board.getBestSquares().entrySet()) {
				for (Point point : entry.getValue()) {
					Square square = board.getBoard()[point.x][point.y];

					for (String value : square.getLegalValues()) {
						if (check(board, point, value)) {
							Board newBoard = update(board, point, value);
							System.out.println(newBoard);
							if (newBoard.isFull()) {
								System.out.println(newBoard);
							}
							else {
								resolve(newBoard, false);
							}
						}
					}
				}
			}
		}
		else {
			System.out.println(board);
		}
	}

	private static boolean check(Board board, Point point, String value) {
		int modRow = (point.x / Board.SQUARE_LATIN_SIZE) * Board.SQUARE_LATIN_SIZE;
		int modColumn = (point.y / Board.SQUARE_LATIN_SIZE) * Board.SQUARE_LATIN_SIZE;

		for (int i = 0; i < board.getSize(); i++) {
			if (board.getBoard()[i][point.y].getValue().equals(value))
				return false;
			if (board.getBoard()[point.x][i].getValue().equals(value))
				return false;
		}

		for (int i = modRow; i < modRow + Board.SQUARE_LATIN_SIZE; i++) {
			for (int j = modColumn; j < modColumn + Board.SQUARE_LATIN_SIZE; j++) {
				if (board.getBoard()[i][j].getValue().equals(value))
					return false;
			}
		}

		return true;
	}

	private static Board update(Board board, Point point, String value) {
		Board newBoard = new Board(board);
		
		for (int i = 0; i < newBoard.getSize(); i++) {
			newBoard.getBoard()[i][point.y].updateLegalValues(value);
			newBoard.getBoard()[point.x][i].updateLegalValues(value);
		}

		int modRow = (point.x / Board.SQUARE_LATIN_SIZE) * Board.SQUARE_LATIN_SIZE;
		int modColumn = (point.y / Board.SQUARE_LATIN_SIZE) * Board.SQUARE_LATIN_SIZE;
		
		for (int i = modRow; i < modRow + Board.SQUARE_LATIN_SIZE; i++) {
			for (int j = modColumn; j < modColumn + Board.SQUARE_LATIN_SIZE; j++) {
				newBoard.getBoard()[i][j].updateLegalValues(value);
			}
		}

		newBoard.getBoard()[point.x][point.y].setValue(value);
		newBoard.getBoard()[point.x][point.y].setTaken(true);
		newBoard.reduceSquareFree();
		newBoard.findBestsSquare();
		
		return newBoard;
	}
}
