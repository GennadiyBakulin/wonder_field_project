package org.javaacadmey.wonder_field;

public class Gift {

  private final String name;
  private final int price;

  public Gift(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }
}
