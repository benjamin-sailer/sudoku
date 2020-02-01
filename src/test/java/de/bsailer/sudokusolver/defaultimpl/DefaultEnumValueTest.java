package de.bsailer.sudokusolver.defaultimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class DefaultEnumValueTest {

  @Test
  public void testgetValue() {
    assertEquals(1, DefaultEnumValue.ONE.getValue());
  }

  @Test
  public void testFrom() {
    assertEquals(DefaultEnumValue.ONE, DefaultEnumValue.from("1"));
  }

  @Test
  public void testFromEmpty() {
    assertNull(DefaultEnumValue.from(""));
  }
}
