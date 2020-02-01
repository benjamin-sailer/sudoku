package de.bsailer.sudokusolver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import de.bsailer.sudokusolver.defaultimpl.DefaultEnumValue;
import de.bsailer.sudokusolver.defaultimpl.DefaultStateContext;

@RunWith(MockitoJUnitRunner.class)
public class SolverTest {

  private Solver sut;

  @Mock
  private State state;

  @Mock
  private StateContext stateContext;

  @Before
  public void setUp() {
    when(state.getStateContext()).thenReturn(stateContext);
  }

  @Test
  public void testConstructor() {
    sut = new Solver(state);
  }

  @Test
  public void testSolveIterativeFailure() {
    sut = new Solver(state);
    when(state.numSetValues()).thenReturn(2);
    when(state.isSolved()).thenReturn(false);
    assertEquals(new Solution(state, Solution.Result.NOT_SOLVED), sut.solve());
  }

  @Test
  public void testSolveIterativeSuccess() {
    sut = new Solver(state);
    when(state.numSetValues()).thenReturn(2, 3);
    when(state.isSolved()).thenReturn(true);
    assertEquals(new Solution(state, Solution.Result.SOLVED), sut.solve());
  }

  @Test
  public void testSolvePositionSufficientRow() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 1), DefaultEnumValue.TWO)
            .setValue(Position.of(0, 2), DefaultEnumValue.THREE)
            .setValue(Position.of(0, 3), DefaultEnumValue.FOUR)
            .setValue(Position.of(0, 4), DefaultEnumValue.FIVE)
            .setValue(Position.of(0, 5), DefaultEnumValue.SIX)
            .setValue(Position.of(0, 6), DefaultEnumValue.SEVEN)
            .setValue(Position.of(0, 7), DefaultEnumValue.EIGHT)
            .setValue(Position.of(0, 8), DefaultEnumValue.NINE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionInsufficientRow() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 1), DefaultEnumValue.TWO)
            .setValue(Position.of(0, 3), DefaultEnumValue.FOUR)
            .setValue(Position.of(0, 4), DefaultEnumValue.FIVE)
            .setValue(Position.of(0, 5), DefaultEnumValue.SIX)
            .setValue(Position.of(0, 6), DefaultEnumValue.SEVEN)
            .setValue(Position.of(0, 7), DefaultEnumValue.EIGHT)
            .setValue(Position.of(0, 8), DefaultEnumValue.NINE);
    final State expected = state;
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionSufficientColumn() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(1, 0), DefaultEnumValue.TWO)
            .setValue(Position.of(2, 0), DefaultEnumValue.THREE)
            .setValue(Position.of(3, 0), DefaultEnumValue.FOUR)
            .setValue(Position.of(4, 0), DefaultEnumValue.FIVE)
            .setValue(Position.of(5, 0), DefaultEnumValue.SIX)
            .setValue(Position.of(6, 0), DefaultEnumValue.SEVEN)
            .setValue(Position.of(7, 0), DefaultEnumValue.EIGHT)
            .setValue(Position.of(8, 0), DefaultEnumValue.NINE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionSufficientTile() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(1, 0), DefaultEnumValue.TWO)
            .setValue(Position.of(2, 0), DefaultEnumValue.THREE)
            .setValue(Position.of(0, 1), DefaultEnumValue.FOUR)
            .setValue(Position.of(1, 1), DefaultEnumValue.FIVE)
            .setValue(Position.of(2, 1), DefaultEnumValue.SIX)
            .setValue(Position.of(0, 2), DefaultEnumValue.SEVEN)
            .setValue(Position.of(1, 2), DefaultEnumValue.EIGHT)
            .setValue(Position.of(2, 2), DefaultEnumValue.NINE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionSufficientMixedFilter() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(1, 0), DefaultEnumValue.TWO)
            .setValue(Position.of(3, 0), DefaultEnumValue.THREE)
            .setValue(Position.of(0, 1), DefaultEnumValue.FOUR)
            .setValue(Position.of(1, 1), DefaultEnumValue.FIVE)
            .setValue(Position.of(4, 0), DefaultEnumValue.SIX)
            .setValue(Position.of(5, 0), DefaultEnumValue.SEVEN)
            .setValue(Position.of(0, 6), DefaultEnumValue.EIGHT)
            .setValue(Position.of(0, 7), DefaultEnumValue.NINE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionNeededStrategyColumn() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(1, 0), DefaultEnumValue.TWO)
            .setValue(Position.of(2, 0), DefaultEnumValue.THREE)
            .setValue(Position.of(4, 1), DefaultEnumValue.ONE)
            .setValue(Position.of(7, 2), DefaultEnumValue.ONE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionNeededStrategyRow() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 1), DefaultEnumValue.TWO)
            .setValue(Position.of(0, 2), DefaultEnumValue.THREE)
            .setValue(Position.of(1, 4), DefaultEnumValue.ONE)
            .setValue(Position.of(2, 7), DefaultEnumValue.ONE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

  @Test
  public void testSolvePositionNeededStrategyTile() {
    // set-up
    final State state =
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(1, 4), DefaultEnumValue.ONE)
            .setValue(Position.of(2, 7), DefaultEnumValue.ONE)
            .setValue(Position.of(4, 1), DefaultEnumValue.ONE)
            .setValue(Position.of(7, 2), DefaultEnumValue.ONE);
    final State expected = state.setValue(Position.of(0, 0), DefaultEnumValue.ONE);
    final Solver sut = new Solver(state);
    // excercise & verify
    assertEquals(expected, sut.solve(state, Position.of(0, 0)));
  }

}
