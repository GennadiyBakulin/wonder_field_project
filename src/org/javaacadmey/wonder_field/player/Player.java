package org.javaacadmey.wonder_field.player;

import java.util.regex.Pattern;
import org.javaacadmey.wonder_field.Game;
import org.javaacadmey.wonder_field.drum.DrumOptionsAdd;

public class Player {

  private final String name;
  private final String city;
  private int score;
  private int amountWinningsBoxes;
  private String[] prizes;

  public Player(String name, String city) {
    this.name = name;
    this.city = city;
    this.score = 0;
    this.amountWinningsBoxes = 0;
  }

  public PlayerAnswer move() {
    System.out.println(
        "Если хотите букву нажмите 'б' и enter, если хотите слово нажмите 'c' и enter");
    String input = Game.scanner.nextLine();
    while (!(input.equalsIgnoreCase("б") || input.equalsIgnoreCase("с"))) {
      System.out.println("Некорректное значение, введите 'б' или 'с'");
      input = Game.scanner.nextLine();
    }

    return input.equalsIgnoreCase("б")
        ? new PlayerAnswer(TypeResponse.LETTER, shoutLetter())
        : new PlayerAnswer(TypeResponse.WORD, speakWord());
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

  public int getScore() {
    return score;
  }

  public void setScore(String rotation) {
    if (rotation.equals(DrumOptionsAdd.MULTIPLICATION_TWO.toString())) {
      score *= 2;
    } else {
      score += Integer.parseInt(rotation);
    }
  }

  public int getAmountWinningsBoxes() {
    return amountWinningsBoxes;
  }

  public void setAmountWinningsMoneys(int amountWinningsBoxes) {
    this.amountWinningsBoxes += amountWinningsBoxes;
  }

  public String[] getPrizes() {
    return prizes != null ? prizes : new String[]{"Вы не получили ни одного приза!"};
  }

  public void setPrizes(String[] prizes) {
    this.prizes = prizes;
  }
}
