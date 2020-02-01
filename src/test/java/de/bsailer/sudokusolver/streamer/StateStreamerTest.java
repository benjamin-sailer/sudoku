package de.bsailer.sudokusolver.streamer;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import de.bsailer.sudokusolver.Position;
import de.bsailer.sudokusolver.State;
import de.bsailer.sudokusolver.defaultimpl.DefaultEnumValue;
import de.bsailer.sudokusolver.defaultimpl.DefaultStateContext;

public class StateStreamerTest {

  @Test
  public void testRead() throws UnsupportedEncodingException {
    // set-up
    final String oneAtFirst = "1;;;;;;;;";
    final String empty = ";;;;;;;;";
    final String matrix =
        String.join("\n", oneAtFirst, empty, empty, empty, empty, empty, empty, empty, empty);
    // excercise
    final State result = new StateStreamer().read(DefaultStateContext.INSTANCE,
        new ByteArrayInputStream(matrix.getBytes("UTF-8")));
    // verify
    assertEquals(
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE),
        result);
  }

  @Test
  public void testWrite() throws UnsupportedEncodingException {
    // set-up
    final String oneAtFirst = "1;;;;;;;;";
    final String empty = ";;;;;;;;";
    final String matrix =
        String.join("\n", oneAtFirst, empty, empty, empty, empty, empty, empty, empty, empty);
    final ByteArrayOutputStream result = new ByteArrayOutputStream();
    // excercise
    new StateStreamer().write(
        new State(DefaultStateContext.INSTANCE).setValue(Position.of(0, 0), DefaultEnumValue.ONE),
        result);
    // verify
    assertEquals(matrix, result.toString("UTF-8"));
  }
}
