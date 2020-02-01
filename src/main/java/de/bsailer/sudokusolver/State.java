package de.bsailer.sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class State {

  private static final List<Filter> FILTERS =
      Arrays.asList(new RowFilter(), new ColumnFilter(), new AreaFilter());

  interface Filter {
    Set<Value> filter(Set<Value> input, State state, Position position);
  }

  private static class RowFilter implements Filter {

    @Override
    public Set<Value> filter(final Set<Value> input, final State state, final Position position) {
      final Set<Value> result = new HashSet<>(input);
      result.removeAll(
          state.getValuesSet(PositionCollectionFactory.forRow(state.getStateContext(), position)));
      return result;
    }

  }

  private static class ColumnFilter implements Filter {

    @Override
    public Set<Value> filter(final Set<Value> input, final State state, final Position position) {
      final Set<Value> result = new HashSet<>(input);
      result.removeAll(state
          .getValuesSet(PositionCollectionFactory.forColumn(state.getStateContext(), position)));
      return result;
    }

  }

  private static class AreaFilter implements Filter {

    @Override
    public Set<Value> filter(final Set<Value> input, final State state, final Position position) {
      final Set<Value> result = new HashSet<>(input);
      result.removeAll(
          state.getValuesSet(PositionCollectionFactory.forTile(state.getStateContext(), position)));
      return result;
    }

  }

  private final List<List<Value>> values;
  private final StateContext stateContext;

  public State(final StateContext stateContext) {
    this(initializeArray(stateContext), stateContext);
  }

  private State(final List<List<Value>> newValues, final StateContext stateContext) {
    assertDimensions(newValues, stateContext);
    this.stateContext = stateContext;
    this.values = newValues;
  }

  private static void assertDimensions(final List<List<Value>> newValues,
      final StateContext stateContext) {
    if (stateContext.numRows() != newValues.size()) {
      throw new AssertionError("stateContext.numRows(): " + stateContext.numRows()
          + " does not match newValues.size() : " + newValues.size());
    }
    for (final List<Value> row : newValues) {
      if (stateContext.numColumns() != row.size()) {
        throw new AssertionError("stateContext.numColumns(): " + stateContext.numColumns()
            + " does not match row.size() : " + row.size());
      }
    }
  }

  public static State fromValues(final List<List<Value>> values, final StateContext stateContext) {
    return new State(values, stateContext);
  }

  public State setValue(final Position position, final Value value) {
    final List<List<Value>> newValues = clone(stateContext, values);
    newValues.get(position.getRow()).set(position.getColumn(), value);
    return new State(newValues, stateContext);
  }

  public boolean isValueSet(final Position position) {
    return getValue(position) != null;
  }

  public boolean isSolved() {
    return numTotalValues() == numSetValues();
  }

  public int numSetValues() {
    return PositionCollectionFactory.forAll(stateContext).stream()
        .filter((position) -> isValueSet(position)).collect(Collectors.counting()).intValue();
  }

  public Set<Value> possibleValues(final Position position) {
    if (isValueSet(position)) {
      return Collections.singleton(getValue(position));
    }
    Set<Value> result = stateContext.allValues();
    for (final Filter filter : filters()) {
      result = filter.filter(result, this, position);
    }
    return result;
  }

  public Value getValue(final Position position) {
    return getValue(values, position);
  }

  @Override
  public boolean equals(final Object o) {
    if (o instanceof State) {
      final State other = (State) o;
      return values.equals(other.values);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return values.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " " + values.toString();
  }

  Set<Value> getValuesSet(final Collection<Position> positions) {
    return positions.stream().map((p) -> getValue(p)).filter((v) -> (v != null))
        .collect(Collectors.toSet());
  }

  private static List<List<Value>> initializeArray(final StateContext stateContext) {
    final List<List<Value>> result = new ArrayList<>(stateContext.numRows());
    for (int row = 0; row < stateContext.numRows(); row++) {
      result.add(initializeRowArray(stateContext));
    }
    return result;
  }

  private Collection<Filter> filters() {
    return FILTERS;
  }

  private static List<Value> initializeRowArray(final StateContext stateContext) {
    final List<Value> result = new ArrayList<>(stateContext.numColumns());
    for (int column = 0; column < stateContext.numColumns(); column++) {
      result.add(null);
    }
    return result;
  }

  private static List<Value> cloneRow(final StateContext stateContext,
      final List<List<Value>> original, final int row) {
    final List<Value> result = new ArrayList<>();
    for (int column = 0; column < stateContext.numColumns(); column++) {
      result.add(getValue(original, Position.of(row, column)));
    }
    return result;
  }

  private int numTotalValues() {
    return PositionCollectionFactory.forAll(stateContext).stream().collect(Collectors.counting())
        .intValue();
  }

  public StateContext getStateContext() {
    return stateContext;
  }

  private static List<List<Value>> clone(final StateContext stateContext,
      final List<List<Value>> original) {
    final List<List<Value>> result = new ArrayList<>();
    for (int row = 0; row < stateContext.numRows(); row++) {
      result.add(cloneRow(stateContext, original, row));
    }
    return result;
  }

  private static Value getValue(final List<List<Value>> values, final Position position) {
    return values.get(position.getRow()).get(position.getColumn());
  }

}
