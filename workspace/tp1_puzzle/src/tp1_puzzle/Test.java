package tp1_puzzle;

import java.util.PriorityQueue;
import java.util.Queue;


public class Test {
	public static void main(String[] args) {
		final int[][] canonical = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		final int[][] current = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
		State finalState = new State(canonical);
		State state = new State(current);
		
		System.out.println(finalState.manhattan(finalState));
		System.out.println(state.squareFalse(finalState));
		System.out.println(state.manhattan(finalState));
		
		SquareFalse sq = new SquareFalse(finalState);
		System.out.println(sq.compare(state, finalState));
		
		Manhattan mh = new Manhattan(finalState);
		System.out.println(mh.compare(state, finalState));
		
		Queue<Integer> queue = new PriorityQueue<Integer>();
		
		int max = 15;
		
		for (int i = max; i > 0; i--) {
			queue.add(i);
		}
		
//		for (int i = 0; i < max; i++) {
//			queue.add(i);
//		}
		
		for (int i = 0; i < max; i++) {
//			System.err.println(queue.poll());
		}
	}
}
