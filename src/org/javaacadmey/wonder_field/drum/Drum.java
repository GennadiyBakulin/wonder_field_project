package org.javaacadmey.wonder_field.drum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Drum {

  private final List<String> value;


  public Drum() {
    value = new ArrayList<>();
    for (int i = 100; i <= 1200; i += 100) {
      value.add(String.valueOf(i));
    }
    value.add(DrumOptionsAdd.MULTIPLICATION_TWO.toString());
    value.add(DrumOptionsAdd.SKIPPING_MOVE.toString());
  }

  public String rotation() {
    return value.get(new Random().nextInt(value.size()));
  }
}
