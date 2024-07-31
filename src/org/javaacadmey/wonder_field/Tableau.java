package org.javaacadmey.wonder_field;

public class Tableau {

  private final String rightAnswer;
  private final boolean[] isOpenLettersOnTableau;

  public Tableau(String rightAnswer) {
    this.rightAnswer = rightAnswer
        .toUpperCase()
        .replaceAll("Ё", "Е")
        .replaceAll("Й", "И");
    isOpenLettersOnTableau = new boolean[rightAnswer.length()];
  }

  public void displayLettersOnTableau() {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < isOpenLettersOnTableau.length; i++) {
        if (isOpenLettersOnTableau[i]) {
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
        if (String.valueOf(rightAnswer.charAt(i)).equalsIgnoreCase(letter)) {
          isOpenLettersOnTableau[i] = true;
        }
      }
    }
  }

  public boolean isLetterAlreadyOpen(String response) {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < rightAnswer.length(); i++) {
        if (String.valueOf(rightAnswer.charAt(i)).equalsIgnoreCase(response)) {
          return isOpenLettersOnTableau[i];
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
    for (boolean isLetter : isOpenLettersOnTableau) {
      if (!isLetter) {
        return true;
      }
    }
    return false;
  }

  public boolean isNotEmptyAttributes() {
    return rightAnswer != null && isOpenLettersOnTableau != null && !rightAnswer.isBlank()
        && isOpenLettersOnTableau.length != 0;
  }

  public String getRightAnswer() {
    return rightAnswer;
  }
}
