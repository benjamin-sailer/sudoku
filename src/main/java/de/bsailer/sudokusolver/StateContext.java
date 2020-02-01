package de.bsailer.sudokusolver;

import java.util.Set;

public interface StateContext {

  int numRows();

  int numColumns();

  int tileId(Position position);

  Set<Value> allValues();

  Value from(String cell);
}
