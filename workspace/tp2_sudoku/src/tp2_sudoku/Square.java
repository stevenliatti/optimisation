package tp2_sudoku;

import java.util.HashSet;
import java.util.Set;

public final class Square {
	private boolean taken;
	private String value;
	private Set<String> legalValues;
	
	public Square() {
		this.taken = false;
		this.value = "-";
		this.legalValues = new HashSet<String>();
		for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
			legalValues.add(String.valueOf(i));
		}
	}
	
	public Square(String value) {
		this.legalValues = new HashSet<String>();
		this.value = value;
		
		if (value.equals("-")) {
			this.taken = false;
			for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
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
		this.legalValues = new HashSet<String>(square.getLegalValues());
	}
	
	public void updatePossibleValues(String value) {
		this.legalValues.remove(value);
	}
	
	

	@Override
	public String toString() {
//		return value;
		return String.valueOf(legalValues.size());
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

	public Set<String> getLegalValues() {
		return legalValues;
	}
	
	public void setLegalValues(Set<String> legalValues) {
		this.legalValues = legalValues;
	}
}
