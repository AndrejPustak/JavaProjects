package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Class which represents a constraint in CalcLayout.
 * @author Andrej
 *
 */
public class RCPosition {
	
	/**
	 * Row in the layout
	 */
	private final int row;
	
	/**
	 * Column in the layout
	 */
	private final int column;
	
	/**
	 * Constructor for the RCPosition
	 * @param row row in the layout
	 * @param column coulumn in the layout
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter for the row
	 * @return row of the constraint
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for the column
	 * @return column of the constraint
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	
	
	
}
