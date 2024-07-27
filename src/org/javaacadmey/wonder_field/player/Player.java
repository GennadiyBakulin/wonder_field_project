package org.javaacadmey.wonder_field.player;

import java.util.regex.Pattern;
import org.javaacadmey.wonder_field.Game;

public class Player {

  private final String name;
  private final String city;

  public Player(String name, String city) {
    this.name = name;
    this.city = city;
  }

  public PlayerAnswer move() {
    System.out.printf("Ход игрока %s, %s\n", name, city);
    System.out.println(
        "Если хотите букву нажмите 'б' и enter, если хотите слово нажмите 'c' и enter");
    String input = Game.scanner.nextLine();
    while (!(input.equalsIgnoreCase("б") || input.equalsIgnoreCase("с"))) {
      System.out.println("Некорректное значение, введите 'б' или 'с'");
      input = Game.scanner.nextLine();
    }
    if (input.equalsIgnoreCase("б")) {
      return new PlayerAnswer(TypeResponse.LETTER, shoutLetter());
    }
    return new PlayerAnswer(TypeResponse.WORD, speakWord());
  }

  public String shoutLetter() {
    Pattern compile = Pattern.compile("[а-яА-ЯёЁ]");
    String letter = Game.scanner.nextLine();

    while (letter.length() != 1 && !compile.matcher(letter).find()) {
      System.out.println("Ошибка! это не русская буква, введите русскую букву");
      letter = Game.scanner.nextLine();
    }
    System.out.printf("Игрок %s: буква %s\n", name, letter);

    return letter;
  }

  public String speakWord() {
    String word = Game.scanner.nextLine();
    System.out.printf("Игрок %s: слово %s\n", name, word);
    return word;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }
}
