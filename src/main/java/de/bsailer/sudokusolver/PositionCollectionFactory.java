package de.bsailer.sudokusolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

class PositionCollectionFactory {

  private PositionCollectionFactory() {
    throw new AssertionError("no instance");
  }

  public static Collection<Position> forRow(final StateContext stateContext,
      final Position position) {
    final Collection<Position> result = new ArrayList<>();
    for (int column = 0; column < stateContext.numColumns(); column++) {
      final Position toAdd = Position.of(position.getRow(), column);
      addExceptPosition(result, toAdd, position);
    }
    return result;
  }

  public static Collection<Position> forColumn(final StateContext stateContext,
      final Position position) {
    final Collection<Position> result = new ArrayList<>();
    for (int row = 0; row < stateContext.numRows(); row++) {
      final Position toAdd = Position.of(row, position.getColumn());
      addExceptPosition(result, toAdd, position);
    }
    return result;
  }

  public static Collection<Position> forTile(final StateContext stateContext,
      final Position tilePosition) {
    return forAll(stateContext).stream().filter((position) -> !tilePosition.equals(position))
        .filter((position) -> stateContext.tileId(tilePosition) == stateContext.tileId(position))
        .collect(Collectors.toList());
  }

  private static void addExceptPosition(final Collection<Position> collection, final Position toAdd,
      final Position position) {
    if (!position.equals(toAdd)) {
      collection.add(toAdd);
    }
  }

  public static Collection<Position> forAll(final StateContext stateContext) {
    final Collection<Position> result = new ArrayList<>();
    for (int row = 0; row < stateContext.numRows(); row++) {
      for (int column = 0; column < stateContext.numColumns(); column++) {
        result.add(Position.of(row, column));
      }
    }
    return result;
  }

  public static Collection<Position> forUnset(final StateContext stateContext, final State state) {
    return forAll(stateContext).stream().filter((position) -> !state.isValueSet(position))
        .collect(Collectors.toList());
  }

}
