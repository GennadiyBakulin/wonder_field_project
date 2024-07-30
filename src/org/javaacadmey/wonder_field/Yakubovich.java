package org.javaacadmey.wonder_field;

import org.javaacadmey.wonder_field.player.Player;
import org.javaacadmey.wonder_field.player.PlayerAnswer;

public class Yakubovich {

  public void welcomePublic() {
    System.out.println(
        "Якубович: Здравствуйте, уважаемые дамы и господа! Пятница! В эфире капитал-шоу «Поле чудес»!");
  }

  public void endGame() {
    System.out.println("Мы прощаемся с вами ровно на одну неделю! Здоровья вам, и до встречи!");
  }

  public void welcomeThreePlayers(Player[] players, int roundNumber) {
    if (roundNumber <= Game.COUNT_GROUP_ROUNDS) {
      System.out.printf("Якубович: приглашаю %d тройку игроков: %s\n", roundNumber,
          joinStrings(nameOfPlayers(players)));
    } else {
      System.out.printf("Якубович: приглашаю победителей групповых этапов: %s\n",
          joinStrings(nameOfPlayers(players)));
    }
  }

  public void saysQuestion(String question) {
    System.out.printf("Якубович: Внимание вопрос!\n%s\n", question);
  }

  public void shoutIfPlayerWins(Player player, boolean isFinallyRound) {
    if (isFinallyRound) {
      System.out.printf("Якубович: И перед нами победитель Капитал шоу поле чудес! Это %s из %s\n",
          player.getName(), player.getCity());
    } else {
      System.out.printf("Якубович: Молодец! %s из %s проходит в финал!\n", player.getName(),
          player.getCity());
    }
  }

  public boolean isCheckResponsePlayer(PlayerAnswer answerPlayer, Tableau tableau) {
    switch (answerPlayer.getTypeResponse()) {
      case LETTER -> {
        if (tableau.getRightAnswer().toUpperCase()
            .contains(answerPlayer.getResponse().toUpperCase())) {
          System.out.println("Якубович: Есть такая буква, откройте ее!");
          tableau.openLetter(answerPlayer.getResponse());
          System.out.println("__________________________________");
          return true;
        } else {
          System.out.println("Якубович: Нет такой буквы! Следующий игрок!");
        }
      }
      case WORD -> {
        if (tableau.getRightAnswer().equalsIgnoreCase(answerPlayer.getResponse())) {
          System.out.printf("Якубович: %s! Абсолютно верно!\n", answerPlayer.getResponse());
          tableau.openFullWord();
          System.out.println("__________________________________");
          return true;
        } else {
          System.out.println("Якубович: Неверно! Следующий игрок!");
        }
      }
    }
    System.out.println("__________________________________");
    tableau.displayLettersOnTableau();
    return false;
  }

  public String[] nameOfPlayers(Player[] players) {
    String[] nameOfPlayers = new String[players.length];
    for (int i = 0; i < nameOfPlayers.length; i++) {
      nameOfPlayers[i] = players[i].getName();
    }
    return nameOfPlayers;
  }

  public String joinStrings(String[] array) {
    return String.join(", ", array);
  }

  public void speakRotationDrum(String name) {
    System.out.printf("Якубович: %s вращайте барабан!\n", name);
  }

  public void skippingMoveSector() {
    System.out.println("Якубович: на барабане сектор Пропуска хода! Следующий игрок!\n");
  }

  public void multiplicationSector(Player player) {
    System.out.printf("Якубович: на барабане сектор умножения очков на 2!\n"
            + "Якубович: у Вас %d очков в случае успешного ответа ваши очки удвоятся!\n",
        player.getScore());
  }

  public void scoreSector(String score) {
    System.out.printf("Якубович: на барабане %s очков! Ваш ответ!\n", score);
  }

  public void shoutPlayingWithBoxes(String name) {
    System.out.printf(
        "Якубович: Поздравляю %s Вы отгадали три буквы подряд и можете выиграть денежный приз в шкатулках!\n"
            + "Якубович: Шкатулки в студию!\n"
            + "Якубович: Выберите номер шкатулки от 1 до %d и нажмите Enter\n", name,
        Box.COUNT_BOX);
  }

  public int checkBox(int numberBox, Box box) {
    if (numberBox == box.getNumberMoneyBox()) {
      System.out.printf("Якубович: Поздравляю Вы верно выбрали шкатулку, ваш выигрыш составил %d\n",
          box.getAmountPrize());
      return box.getAmountPrize();
    } else {
      System.out.println("Якубович: Увы вы не отгадали шкатулку");
      return 0;
    }
  }

  public void speakCountScore(Player finalist) {
    System.out.printf("Якубович: %s Вы набрали %d очков\n", finalist.getName(),
        finalist.getScore());
  }

  public boolean offersPlaySuperGame(Player finalist) {
    String answer;
    System.out.printf("Якубович: %s хотите сыграть в Супер Игру?\n", finalist.getName());
    System.out.println("Если согласны сыграть в Супер Игру введите \"да\" и нажмите Enter,"
        + " если не согласны введите \"нет\" и нажмите Enter.");

    while (!((answer = Game.scanner.nextLine()).equalsIgnoreCase("да")
        || answer.equalsIgnoreCase("нет"))) {
      System.out.println("Не верный ввод, повторите заново!");
      System.out.println("Если согласны сыграть в Супер Игру введите \"да\" и нажмите Enter,"
          + " если не согласны введите \"нет\" и нажмите Enter.");
    }
    return answer.equalsIgnoreCase("да");
  }

  public void askThreeLetters(Player finalist) {
    System.out.printf("Якубович: %s назовите 3 буквы\n"
        + "Вводите по одной букве и нажимайте Enter\n", finalist.getName());
  }

  public void speakOpenLetters() {
    System.out.println("Якубович: откройте эти буквы в слове если они есть!");
  }

  public void askWordToPlaySuperGame(Player finalist) {
    System.out.printf("Якубович: %s назовите слово!\n"
        + "Введите слово и нажмите Enter\n", finalist.getName());
  }

  public void notWinnerSuperGame(Player finalist, String superPrize) {
    System.out.println("Якубович: увы Вы не угадали слово!");
    System.out.printf("Якубович: с Поля Чудес %s увозит:\n"
            + "%s\n"
            + "Деньги в сумме %d\n",
        finalist.getName(), joinStrings(finalist.getPrizes()),
        finalist.getAmountWinningsBoxes());
    System.out.printf("Якубович: %s в Супер Игре вы могли выиграть - %s\n",
        finalist.getName(), superPrize);
  }

  public void winnerSuperGame(Player finalist, String superPrize) {
    System.out.println("Якубович: Вы стали победителем Супер Игры в капитал шоу Поле чудес!");
    System.out.printf("Якубович: с Поля Чудес %s увозит:\n"
            + "%s\n"
            + "Деньги в сумме %d\n",
        finalist.getName(), joinStrings(finalist.getPrizes()),
        finalist.getAmountWinningsBoxes());
    System.out.printf("Якубович: %s в Супер Игре Вы выиграли - %s\n"
            + "Поздравляем Вас!!!\n",
        finalist.getName(), superPrize);
  }
}
