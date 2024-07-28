package org.javaacadmey.wonder_field;

import java.util.Scanner;
import org.javaacadmey.wonder_field.drum.Drum;
import org.javaacadmey.wonder_field.drum.DrumOptionsAdd;
import org.javaacadmey.wonder_field.player.Player;
import org.javaacadmey.wonder_field.player.PlayerAnswer;

public class Game {

  public static Scanner scanner = new Scanner(System.in);

  public static final int COUNT_PLAYERS = 3;
  public static final int COUNT_ROUNDS = 4;
  public static final int COUNT_GROUP_ROUNDS = 3;
  public static final int INDEX_FINAL_ROUND = 3;
  private final String[] questions;
  private final String[] answers;
  private final Player[] winners;
  private final Yakubovich yakubovich;
  private Tableau tableau;
  private final Drum drum;

  public Game() {
    questions = new String[COUNT_ROUNDS];
    answers = new String[COUNT_ROUNDS];
    winners = new Player[COUNT_GROUP_ROUNDS];
    yakubovich = new Yakubovich();
    drum = new Drum();
  }

//  public void init() {
//    System.out.println(
//        "Запуск игры \"Поле Чудес\" - подготовка к игре. Вам нужно ввести вопросы и ответы для игры.");
//    for (int i = 0; i < COUNT_ROUNDS; i++) {
//      System.out.printf("Введите вопрос #%d\n", i + 1);
//      questions[i] = scanner.nextLine();
//      System.out.printf("Введите ответ на вопрос #%d\n", i + 1);
//      answers[i] = scanner.nextLine();
//    }
//    System.out.println("Иницализация закончена, игра начнется через 5 секунд");
//    try {
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//
//    System.out.println("\n".repeat(50));
//  }

  public void init() {
    questions[0] = "Вопрос 1";
    questions[1] = "Вопрос 2";
    questions[2] = "Вопрос 3";
    questions[3] = "Вопрос 4";
    answers[0] = "кошка";
    answers[1] = "собака";
    answers[2] = "елка";
    answers[3] = "зверь";
  }

  public Player[] createPlayers() {
    Player[] players = new Player[COUNT_PLAYERS];
    for (int i = 0; i < COUNT_PLAYERS; i++) {
      System.out.printf("Игрок №%s представьтесь: имя,город. Например: Иван,Москва\n", i + 1);
      String[] player = scanner.nextLine().split(",");
      players[i] = new Player(player[0], player[1]);
    }
    return players;
  }

  public boolean isTableauCompletelyFilled() {
    return !tableau.isContainsUnknownLetters();
  }

  public boolean moveOfPlayer(Player player) {
    PlayerAnswer move;
    String rotationDrum;
    int counterForGuessingThreeLettersInRow = 0;
    while (true) {
      System.out.printf("Ход игрока %s, %s\n", player.getName(), player.getCity());
      yakubovich.speakRotationDrum(player.getName());
      rotationDrum = drum.rotation();
      if (rotationDrum.equals(DrumOptionsAdd.SKIPPING_MOVE.toString())) {
        yakubovich.skippingMoveSector();
        return false;
      }
      if (rotationDrum.equals(DrumOptionsAdd.MULTIPLICATION_TWO.toString())) {
        yakubovich.multiplicationSector(player);
      } else {
        yakubovich.scoreSector(rotationDrum);
      }
      move = player.move();
      switch (move.getTypeResponse()) {
        case LETTER -> {
          if (yakubovich.isCheckResponsePlayer(move, tableau)) {
            player.setScore(rotationDrum);
            if (++counterForGuessingThreeLettersInRow == 3) {
              player.setAmountWinningsBoxes(playingWithBoxes(player));
              counterForGuessingThreeLettersInRow = 0;
            }
            if (isTableauCompletelyFilled()) {
              return true;
            }
          } else {
            return false;
          }
        }
        case WORD -> {
          if (yakubovich.isCheckResponsePlayer(move, tableau)) {
            player.setScore(rotationDrum);
            return true;
          }
          return false;
        }
      }
    }
  }

  private int playingWithBoxes(Player player) {
    Box box = new Box();
    int numberBox;
    yakubovich.shoutPlayingWithBoxes(player.getName(), box);
    while (!(scanner.hasNextInt() && (numberBox = scanner.nextInt()) >= 1
        && numberBox <= Box.COUNT_BOX)) {
      scanner.nextLine();
      System.out.printf("Не верный ввод! Введите число от 1 до %d и нажмите Enter\n",
          Box.COUNT_BOX);
    }
    return yakubovich.checkBox(numberBox, box);
  }

  public void playRound(Player[] players, int numberRound) {
    boolean isEndOfRound = false;
    while (!isEndOfRound) {
      for (Player player : players) {
        if (moveOfPlayer(player)) {
          if (numberRound - 1 < INDEX_FINAL_ROUND) {
            yakubovich.shoutIfPlayerWins(player, false);
            winners[numberRound - 1] = player;
          } else {
            yakubovich.shoutIfPlayerWins(player, true);
          }
          isEndOfRound = true;
          break;
        }
      }
    }
  }

  public void playGroupRounds() {
    for (int i = 1; i <= COUNT_GROUP_ROUNDS; i++) {
      System.out.println("__________________________________");
      System.out.printf("Играет %s тройка игроков\n", i);
      Player[] players = createPlayers();
      tableau = new Tableau(answers[i - 1]);
      yakubovich.welcomeThreePlayers(players, i);
      yakubovich.askQuestion(questions[i - 1]);
      tableau.displayLettersOnTableau();
      playRound(players, i);
    }
  }

  public void playFinalRound() {
    tableau = new Tableau(answers[INDEX_FINAL_ROUND]);
    System.out.println("__________________________________");
    System.out.println("Финал игры Поле чудес!");
    yakubovich.welcomeThreePlayers(winners, INDEX_FINAL_ROUND + 1);
    yakubovich.askQuestion(questions[INDEX_FINAL_ROUND]);
    tableau.displayLettersOnTableau();
    playRound(winners, INDEX_FINAL_ROUND + 1);
  }

  public void start() {
    yakubovich.start();
    playGroupRounds();
    playFinalRound();
    yakubovich.end();
  }
}
