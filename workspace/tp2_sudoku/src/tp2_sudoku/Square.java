package tp2_sudoku;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe définit une case du sudoku, avec
 * son statut (libre ou occupée), sa valeur en String,
 * sa position dans le sudoku et la liste des valeurs
 * légales qu'elle accepte.
 * 
 * @author Steven Liatti
 */
public final class Square {
	private boolean taken;
	private String value;
	private List<String> legalValues;
	private Point position;
	
	/**
	 * Construit une case à partir d'une valeur et
	 * de coordonnées x et y.
	 * 
	 * @param value
	 * @param x
	 * @param y
	 */
	public Square(String value, int x, int y) {
		this.legalValues = new ArrayList<String>();
		this.value = value;
		this.position = new Point(x, y);
		
		if (value.equals("-")) {
			this.taken = false;
			for (int i = 1; i <= Board.SUDOKU_SIZE; i++) {
				legalValues.add(String.valueOf(i));
			}
		}
		else {
			this.taken = true;
		}
	}
	
	/**
	 * Construit une case à partir d'une valeur et
	 * d'un point.
	 * 
	 * @param value
	 * @param position
	 */
	public Square(String value, Point position) {
		this.legalValues = new ArrayList<String>();
		this.value = value;
		this.position = new Point(position);
		
		if (value.equals("-")) {
			this.taken = false;
			for (int i = 1; i <= Board.SUDOKU_SIZE; i++) {
				legalValues.add(String.valueOf(i));
			}
		}
		else {
			this.taken = true;
		}
	}
	
	/**
	 * Construit une case à partir d'une autre.
	 * 
	 * @param square
	 */
	public Square(Square square) {
		this.taken = square.taken;
		this.value = new String(square.value);
		this.legalValues = new ArrayList<String>(square.getLegalValues());
		this.position = new Point(square.position);
	}
	
	/**
	 * Mets à jour les valeurs légales d'une case.
	 * 
	 * @param value
	 */
	public void updateLegalValues(String value) {
		this.legalValues.remove(value);
	}

	/**
	 * Affiche la valeur en String.
	 * 
	 * @return la valeur de la case
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * Indique si la case est libre.
	 * 
	 * @return si la case est libre
	 */
	public boolean isFree() {
		return !taken;
	}
	
	/**
	 * Indique si la case est occupée.
	 * 
	 * @return si la case est occupée
	 */
	public boolean isTaken() {
		return taken;
	}
	
	/**
	 * Change l'occupation de la case.
	 * 
	 * @param taken
	 */
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	/**
	 * Retourne la valeur (le chiffre).
	 * 
	 * @return la valeur (le chiffre)
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Change la valeur (le chiffre).
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Retourne les valeurs légales.
	 * 
	 * @return les valeurs légales
	 */
	public List<String> getLegalValues() {
		return legalValues;
	}

	/**
	 * Retourne la postion de la case dans la grille.
	 * 
	 * @return la postion de la case dans la grille
	 */
	public Point getPosition() {
		return position;
	}
}
