package tp1_puzzle;

import java.util.Scanner;

public class Test {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous entrer à la main les états initiaux et finaux ? (y/n)");
		if (sc.next().equals("y")) {
			System.out.println("OCucou");
		}
	}
}
