package org.javaacadmey.wonder_field;

import org.javaacadmey.wonder_field.player.Player;
import org.javaacadmey.wonder_field.player.PlayerAnswer;

public class Yakubovich {

  public void start() {
    System.out.println(
        "Якубович: Здравствуйте, уважаемые дамы и господа! Пятница! В эфире капитал-шоу «Поле чудес»!");
  }

  public void end() {
    System.out.println("Мы прощаемся с вами ровно на одну неделю! Здоровья вам, до встречи!");
  }

  public void welcomeThreePlayers(Player[] players, int roundNumber) {
    if (roundNumber < 4) {
      System.out.printf("Якубович: приглашаю %d тройку игроков: %s\n", roundNumber,
          connectingLinesWithCommas(nameOfPlayers(players)));
    } else {
      System.out.printf("Якубович: приглашаю победителей групповых этапов: %s\n",
          connectingLinesWithCommas(nameOfPlayers(players)));
    }
  }

  public void askQuestion(String question) {
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
          System.out.println("Якубович: Нет такой буквы! Следующий игрок, крутите барабан!");
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
    return false;
  }

  public String[] nameOfPlayers(Player[] players) {
    String[] nameOfPlayers = new String[players.length];
    for (int i = 0; i < nameOfPlayers.length; i++) {
      nameOfPlayers[i] = players[i].getName();
    }
    return nameOfPlayers;
  }

  public String connectingLinesWithCommas(String[] names) {
    return String.join(", ", names);
  }
}
