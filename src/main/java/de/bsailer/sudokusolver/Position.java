package de.bsailer.sudokusolver;

import java.util.Objects;

public class Position {

  private final int row;
  private final int column;

  private Position(final int row, final int column) {
    this.row = row;
    this.column = column;
  }

  public static Position of(final int row, final int column) {
    return new Position(row, column);
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(final Object o) {
    if (o instanceof Position) {
      final Position other = (Position) o;
      return row == other.row && column == other.column;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " (" + row + ", " + column + ")";
  }
}
