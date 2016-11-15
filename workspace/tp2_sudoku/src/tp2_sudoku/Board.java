package tp2_sudoku;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Board {
	public final static int SUDOKU_SIZE = 9;
	public final static int SQUARE_LATIN_SIZE = (int) Math.sqrt(SUDOKU_SIZE);

	private Square[][] board;
	private int size;
	private int squaresFree;
	private TreeMap<Integer, List<Point>> bestSquares;

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Cases libres : " + squaresFree + "\n");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				sb.append(board[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Square getBestSquare() {
		List<Point> points = bestSquares.get(bestSquares.firstKey());
		Point point = points.get(0);
		return board[point.x][point.y];
	}

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

	public void reduceSquareFree() {
		squaresFree--;
	}

	public boolean isFull() {
		return squaresFree == 0;
	}

	public Square[][] getBoard() {
		return board;
	}

	public int getSize() {
		return size;
	}

	public int getSquaresFree() {
		return squaresFree;
	}

	public void setSquaresFree(int squaresFree) {
		this.squaresFree = squaresFree;
	}

	public void setBoard(Square[][] board) {
		this.board = board;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public TreeMap<Integer, List<Point>> getBestSquares() {
		return bestSquares;
	}

	public void setBestSquares(TreeMap<Integer, List<Point>> bestSquares) {
		this.bestSquares = bestSquares;
	}
}
