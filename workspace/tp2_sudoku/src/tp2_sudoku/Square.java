package tp2_sudoku;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class Square {
	private boolean taken;
	private String value;
	private List<String> legalValues;
	private Point position;
	
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
	
	public Square(Square square) {
		this.taken = square.taken;
		this.value = new String(square.value);
		this.legalValues = new ArrayList<String>(square.getLegalValues());
		this.position = new Point(square.position);
	}
	
	public void updateLegalValues(String value) {
		this.legalValues.remove(value);
	}

	@Override
	public String toString() {
		return value;
//		return String.valueOf(legalValues.size());
//		return possibleValues.toString();
	}

	public boolean isFree() {
		return !taken;
	}
	
	public boolean isTaken() {
		return taken;
	}
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getLegalValues() {
		return legalValues;
	}
	
	public void setLegalValues(List<String> legalValues) {
		this.legalValues = legalValues;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
