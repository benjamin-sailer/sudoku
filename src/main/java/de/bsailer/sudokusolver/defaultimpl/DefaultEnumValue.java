package de.bsailer.sudokusolver.defaultimpl;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import de.bsailer.sudokusolver.Value;

public enum DefaultEnumValue implements Value {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

  private final int value;

  private static final Map<String, DefaultEnumValue> STRING_MAP = createStringMap();

  DefaultEnumValue(final int value) {
    this.value = value;
  }

  private static Map<String, DefaultEnumValue> createStringMap() {
    return Arrays.asList(values()).stream()
        .collect(Collectors.toMap((v) -> String.valueOf(v.getValue()), (v) -> v));

  }

  @Override
  public int getValue() {
    return value;
  }

  public static Value from(final String cell) {
    return STRING_MAP.get(cell);
  }

}
