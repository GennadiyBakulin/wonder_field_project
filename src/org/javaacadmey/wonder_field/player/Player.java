package org.javaacadmey.wonder_field.player;

import java.util.Arrays;
import java.util.regex.Pattern;
import org.javaacadmey.wonder_field.Game;
import org.javaacadmey.wonder_field.Gift;
import org.javaacadmey.wonder_field.drum.DrumAdditionalSectors;

public class Player {

  private final String name;
  private final String city;
  private int score = 0;
  private int amountWinningsBoxes = 0;
  private Gift[] gifts = new Gift[0];

  public Player(String name, String city) {
    this.name = name;
    this.city = city;
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
    Pattern compile = Pattern.compile("[А-ЯЁ]");
    String letter = Game.scanner.nextLine().toUpperCase();

    while (letter.length() != 1 || !compile.matcher(letter).find()) {
      System.out.println("Ошибка! это не русская буква, введите русскую букву");
      letter = Game.scanner.nextLine().toUpperCase();
    }

    System.out.printf("Игрок %s: буква %s\n", name, letter);

    if (letter.equals("Ё")) {
      letter = "Е";
    }

    if (letter.equals("Й")) {
      letter = "И";
    }

    return letter;
  }

  public String speakWord() {
    String word = Game.scanner.nextLine().toUpperCase();
    System.out.printf("Игрок %s: слово %s\n", name, word);

    return word
        .replaceAll("Ё", "Е")
        .replaceAll("Й", "И");
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

  public void setScore(String sector) {
    if (sector.equals(DrumAdditionalSectors.MULTIPLICATION_TWO.toString())) {
      score *= 2;
    } else {
      score += Integer.parseInt(sector);
    }
  }

  public int getAmountWinningsBoxes() {
    return amountWinningsBoxes;
  }

  public void setAmountWinningsMoneys(int amountWinningsBoxes) {
    this.amountWinningsBoxes += amountWinningsBoxes;
  }

  public String[] namingThreeLetters() {
    String[] letters = new String[3];

    for (int i = 0; i < letters.length; i++) {
      letters[i] = shoutLetter();
    }

    return letters;
  }

  public Gift[] getGifts() {
    return gifts;
  }

  public void addGift(Gift gift) {
    gifts = Arrays.copyOf(gifts, gifts.length + 1);
    gifts[gifts.length - 1] = gift;
  }
}
