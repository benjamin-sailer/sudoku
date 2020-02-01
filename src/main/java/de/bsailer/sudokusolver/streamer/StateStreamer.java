package de.bsailer.sudokusolver.streamer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import de.bsailer.sudokusolver.Position;
import de.bsailer.sudokusolver.State;
import de.bsailer.sudokusolver.StateContext;
import de.bsailer.sudokusolver.Value;

public class StateStreamer {

  static List<Value> createList(final String line, final StateContext stateContext) {
    final StrTokenizer t = new StrTokenizer(line, ";").setIgnoreEmptyTokens(false);
    return t.getTokenList().stream().map((cell) -> stateContext.from(cell))
        .collect(Collectors.toList());
  }

  public State read(final StateContext stateContext, final InputStream inputStream) {
    final List<List<Value>> values = new ArrayList<>();
    try (final InputStreamReader ir = new InputStreamReader(inputStream);
        final BufferedReader br = new BufferedReader(ir)) {
      br.lines().forEach((line) -> values.add(createList(line, stateContext)));
    } catch (final IOException e) {
      throw new IllegalArgumentException("cannot read state from input stream " + inputStream, e);
    }
    return State.fromValues(values, stateContext);
  }

  public void write(final State state, final OutputStream target) {
    final StateContext stateContext = state.getStateContext();
    final List<String> content = new ArrayList<>();
    for (int row = 0; row < stateContext.numRows(); row++) {
      final List<String> rowContent = new ArrayList<>();
      for (int column = 0; column < stateContext.numColumns(); column++) {
        final Position position = Position.of(row, column);
        rowContent.add(cell(state, position));
      }
      content.add(StringUtils.join(rowContent, ";"));
    }
    try {
      target.write(StringUtils.join(content, "\n").getBytes("UTF-8"));
    } catch (final IOException e) {
      throw new IllegalArgumentException("write failed: ", e);
    }

  }

  private String cell(final State state, final Position position) {
    final Value value = state.getValue(position);
    return value != null ? String.valueOf(value.getValue()) : "";
  }

}
