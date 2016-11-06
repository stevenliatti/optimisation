package tp1_puzzle;


public class Test {
	public static void main(String[] args) {
		final int[][] canonical = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] current = {{5,2,8}, {4,0,1}, {7,3,6}};
		State finalState = new State(canonical);
		State state = new State(current);
		System.out.println(State.squareFalse(state, finalState));
		System.out.println(State.manhattan(state, finalState));
	}
}
