package de.bsailer.sudokusolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import de.bsailer.sudokusolver.defaultimpl.DefaultEnumValue;
import de.bsailer.sudokusolver.defaultimpl.DefaultStateContext;

public class StateTest {

  private State sut;

  @Before
  public void setUp() {
    sut = new State(DefaultStateContext.INSTANCE);
  }

  @Test
  public void testisValueSetFalse() {
    assertFalse(sut.isValueSet(Position.of(0, 0)));
  }

  @Test
  public void testSetValue() {
    final State state = sut.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    assertTrue(state.isValueSet(Position.of(0, 0)));
  }

  @Test
  public void testEqualsTrue() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final State state2 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    assertEquals(state1, state2);
  }

  @Test
  public void testEqualsFalse() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final State state2 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.TWO);
    assertNotEquals(state1, state2);
  }

  @Test
  public void testEqualsOtherObjectType() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    assertNotEquals(state1, new Object());
  }

  @Test
  public void testEqualsOtherNull() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    assertNotEquals(state1, null);
  }

  @Test
  public void testHashCode() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final State state2 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    assertEquals(state1.hashCode(), state2.hashCode());
  }

  @Test
  public void testPossibleValuesNoLimitation() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.allOf(DefaultEnumValue.class);
    assertEquals(expected, state1.possibleValues(Position.of(3, 3)));
  }

  @Test
  public void testPossibleValuesAlreadyFixed() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.of(DefaultEnumValue.ONE);
    assertEquals(expected, state1.possibleValues(Position.of(0, 0)));
  }

  @Test
  public void testPossibleValuesSameRowLimitation() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.allOf(DefaultEnumValue.class);
    expected.remove(DefaultEnumValue.ONE);
    assertEquals(expected, state1.possibleValues(Position.of(0, 6)));
  }

  @Test
  public void testPossibleValuesSameColumnLimitation() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.allOf(DefaultEnumValue.class);
    expected.remove(DefaultEnumValue.ONE);
    assertEquals(expected, state1.possibleValues(Position.of(6, 0)));
  }

  @Test
  public void testPossibleValuesSameAreaLimitation() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.allOf(DefaultEnumValue.class);
    expected.remove(DefaultEnumValue.ONE);
    assertEquals(expected, state1.possibleValues(Position.of(1, 1)));
  }

  @Test
  public void testPossibleValuesSameAreaLimitationFalse1() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.allOf(DefaultEnumValue.class);
    assertEquals(expected, state1.possibleValues(Position.of(1, 3)));
  }

  @Test
  public void testPossibleValuesSameAreaLimitationFalse2() {
    final State state1 =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Set<DefaultEnumValue> expected = EnumSet.allOf(DefaultEnumValue.class);
    assertEquals(expected, state1.possibleValues(Position.of(3, 1)));
  }

  @Test
  public void testNumSetValuesZero() {
    assertEquals(0, sut.numSetValues());
  }

  @Test
  public void testNumSetValuesOne() {
    assertEquals(1, sut.setValue(Position.of(0, 0), DefaultEnumValue.ONE).numSetValues());
  }

  @Test
  public void testIsSolvedFalse() {
    assertFalse(sut.isSolved());
  }

  @Test
  public void testIsSolvedTrue() {
    final List<Value> row = Arrays.asList(DefaultEnumValue.values());
    final List<List<Value>> values = Arrays.asList(row, row, row, row, row, row, row, row, row);
    assertTrue(State.fromValues(values, DefaultStateContext.INSTANCE).isSolved());
  }

  @Test
  public void testAreaFilter() {
    // set-up
    final State sut =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(4, 1), DefaultEnumValue.ONE);
    // excercise & verify
    assertEquals(EnumSet.of(DefaultEnumValue.TWO, DefaultEnumValue.THREE, DefaultEnumValue.FOUR,
        DefaultEnumValue.FIVE, DefaultEnumValue.SIX, DefaultEnumValue.SEVEN, DefaultEnumValue.EIGHT,
        DefaultEnumValue.NINE), sut.possibleValues(Position.of(3, 0)));
  }

  @Test
  public void testCombinedFilter() {
    // set-up
    final State sut =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(1, 0), DefaultEnumValue.TWO)
            .setValue(Position.of(2, 0), DefaultEnumValue.THREE)
            .setValue(Position.of(4, 1), DefaultEnumValue.ONE)
            .setValue(Position.of(7, 2), DefaultEnumValue.ONE);
    // excercise & verify
    assertEquals(
        EnumSet.of(DefaultEnumValue.FOUR, DefaultEnumValue.FIVE, DefaultEnumValue.SIX,
            DefaultEnumValue.SEVEN, DefaultEnumValue.EIGHT, DefaultEnumValue.NINE),
        sut.possibleValues(Position.of(3, 0)));
  }

}
