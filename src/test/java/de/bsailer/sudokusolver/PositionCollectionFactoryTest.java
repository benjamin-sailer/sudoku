package de.bsailer.sudokusolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.Collection;
import java.util.HashSet;
import org.junit.Test;
import de.bsailer.sudokusolver.defaultimpl.DefaultEnumValue;
import de.bsailer.sudokusolver.defaultimpl.DefaultStateContext;

public class PositionCollectionFactoryTest {

  private static final int SUDOKU_SIZE = 81;
  private static final int ROWLENGTH_MINUS_ONE = 8;
  private static final int COLUMN = 4;
  private static final int ROW = 2;

  @Test
  public void forRow() {
    // set-up
    final Position source = Position.of(ROW, COLUMN);
    // excercise
    final Collection<Position> result =
        PositionCollectionFactory.forRow(DefaultStateContext.INSTANCE, source);
    // verify
    assertEquals(ROWLENGTH_MINUS_ONE, result.size());
    for (final Position position : result) {
      assertEquals(ROW, position.getRow());
    }
    assertFalse(new HashSet<>(result).contains(source));
  }

  @Test
  public void forColumn() {
    // set-up
    final Position source = Position.of(ROW, COLUMN);
    // excercise
    final Collection<Position> result =
        PositionCollectionFactory.forColumn(DefaultStateContext.INSTANCE, source);
    // verify
    assertEquals(ROWLENGTH_MINUS_ONE, result.size());
    for (final Position position : result) {
      assertEquals(COLUMN, position.getColumn());
    }
    assertFalse(new HashSet<>(result).contains(source));
  }

  @Test
  public void forTile() {
    // set-up
    final Position source = Position.of(ROW, COLUMN);
    // excercise
    final Collection<Position> result =
        PositionCollectionFactory.forTile(DefaultStateContext.INSTANCE, source);
    // verify
    assertEquals(ROWLENGTH_MINUS_ONE, result.size());
    assertFalse(new HashSet<>(result).contains(source));
  }

  @Test
  public void forAll() {
    // excercise
    final Collection<Position> result =
        PositionCollectionFactory.forAll(DefaultStateContext.INSTANCE);
    // verify
    assertEquals(SUDOKU_SIZE, result.size());
  }

  @Test
  public void forUnsetAll() {
    // set-up
    final State state = new State(DefaultStateContext.INSTANCE);
    // excercise
    final Collection<Position> result =
        PositionCollectionFactory.forUnset(DefaultStateContext.INSTANCE, state);
    // verify
    assertEquals(SUDOKU_SIZE, result.size());
  }

  @Test
  public void forUnsetMinusOne() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    // excercise
    final Collection<Position> result =
        PositionCollectionFactory.forUnset(DefaultStateContext.INSTANCE, state);
    // verify
    assertEquals(SUDOKU_SIZE - 1, result.size());
  }
}
