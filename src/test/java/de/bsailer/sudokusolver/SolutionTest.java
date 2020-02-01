package de.bsailer.sudokusolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import de.bsailer.sudokusolver.defaultimpl.DefaultEnumValue;
import de.bsailer.sudokusolver.defaultimpl.DefaultStateContext;

public class SolutionTest {

  @Test
  public void testEqualsTrue() {
    // set-up
    final Solution reference =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    final Solution sut =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    // excercis & verify
    assertTrue(sut.equals(reference));
  }

  @Test
  public void testEqualsFalseResult() {
    // set-up
    final Solution reference =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    final Solution sut =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.SOLVED);
    // excercis & verify
    assertFalse(sut.equals(reference));
  }

  @Test
  public void testEqualsFalseState() {
    // set-up
    final Solution reference =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    final Solution sut = new Solution(
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE),
        Solution.Result.NOT_SOLVED);
    // excercis & verify
    assertFalse(sut.equals(reference));
  }

  @Test
  public void testEqualsFalseType() {
    // set-up
    final Object reference = new Object();
    final Solution sut = new Solution(
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE),
        Solution.Result.NOT_SOLVED);
    // excercis & verify
    assertFalse(sut.equals(reference));
  }

  @Test
  public void testHashCode() {
    // set-up
    final Solution reference =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    final Solution sut =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    // excercis & verify
    assertEquals(reference.hashCode(), sut.hashCode());
  }

  @Test
  public void testGetState() {
    // set-up
    final Solution sut =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    // excercis & verify
    assertEquals(new State(DefaultStateContext.INSTANCE), sut.getState());
  }

  @Test
  public void testGetResult() {
    // set-up
    final Solution sut =
        new Solution(new State(DefaultStateContext.INSTANCE), Solution.Result.NOT_SOLVED);
    // excercis & verify
    assertEquals(Solution.Result.NOT_SOLVED, sut.getResult());
  }

}
