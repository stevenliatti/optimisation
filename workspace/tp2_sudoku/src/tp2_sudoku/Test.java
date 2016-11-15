package tp2_sudoku;

public class Test {
	public static void main(String[] args) {
		int size = 9;
		Square[][] board = new Square[size][size];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = new Square("42", i, j);
			}
		}
		System.out.println("-----------------------------");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.println("Avant : " + board[i][j].getLegalValues());
			}
		}
		func(board, size - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.println("Après : " + board[i][j].getLegalValues());
			}
		}
	}
	
	public static void func(Square[][] board, int recursion) {
		if (recursion == 0) {
			return;
		}
		else {
			if (recursion % 2 == 0) {
				board[0][0].getLegalValues().add(String.valueOf(recursion));
				func(board, recursion - 1);
			}
			else {
				board[0][1].getLegalValues().add(String.valueOf(recursion));
				func(board, recursion - 1);
			}
			
		}
	}
}
