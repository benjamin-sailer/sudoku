package de.bsailer.sudokusolver;

import static org.junit.Assert.assertEquals;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import de.bsailer.sudokusolver.defaultimpl.DefaultStateContext;
import de.bsailer.sudokusolver.streamer.StateStreamer;

public class SudokuWorkflowIT {

  @Test
  public void solveRiddle1() throws UnsupportedEncodingException {
    // excercise
    final StateStreamer streamer = new StateStreamer();
    final State state =
        streamer.read(DefaultStateContext.INSTANCE, getClass().getResourceAsStream("/riddle1.csv"));
    final State expectedState = streamer.read(DefaultStateContext.INSTANCE,
        getClass().getResourceAsStream("/riddle1_solution.csv"));
    final Solver solver = new Solver(state);
    final Solution solution = solver.solve();
    // verify
    assertEquals(new Solution(expectedState, Solution.Result.SOLVED), solution);
  }
}
