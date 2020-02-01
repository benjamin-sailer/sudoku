package de.bsailer.sudokusolver;

import java.util.Arrays;

public final class Solution {

  public enum Result {
    SOLVED, NOT_SOLVED
  }

  private final State state;
  private final Solution.Result result;

  public Solution(final State state, final Solution.Result result) {
    this.result = result;
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public Solution.Result getResult() {
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (o instanceof Solution) {
      final Solution other = (Solution) o;
      return propertyList().equals(other.propertyList());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return propertyList().hashCode();
  }

  private Object propertyList() {
    return Arrays.asList(state, result);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " " + propertyList().toString();
  }
}
