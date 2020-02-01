package de.bsailer.sudokusolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PositionTest {

  @Test
  public void testEqualsTrue() {
    assertTrue(Position.of(0, 0).equals(Position.of(0, 0)));
  }

  @Test
  public void testEqualsFalseDifferentRow() {
    assertFalse(Position.of(0, 0).equals(Position.of(1, 0)));
  }

  @Test
  public void testEqualsFalseDifferentColumn() {
    assertFalse(Position.of(0, 0).equals(Position.of(0, 1)));
  }

  @Test
  public void testEqualsFalseDifferentType() {
    assertFalse(Position.of(0, 0).equals(new Object()));
  }

  @Test
  public void testHashCode() {
    assertEquals(Position.of(0, 0).hashCode(), Position.of(0, 0).hashCode());
  }

  @Test
  public void testGetRow() {
    assertEquals(2, Position.of(2, 4).getRow());
  }

  @Test
  public void testGetColumn() {
    assertEquals(4, Position.of(2, 4).getColumn());
  }

}
