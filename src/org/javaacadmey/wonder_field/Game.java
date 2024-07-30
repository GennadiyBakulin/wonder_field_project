package org.javaacadmey.wonder_field;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.javaacadmey.wonder_field.drum.Drum;
import org.javaacadmey.wonder_field.drum.DrumOptionsAdd;
import org.javaacadmey.wonder_field.player.Player;
import org.javaacadmey.wonder_field.player.PlayerAnswer;

public class Game {

  public static Scanner scanner = new Scanner(System.in);

  public static final int COUNT_PLAYERS = 3;
  public static final int COUNT_ROUNDS = 5;
  public static final int COUNT_GROUP_ROUNDS = 3;
  public static final int INDEX_FINAL_ROUND = 3;
  public static final int INDEX_SUPER_GAME = 4;
  private final String[] questions;
  private final String[] answers;
  private final Player[] winners;
  private final Yakubovich yakubovich;
  private Tableau tableau;
  private final Drum drum;
  private Player finalist;
  private final String[][] listPrize;
  private final String[] listSuperPrizes;

  public Game() {
    questions = new String[COUNT_ROUNDS];
    answers = new String[COUNT_ROUNDS];
    winners = new Player[COUNT_GROUP_ROUNDS];
    yakubovich = new Yakubovich();
    drum = new Drum();
    listPrize = new String[][]{
        {"Холодильник", "1000"},
        {"Утюг", "300"},
        {"Телевизор", "1200"},
        {"Стиральная машина", "1000"},
        {"Блендер", "400"},
        {"Чайник", "100"},
        {"Микроволновая печь", "500"}
    };
    listSuperPrizes = new String[]{
        "Автомобиль",
        "Квартира",
        "Хомячок",
        "Квадроцикл",
        "Путешествие на двоих в Египет"
    };
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
  //   System.out.println("Введите супервопрос");
  // questions[INDEX_SUPER_GAME] = scanner.nextLine();
  //      System.out.prinln("Введите ответ на супервопрос");
//      answers[INDEX_SUPER_GAME] = scanner.nextLine();
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
    questions[4] = "Вопрос Суперигры";
    answers[0] = "кошка";
    answers[1] = "собака";
    answers[2] = "елка";
    answers[3] = "зверь";
    answers[4] = "казначей";
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
    int numberGuessingLettersInRow = 0;
    String rotationDrum;
    PlayerAnswer playerAnswer;

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

      playerAnswer = player.move();

      if (!yakubovich.isCheckResponsePlayer(playerAnswer, tableau)) {
        return false;
      }

      player.setScore(rotationDrum);

      switch (playerAnswer.getTypeResponse()) {
        case LETTER -> {
          if (++numberGuessingLettersInRow == 3) {
            player.setAmountWinningsMoneys(playingWithBoxes(player));
            numberGuessingLettersInRow = 0;
          }
          if (isTableauCompletelyFilled()) {
            return true;
          }
        }
        case WORD -> {
          return true;
        }
      }
    }
  }

  private int playingWithBoxes(Player player) {
    Box box = new Box();
    int numberBox;
    yakubovich.shoutPlayingWithBoxes(player.getName());
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
            finalist = player;
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
      yakubovich.saysQuestion(questions[i - 1]);
      tableau.displayLettersOnTableau();
      playRound(players, i);
    }
  }

  public void playFinalRound() {
    tableau = new Tableau(answers[INDEX_FINAL_ROUND]);
    System.out.println("__________________________________");
    System.out.println("Финал игры Поле чудес!");
    yakubovich.welcomeThreePlayers(winners, INDEX_FINAL_ROUND + 1);
    yakubovich.saysQuestion(questions[INDEX_FINAL_ROUND]);
    tableau.displayLettersOnTableau();
    playRound(winners, INDEX_FINAL_ROUND + 1);
  }

  private void playSuperGame() {
    String superPrize;
    String[] threeLetters = new String[3];
    selectPrize(finalist);
    superPrize = listSuperPrizes[new Random().nextInt(listSuperPrizes.length + 1)];
    if (yakubovich.offersPlaySuperGame(finalist)) {
      tableau = new Tableau(answers[INDEX_SUPER_GAME]);
      yakubovich.saysQuestion(questions[INDEX_SUPER_GAME]);
      yakubovich.askThreeLetters(finalist);
      for (int i = 0; i < threeLetters.length; i++) {
        threeLetters[i] = finalist.shoutLetter();
      }
      yakubovich.speakOpenLetters();
      for (String threeLetter : threeLetters) {
        tableau.openLetter(threeLetter);
      }
      yakubovich.askWordToPlaySuperGame(finalist);
      if (finalist.speakWord().equalsIgnoreCase(tableau.getRightAnswer())) {
        tableau.openFullWord();
        yakubovich.winnerSuperGame(finalist, superPrize);
      } else {
        yakubovich.notWinnerSuperGame(finalist, superPrize);
      }

    } else {
      yakubovich.notWinnerSuperGame(finalist, superPrize);
    }
  }

  private void selectPrize(Player finalist) {
    int score = finalist.getScore();
    int min = 100;
    String[] selectPrizes = new String[0];
    yakubovich.speakCountScore(finalist);
    for (int i = 0; i < listPrize.length; i++) {
      System.out.printf("%d\t%s\t%s\n", i + 1, listPrize[i][0], listPrize[i][1]);
    }
    System.out.println(
        "Выберите призы на количество ваших очков: введите номер приза и нажимайте Enter");
    while (score > min) {
      int index;
      try {
        if ((index = Integer.parseInt(scanner.nextLine())) < 1 || index > listPrize.length) {
          throw new NumberFormatException();
        }

        int amount = Integer.parseInt(listPrize[index - 1][1]);

        if (amount <= score) {
          selectPrizes = Arrays.copyOf(selectPrizes, selectPrizes.length + 1);
          selectPrizes[selectPrizes.length - 1] = listPrize[index - 1][0];
          score -= amount;
        } else {
          System.out.println(
              "У Вас не достаточно очков на данный приз, выберите другой приз и нажмите Enter");
        }
      } catch (NumberFormatException e) {
        System.out.println("Не верный ввод! введите номер приза и нажимайте Enter");
      }
    }
    finalist.setPrizes(selectPrizes);
  }

  public void start() {
    yakubovich.welcomePublic();
    playGroupRounds();
    playFinalRound();
    playSuperGame();
    yakubovich.endGame();
  }
}
