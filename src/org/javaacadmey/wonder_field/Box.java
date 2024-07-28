package org.javaacadmey.wonder_field;

import java.util.Random;

public class Box {

  public static final int COUNT_BOX = 2;
  private final int numberMoneyBox;
  private final int amountPrize;

  public Box() {
    Random rnd = new Random();
    numberMoneyBox = rnd.nextInt(1, COUNT_BOX + 1);
    amountPrize = rnd.nextInt(5, 11) * 10000;
  }

  public int getNumberMoneyBox() {
    return numberMoneyBox;
  }

  public int getAmountPrize() {
    return amountPrize;
  }
}
