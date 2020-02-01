package de.bsailer.sudokusolver.defaultimpl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import de.bsailer.sudokusolver.Position;
import de.bsailer.sudokusolver.StateContext;
import de.bsailer.sudokusolver.Value;

public enum DefaultStateContext implements StateContext {

  INSTANCE;

  private static final int NUM_ROWS = 9;
  private static final int NUM_COLUMNS = 9;

  private static final List<Integer> COLUMN_AREA_MAP = Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2, 2);
  private static final List<Integer> ROW_AREA_MAP = Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2, 2);
  private static final int NUM_AREAS_IN_ROW = 3;

  @Override
  public int numRows() {
    return NUM_ROWS;
  }

  @Override
  public int numColumns() {
    return NUM_COLUMNS;
  }

  @Override
  public int tileId(final Position position) {
    return NUM_AREAS_IN_ROW * ROW_AREA_MAP.get(position.getRow())
        + COLUMN_AREA_MAP.get(position.getColumn());
  }

  @Override
  public Set<Value> allValues() {
    return new HashSet<>(EnumSet.allOf(DefaultEnumValue.class));
  }

  @Override
  public Value from(final String cell) {
    return DefaultEnumValue.from(cell);
  }
}
