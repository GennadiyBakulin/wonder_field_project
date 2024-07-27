package org.javaacadmey.wonder_field.player;

public class PlayerAnswer {

  private final TypeResponse typeResponse;
  private final String response;

  public PlayerAnswer(TypeResponse typeResponse, String response) {
    this.typeResponse = typeResponse;
    this.response = response;
  }

  public TypeResponse getTypeResponse() {
    return typeResponse;
  }

  public String getResponse() {
    return response;
  }
}
