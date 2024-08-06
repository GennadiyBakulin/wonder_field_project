package org.javaacadmey.wonder_field;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import org.javaacadmey.wonder_field.drum.Drum;
import org.javaacadmey.wonder_field.player.Player;
import org.javaacadmey.wonder_field.player.PlayerAnswer;
import org.javaacadmey.wonder_field.player.TypeResponse;

public class Game {

  public static Scanner scanner = new Scanner(System.in);
  public static final int COUNT_PLAYERS = 3;
  public static final int COUNT_ROUNDS = 5;
  public static final int COUNT_GROUP_ROUNDS = 3;
  public static final int INDEX_FINAL_ROUND = 3;
  public static final int INDEX_SUPER_GAME = 4;

  private final String[] questions = new String[COUNT_ROUNDS];
  private final String[] answers = new String[COUNT_ROUNDS];
  private final Player[] winners = new Player[COUNT_GROUP_ROUNDS];
  private final Yakubovich yakubovich = new Yakubovich();
  private Tableau tableau;
  private final Drum drum = new Drum();
  private Player finalist;
  private final Gift[] gifts = new Gift[]{
      new Gift("Холодильник", 1000),
      new Gift("Утюг", 300),
      new Gift("Телевизор", 1200),
      new Gift("Стиральная машина", 1000),
      new Gift("Блендер", 400),
      new Gift("Чайник", 100),
      new Gift("Микроволновая печь", 500)
  };
  private final SuperPrize[] listSuperPrizes = new SuperPrize[]{
      new SuperPrize("Автомобиль"),
      new SuperPrize("Квартира"),
      new SuperPrize("Хомячок"),
      new SuperPrize("Путешествие на двоих в Египет")
  };

  public void init() {
    System.out.println(
        "Запуск игры \"Поле Чудес\" - подготовка к игре. Вам нужно ввести вопросы и ответы для игры.");
    for (int i = 0; i < COUNT_ROUNDS - 1; i++) {
      System.out.printf("Введите вопрос #%d\n", i + 1);
      questions[i] = scanner.nextLine();
      System.out.printf("Введите ответ на вопрос #%d\n", i + 1);
      answers[i] = scanner.nextLine();
    }
    System.out.println("Введите супервопрос");
    questions[INDEX_SUPER_GAME] = scanner.nextLine();
    System.out.println("Введите ответ на супервопрос");
    answers[INDEX_SUPER_GAME] = scanner.nextLine();
    System.out.println("Иницализация закончена, игра начнется через 5 секунд");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("\n".repeat(50));
  }

  public Player[] createPlayers() {
    Player[] players = new Player[COUNT_PLAYERS];

    for (int i = 0; i < COUNT_PLAYERS; i++) {
      do {
        System.out.printf("Игрок №%s представьтесь: имя,город. Например: Иван,Москва\n", i + 1);
        String[] player = scanner.nextLine().split(",");

        if (player.length == 2 && !(player[0].isBlank() || player[1].isBlank())) {
          players[i] = new Player(player[0].trim(), player[1].trim());
          break;
        }

        System.out.println("Не верный ввод! Введите имя,город. Например: Иван,Москва");
      } while (true);
    }

    return players;
  }

  public boolean moveOfPlayer(Player player) {
    int countLettersGuessedPlayer = 0;
    PlayerAnswer playerAnswer;
    String sector;

    while (true) {
      System.out.printf("Ход игрока %s, %s\n", player.getName(), player.getCity());
      yakubovich.speakRotationDrum(player.getName());
      sector = drum.rotation();

      if (!yakubovich.checkSectorOnDrum(sector)) {
        return false;
      }

      playerAnswer = player.move();

      if (playerAnswer.getTypeResponse() == TypeResponse.LETTER
          && yakubovich.checkLetterAlreadyOpen(playerAnswer, tableau)) {
        continue;
      }

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

        if (!moveOfPlayer(player)) {
          continue;
        }

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
    SuperPrize superPrize = listSuperPrizes[new Random().nextInt(listSuperPrizes.length)];
    selectPrize(finalist);

    if (yakubovich.offersPlaySuperGame(finalist)) {
      playSuperGame(superPrize);
    } else {
      yakubovich.notPlaySuperGame(finalist, superPrize);
    }
  }

  private void playSuperGame(SuperPrize superPrize) {
    String[] threeLetters;
    tableau = new Tableau(answers[INDEX_SUPER_GAME]);

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

  private void selectPrize(Player finalist) {
    int score = finalist.getScore();
    int minPricePrize;

    Arrays.sort(gifts, (gift1, gift2) -> gift1.getPrice() - gift2.getPrice());
    minPricePrize = gifts[0].getPrice();

    for (int i = 0; i < gifts.length; i++) {
      System.out.printf("%d\t%s\t%s\n", i + 1, gifts[i].getName(), gifts[i].getPrice());
    }

    yakubovich.speakCountScore(finalist);

    while (score >= minPricePrize) {
      int index;
      int amount;
      try {
        if ((index = scanner.nextInt()) < 1 || index > gifts.length) {
          throw new NoSuchElementException();
        }

        amount = gifts[index - 1].getPrice();

        if (amount <= score) {
          finalist.addGift(gifts[index - 1]);
          score -= amount;
          System.out.println("Осталось" + score + "очков");
        } else {
          System.out.println(
              "У Вас не достаточно очков на данный приз, выберите другой приз и нажмите Enter");
        }
      } catch (NoSuchElementException e) {
        System.out.println("Не верный ввод! вводите номер приза и нажимайте Enter");
      }
      scanner.nextLine();
    }
  }

  public int playingWithBoxes(Player player) {
    Box box = new Box();
    int numberBox;

    yakubovich.shoutPlayingWithBoxes(player.getName());

    while (!(scanner.hasNextInt() && (numberBox = scanner.nextInt()) >= 1
        && numberBox <= Box.COUNT_BOX)) {
      scanner.nextLine();
      System.out.printf("Не верный ввод! Введите число от 1 до %d и нажмите Enter\n",
          Box.COUNT_BOX);
    }
    scanner.nextLine();

    return yakubovich.isCheckBox(numberBox, box) ? box.getAmountPrize() : 0;
  }

  public boolean isTableauFilled() {
    return !tableau.isContainsUnknownLetters();
  }

  public void start() {
    yakubovich.welcomePublic();
    playGroupRounds();
    playFinalRound();
    superGame();
    yakubovich.endGame();
  }
}
