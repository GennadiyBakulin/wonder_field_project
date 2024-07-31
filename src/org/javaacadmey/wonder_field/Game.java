package org.javaacadmey.wonder_field;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.javaacadmey.wonder_field.drum.Drum;
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

  public boolean moveOfPlayer(Player player) {
    int countLettersGuessedPlayer = 0;
    String sector;
    PlayerAnswer playerAnswer;

    while (true) {
      System.out.printf("Ход игрока %s, %s\n", player.getName(), player.getCity());
      yakubovich.speakRotationDrum(player.getName());
      sector = drum.rotation();

      if (!yakubovich.checkSectorOnDrum(sector)) {
        return false;
      }

      playerAnswer = player.move();

      if (!yakubovich.checkResponsePlayer(playerAnswer, tableau)) {
        return false;
      }

      player.setScore(sector);

      switch (playerAnswer.getTypeResponse()) {
        case LETTER -> {
          if (++countLettersGuessedPlayer == 3) {
            player.setAmountWinningsMoneys(playingWithBoxes(player));
            countLettersGuessedPlayer = 0;
          }
          if (isTableauFilled()) {
            return true;
          }
        }
        case WORD -> {
          return true;
        }
      }
    }
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

  private void superGame() {
    selectPrize(finalist);
    String superPrize = listSuperPrizes[new Random().nextInt(listSuperPrizes.length)];
    if (yakubovich.offersPlaySuperGame(finalist)) {
      playSuperGame(superPrize);
    } else {
      yakubovich.notPlaySuperGame(finalist, superPrize);
    }
  }

  private void playSuperGame(String superPrize) {
    tableau = new Tableau(answers[INDEX_SUPER_GAME]);
    String[] threeLetters;
    yakubovich.saysQuestion(questions[INDEX_SUPER_GAME]);
    yakubovich.suggestsNamingThreeLetters();
    threeLetters = finalist.namingThreeLetters();
    yakubovich.speakOpenLetters();
    for (String letter : threeLetters) {
      tableau.openLetter(letter);
    }
    tableau.displayLettersOnTableau();
    yakubovich.askWord();
    if (yakubovich.checkResponseSuperGame(finalist.speakWord(), tableau)) {
      tableau.openFullWord();
      yakubovich.winnerSuperGame(finalist, superPrize);
    } else {
      yakubovich.notWinnerSuperGame(finalist, superPrize);
    }
  }

  public void start() {
    yakubovich.welcomePublic();
    playGroupRounds();
    playFinalRound();
    superGame();
    yakubovich.endGame();
  }

  private void selectPrize(Player finalist) {
    String[] selectPrizes = new String[0];
    int score = finalist.getScore();
    int minimumPricePrize = Integer.parseInt(listPrize[0][1]);

    yakubovich.speakCountScore(finalist);
    for (int i = 0; i < listPrize.length; i++) {
      if (minimumPricePrize > Integer.parseInt(listPrize[i][1])) {
        minimumPricePrize = Integer.parseInt(listPrize[i][1]);
      }
      System.out.printf("%d\t%s\t%s\n", i + 1, listPrize[i][0], listPrize[i][1]);
    }
    System.out.println(
        "Выберите призы на количество ваших очков: вводите номер приза и нажимайте Enter");
    while (score >= minimumPricePrize) {
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
          System.out.println("Осталось" + score + "очков");
        } else {
          System.out.println(
              "У Вас не достаточно очков на данный приз, выберите другой приз и нажмите Enter");
        }
      } catch (NumberFormatException e) {
        System.out.println("Не верный ввод! вводите номер приза и нажимайте Enter");
      }
    }
    finalist.setPrizes(selectPrizes);
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
    return yakubovich.isCheckBox(numberBox, box) ? box.getAmountPrize() : 0;
  }

  public boolean isTableauFilled() {
    return !tableau.isContainsUnknownLetters();
  }
}
