package de.bsailer.sudokusolver;

import java.util.Set;

public class Solver {

  private State state;

  public Solver(final State state) {
    this.state = state;
  }

  public Solution solve() {
    int oldNumSetValues = 0;
    int currentNumSetValues = 0;
    do {
      oldNumSetValues = currentNumSetValues;
      state = solve(state);
      currentNumSetValues = state.numSetValues();
    } while (oldNumSetValues < currentNumSetValues);
    return new Solution(state,
        state.isSolved() ? Solution.Result.SOLVED : Solution.Result.NOT_SOLVED);
  }

  State solve(final State state) {
    State result = state;
    for (final Position position : PositionCollectionFactory.forUnset(state.getStateContext(),
        state)) {
      result = solve(result, position);
    }
    return result;
  }

  public State solve(final State state, final Position position) {
    final Set<Value> possibleValues = state.possibleValues(position);
    if (possibleValues.size() == 1) {
      final Value targetValue = possibleValues.iterator().next();
      return state.setValue(position, targetValue);
    } else {
      for (final Value value : possibleValues) {
        boolean onlyPossiblePosition = true;
        if (hasOtherPossiblePositionInColumn(state, value, position)
            && hasOtherPossiblePositionInRow(state, value, position)) {
          onlyPossiblePosition = false;
        }
        if (onlyPossiblePosition) {
          return state.setValue(position, value);
        }
      }
    }
    return state;
  }

  private boolean hasOtherPossiblePositionInColumn(final State state, final Value value,
      final Position position) {
    for (final Position otherPosition : PositionCollectionFactory.forColumn(state.getStateContext(),
        position)) {
      if (isValidValueAtPosition(state, value, otherPosition)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasOtherPossiblePositionInRow(final State state, final Value value,
      final Position position) {
    for (final Position otherPosition : PositionCollectionFactory.forRow(state.getStateContext(),
        position)) {
      if (isValidValueAtPosition(state, value, otherPosition)) {
        return true;
      }
    }
    return false;
  }

  private boolean isValidValueAtPosition(final State state, final Value value,
      final Position position) {
    final Set<Value> possibleValues = state.possibleValues(position);
    return possibleValues.contains(value);
  }

}
