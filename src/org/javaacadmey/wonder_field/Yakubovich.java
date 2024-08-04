package org.javaacadmey.wonder_field;

import java.util.Arrays;
import org.javaacadmey.wonder_field.drum.DrumAdditionalSectors;
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
          joinToString(nameOfPlayers(players)));
    } else {
      System.out.printf("Якубович: приглашаю победителей групповых этапов: %s\n",
          joinToString(nameOfPlayers(players)));
    }
  }

  public void saysQuestion(String question) {
    System.out.printf("Якубович: Внимание вопрос!\n%s\n", question);
  }

  public boolean checkLetterAlreadyOpen(PlayerAnswer playerAnswer, Tableau tableau) {
    if (tableau.isLetterAlreadyOpen(playerAnswer.getResponse())) {
      System.out.println("Якубович: Такая буква уже открыта на табло, введите другую букву!");
      return true;
    }
    return false;
  }

  public boolean checkResponsePlayer(PlayerAnswer answerPlayer, Tableau tableau) {
    switch (answerPlayer.getTypeResponse()) {
      case LETTER -> {
        if (tableau.getRightAnswer().contains(answerPlayer.getResponse())) {
          System.out.println("Якубович: Есть такая буква, откройте ее!");
          tableau.openLetter(answerPlayer.getResponse());
          tableau.displayLettersOnTableau();
          System.out.println("__________________________________");
          return true;
        } else {
          System.out.println("Якубович: Нет такой буквы! Следующий игрок!");
        }
      }
      case WORD -> {
        if (tableau.getRightAnswer().equals(answerPlayer.getResponse())) {
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

  public void shoutIfPlayerWins(Player player, boolean isFinallyRound) {
    if (isFinallyRound) {
      System.out.printf("Якубович: И перед нами победитель Капитал шоу поле чудес! Это %s из %s\n",
          player.getName(), player.getCity());
    } else {
      System.out.printf("Якубович: Молодец! %s из %s проходит в финал!\n", player.getName(),
          player.getCity());
    }
  }

  public String[] nameOfPlayers(Player[] players) {
    String[] nameOfPlayers = new String[players.length];
    for (int i = 0; i < nameOfPlayers.length; i++) {
      nameOfPlayers[i] = players[i].getName();
    }

    return nameOfPlayers;
  }

  public String joinToString(String[] array) {
    return String.join(", ", array);
  }

  public void speakRotationDrum(String name) {
    System.out.printf("Якубович: %s вращайте барабан!\n", name);
  }

  public boolean checkSectorOnDrum(String sector) {
    if (sector.equals(DrumAdditionalSectors.SKIPPING_MOVE.toString())) {
      System.out.println("Якубович: на барабане сектор Пропуска хода! Следующий игрок!\n");
      return false;
    } else if (sector.equals(DrumAdditionalSectors.MULTIPLICATION_TWO.toString())) {
      System.out.println("Якубович: на барабане сектор умножения очков на 2!\n"
          + "Якубович: в случае успешного ответа ваши очки удвоятся! Ваш ответ!");
    } else {
      System.out.printf("Якубович: на барабане %s очков! Ваш ответ!\n", sector);
    }

    return true;
  }

  public void shoutPlayingWithBoxes(String name) {
    System.out.printf(
        "Якубович: Поздравляю %s Вы отгадали три буквы подряд!\n"
            + "Якубович: Шкатулки в студию!\n"
            + "Якубович: Выберите номер шкатулки от 1 до %d и нажмите Enter\n",
        name,
        Box.COUNT_BOX);
  }

  public boolean isCheckBox(int numberBox, Box box) {
    if (numberBox == box.getNumberMoneyBox()) {
      System.out.printf("Якубович: Поздравляю Вы верно выбрали шкатулку, "
          + "ваш выигрыш составил %d\n", box.getAmountPrize());
      return true;
    } else {
      System.out.println("Якубович: Увы эта шкатулка пустая!");
      return false;
    }
  }

  public void speakCountScore(Player finalist) {
    System.out.printf("Якубович: %s Вы набрали %d очков\n",
        finalist.getName(),
        finalist.getScore());
    System.out.println(
        "(Выберите призы на количество ваших очков: вводите номер приза и нажимайте Enter)");
  }

  public boolean offersPlaySuperGame(Player finalist) {
    String answer;

    System.out.printf("Якубович: %s хотите сыграть в Супер Игру?\n", finalist.getName());
    System.out.println("(Если согласны сыграть в Супер Игру введите \"да\" и нажмите Enter,"
        + " если не согласны введите \"нет\" и нажмите Enter.)");

    while (!((answer = Game.scanner.nextLine()).equalsIgnoreCase("да")
        || answer.equalsIgnoreCase("нет"))) {
      System.out.println("Не верный ввод, повторите заново!");
      System.out.println("Если согласны сыграть в Супер Игру введите \"да\" и нажмите Enter,"
          + " если не согласны введите \"нет\" и нажмите Enter.");
    }

    return answer.equalsIgnoreCase("да");
  }

  public void suggestsNamingThreeLetters() {
    System.out.println("Якубович: назовите три буквы! (Вводите по одной букве и нажимайте Enter)");
  }

  public void speakOpenLetters() {
    System.out.println("Якубович: откройте эти буквы в слове если они есть!");
  }

  public void askWord() {
    System.out.println("Якубович: назовите слово! (Введите слово и нажмите Enter)");
  }

  public void winnerSuperGame(Player finalist, SuperPrize superPrize) {
    System.out.println("Якубович: Вы стали победителем Супер Игры в капитал шоу Поле чудес!");
    showWinnings(finalist);
    System.out.printf("Якубович: %s в Супер Игре Вы выиграли - %s\n"
            + "Поздравляем Вас!!!\n",
        finalist.getName(), superPrize.getName());
  }

  public void notWinnerSuperGame(Player finalist, SuperPrize superPrize) {
    System.out.println("Якубович: увы Вы не угадали слово!");
    showWinnings(finalist);
    System.out.printf("Якубович: %s в Супер Игре вы могли выиграть - %s\n",
        finalist.getName(), superPrize.getName());
  }

  public void showWinnings(Player finalist) {
    String[] listGifts = new String[0];

    for (Gift gift : finalist.getGifts()) {
      listGifts = Arrays.copyOf(listGifts, listGifts.length + 1);
      listGifts[listGifts.length - 1] = gift.getName();
    }

    System.out.printf("Якубович: с Поля Чудес %s увозит:\n"
            + "Деньги в сумме %d\n"
            + "%s\n",
        finalist.getName(),
        finalist.getAmountWinningsBoxes(),
        listGifts.length > 0 ? joinToString(listGifts) : "");
  }

  public boolean checkResponseSuperGame(String word, Tableau tableau) {
    return word.equals(tableau.getRightAnswer());
  }

  public void notPlaySuperGame(Player finalist, SuperPrize superPrize) {
    System.out.println("Якубович: Это Ваше полное право!"
        + "Для нас Вы все равно победитель капитал-шоу Поле чудес!");
    showWinnings(finalist);
    System.out.printf("Якубович: В Супер Игре вы могли выиграть - %s\n", superPrize.getName());
  }
}
