package org.javaacadmey.wonder_field;

import java.util.Scanner;

public class Game {

  public static Scanner scanner = new Scanner(System.in);

  private final int COUNT_PLAYERS = 3;
  private final int COUNT_ROUNDS = 4;
  private final int COUNT_GROUP_ROUNDS = 3;
  private final int INDEX_FINAL_ROUND = 3;
  private final String[] questions = new String[4];
  private final String[] answers = new String[4];
  Tableau tableau;
  Yakubovich yakubovich;
  private String[] winners;

  public void init() {
    System.out.println(
        "Запуск игры \"Поле Чудес\" - подготовка к игре. Вам нужно ввести вопросы и ответы для игры.");
    for (int i = 0; i < COUNT_ROUNDS; i++) {
      System.out.printf("Введите вопрос #%d\n", i);
      questions[i] = scanner.nextLine();
      System.out.printf("Введите ответ на вопрос #%d\n", i);
      answers[i] = scanner.nextLine();
    }
    System.out.println("Иницализация закончена, игра начнется через 5 секунд");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
  }

  public String[] createPlayers() {
    String[] players = new String[COUNT_PLAYERS];
    for (int i = 0; i < COUNT_PLAYERS; i++) {
      System.out.printf("Игрок №%s представьтесь: имя,город. Например: Иван,Москва\n", i + 1);
      players[0] = scanner.nextLine().trim();
    }
    return players;
  }

  public String[] playersName(String[] players) {
    String[] playersName = new String[players.length];
    for (int i = 0; i < playersName.length; i++) {
      playersName[i] = players[i].split(",")[0];
    }
    return playersName;
  }
}
