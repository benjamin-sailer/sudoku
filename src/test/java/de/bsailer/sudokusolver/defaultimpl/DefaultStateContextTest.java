package de.bsailer.sudokusolver.defaultimpl;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import de.bsailer.sudokusolver.Position;

public class DefaultStateContextTest {

  @Test
  public void testTileIdSameQuadrant() {
    for (int tileRow = 0; tileRow < 3; tileRow++) {
      for (int tileColumn = 0; tileColumn < 3; tileColumn++) {
        final int referenceTileRow = 3 * tileRow;
        final int recerenceTileColumn = 3 * tileColumn;
        testAllPositionCombinationsForUpperLeftReference(referenceTileRow, recerenceTileColumn);
      }
    }
  }

  private void testAllPositionCombinationsForUpperLeftReference(final int referenceTileRow,
      final int recerenceTileColumn) {
    final Position referencePosition = Position.of(referenceTileRow, recerenceTileColumn);
    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 3; column++) {
        assertEquals(DefaultStateContext.INSTANCE.tileId(referencePosition),
            DefaultStateContext.INSTANCE
                .tileId(Position.of(referenceTileRow + row, recerenceTileColumn + column)));
      }
    }
  }

  @Test
  public void testAllValues() {
    assertEquals(9, DefaultStateContext.INSTANCE.allValues().size());
  }

  @Test
  public void testNumRows() {
    assertEquals(9, DefaultStateContext.INSTANCE.numRows());
  }

  @Test
  public void testNumColumns() {
    assertEquals(9, DefaultStateContext.INSTANCE.numColumns());
  }

  @Test
  public void testFrom() {
    assertEquals(DefaultEnumValue.ONE, DefaultStateContext.INSTANCE.from("1"));
  }
}
