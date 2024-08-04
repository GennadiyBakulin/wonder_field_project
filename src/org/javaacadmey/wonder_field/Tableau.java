package org.javaacadmey.wonder_field;

public class Tableau {

  private final String rightAnswer;
  private final boolean[] openLettersOnTableau;

  public Tableau(String rightAnswer) {
    this.rightAnswer = rightAnswer
        .toUpperCase()
        .replaceAll("Ё", "Е")
        .replaceAll("Й", "И");

    openLettersOnTableau = new boolean[rightAnswer.length()];
  }

  public void displayLettersOnTableau() {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < openLettersOnTableau.length; i++) {
        if (openLettersOnTableau[i]) {
          System.out.print(" " + rightAnswer.charAt(i));
        } else {
          System.out.print(" _");
        }
      }
      System.out.println(" ");
    }
  }

  public void openLetter(String letter) {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < rightAnswer.length(); i++) {
        if (String.valueOf(rightAnswer.charAt(i)).equals(letter)) {
          openLettersOnTableau[i] = true;
        }
      }
    }
  }

  public boolean isLetterAlreadyOpen(String response) {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < rightAnswer.length(); i++) {
        if (String.valueOf(rightAnswer.charAt(i)).equals(response)) {
          return openLettersOnTableau[i];
        }
      }
    }
    return false;
  }

  public void openFullWord() {
    for (char letter : rightAnswer.toCharArray()) {
      System.out.print(" " + letter);
    }
    System.out.println(" ");
  }

  public boolean isContainsUnknownLetters() {
    for (boolean letterOnTableau : openLettersOnTableau) {
      if (!letterOnTableau) {
        return true;
      }
    }
    return false;
  }

  public boolean isNotEmptyAttributes() {
    return rightAnswer != null && openLettersOnTableau != null && !rightAnswer.isBlank()
        && openLettersOnTableau.length != 0;
  }

  public String getRightAnswer() {
    return rightAnswer;
  }
}
