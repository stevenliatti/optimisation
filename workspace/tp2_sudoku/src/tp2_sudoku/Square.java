package tp2_sudoku;

import java.util.HashSet;
import java.util.Set;

public class Square {
	private boolean taken;
	private String value;
	private Set<String> possibleValues;
	
	public Square() {
		this.taken = false;
		this.value = "-";
		this.possibleValues = new HashSet<String>();
		for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
			possibleValues.add(String.valueOf(i));
		}
	}
	
	public Square(String value) {
		this.possibleValues = new HashSet<String>();
		this.value = value;
		
		if (value.equals("-")) {
			this.taken = false;
			for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
				possibleValues.add(String.valueOf(i));
			}
		}
		else {
			this.taken = true;
		}
	}

	@Override
	public String toString() {
//		return value;
		return String.valueOf(possibleValues.size());
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

	public Set<String> getPossibleValues() {
		return possibleValues;
	}
	
	public void setPossibleValues(Set<String> possibleValues) {
		this.possibleValues = possibleValues;
	}
}
