package org.javaacadmey.wonder_field.drum;

import java.util.Random;

public class Drum {

  private final String[] value = new String[]{
      "100",
      "200",
      "300",
      "400",
      "500",
      "600",
      "700",
      "800",
      "900",
      "1000",
      "1100",
      "1200",
      DrumAdditionalSectors.MULTIPLICATION_TWO.toString(),
      DrumAdditionalSectors.SKIPPING_MOVE.toString()
  };

  public String rotation() {
    return value[new Random().nextInt(value.length)];
  }
}
