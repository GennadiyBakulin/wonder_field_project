package org.javaacadmey.wonder_field.player;

import org.javaacadmey.wonder_field.Game;

public class Player {

  private String name;
  private String city;
  private PlayerAnswer playerAnswer;

  public PlayerAnswer move() {
    System.out.printf("Ход игрока %s, %s\n", name, city);
    System.out.println(
        "Если хотите букву нажмите 'б' и enter, если хотите слово нажмите 'c' и enter");
    String input = Game.scanner.nextLine();
    while (true) {
      if (input.equals("б")) {
        playerAnswer.setTypeResponse(TypeResponse.LETTER);
        playerAnswer.setResponse(shoutLetter());
        break;
      }
      if (input.equals("c")) {
        playerAnswer.setTypeResponse(TypeResponse.WORD);
        playerAnswer.setResponse(speakWord());
        break;
      }
      System.out.println("Некорректное значение, введите 'б' или 'с'");
      input = Game.scanner.nextLine();
    }
    return playerAnswer;
  }

  public String shoutLetter() {
    return "";
  }

  public String speakWord() {
    return "";
  }
}
